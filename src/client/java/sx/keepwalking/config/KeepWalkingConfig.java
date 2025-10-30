package sx.keepwalking.config;

import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class KeepWalkingConfig {

    public static boolean keepWalkingEnabled = true;
    public static boolean keepForward = true;
    public static boolean keepBack = true;
    public static boolean keepLeft = true;
    public static boolean keepRight = true;
    public static boolean keepJumping = true;
    public static boolean keepSprinting = true;

    public static boolean wasSprintingOnGuiOpen = false;

    public static SneakModes sneakMode = SneakModes.OFF;
    public static boolean wasSneakingOnGuiOpen = false;

    public static int dimOpacityPercent = 0; // default value is 0

    // save file path
    private static final File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "keepwalking.properties");
    private static final Properties properties = new Properties();

    // Liest die Datei und lädt die Werte in die Variablen
    public static void loadConfig() {
        try {
            if (configFile.exists()) {
                FileInputStream fis = new FileInputStream(configFile);
                properties.load(fis);
                fis.close();
            } else {
                // Datei existiert nicht, setze Standardwerte in Properties
                saveConfig(); // Speichert die Standardwerte in einer neuen Datei
            }

            keepWalkingEnabled = Boolean.parseBoolean(properties.getProperty("keepWalkingEnabled", "true"));
            keepForward = Boolean.parseBoolean(properties.getProperty("keepForward", "true"));
            keepBack = Boolean.parseBoolean(properties.getProperty("keepBack", "true"));
            keepLeft = Boolean.parseBoolean(properties.getProperty("keepLeft", "true"));
            keepRight = Boolean.parseBoolean(properties.getProperty("keepRight", "true"));
            keepJumping = Boolean.parseBoolean(properties.getProperty("keepJumping", "true"));
            keepSprinting = Boolean.parseBoolean(properties.getProperty("keepSprinting", "true"));

            sneakMode = parseSneakMode(properties.getProperty("sneakMode", SneakModes.OFF.name()));

        } catch (IOException e) {
            System.err.println("KeepWalking: Konnte die Config nicht laden!");
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            properties.setProperty("keepWalkingEnabled", String.valueOf(keepWalkingEnabled));
            properties.setProperty("keepForward", String.valueOf(keepForward));
            properties.setProperty("keepBack", String.valueOf(keepBack));
            properties.setProperty("keepLeft", String.valueOf(keepLeft));
            properties.setProperty("keepRight", String.valueOf(keepRight));
            properties.setProperty("keepJumping", String.valueOf(keepJumping));
            properties.setProperty("sneakMode", sneakMode.name());
            properties.setProperty("keepSprinting", String.valueOf(keepSprinting));

            FileOutputStream fos = new FileOutputStream(configFile);
            properties.store(fos, "KeepWalking Mod Konfiguration");
            fos.close();

        } catch (IOException e) {
            System.err.println("KeepWalking: Konnte die Config nicht speichern!");
            e.printStackTrace();
        }
    }
    private static SneakModes parseSneakMode(String s) {
        try {
            return SneakModes.valueOf(s.toUpperCase()); // find enum value
        } catch (IllegalArgumentException e) {
            return SneakModes.OFF; // take default value if not found
        }
    }
}