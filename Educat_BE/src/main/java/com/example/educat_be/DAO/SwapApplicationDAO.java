package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Students;
import com.example.educat_be.Entity.SwapApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SwapApplicationDAO extends JpaRepository<SwapApplication,Long> {


    @Query("select s from SwapApplication  s where s.recipient.student_id=?1 and s.status='PENDING'")
    List<SwapApplication> getRequest(String recipientId);

    @Query("select s.applicant.student_id from SwapApplication s where s.recipient.student_id=?2 and s.Id=?1")
    String getApplicantId(Long applicationId,String recipientId);

    @Query("select a from SwapApplication a where a.Id=?1")
    SwapApplication findByApplicationId(Long applicationId);
}
