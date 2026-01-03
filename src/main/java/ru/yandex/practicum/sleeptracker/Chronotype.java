package ru.yandex.practicum.sleeptracker;

public enum Chronotype {
    LARK, OWL, PIGEON;

    @Override
    public String toString() {
        return switch (this) {
            case LARK -> "Жаворонок";
            case OWL -> "Сова";
            case PIGEON -> "Голубь";
        };
    }
}
