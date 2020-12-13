package com.teams_mars.registration_login_module.controller;

import com.teams_mars._global_domain.Role;
import com.teams_mars._global_domain.User;
import com.teams_mars.customer_module.service.RoleService;
import com.teams_mars.customer_module.service.UserService;
import com.teams_mars.customer_module.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RegistrationController {

    UserService userService;
    RoleService roleService;
    VerificationService verificationService;

    @Autowired
    protected AuthenticationManager authenticationManager;


    RegistrationController(UserService userService, RoleService roleService, VerificationService verificationService) {
        this.roleService = roleService;
        this.userService = userService;
        this.verificationService = verificationService;
    }


    @ModelAttribute("allTheRoles")
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @GetMapping({"/register"})
    public String register(@ModelAttribute("user") User user) {
        return "user/registrationForm";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result,
                           RedirectAttributes redirectAttributes) {

        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null)
            result.rejectValue("email", "error.user", "There is already a user with this email address..");
        if (result.hasErrors()) {
            return "user/registrationForm";
        } else {
            userService.save(user);
            verificationService.generateCreateAndEmailVerification(user.getUserId());
            redirectAttributes.addFlashAttribute("unverifiedUser", user);
            int userId = user.getUserId();
            return "redirect:/registerSuccess?userId=" + userId;
        }
    }

    @GetMapping("/registerSuccess")
    public String success(@RequestParam("userId") int userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("unverifiedUser", user);
        return "user/verificationPage";
    }

    @GetMapping("/verifyFromEmail")
    public String verifyFromEmail(@RequestParam("id") String userId, Model model) {
        User user = userService.getUserById(Integer.parseInt(userId));
        model.addAttribute("unverifiedUser", user);
        return "user/verificationPage";
    }


    @GetMapping("/verifyAccount")
    public String verify(@RequestParam("verificationNumber") String verificationNumber,
                         @RequestParam("userId") String userId, Model model) {

        Boolean doesExist = verificationService.verifyCode(verificationNumber, userId);
        User user = userService.getUserById(Integer.parseInt(userId));
        int countTries= verificationService.getVerificationUsingUser(Integer.parseInt(userId)).getCountTries();

        if (doesExist) {
            if (verificationService.isVerificationCodeExpired(user)) {
                model.addAttribute("unverifiedUser", user);
                model.addAttribute("message", "Your Verification Code has expired. Get a new Verification Code");
                return "user/verificationPage";
            }
            verificationService.updateVerificationCount(0, user);

            String authority =  user.getUserRole().getRole();
            autWithAuthManager(user.getEmail(),user.getPassword(),authority);
            return "redirect:/";
        } else {
            countTries++;
            verificationService.updateVerificationCount(countTries, user);
            if (countTries == 3) {
                countTries = 0;
                verificationService.updateVerificationCount(countTries, user);
                return "redirect:/login";
            }
            String message = "Wrong verification code..." + (3 - countTries) + " left. Please try Again !!!";
            model.addAttribute("unverifiedUser", user);
            model.addAttribute("message", message);
            return "user/verificationPage";
        }


    }

    @PutMapping("/resendVerificationCode/{user_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void sendVerificationCode(@PathVariable String user_id) {

        int userId = Integer.parseInt(user_id);
        verificationService.generateCreateAndEmailVerification(userId);

    }

    public void autWithAuthManager(String username,String password,String authority){
        Authentication authentication = new UsernamePasswordAuthenticationToken(username,password, AuthorityUtils.createAuthorityList(authority));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}





