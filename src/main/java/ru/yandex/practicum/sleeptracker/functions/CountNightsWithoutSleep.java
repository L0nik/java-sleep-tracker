package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.EmptySleepLogException;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class CountNightsWithoutSleep implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepLog) throws EmptySleepLogException {
        if (sleepLog.isEmpty()) {
            throw new EmptySleepLogException("Лог сна не должен быть пустым!");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String description = "Количество бессонных ночей за период с " +
                sleepLog.getFirst().getStartOfSession().format(formatter) +
                " по " +
                sleepLog.getLast().getEndOfSession().format(formatter);
        int countAllNights = Period.between(
                sleepLog.getFirst().getStartOfSession().toLocalDate(),
                sleepLog.getLast().getEndOfSession().toLocalDate()
        ).getDays();
        if (sleepLog.getFirst().getStartOfSession().getHour() < 12) {
            countAllNights += 1;
        }
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
        int result = countAllNights - countNightsSlept;

        return new SleepAnalysisResult<>(description, result);
    }
}
