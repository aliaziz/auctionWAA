package com.teams_mars.customer_module.service;

import com.teams_mars._global_domain.User;
import com.teams_mars.customer_module.domain.Verification;

import java.io.IOException;

public interface VerificationService {

    void generateCreateAndEmailVerification(int userId);

    Verification getVerificationUsingUser(int userId);

    void updateVerificationCount(int countTries, User user);

    void update(Verification verification,User user);

    Boolean verifyCode(String verificationNumber,String userId);

    void sendEmailForPasswordVerification(User user) throws IOException;

    Boolean isVerificationCodeExpired(User user);

    Boolean isVerificationCodeValid(String verificationNumber,String userId);



}
