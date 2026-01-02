package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AnalyticalFunctions {

    public static SleepAnalysisResult<Integer> countSessions(List<SleepingSession> sleepLog) {
        String description = "Всего сессий сна за период";
        Integer result = sleepLog.size();
        return new SleepAnalysisResult<>(description, result);
    }

    public static SleepAnalysisResult<Long> findMinSession(List<SleepingSession> sleepLog) {
        String description = "Минимальная продолжительность сессии (в минутах)";
        Long result = sleepLog.stream()
                .map(SleepingSession::getSleepDurationInMinutes)
                .reduce(0L, (minSleepDuration, sleepDuration) -> {
                    return (sleepDuration < minSleepDuration || minSleepDuration == 0) ? sleepDuration : minSleepDuration;
                });
        return new SleepAnalysisResult<>(description, result);
    }

    public static SleepAnalysisResult<Long> findMaxSession(List<SleepingSession> sleepLog) {
        String description = "Максимальная продолжительность сессии (в минутах)";
        Long result = sleepLog.stream()
                .map(SleepingSession::getSleepDurationInMinutes)
                .reduce(0L, (minSleepDuration, sleepDuration) -> {
                    return (sleepDuration > minSleepDuration || minSleepDuration == 0) ? sleepDuration : minSleepDuration;
                });
        return new SleepAnalysisResult<>(description, result);
    }

    public static SleepAnalysisResult<Long> findAverageSession(List<SleepingSession> sleepLog) {
        String description = "Средняя продолжительность сессии (в минутах)";
        Long sum = sleepLog.stream()
                .map(SleepingSession::getSleepDurationInMinutes)
                .reduce(0L, Long::sum);
        Long result = sum/sleepLog.size();
        return new SleepAnalysisResult<>(description, result);
    }

    public static SleepAnalysisResult<Long> countBadSleepSessions(List<SleepingSession> sleepLog) {
        String description = "Количество сессий с плохим качество сна";
        Long result = sleepLog.stream()
                .filter(sleepingSession -> sleepingSession.getSleepQuality().equals(SleepQuality.BAD))
                .count();
        return new SleepAnalysisResult<>(description, result);
    }

    public static SleepAnalysisResult<Integer> countNightsWithoutSleep(List<SleepingSession> sleepLog) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String description = "Количество бессонных ночей за период с " +
                sleepLog.getFirst().getStartOfSession().format(formatter) +
                " по " +
                sleepLog.getLast().getEndOfSession().format(formatter);
        int countAllNights = Period.between(
                sleepLog.getFirst().getStartOfSession().toLocalDate(),
                sleepLog.getLast().getEndOfSession().toLocalDate()
        ).getDays();
        System.out.println(countAllNights);
        int countNightsSlept = sleepLog.stream()
                .reduce(
                        0,
                        (count, sleepingSession) -> {
                            LocalDateTime start = sleepingSession.getStartOfSession();
                            LocalDateTime end = sleepingSession.getEndOfSession();
                            if (start.getDayOfMonth() == end.getDayOfMonth())
                                return start.getHour() <= 6 ? count + 1 : count;
                            else
                                return count + 1;
                        },
                        Integer::sum
                );
        System.out.println(countNightsSlept);
        int result = countAllNights - countNightsSlept;

        return new SleepAnalysisResult<>(description, result);
    }
}
