package com.example.educat_be.DTO;

import java.sql.Time;
import java.time.DayOfWeek;

public class CourseScheduleDTO {
    private String courseCode;
    private String name;
    private Integer credits;
    private Integer capacity;
    private Time time;
    private DayOfWeek day;
    private String room;

    public CourseScheduleDTO(String courseCode, String name, Integer credits, Integer capacity, Time time, DayOfWeek day, String room) {
        this.courseCode = courseCode;
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
        this.time = time;
        this.day = day;
        this.room = room;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
