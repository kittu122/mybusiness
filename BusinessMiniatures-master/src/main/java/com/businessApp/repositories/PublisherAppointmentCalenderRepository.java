package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.businessApp.model.AppointmentCalendar;

public interface PublisherAppointmentCalenderRepository
		extends
			MongoRepository<AppointmentCalendar, Long>
{

}