package sx.keepwalking.config;

public enum SneakModes {
    OFF("OFF"),
    MAINTAIN("Maintain"),
    ON("ON");

    private final String displayName;

    SneakModes(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}