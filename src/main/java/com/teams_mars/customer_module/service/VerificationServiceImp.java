package com.teams_mars.customer_module.service;


import com.teams_mars._global_domain.User;
import com.teams_mars.customer_module.domain.Verification;
import com.teams_mars.customer_module.repository.UserRepository;
import com.teams_mars.customer_module.repository.VerificationRepository;
import lombok.SneakyThrows;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class VerificationServiceImp implements VerificationService {

    Random random;
    VerificationRepository verificationRepository;
    UserService userService;

    public VerificationServiceImp(VerificationRepository verificationRepository,
                                  UserRepository userRepository, UserService userService) {
        this.verificationRepository = verificationRepository;
        this.userService = userService;
    }


    @Override
    public void generateCreateAndEmailVerification(int userId) {

        //Generating 5-digit Verification Code
        random = new Random();
        String code = Integer.toString(random.nextInt(100000));

        Verification verification = new Verification();

        User user = userService.getUserById(userId);

        //Setting Verification
        verification.setVerificationCode(code);
        verification.setUser(user);
        verification.setTimeIssued(LocalDateTime.now());

        //saving to DB
        update(verification, user);

        String content = "Hi " + user.getFirstName() + "," +
                "We just need to verify your email address before you can access Auction Services." +
                " Click link to verify your email address http://localhost:8888/verifyFromEmail?id=" + user.getUserId() +

                "                      <p>Verification Code: " + verification.getVerificationCode() +
                " Thanks! – The Auction Service team";

        System.out.println(content);
        //sendEmailToUser(user,content);

    }

    @Override
    public Verification getVerificationUsingUser(int userid) {
        User user = userService.getUserById(userid);
        if (null != user)
            return verificationRepository.findVerificationByUser(user);
        return null;
    }

    @Override
    public void updateVerificationCount(int countTries, User user) {
        Verification verification = getVerificationUsingUser(user.getUserId());
        verification.setCountTries(countTries);
        update(verification, user);
    }

    @Override
    public void update(Verification verification, User user) {
        Verification verificationOne = verificationRepository.findVerificationByUser(user);
        if (null == verificationOne)
            verificationRepository.save(verification);
        else {
            verificationOne.setVerificationCode(verification.getVerificationCode());
            verificationOne.setTimeIssued(verification.getTimeIssued());
            verificationRepository.save(verificationOne);
        }
    }

    @Override
    public Boolean verifyCode(String verificationNumber, String userId) {

        User user = userService.getUserById(Integer.parseInt(userId));
        if (null != user) {
            Verification verification = verificationRepository.findVerificationByUser(user);
            if (!verification.getVerificationCode().equals(verificationNumber)) {
                return false;
            } else {
                user.setVerificationCodeVerified(true);
                userService.save(user);
                return true;
            }
        }
        return false;

    }

    @Override
    @SneakyThrows
    public void sendEmailForPasswordVerification(User user) throws IOException {

        random = new Random();
        String code = Integer.toString(random.nextInt());

        Verification verification = new Verification();

        //Setting Verification
        verification.setVerificationCode(code);
        verification.setUser(user);
        verification.setTimeIssued(LocalDateTime.now());

        //saving to DB
        update(verification, user);

        //creating the content for the email
        String content2 = "Hi " + user.getFirstName() + "," +
                "This is to change your password. We just need to verify your email address before you can access Auction Services." +
                " Click link to verify your email address http://localhost:8888/user/changePassword?verificationNumber=" + code + "&userId=" + user.getUserId() +


                " Thanks! – The Auction Service team";

        System.out.println(content2);
        //sending email to the user
        //sendEmailToUser(user,content2);

    }

    @Override
    public Boolean isVerificationCodeExpired(User user) {
        Verification verification = verificationRepository.findVerificationByUser(user);
        LocalDateTime expirationTime = verification.getTimeIssued().plusSeconds(50);
        if (LocalDateTime.now().isAfter(expirationTime)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isVerificationCodeValid(String verificationNumber, String userId) {
        // Check if the verificationNumber is valid and the userId is Present
        User user = userService.getUserById(Integer.parseInt(userId));
        if (verificationNumber != null && user != null)
            return true;
        return false;

    }

    //method Emails data to given email address
    @SneakyThrows
    public void sendEmailToUser(User user, String content) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{  \"personalizations\": [    {      \"to\": [        {          \"email\": \"" + user.getEmail() + "\"        }" +
                "      ],      \"subject\": \"Auction Account Verification\"    }  ],  \"from\": {    \"email\": \"auction@auction.com\"  },  \"content\": [    {      \"type\": \"text/plain\"," +
                "      \"value\": \"" + content + "\"    }  ]}");
        Request request = new Request.Builder()
                .url("https://rapidprod-sendgrid-v1.p.rapidapi.com/mail/send")
                .post(body)
                .addHeader("x-rapidapi-host", "rapidprod-sendgrid-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "15d56e6f8cmshb76c8c384c74d28p1bd429jsnf78f7eb7e2a1")
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response);
    }
}