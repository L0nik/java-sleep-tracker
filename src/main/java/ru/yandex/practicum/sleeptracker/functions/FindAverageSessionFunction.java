package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class FindAverageSessionFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepLog) {
        String description = "Средняя продолжительность сессии (в минутах)";
        if (sleepLog.isEmpty())
            return new SleepAnalysisResult<>(description, 0L);
        Long sum = sleepLog.stream()
                .map(SleepingSession::getSleepDurationInMinutes)
                .reduce(0L, Long::sum);
        Long result = sum / sleepLog.size();
        return new SleepAnalysisResult<>(description, result);
    }
}
