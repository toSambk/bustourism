package ru.bustourism.exceptions;

public class NotEnoughSeatsException extends RuntimeException {
    public NotEnoughSeatsException() {
        super("Недостаточно мест у тура!");
    }
}
