package com.healthcare.integration.service;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.healthcare.model.entity.Agency;
import com.healthcare.model.entity.Caregiver;
import com.healthcare.model.entity.Company;
import com.healthcare.repository.CareGiverRepository;
import com.healthcare.service.CareGiverService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CareGiverServiceRedisTest {
    @MockBean
    private CareGiverRepository careGiverRepository;

    @Autowired
    private CareGiverService careGiverService;

    @Before
    public void setup() {
    }
    
    // Remove data added during test from redis once test case executed successfully
    public void cleanup(Long id){
    	careGiverService.deleteById(id);
    }
    
    @Test
    public void saveACareGiverToRedisAndRetrievedItFromRedis() {
        Caregiver careGiver = getCareGiver();
        careGiver.setId(100L);
        Mockito.when(careGiverRepository.save(careGiver)).thenReturn(careGiver);
        careGiverService.save(careGiver);
        Caregiver careGiverSaved = careGiverService.findById(100L);
        Assert.assertNotNull(careGiverSaved);
        
        cleanup(careGiverSaved.getId());
    }

    @Test
    public void updateACareGiverToRedis() {
    	String firstName = "first name updated";
    	String lastName = "last name updated";
    	
        Caregiver careGiver = getCareGiver();
        careGiver.setId(100L);
        Mockito.when(careGiverRepository.save(careGiver)).thenReturn(careGiver);
        careGiverService.save(careGiver);
        
        Caregiver careGiverSaved = careGiverService.findById(careGiver.getId());
        careGiverSaved.setFirstName(firstName);
        careGiverSaved.setLastName(lastName);
        
        Mockito.when(careGiverRepository.save(careGiverSaved)).thenReturn(careGiverSaved);
        careGiverService.save(careGiverSaved);

        Caregiver careGiverModified = careGiverService.findById(careGiver.getId());
        Assert.assertEquals(careGiverModified.getFirstName(), firstName);
        Assert.assertEquals(careGiverModified.getLastName(), lastName);
        cleanup(careGiverModified.getId());
    }

    @Test
    public void deleteACareGiverFromRedis() {
        Caregiver careGiver = getCareGiver();
        careGiver.setId(100L);
        Mockito.when(careGiverRepository.save(careGiver)).thenReturn(careGiver);
        careGiverService.save(careGiver);
        Mockito.doNothing().when(careGiverRepository).delete(1L);
        Assert.assertNotNull(careGiverService.deleteById(careGiver.getId()));
    }

    private Caregiver getCareGiver() {
		final Caregiver careGiver = new Caregiver();
        careGiver.setFirstName("First Name");
        careGiver.setLastName("Last Name");
        careGiver.setCareGiverType(1L);
        careGiver.setUsername("testUser");
        careGiver.setPassword("password");
        careGiver.setPhone("1234567890");
        careGiver.setCertificate("Medical Certificate");
        careGiver.setCertificateStart(Timestamp.valueOf("2007-09-23 10:10:10.0"));
        careGiver.setCertificateEnd(Timestamp.valueOf("2007-10-23 10:10:10.0"));
        careGiver.setAgency(getAgency());
        careGiver.setCompany(getCompany());
		return careGiver;
	}

	private Agency getAgency() {
		final Long agencyId = 100L;
        final Agency agency = new Agency();
        agency.setId(agencyId);
		return agency;
	}

	private Company getCompany() {
		final Long companyId = 100L;
        final Company company = new Company();
        company.setId(companyId);
		return company;
	}

}
