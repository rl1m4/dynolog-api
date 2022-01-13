package com.github.throyer.appointments.domain.timeentry.service;

import com.github.throyer.appointments.domain.timeentry.dto.CreateTimeEntryData;
import com.github.throyer.appointments.domain.timeentry.dto.TimeEntryDetails;
import com.github.throyer.appointments.domain.timeentry.entity.TimeEntry;
import com.github.throyer.appointments.domain.timeentry.repository.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTimeEntryService {
    
    @Autowired
    private TimeEntryRepository repository;
    
    public TimeEntryDetails create(CreateTimeEntryData data) {
        var created = repository.save(new TimeEntry(data));
        return new TimeEntryDetails(created);
    }
}