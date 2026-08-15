package ru.bustourism.forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationFormBean {

    @Size(min = 4, max = 32, message = "Логин должен содержать от 4 до 32 символов.")
    @Pattern(regexp = "[a-zA-Z][a-zA-Z_0-9]*", message = "Логин должен содержать латинские буквы и цифры.")
    private String login;

    @Size(min = 4, max = 32, message = "Пароль должен содержать от 4 до 32 символов.")
    @Size(min = 4, max = 32, message = "Пароль должен содержать от 4 до 32 символов.")
    @Size(min = 4, max = 32, message = "Пароль должен содержать от 4 до 32 символов.")
    private String password;

    private String passwordConfirmation;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String stringConfirmation) {
        this.passwordConfirmation = stringConfirmation;
    }

}
