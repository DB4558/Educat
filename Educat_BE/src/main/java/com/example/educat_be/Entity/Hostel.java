package com.example.educat_be.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="hostel")
public class Hostel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hostelId")
    private Long hostelId;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "room",nullable = false)
    private String room;

    @Column(name = "floor",nullable = false)
    private String floor;

    @OneToOne
    @JoinColumn(name = "studentId",referencedColumnName = "student_id")
    private Students students;

    public Hostel() {
    }

    public Hostel(Long hostelId, String name, String room, String floor, Students students) {
        this.hostelId = hostelId;
        this.name = name;
        this.room = room;
        this.floor = floor;
        this.students = students;
    }

    public Long getHostelId() {
        return hostelId;
    }

    public void setHostelId(Long hostelId) {
        this.hostelId = hostelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Hostel{" +
                "hostelId=" + hostelId +
                ", name='" + name + '\'' +
                ", room='" + room + '\'' +
                ", floor='" + floor + '\'' +
                ", students=" + students +
                '}';
    }
}
