package com.healthcare.integration.service;

import com.healthcare.model.entity.Agency;
import com.healthcare.model.entity.Meal;
import com.healthcare.model.entity.User;
import com.healthcare.model.entity.Visit;
import com.healthcare.service.MealService;
import com.healthcare.service.VisitService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

//@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VisitServiceTest {
	@Autowired
	private MealService mealService;

	@Autowired
	private VisitService visitService;

	// @Test
	public void shouldSaveAVisit() {
		Timestamp checkInTime = new Timestamp(new Date().getTime());
		Timestamp checkOutTime = new Timestamp(new Date().getTime());
		String userComments = "all ok";
		String notes = ".";
		Visit visit = new Visit();
		Meal meal = new Meal();
		meal.setMealClass("meal class 1");
		meal.setName("meal A");
		mealService.save(meal);
//		visit.setMeal(meal);
		Agency agency = new Agency();
		User user = new User();
		visit.setUser(user);
		visit.setCheckInTime(checkInTime);
		visit.setCheckOutTime(checkOutTime);
		// visit.setVitiAgency(vitiAgency);//whats is that..transportation
		// agency?
		// visit.setSelectedActivity(selectedActivity);// enum or enum by
		// catalog
		// visit.setSelectedSeat(selectedSeat);// enum or enum by catalog
		// visit.setSelectedMeal(selectedMeal);// enum or enum by catalog
		// visit.setSignatureType(signatureType);// what is this and enum or
		// enum by catalog
		// visit.setStatus(status);// what is this and enum or enum by catalog
		// visit.setUser1(user1);// it is different from user and user2 ??
		// visit.setUser2(user2);// it is different from user and user1 ??
		// visit.setUserBarcodeId(userBarcodeId);// just a string really?
		visit.setUserComments(userComments);
		// visit.setUserSignature(userSignature); // what is this
		// visit.setUserSignatureType(userSignatureType);// what is this and
		// enum or enum by catalog
		visit.setNotes(notes);
		visitService.save(visit);
		Assert.assertNotNull(visit.getId());
	}

}
