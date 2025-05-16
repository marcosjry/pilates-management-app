package com.user.managament.model.classroom;

import java.util.Arrays;

public enum ClassroomType {
    HIDRO("HIDRO"),
    PILATES("PILATES");

    private String type;

    ClassroomType(String type){
        this.type = type;
    }

    public static ClassroomType fromString(String value) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ClassroomType: " + value));
    }
}
