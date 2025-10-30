package sx.keepwalking.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreen {

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Text.literal("KeepWalking Config"));

        ConfigCategory general = builder.getOrCreateCategory(Text.literal("Allgemein"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startBooleanToggle(
            Text.literal("KeepWalking activated"),
            KeepWalkingConfig.keepWalkingEnabled
        ).setDefaultValue(true)
        .setSaveConsumer(value -> KeepWalkingConfig.keepWalkingEnabled = value)
        .build());

        general.addEntry(entryBuilder.startBooleanToggle(
            Text.literal("Keep Jumping"),
            KeepWalkingConfig.keepJumping
        ).setDefaultValue(true)
        .setSaveConsumer(value -> KeepWalkingConfig.keepJumping = value)
        .build());

// Schleichen (Sneak)
general.addEntry(entryBuilder.startEnumSelector(
        Text.literal("Schleichen (Sneak)"),  // Der Titel der Option
        SneakModes.class,                   // Die Enum-Klasse, die die Optionen enthält
        KeepWalkingConfig.sneakMode        // Der aktuell in der Config gespeicherte Wert
    )
    .setDefaultValue(SneakModes.OFF)        // Der Standardwert
    .setTooltip(Text.literal(              // Der Tooltip, der alles erklärt
        "Aus: Normales Verhalten.\n" +
        "An: Erlaubt Schleichen im GUI per Tastendruck.\n" +
        "Beibehalten: Hält das Schleichen, wenn das GUI geöffnet wurde."
    ))
    .setSaveConsumer(newValue -> KeepWalkingConfig.sneakMode = newValue) // Speichert die Auswahl
    .setEnumNameProvider(value -> Text.literal(((SneakModes) value).getDisplayName())) // Zeigt "Aus", "An" statt "OFF", "ON"
    .build());



        return builder.build();
    }
}
