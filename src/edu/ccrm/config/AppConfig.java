package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();
    private final Path dataFolder;
    private final int maxCreditsPerStudent = 30; // configurable

    private AppConfig() {
        String home = System.getProperty("user.home");
        this.dataFolder = Paths.get(home, "ccrm_data");
    }

    public static AppConfig getInstance() { return INSTANCE; }
    public Path getDataFolder() { return dataFolder; }
    public int getMaxCreditsPerStudent() { return maxCreditsPerStudent; }
}
