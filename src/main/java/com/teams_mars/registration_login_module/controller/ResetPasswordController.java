package com.teams_mars.registration_login_module.controller;


import com.teams_mars._global_domain.User;
import com.teams_mars.customer_module.domain.ResetPassword;
import com.teams_mars.customer_module.service.UserService;
import com.teams_mars.customer_module.service.VerificationService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@SessionAttributes("changePassUser")
public class ResetPasswordController {

    UserService userService;
    VerificationService verificationService;

    @Autowired
    protected AuthenticationManager authenticationManager;


    public ResetPasswordController(UserService userService, VerificationService verificationService){
        this.userService = userService;
        this.verificationService = verificationService;
    }

    @GetMapping("/ResetPassword")
    public String resetController(){
        return "user/ResetPassword";
    }

    @SneakyThrows
    @PutMapping("/user/sendEmail/{emailAddress}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void sendEmailToUsers(@PathVariable("emailAddress") String emailAddress){
        User user = userService.findUserByEmail(emailAddress);
        if(user!=null){
            verificationService.sendEmailForPasswordVerification(user);
        }

    }

    @GetMapping("/user/changePassword")
    public String changePassword(@RequestParam("verificationNumber")String verificationNum,
                                 @RequestParam("userId") String userIdNum, @ModelAttribute("resetPass") ResetPassword resetPassword, Model model){

        if(verificationService.isVerificationCodeValid(verificationNum,userIdNum)){
            int user_Id = Integer.parseInt(userIdNum);
            User changePassUser = userService.getUserById(user_Id);

            if(verificationService.isVerificationCodeExpired(changePassUser)){
                model.addAttribute("verificationExpired","The verification code that you entered is expired. Please send another request .!!!");
                return "user/ResetPassword";
            }else{
                model.addAttribute("changePassUser",changePassUser);
                return "user/ResetPasswordInputPage";
            }
        }

        else{
            return "redirect:/error/wrongVerification";
        }


    }

    @PostMapping("/user/changePassword")
    public String changePasswordInput(@Valid @ModelAttribute("resetPass")ResetPassword resetPassword,
                                      BindingResult result,HttpServletRequest request){


        Boolean checkEquality = (resetPassword.getNewPassword().equals(resetPassword.getConfirmPassword())) ?false:true;

        if(checkEquality){
            result.rejectValue("confirmPassword","error.resetPass","Password does not match...");
            return "user/ResetPasswordInputPage";
        }
        User user = userService.getUserById(resetPassword.getUserId());
        if (user == null){
            result.rejectValue("confirmPassword","error.resetPass", "No user found to change Password");
            return "user/ResetPasswordInputPage";
        }
        userService.changePassword(user,resetPassword.getNewPassword());

        authWithAuthManager(request,user.getEmail(),resetPassword.getNewPassword());
        return "redirect:/";
    }
    public void authWithAuthManager(HttpServletRequest request, String username, String password) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
