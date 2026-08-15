package ru.bustourism.exceptions;

public class TourNotFoundException extends RuntimeException {
    public TourNotFoundException() {
        super("Тур не найден!");
    }
}
