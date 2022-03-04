package com.github.appointmentsio.api.domain.timeentry.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.appointmentsio.api.domain.timeentry.entity.TimeEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.github.appointmentsio.api.utils.Constants.PATTERNS.DATE_ISO_WITH_TIMEZONE;

@Getter
public class TimeEntrySummary {

    @Schema(description = "nano id", example = "V1StGXR8_Z5jdHi6B-myT", required = true)
    private final String id;

    @Schema(example = "Refatoração no back-end")
    private final String description;

    @Schema(example = "2022-02-14T00:32:00.000Z", required = true)
    @JsonFormat(timezone = "UTC", pattern = DATE_ISO_WITH_TIMEZONE)
    private final LocalDateTime start;

    @Schema(example = "2022-02-14T00:48:00.000Z", required = true)
    @JsonFormat(timezone = "UTC", pattern = DATE_ISO_WITH_TIMEZONE)
    private final LocalDateTime stop;

    public TimeEntrySummary(TimeEntry timeEntry) {
        this.id = timeEntry.getNanoId();
        this.description = timeEntry.getDescription();
        this.start = timeEntry.getStart();
        this.stop = timeEntry.getStop();
    }
}
