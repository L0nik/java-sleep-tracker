package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class AnalyticalFunctions {

    public static SleepAnalysisResult<Integer> countSessions(List<SleepingSession> sleepLog) {
        String description = "Всего сессий сна за период";
        Integer result = sleepLog.size();
        return new SleepAnalysisResult<Integer>(description, result);
    }

    public static SleepAnalysisResult<Long> findMinSession(List<SleepingSession> sleepLog) {
        String description = "Минимальная продолжительность сессии (в минутах)";
        Long result = sleepLog.stream()
                .map(SleepingSession::getSleepDurationInMinutes)
                .reduce(0L, (minSleepDuration, sleepDuration) -> {
                    return (sleepDuration < minSleepDuration || minSleepDuration == 0) ? sleepDuration : minSleepDuration;
                });
        return new SleepAnalysisResult<Long>(description, result);
    }

    public static SleepAnalysisResult<Long> findMaxSession(List<SleepingSession> sleepLog) {
        String description = "Максимальная продолжительность сессии (в минутах)";
        Long result = sleepLog.stream()
                .map(SleepingSession::getSleepDurationInMinutes)
                .reduce(0L, (minSleepDuration, sleepDuration) -> {
                    return (sleepDuration > minSleepDuration || minSleepDuration == 0) ? sleepDuration : minSleepDuration;
                });
        return new SleepAnalysisResult<Long>(description, result);
    }

    public static SleepAnalysisResult<Long> findAverageSession(List<SleepingSession> sleepLog) {
        String description = "Средняя продолжительность сессии (в минутах)";
        Long sum = sleepLog.stream()
                .map(SleepingSession::getSleepDurationInMinutes)
                .reduce(0L, Long::sum);
        Long result = sum/sleepLog.size();
        return new SleepAnalysisResult<Long>(description, result);
    }

    public static SleepAnalysisResult<Long> countBadSleepSessions(List<SleepingSession> sleepLog) {
        String description = "Количество сессий с плохим качество сна";
        Long result = sleepLog.stream()
                .filter(sleepingSession -> sleepingSession.getSleepQuality().equals(SleepQuality.BAD))
                .count();
        return new SleepAnalysisResult<Long>(description, result);
    }
}
