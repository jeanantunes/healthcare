package com.healthcare.api;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.model.entity.ServicePlan;
import com.healthcare.service.ServicePlanService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/serviceplan")
public class ServicePlanController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServicePlanService servicePlanService;

	@ApiOperation(value = "save service plan", notes = "save service plan")
	@ApiParam(name = "servicePlan", value = "service plan to save", required = true)
	@PostMapping()
	public ResponseEntity<ServicePlan> create(@RequestBody ServicePlan servicePlan) {
		servicePlan = servicePlanService.save(servicePlan);
		return new ResponseEntity<ServicePlan>(servicePlan, HttpStatus.OK);
	}

	@ApiOperation(value = "get service plan by id", notes = "get service plan by id")
	@ApiImplicitParam(name = "id", value = "service plan id", required = true, dataType = "Long")
	@GetMapping("/{id}")
	public ServicePlan read(@PathVariable Long id) {
		logger.info("id : " + id);
		return servicePlanService.findById(id);
	}

	@ApiOperation(value = "get all service plans", notes = "get all service plans")
	@GetMapping()
	public List<ServicePlan> readAll() {
		return servicePlanService.findAll();
	}
	
	@ApiOperation(value = "get service calendar by servce plan id", notes = "get service calendar by service plan id")
	@ApiImplicitParam(name = "servicePlanId", value = "service plan id", required = true, dataType = "Long")
	@GetMapping("/calendar/{servicePlanId}")
	public List<Date> getServiceCalendar(@PathVariable Long servicePlanId) {
		logger.info("id : " + servicePlanId);
		return servicePlanService.getServiceCalendar(servicePlanId);
	}

	@ApiOperation(value = "update service plan", notes = "update service plan")
	@ApiParam(name = "servicePlan", value = "service plan to update", required = true)
	@PutMapping()
	public ResponseEntity<ServicePlan> update(@RequestBody ServicePlan servicePlan) {
		servicePlan = servicePlanService.save(servicePlan);
		return new ResponseEntity<ServicePlan>(servicePlan, HttpStatus.OK);
	}

	@ApiOperation(value = "delete service plan", notes = "delete service plan")
	@ApiImplicitParam(name = "id", value = "service plan id", required = true, dataType = "Long")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		logger.info("id : " + id);
		servicePlanService.deleteById(id);
	}
}
