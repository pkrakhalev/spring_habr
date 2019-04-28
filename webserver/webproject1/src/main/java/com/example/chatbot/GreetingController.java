package com.example.chatbot;

import com.example.chatbot.domain.*;
import com.example.chatbot.repos.UserRepo;
import com.example.imggen.IdenticonGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class GreetingController {
    private final UserRepo userRepo;

    @Autowired
    public GreetingController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("showAdditionalInfo", false);
        return "login";
    }

    @PostMapping(path = "/")
    public String signIn(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam(required = false) String firstName,
                         @RequestParam(required = false) String lastName,
                         Model model) {
        System.out.println(username + " " + password);

        model.addAttribute("name", username);

        User userFromDb = userRepo.findByLogin(username);

        if (userFromDb == null) {
            System.out.println("New user");
            if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
                model.addAttribute("password", password);
                model.addAttribute("showAdditionalInfo", true);
                return "login";
            } else {
                User user = new User(username, password);
                UserInfo userinfo = new UserInfo(firstName, lastName);
                String avatar = IdenticonGenerator.generateImage(userinfo.toString());
                user.setAvatar(avatar);
                user.setUserInfo(userinfo);
                userinfo.setUser(user);
                userRepo.save(user);
                model.addAttribute("user", user);
                model.addAttribute("showAdditionalInfo", false);
                return "greeting";
            }
        } else {
            if (userFromDb.getPassword().equals(password)) {
                model.addAttribute("user", userFromDb);
                return "greeting";
            }
        }
        return "login";
    }
}