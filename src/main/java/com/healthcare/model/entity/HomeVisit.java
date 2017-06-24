package com.healthcare.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "home_visit")
@EqualsAndHashCode(callSuper = true)
public @Data class HomeVisit extends Audit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "check_in_time")
	private Timestamp checkInTime;
	@Column(name = "check_out_time")
	private Timestamp checkOutTime;
	@ManyToOne
	@JoinColumn(name = "meal_id")
	private Meal meal;
	@Column(name = "carereceiver_signature")
	private String carereceiverSignature;
	@Column(name = "carereceiver_comments")
	private String carereceiverComments;
	private String notes;
	private String status;
	@ManyToOne
	@JoinColumn(name = "serviceplan_id")
	private ServicePlan servicePlan;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "caregiver_id")
	private Caregiver caregiver;
}
