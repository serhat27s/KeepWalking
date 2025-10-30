package sx.keepwalking;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

import sx.keepwalking.config.KeepWalkingConfig;
import sx.keepwalking.config.SneakModes;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("KeepWalking Settings"));

            builder.setSavingRunnable(KeepWalkingConfig::saveConfig);

            ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();


            // Mod ON/OFF
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("KeepWalking activated"), KeepWalkingConfig.keepWalkingEnabled)
                    .setDefaultValue(true)
                    .setTooltip(Text.literal("Activate or deactivate the mod."))
                    .setSaveConsumer(newValue -> KeepWalkingConfig.keepWalkingEnabled = newValue)
                    .build());

            // Jump
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Jump"), KeepWalkingConfig.keepJumping)
                    .setDefaultValue(true)
                    .setTooltip(Text.literal("Allows to jump while in GUI."))
                    .setSaveConsumer(newValue -> KeepWalkingConfig.keepJumping = newValue)
                    .build());

            // Sprint
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Sprint"), KeepWalkingConfig.keepSprinting)
                    .setDefaultValue(true)
                    .setTooltip(Text.literal("Allows to sprint while in GUI."))
                    .setSaveConsumer(newValue -> KeepWalkingConfig.keepSprinting = newValue)
                    .build());

            // Sneak
            general.addEntry(entryBuilder.startEnumSelector(Text.literal("Sneak"), SneakModes.class, KeepWalkingConfig.sneakMode)
                    .setDefaultValue(SneakModes.OFF)
                    .setTooltip(Text.literal("ON: Allows to sneak in the GUI.\n" +
                                            "Maintain: When sneaking before opening a GUI, \n" + "continues to sneak without having to press a key down."))
                    .setSaveConsumer(newValue -> KeepWalkingConfig.sneakMode = newValue)
                    .setEnumNameProvider(value -> Text.literal(((SneakModes) value).getDisplayName()))
                    .build());

            // Dimming Slider
            general.addEntry(entryBuilder.startIntSlider(
                    Text.literal("GUI Dimming"),
                    KeepWalkingConfig.dimOpacityPercent,
                    0,
                    100
                    )
                    .setDefaultValue(0)
                    .setTooltip(Text.literal("0% = transparent, no dimming. \n" +
                    "100% = default dimming effect."))
                    .setTextGetter(value -> Text.literal(value + "%"))
                    .setSaveConsumer(newValue -> KeepWalkingConfig.dimOpacityPercent = newValue)
                    .build());

            return builder.build();
        };
    }
}