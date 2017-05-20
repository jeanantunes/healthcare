package com.healthcare.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.healthcare.model.entity.Agency;
import com.healthcare.repository.AgencyRepository;
import com.healthcare.service.AgencyService;

@Service
@Transactional
public class AgencyServiceImpl implements AgencyService {
	@Autowired
	AgencyRepository agencyRepository;

	@Autowired
	private RedisTemplate<String, Agency> agencyRedisTemplate;
	
	private static String AGENCY_KEY = "Agency";

	@Override
	public Agency save(Agency agency) {
		agency = agencyRepository.save(agency);
		agencyRedisTemplate.opsForHash().put(AGENCY_KEY, agency.getId(), agency);
		return agency;
	}

	@Override
	public void deleteById(Long id) {
		agencyRepository.delete(id);
		agencyRedisTemplate.opsForHash().delete(AGENCY_KEY, id);
	}

	@Override
	public Agency findById(Long id) {
		Agency agency = (Agency) agencyRedisTemplate.opsForHash().get(AGENCY_KEY, id);
		if (agency == null)
			agency = agencyRepository.findOne(id);
		return agency;
	}
}
