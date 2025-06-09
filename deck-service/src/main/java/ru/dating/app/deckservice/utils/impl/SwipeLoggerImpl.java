package ru.dating.app.deckservice.utils.impl;

import org.springframework.stereotype.Service;
import ru.dating.app.deckservice.utils.SwipeLogger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class SwipeLoggerImpl implements SwipeLogger {
    private final Path csvPath = Paths.get("infrastructure-services", "tsung", "test-swipes.csv");
    private final Random random = new Random();

    @Override
    public void log(UUID swiperId, UUID targetId, String direction) {
        try {
            if (Files.notExists(csvPath)) {
                Files.createDirectories(csvPath.getParent());
                Files.createFile(csvPath);
            }
            String line = swiperId + "," + targetId + "," + direction;
            Files.write(csvPath, List.of(line), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write deck file", e);
        }
    }
}
