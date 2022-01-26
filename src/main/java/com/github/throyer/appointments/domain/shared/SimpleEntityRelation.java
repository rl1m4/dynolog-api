package com.github.throyer.appointments.domain.shared;

public record SimpleEntityRelation(Long id, String name) implements Identity {
    @Override
    public Long getId() {
        return this.id;
    }
}
