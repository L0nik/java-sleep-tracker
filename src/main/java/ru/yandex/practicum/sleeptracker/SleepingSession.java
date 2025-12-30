package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {

    private final LocalDateTime startOfSession;
    private final LocalDateTime endOfSession;
    private final SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime startOfSession, LocalDateTime endOfSession, SleepQuality sleepQuality) {
        this.startOfSession = startOfSession;
        this.endOfSession = endOfSession;
        this.sleepQuality = sleepQuality;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s", startOfSession, endOfSession, sleepQuality);
    }
}
