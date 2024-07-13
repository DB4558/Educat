package com.example.educat_be.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.DayOfWeek;

@Entity
@Table(name="courseSchedule")
public class CourseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @Column(name="time",nullable = false)
    private Time time;

    @Column(name="day",nullable = false)
    private DayOfWeek day;

    @Column(name="room",nullable = false)
    private String room;

    @ManyToOne
    @JoinColumn(name = "courseId",referencedColumnName = "courseId")
    @JsonIgnore
    private Courses courses;

    public CourseSchedule() {
    }

    public CourseSchedule(Long id, Time time, DayOfWeek day, String room, Courses courses) {
        Id = id;
        this.time = time;
        this.day = day;
        this.room = room;
        this.courses = courses;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "CourseSchedule{" +
                "Id=" + Id +
                ", time=" + time +
                ", day=" + day +
                ", room='" + room + '\'' +
                ", courses=" + courses +
                '}';
    }
}
