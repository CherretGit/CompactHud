package com.cherret;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.awt.*;

public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.of("hud", "compact_hud"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("compact_hud.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public Color color_player_name = Color.WHITE;

    @SerialEntry
    public Color color_line_horizontal = Color.WHITE;

    @SerialEntry
    public Color color_line_vertical = Color.WHITE;

    @SerialEntry
    public Boolean rectangleMode = false;

    public static void loadConfig() {
        HANDLER.load();
    }

    public static void saveConfig() {
        HANDLER.save();
    }
}
