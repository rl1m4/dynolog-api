package com.github.appointmentsio.api.domain.timeentry.service;

import com.github.appointmentsio.api.domain.project.repository.ProjectRepository;
import com.github.appointmentsio.api.domain.session.service.SessionService;
import com.github.appointmentsio.api.domain.timeentry.entity.TimeEntry;
import com.github.appointmentsio.api.domain.timeentry.form.CreateTimeEntryProps;
import com.github.appointmentsio.api.domain.timeentry.model.TimeEntryInfo;
import com.github.appointmentsio.api.domain.timeentry.repository.TimeEntryRepository;
import com.github.appointmentsio.api.domain.user.repository.UserRepository;
import com.github.appointmentsio.api.errors.Error;
import com.github.appointmentsio.api.errors.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import static com.github.appointmentsio.api.domain.session.service.SessionService.authorizedOrThrow;
import static com.github.appointmentsio.api.utils.Constraints.MESSAGES.NOT_AUTHORIZED_TO_CREATE;
import static com.github.appointmentsio.api.utils.Constraints.MESSAGES.TIMEENTRY_DATE_INTERVAL_INVALID;
import static com.github.appointmentsio.api.utils.Messages.message;
import static com.github.appointmentsio.api.utils.Response.notFound;
import static com.github.appointmentsio.api.utils.Response.unauthorized;

@Service
public class CreateTimeEntryService {
    private final TimeEntryRepository timeEntryRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public CreateTimeEntryService(
            TimeEntryRepository timeEntryRepository,
            UserRepository userRepository,
            ProjectRepository projectRepository
    ) {
        this.timeEntryRepository = timeEntryRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public TimeEntryInfo create(CreateTimeEntryProps props) {

        var exception = new BadRequestException();

        var start = props.getStart();
        var stop = props.getStop();

        if (start.isAfter(stop) || stop.isBefore(start)) {
            exception.add(new Error("start or stop", message(TIMEENTRY_DATE_INTERVAL_INVALID)));
        }

        if (exception.hasError()) {
            throw exception;
        }

        var userId = userRepository.findOptionalIdByNanoid(props.getUserId())
                .orElseThrow(() -> notFound("User not found"));

        var authorized = authorizedOrThrow();

        if (!authorized.canModify(userId)) {
            throw unauthorized(message(NOT_AUTHORIZED_TO_CREATE, "'time entries'"));
        }

        var projectId = props.getProjectId()
                .flatMap(projectRepository::findOptionalIdByNanoid);


        var id = timeEntryRepository.save(new TimeEntry(props, userId, projectId)).getId();

        var timeEntry = timeEntryRepository.findByIdFetchUserAndProject(id);

        return new TimeEntryInfo(timeEntry);
    }
}
