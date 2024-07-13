package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HostelDAO extends JpaRepository<Hostel,Long> {

    @Query("select h from Hostel  h where h.students.student_id=?1")
    Hostel viewHostel(String studentId);

    @Query("select h from Hostel h where h.students is null")
    List<Hostel> viewAvailableRooms();

    @Query("select h from Hostel h where h.students is not null")
    List<Hostel> viewNotAvailableRooms();
}
