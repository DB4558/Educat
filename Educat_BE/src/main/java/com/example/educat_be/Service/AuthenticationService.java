package com.example.educat_be.Service;

import com.example.educat_be.AuthenticationAndAuthorisation.JwtTokenProvider;
import com.example.educat_be.DAO.TokenDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private TokenDAO tokenDAO;

    @Transactional
    public String refreshAccessToken(String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            String username = jwtTokenProvider.getuserfromtoken(refreshToken);
            return jwtTokenProvider.generateAccessToken(username);
        }
        else {
            tokenDAO.deleteToken(refreshToken);
            return null;
        }
    }
}
