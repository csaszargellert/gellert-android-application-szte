package com.app.nailappointment.dto;

import com.app.nailappointment.db.DateEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserDTO {

    private String id;

    private String email;

    private String username;

    private List<DateEntity> bookingSlots = new ArrayList<>();

    public UserDTO() {}

    public UserDTO(String id, String email, String username) {
            this.id = id;
            this.email = email;
            this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public List<DateEntity> getBookingSlots() {
        return bookingSlots;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addBookingSlots(List<DateEntity> dates) {
        if (Objects.isNull(dates)) {
            return;
        }
        dates.forEach(this::addBookingSlot);
    }

    public void addBookingSlot(DateEntity date) {
        if (Objects.isNull(date)) {
            return;
        }
        this.bookingSlots.add(date);
    }

    public void clearBookingSlots() {
        if (Objects.isNull(bookingSlots)) {
            return;
        }
        this.bookingSlots.clear();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> user = new HashMap<>();
        user.put("id", getId());
        user.put("email", getEmail());
        user.put("username", getUsername());
        return user;
    }
}
