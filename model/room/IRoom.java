package model.room;

import model.room.enums.RoomType;

/*
 * Copyright (c) 2023. Taras Bahnyuk
 * All rights reserved.
 */
public interface IRoom {
    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public boolean isFree();
}
