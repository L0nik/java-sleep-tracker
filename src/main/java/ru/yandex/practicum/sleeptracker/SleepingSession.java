package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {

    private final LocalDateTime startOfSession;
    private final LocalDateTime endOfSession;
    private final SleepQuality sleepQuality;
    private final Duration sleepDuration;

    public SleepingSession(LocalDateTime startOfSession, LocalDateTime endOfSession, SleepQuality sleepQuality) {
        this.startOfSession = startOfSession;
        this.endOfSession = endOfSession;
        this.sleepQuality = sleepQuality;
        this.sleepDuration = Duration.between(this.startOfSession, this.endOfSession);
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s", startOfSession, endOfSession, sleepQuality);
    }

    public LocalDateTime getStartOfSession() {
        return startOfSession;
    }

    public LocalDateTime getEndOfSession() {
        return endOfSession;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    public Long getSleepDurationInMinutes() {
        return this.sleepDuration.toMinutes();
    }
}
