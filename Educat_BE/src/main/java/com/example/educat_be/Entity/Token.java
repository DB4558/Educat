package com.example.educat_be.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "email")
    private String email;

    @Column(name = "access_token")
    private String accesstoken;

    @Column(name = "refresh_token")
    private String refreshtoken;

    @Column(name = "ipAddress")
    private String ipAddress;

    public Token() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Token(Long id, String email, String accesstoken, String refreshtoken, String ipAddress) {
        Id = id;
        this.email = email;
        this.accesstoken = accesstoken;
        this.refreshtoken = refreshtoken;
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "Token{" +
                "Id=" + Id +
                ", email='" + email + '\'' +
                ", accesstoken='" + accesstoken + '\'' +
                ", refreshtoken='" + refreshtoken + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
