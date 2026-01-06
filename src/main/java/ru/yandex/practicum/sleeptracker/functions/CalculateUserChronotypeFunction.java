package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.Chronotype;
import ru.yandex.practicum.sleeptracker.EmptySleepLogException;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class CalculateUserChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Chronotype>> {

    private static final LocalTime lateTimeToSleep = LocalTime.of(23, 0, 0);
    private static final LocalTime lateTimeToWakeUp = LocalTime.of(9, 0, 0);
    private static final LocalTime earlyTimeToSleep = LocalTime.of(22, 0, 0);
    private static final LocalTime earlyTimeToWakeUp = LocalTime.of(7, 0, 0);

    @Override
    public SleepAnalysisResult<Chronotype> apply(List<SleepingSession> sleepLog) throws EmptySleepLogException {
        if (sleepLog.isEmpty()) {
            throw new EmptySleepLogException("Лог сна не должен быть пустым!");
        }
        String description = "Хронотип пользователя";
        List<SleepingSession> nightSleepSessions = sleepLog.stream()
                .filter(
                        (sleepingSession) -> {
                            LocalDateTime start = sleepingSession.getStartOfSession();
                            LocalDateTime end = sleepingSession.getEndOfSession();
                            if (start.getDayOfMonth() == end.getDayOfMonth())
                                return start.getHour() <= 6;
                            else
                                return true;
                        }
                ).toList();
        BiPredicate<LocalDateTime, LocalDateTime> isLarkSession = (start, end) ->
                start.getDayOfMonth() != end.getDayOfMonth() && start.toLocalTime().isBefore(earlyTimeToSleep) &&
                        end.toLocalTime().isBefore(earlyTimeToWakeUp);

        BiPredicate<LocalDateTime, LocalDateTime> isOwlSession = (start, end) -> {
            if (start.getDayOfMonth() != end.getDayOfMonth())
                return start.toLocalTime().isAfter(lateTimeToSleep) && end.toLocalTime().isAfter(lateTimeToWakeUp);
            else
                return end.toLocalTime().isAfter(lateTimeToWakeUp);
        };
        BiPredicate<LocalDateTime, LocalDateTime> isPigeonSession = (start, end) ->
                !isLarkSession.test(start, end) && !isOwlSession.test(start, end);

        long countLarkSessions = nightSleepSessions.stream()
                .filter(sleepingSession -> isLarkSession.test(
                        sleepingSession.getStartOfSession(),
                        sleepingSession.getEndOfSession()
                )).count();
        long countOwlSessions = nightSleepSessions.stream()
                .filter(sleepingSession -> isOwlSession.test(
                        sleepingSession.getStartOfSession(),
                        sleepingSession.getEndOfSession()
                )).count();
        long countPigeonSessions = nightSleepSessions.stream()
                .filter(sleepingSession -> isPigeonSession.test(
                        sleepingSession.getStartOfSession(),
                        sleepingSession.getEndOfSession()
                )).count();

        Chronotype result;
        if (countOwlSessions > countLarkSessions && countOwlSessions > countPigeonSessions)
            result = Chronotype.OWL;
        else if (countLarkSessions > countOwlSessions && countLarkSessions > countPigeonSessions)
            result = Chronotype.LARK;
        else
            result = Chronotype.PIGEON;
        return new SleepAnalysisResult<>(description, result);
    }
}
