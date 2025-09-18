package edu.ccrm.io;

import edu.ccrm.config.AppConfig;

import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {
    public Path runBackup(Path sourceDir) throws IOException{
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path backup = AppConfig.getInstance().getDataFolder().resolve("backup_" + ts);
        Files.createDirectories(backup);
        try (var stream = Files.walk(sourceDir)) {
            stream.forEach(src -> {
                try {
                    Path dest = backup.resolve(sourceDir.relativize(src));
                    if (Files.isDirectory(src)) {
                        if (!Files.exists(dest)) Files.createDirectories(dest);
                    } else {
                        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return backup;
    }
}
