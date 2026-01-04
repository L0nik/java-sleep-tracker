package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SleepTrackerAppTest {

    private final static List<SleepingSession> emptySleepLog = new ArrayList<>();
    private final static List<SleepingSession> sleepLog = new ArrayList<>();

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
}