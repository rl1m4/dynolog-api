package com.github.appointmentsio.api.domain.user.model;

import com.github.appointmentsio.api.domain.shared.model.Addressable;
import com.github.appointmentsio.api.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(name = "User", requiredProperties = {"id", "name", "email", "roles"})
public class SimplifiedUser implements Addressable {
    private final String id;
    private final String name;
    private final String email;

    private final String timezone;
    private final String dateFormat;
    private final String timeFormat;

    private final List<String> roles;

    @Override
    public String getEmail() {
        return this.email;
    }

    public SimplifiedUser(User user) {
        this.id = user.getNanoId();
        this.name = user.getName();
        this.email = user.getEmail();

        this.timezone = user.getTimezone();
        this.dateFormat = user.getDateFormat();
        this.timeFormat = user.getTimeFormat();

        if (user.getRoles().isEmpty()) {
            this.roles = List.of();
        } else {
            this.roles = user.getRoles();
        }
    }
}