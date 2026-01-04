package ru.yandex.practicum.sleeptracker;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SleepAnalysisResult<?> that = (SleepAnalysisResult<?>) o;
        return Objects.equals(description, that.description) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, result);
    }
}
