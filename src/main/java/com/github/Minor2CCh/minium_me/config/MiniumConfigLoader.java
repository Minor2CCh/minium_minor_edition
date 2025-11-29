package com.github.Minor2CCh.minium_me.config;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class MiniumConfigLoader {
    private static final File DIR = FabricLoader.getInstance().getConfigDir().toFile();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILENAME = Minium_me.MOD_ID +".json";
    private static final Path CONFIG_PATH = Path.of(new File(DIR,FILENAME).getPath());
    private static MiniumConfig modConfig;
    public static void load(){
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                modConfig = GSON.fromJson(reader, MiniumConfig.class);
            } catch (Exception e) {
                System.err.println("Failed to load config: " + e.getMessage());
                modConfig = new MiniumConfig();
            }

            try{
                modConfig.fillDefaults();
            } catch (Exception e) {
                System.err.println("Failed to load config: " + e.getMessage());
                modConfig = new MiniumConfig();
            }
            // ここで新フィールド補完

        } else {
            modConfig = new MiniumConfig();
        }
        save(); // 初回生成

    }
    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(modConfig, writer);
            }
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }
    public static MiniumConfig getConfig() {
        return modConfig;
    }
}
