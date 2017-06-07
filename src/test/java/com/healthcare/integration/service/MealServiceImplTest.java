package com.healthcare.integration.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.healthcare.DbUnitIntegrationTestConfiguration;
import com.healthcare.model.entity.Meal;
import com.healthcare.model.entity.Visit;
import com.healthcare.service.MealService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@TestExecutionListeners(
        value = {DbUnitTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@DatabaseSetup(value = "/dataset/service/MealServiceImplIntegrationTest.xml")
@ContextConfiguration(classes = {DbUnitIntegrationTestConfiguration.class})
@Transactional
@SpringBootTest
public class MealServiceImplTest {

    @Autowired
    private MealService sut;

    @Autowired
    private EntityManager em;

    @Test
    public void testCreate() {
        // given
        final Long visitId = 100L;
        Meal meal = new Meal();
        meal.setMealClass("Class");
        meal.setName("Name");

        Visit visit = new Visit();
        visit.setId(visitId);
        meal.setVisit(visit);
        // when
        Meal result = sut.save(meal);
        // then
        assertThat(result, notNullValue());
        assertThat(result.getId(), notNullValue());
    }

    @Test
    @ExpectedDatabase(
            value = "/dataset/service/MealServiceImplIntegrationTest.testUpdate.expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT
    )
    public void testUpdate() {
        // given
        final Long visitId = 100L;
        final Long mealId = 100L;
        Meal meal = new Meal();
        meal.setId(mealId);
        meal.setMealClass("Class 1");
        meal.setName("Name 1");

        Visit visit = new Visit();
        visit.setId(visitId);
        meal.setVisit(visit);
        // when
        Meal result = sut.save(meal);
        // then
        assertThat(result, notNullValue());

        em.flush();
    }

    @Test
    public void testFindById() {
        // given
        final Long mealId = 100L;
        // when
        Meal result = sut.findById(mealId);
        // then
        assertThat(result, notNullValue());
        assertThat(result.getId(), equalTo(mealId));
    }

    @Test
    @ExpectedDatabase(
            value = "/dataset/service/MealServiceImplIntegrationTest.testDeleteById.expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT
    )
    public void testDeleteById() {
        // given
        final Long mealId = 100L;
        // when
        sut.deleteById(mealId);
        // then
        em.flush();
    }
}