package ru.bustourism.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.User;
import ru.bustourism.forms.RegistrationFormBean;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

   // @PostMapping(path = "/login")
    @Deprecated
    public String login(HttpSession session, @RequestParam String login, @RequestParam String password, ModelMap model) {
        try {
            User found = usersRepository.findByLoginAndEncryptedPassword(login, password);
            session.setAttribute("userId", found.getId());
            return "redirect:/dashboard";
        } catch(NoResultException | NullPointerException notFound) {
            logger.warn("Пользователь не найден", notFound);
            model.addAttribute("login", login);
            return "mainPage";
        }
    }

    @GetMapping(path="/register")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping(path = "/register")
    public String registration(@Validated @ModelAttribute("form") RegistrationFormBean form, BindingResult result) {

        if (form.getPasswordConfirmation() == null || !form.getPassword().equals(form.getPasswordConfirmation())) {
            result.addError(new FieldError("form", "passwordConfirmation", "Поля с подтверждением пароля не совпадают"));
        }
        try {
            usersRepository.save(new User(form.getLogin(), encoder.encode(form.getPassword()), false));
        } catch(Exception e) {
            logger.warn("Пользователь с таким логином уже существует", e);
            result.addError(new FieldError("form", "login", "Пользователь с таким логином уже существует"));
        }

        if(result.hasErrors()) {
            return "registration";
        }

        return "redirect:/";

    }

    @ModelAttribute("form")
    public RegistrationFormBean newFormBean() {
        RegistrationFormBean bean = new RegistrationFormBean();
        bean.setLogin("");
        bean.setPassword("");
        bean.setPasswordConfirmation("");
        return bean;
    }





}
