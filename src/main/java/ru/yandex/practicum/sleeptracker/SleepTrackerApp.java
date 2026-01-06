package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class SleepTrackerApp {

    private static final ArrayList<SleepingSession> sleepLog = new ArrayList<>();
    private static final ArrayList<Function> functions = new ArrayList<>();


    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Не передан путь к файлу лога сна");
            return;
        }
        String path = args[0];
        loadSleepLog(path);

        functions.add(new CountSessionsFunction());
        functions.add(new FindMinSessionFunction());
        functions.add(new FindMaxSessionFunction());
        functions.add(new FindAverageSessionFunction());
        functions.add(new CountBadSleepSessionsFunction());
        functions.add(new CountNightsWithoutSleep());
        functions.add(new CalculateUserChronotypeFunction());

        try {
            functions.stream()
                    .map(function -> function.apply(sleepLog))
                    .toList().forEach(System.out::println);
        } catch (EmptySleepLogException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void loadSleepLog(String path) {
        Consumer<String> linePareser = line -> {
            String[] parts = line.split(";");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
            LocalDateTime startOfSession = LocalDateTime.parse(parts[0], formatter);
            LocalDateTime endOfSession = LocalDateTime.parse(parts[1], formatter);
            SleepQuality sleepQuality = SleepQuality.valueOf(parts[2]);
            SleepingSession sleepingSession = new SleepingSession(startOfSession, endOfSession, sleepQuality);
            sleepLog.add(sleepingSession);
        };
        try (BufferedReader br = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            br.lines().forEach(linePareser);
        } catch (IOException exception) {
            System.out.println("Error reading sleep log.");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}