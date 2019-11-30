package ru.bustourism.exceptions;

public class SeatNotFoundException extends RuntimeException {

    public SeatNotFoundException() {
        super("Данный пользователь не бронировал данный тур");
    }

}
