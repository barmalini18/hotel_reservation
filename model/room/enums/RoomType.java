package model.room.enums;

/*
 * Copyright (c) 2023. Taras Bahnyuk
 * All rights reserved.
 */
public enum RoomType {
    SINGLE("1"),
    DOUBLE("2"),

    SEAVIEW("3"),

    MOUNTAINVIEW("4");

    public final String label;

    RoomType(String label) {
        this.label = label;
    }

    public static RoomType valueOfLabel(String label) {
        for (RoomType roomType : values()) {
            if (roomType.label.equals(label)) {
                return roomType;
            }
        }
        throw new IllegalArgumentException();
    }
}
