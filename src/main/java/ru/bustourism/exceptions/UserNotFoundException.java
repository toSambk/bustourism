package ru.bustourism.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Пользователь не найден!");
    }
}
