package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult<T> {
    private final String description;
    private final T result;

    public SleepAnalysisResult(String description, T result) {
        this.description = description;
        this.result = result;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", description, result);
    }
}
