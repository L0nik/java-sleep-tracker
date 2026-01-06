package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class FindMaxSessionFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepLog) {
        String description = "Максимальная продолжительность сессии (в минутах)";
        Long result = sleepLog.stream()
                .map(SleepingSession::getSleepDurationInMinutes)
                .reduce(0L, (minSleepDuration, sleepDuration) -> {
                    return (sleepDuration > minSleepDuration || minSleepDuration == 0) ? sleepDuration : minSleepDuration;
                });
        return new SleepAnalysisResult<>(description, result);
    }
}
