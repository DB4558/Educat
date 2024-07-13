package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TokenDAO extends JpaRepository<Token,Long> {

    @Modifying
    @Query("delete from Token t where t.email=?1")
    void removeToken(String email);


    @Modifying
    @Query("update Token t set t.accesstoken=?1 where t.refreshtoken=?2")
    void updateAccessToken(String accesstoken,String refreshtoken);


    @Query("select t.accesstoken from Token t where t.email=?1")
    String findByUsername(String username);
    @Query("select t.email from Token t where t.accesstoken=?1")
    String findUserByToken(String token);


    @Query("select t.ipAddress from Token t where t.email=?1")
    String findipAdress(String username);
    @Modifying
    @Transactional
    @Query("update Token t set t.ipAddress=?2 where t.email=?1")
    void saveip(String username,String ipaddress);


    @Modifying
    @Transactional
    @Query("delete from Token  t where t.refreshtoken=?1")
    void deleteToken(String token);

}
