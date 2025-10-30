package sx.keepwalking.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "keepwalking")
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean enableInAllScreens = true;

    @ConfigEntry.Gui.Tooltip
    public boolean enableSprint = true;

    @ConfigEntry.Gui.Tooltip
    public boolean enableSneak = true;

    @ConfigEntry.Gui.Tooltip
    public boolean enableJump = true;
}
