package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class CountBadSleepSessionsFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepLog) {
        String description = "Количество сессий с плохим качество сна";
        Long result = sleepLog.stream()
                .filter(sleepingSession -> sleepingSession.getSleepQuality().equals(SleepQuality.BAD))
                .count();
        return new SleepAnalysisResult<>(description, result);
    }
}
