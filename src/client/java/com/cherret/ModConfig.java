package com.cherret;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;

public class ModConfig {
    public Screen config(Screen parentScreen) {
        Config.loadConfig();
        Config configInstance = Config.HANDLER.instance();
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("config.hud.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.hud.category_name"))
                        .tooltip(Text.translatable("config.hud.category_tooltip"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.hud.group_name"))
                                .description(OptionDescription.of(Text.translatable("config.hud.group_description")))
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("config.hud.color_player_name"))
                                        .description(OptionDescription.of(Text.translatable("config.hud.color_player_name.description")))
                                        .binding(Color.WHITE, () -> configInstance.color_player_name, newVal -> {configInstance.color_player_name = newVal; Config.saveConfig();})
                                        .controller(ColorControllerBuilder::create)
                                        .build()
                                )
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("config.hud.color_line_horizontal"))
                                        .description(OptionDescription.of(Text.translatable("config.hud.color_line_horizontal.description")))
                                        .binding(Color.WHITE, () -> configInstance.color_line_horizontal, newVal -> {configInstance.color_line_horizontal = newVal; Config.saveConfig();})
                                        .controller(ColorControllerBuilder::create)
                                        .build()
                                )
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("config.hud.color_line_vertical"))
                                        .description(OptionDescription.of(Text.translatable("config.hud.color_line_vertical.description")))
                                        .binding(Color.WHITE, () -> configInstance.color_line_vertical, newVal -> {configInstance.color_line_vertical = newVal; Config.saveConfig();})
                                        .controller(ColorControllerBuilder::create)
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("config.hud.rectangle_mode"))
                                        .description(OptionDescription.of(Text.translatable("config.hud.rectangle_mode.description")))
                                        .binding(true, () -> configInstance.rectangleMode, newVal -> {configInstance.rectangleMode = newVal; Config.saveConfig();})
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .build()
                .generateScreen(parentScreen);
    }
}
