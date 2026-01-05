package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SleepTrackerAppTest {

    private static final List<SleepingSession> emptySleepLog = new ArrayList<>();
    private static final List<SleepingSession> sleepLog = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    @BeforeAll
    public static void beforeAll() {
        SleepingSession session1 = new SleepingSession(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                SleepQuality.NORMAL
        );
        SleepingSession session2 = new SleepingSession(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(1),
                SleepQuality.BAD
        );
        sleepLog.add(session1);
        sleepLog.add(session2);
    }

    @Test
    public void countSessionsNoSessionsTest() {
        SleepAnalysisResult<Integer> actual = AnalyticalFunctions.countSessions(emptySleepLog);
        SleepAnalysisResult<Integer> expected = new SleepAnalysisResult<>("Всего сессий сна за период", 0);
        assertEquals(expected, actual);
    }

    @Test
    public void countSessions1SessionTest() {
        SleepAnalysisResult<Integer> expected = new SleepAnalysisResult<>(
                "Всего сессий сна за период",
                2
        );
        SleepAnalysisResult<Integer> actual = AnalyticalFunctions.countSessions(sleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void findMinSessionNoSessionsTest() {
        SleepAnalysisResult<Long> expected = new SleepAnalysisResult<>(
                "Минимальная продолжительность сессии (в минутах)",
                0L
        );
        SleepAnalysisResult<Long> actual = AnalyticalFunctions.findMinSession(emptySleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void findMinSessionMultipleSessionsTest() {
        SleepAnalysisResult<Long> expected = new SleepAnalysisResult<>(
                "Минимальная продолжительность сессии (в минутах)",
                60L
        );
        SleepAnalysisResult<Long> actual = AnalyticalFunctions.findMinSession(sleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void findMaxSessionNoSessionsTest() {
        SleepAnalysisResult<Long> expected = new SleepAnalysisResult<>(
                "Максимальная продолжительность сессии (в минутах)",
                0L
        );
        SleepAnalysisResult<Long> actual = AnalyticalFunctions.findMaxSession(emptySleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void findMaxSessionMultipleSessionsTest() {
        SleepAnalysisResult<Long> expected = new SleepAnalysisResult<>(
                "Максимальная продолжительность сессии (в минутах)",
                120L
        );
        SleepAnalysisResult<Long> actual = AnalyticalFunctions.findMaxSession(sleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void findAverageSessionNoSessionsTest() {
        SleepAnalysisResult<Long> expected = new SleepAnalysisResult<>(
                "Средняя продолжительность сессии (в минутах)",
                0L
        );
        SleepAnalysisResult<Long> actual = AnalyticalFunctions.findAverageSession(emptySleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void findAverageSessionMultipleSessionsTest() {
        SleepAnalysisResult<Long> expected = new SleepAnalysisResult<>(
                "Средняя продолжительность сессии (в минутах)",
                90L
        );
        SleepAnalysisResult<Long> actual = AnalyticalFunctions.findAverageSession(sleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void countBadSleepSessionsNoSessionsTest() {
        SleepAnalysisResult<Long> expected = new SleepAnalysisResult<>(
                "Количество сессий с плохим качество сна",
                0L
        );
        SleepAnalysisResult<Long> actual = AnalyticalFunctions.countBadSleepSessions(emptySleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void countBadSleepSessionsMultipleSessionsTest() {
        SleepAnalysisResult<Long> expected = new SleepAnalysisResult<>(
                "Количество сессий с плохим качество сна",
                1L
        );
        SleepAnalysisResult<Long> actual = AnalyticalFunctions.countBadSleepSessions(sleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void countNightsWithoutSleepNoSessionsTest() {
        assertThrows(EmptySleepLogException.class, () -> AnalyticalFunctions.countNightsWithoutSleep(emptySleepLog));
    }

    @Test
    public void countNightsWithoutSleepMonthChangeTest() {
        ArrayList<SleepingSession> sleepLog = new ArrayList<>();
        SleepingSession session1 = new SleepingSession(
                LocalDateTime.of(2025, 12, 31, 23, 0, 0),
                LocalDateTime.of(2026, 1, 1, 7, 0, 0),
                SleepQuality.NORMAL
        );
        sleepLog.add(session1);
        SleepAnalysisResult<Integer> expected = new SleepAnalysisResult<>(
                "Количество бессонных ночей за период с " +
                        sleepLog.getFirst().getStartOfSession().format(formatter) +
                        " по " +
                        sleepLog.getLast().getEndOfSession().format(formatter),
                0
        );
        SleepAnalysisResult<Integer> actual = AnalyticalFunctions.countNightsWithoutSleep(sleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void countNightsWithoutSleepDoesNotCountSleepDuringDayTest() {
        ArrayList<SleepingSession> sleepLog = new ArrayList<>();
        SleepingSession session1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 12, 0, 0),
                LocalDateTime.of(2026, 1, 1, 13, 0, 0),
                SleepQuality.NORMAL
        );
        SleepingSession session2 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 23, 0, 0),
                LocalDateTime.of(2026, 1, 1, 7, 0, 0),
                SleepQuality.NORMAL
        );
        sleepLog.add(session1);
        sleepLog.add(session2);
        SleepAnalysisResult<Integer> expected = new SleepAnalysisResult<>(
                "Количество бессонных ночей за период с " +
                        sleepLog.getFirst().getStartOfSession().format(formatter) +
                        " по " +
                        sleepLog.getLast().getEndOfSession().format(formatter),
                0
        );
        SleepAnalysisResult<Integer> actual = AnalyticalFunctions.countNightsWithoutSleep(sleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void countNightsWithoutSleepLateSleepTest() {
        ArrayList<SleepingSession> sleepLog = new ArrayList<>();
        SleepingSession session1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 2, 0, 0),
                LocalDateTime.of(2026, 1, 1, 9, 0, 0),
                SleepQuality.NORMAL
        );
        sleepLog.add(session1);
        SleepAnalysisResult<Integer> expected = new SleepAnalysisResult<>(
                "Количество бессонных ночей за период с " +
                        sleepLog.getFirst().getStartOfSession().format(formatter) +
                        " по " +
                        sleepLog.getLast().getEndOfSession().format(formatter),
                0
        );
        SleepAnalysisResult<Integer> actual = AnalyticalFunctions.countNightsWithoutSleep(sleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void calculateUserChronotypeNoSessionsTest() {
        assertThrows(EmptySleepLogException.class, () -> AnalyticalFunctions.calculateUserChronotype(emptySleepLog));
    }

    @Test
    public void calculateUserChronotypeOwlTest() {
        ArrayList<SleepingSession> sleepLog = new ArrayList<>();
        SleepingSession session1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 1, 0, 0),
                LocalDateTime.of(2026, 1, 1, 10, 0, 0),
                SleepQuality.NORMAL
        );
        SleepingSession session2 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 23, 0, 0),
                LocalDateTime.of(2026, 1, 2, 10, 0, 0),
                SleepQuality.NORMAL
        );
        SleepingSession session3 = new SleepingSession(
                LocalDateTime.of(2026, 1, 2, 21, 0, 0),
                LocalDateTime.of(2026, 1, 3, 6, 0, 0),
                SleepQuality.NORMAL
        );
        sleepLog.add(session1);
        sleepLog.add(session2);
        sleepLog.add(session3);
        SleepAnalysisResult<Chronotype> expected = new SleepAnalysisResult<>(
                "Хронотип пользователя",
                Chronotype.OWL
        );
        SleepAnalysisResult<Chronotype> actual = AnalyticalFunctions.calculateUserChronotype(sleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void calculateUserChronotypeLarkTest() {
        ArrayList<SleepingSession> sleepLog = new ArrayList<>();
        SleepingSession session1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 20, 0, 0),
                LocalDateTime.of(2026, 1, 2, 5, 0, 0),
                SleepQuality.NORMAL
        );
        SleepingSession session2 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 23, 0, 0),
                LocalDateTime.of(2026, 1, 2, 9, 0, 0),
                SleepQuality.NORMAL
        );
        SleepingSession session3 = new SleepingSession(
                LocalDateTime.of(2026, 1, 2, 21, 0, 0),
                LocalDateTime.of(2026, 1, 3, 6, 0, 0),
                SleepQuality.NORMAL
        );
        sleepLog.add(session1);
        sleepLog.add(session2);
        sleepLog.add(session3);
        SleepAnalysisResult<Chronotype> expected = new SleepAnalysisResult<>(
                "Хронотип пользователя",
                Chronotype.LARK
        );
        SleepAnalysisResult<Chronotype> actual = AnalyticalFunctions.calculateUserChronotype(sleepLog);
        assertEquals(expected, actual);
    }

    @Test
    public void calculateUserChronotypeEqualLarkAndOwlSessionsTest() {
        ArrayList<SleepingSession> sleepLog = new ArrayList<>();
        SleepingSession session1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 1, 0, 0),
                LocalDateTime.of(2026, 1, 1, 10, 0, 0),
                SleepQuality.NORMAL
        );
        SleepingSession session2 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 22, 0, 0),
                LocalDateTime.of(2026, 1, 2, 6, 0, 0),
                SleepQuality.NORMAL
        );
        sleepLog.add(session1);
        sleepLog.add(session2);
        SleepAnalysisResult<Chronotype> expected = new SleepAnalysisResult<>(
                "Хронотип пользователя",
                Chronotype.PIGEON
        );
        SleepAnalysisResult<Chronotype> actual = AnalyticalFunctions.calculateUserChronotype(sleepLog);
        assertEquals(expected, actual);
    }
}