package ru.yandex.practicum.sleeptracker;

public class EmptySleepLogException extends RuntimeException {
    public EmptySleepLogException(String message) {
        super(message);
    }
}
