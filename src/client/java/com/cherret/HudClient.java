package com.cherret;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class HudClient implements ClientModInitializer {
	private static final int x = 10;
	private static final int y = 10;
	private static final int iconSize = 16;
	private static final int textX = x + iconSize + 5;
	private static final int lineY = y + iconSize / 2;
	private static int line_width = 2;
	private static int line_height;
	private static int airX = textX + 14;
	private static int airY;
	private static final int healthX = textX + 14;
	private static final int healthY  = lineY + 11;
	private static final int barWidth = 120;
	private static final int barHeight = 5;
	private static final int hungerX = textX + 14;
	private static final int hungerY = lineY + 21;
	private static final int experienceX = textX + 14;
	private static final int experienceY = lineY + 31;
	private static final int armorX = textX + 14;
	private static final int armorY = lineY + 41;;
	private static boolean isArmorVisible = false;
	private static boolean isAirVisible = false;
	private static final Identifier hpBar = Identifier.of("hud", "textures/gui/red_progress.png");
	private static final Identifier hpBackground = Identifier.of("hud", "textures/gui/red_background.png");
	private static final Identifier hpPoisBar = Identifier.of("hud", "textures/gui/green_progress.png");
	private static final Identifier hpPoisBackground = Identifier.of("hud", "textures/gui/green_background.png");
	private static final Identifier hpWitBar = Identifier.of("hud", "textures/gui/black_progress.png");
	private static final Identifier hpWitBackground = Identifier.of("hud", "textures/gui/black_background.png");
	private static final Identifier notchedProgress = Identifier.of("hud", "textures/gui/notched_20_progress.png");
	private static final Identifier notcheBackgroud = Identifier.of("hud", "textures/gui/notched_20_background.png");
	private static final Identifier hpFull = Identifier.of("hud", "textures/gui/hp_full.png");
	private static final Identifier poisFull = Identifier.of("hud", "textures/gui/pois_full.png");
	private static final Identifier witFull = Identifier.of("hud", "textures/gui/wit_full.png");
	private static final Identifier hardFull = Identifier.of("hud", "textures/gui/hard_full.png");
	private static final Identifier hardPoisFull = Identifier.of("hud", "textures/gui/pois_hard_full.png");
	private static final Identifier hardWitFull = Identifier.of("hud", "textures/gui/wit_hard_full.png");
	private static final Identifier hungerBackground = Identifier.of("hud", "textures/gui/yellow_background.png");
	private static final Identifier hungerBar = Identifier.of("hud", "textures/gui/yellow_progress.png");
	private static final Identifier hungerFull = Identifier.of("hud", "textures/gui/food_full.png");
	private static final Identifier hungerHFull = Identifier.of("hud", "textures/gui/food_full_hunger.png");
	private static final Identifier experienceBar = Identifier.of("hud", "textures/gui/green_progress.png");
	private static final Identifier experienceBackground = Identifier.of("hud", "textures/gui/green_background.png");
	private static final Identifier xp = Identifier.of("hud", "textures/gui/xp.png");
	private static final Identifier airBar = Identifier.of("hud", "textures/gui/blue_progress.png");
	private static final Identifier airBackground = Identifier.of("hud", "textures/gui/blue_background.png");
	private static final Identifier airFull = Identifier.of("hud", "textures/gui/air.png");
	private static final Identifier air_bursting = Identifier.of("hud", "textures/gui/air_bursting.png");
	private static final Identifier armorIcon = Identifier.of("hud", "textures/gui/armor_full.png");
	private static final Identifier armorBar = Identifier.of("hud", "textures/gui/white_progress.png");
	private static final Identifier armorBackground = Identifier.of("hud", "textures/gui/white_background.png");

    @Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(this::onHudRender);
	}

	public void onHudRender(DrawContext context, RenderTickCounter renderTickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.options.hudHidden) {
			return;
		}
		GameProfile profile = client.player.getGameProfile();
		SkinTextures skinTexture = client.getSkinProvider().getSkinTextures(profile);
		renderPlayerHead(context, skinTexture, x, y, iconSize);
		renderPlayerName(context, client.player.getName().getString(), textX, y + iconSize / 2 - 4);
		renderLine(context, textX, y + iconSize / 2 + 6, 155, 2);
		if (client.player.isCreative() || client.player.isSpectator()) {
			return;
		}
		line_height = 35;
		airY = lineY + 41;
		if (isArmorVisible && isAirVisible) {
			airY += 10;
			line_height += 18;
		}
		else if (isArmorVisible || isAirVisible) {
			line_height += 8;
		}
		renderLine(context, textX, lineY + 6, line_width, line_height);
		if (client.player.isSubmergedInWater()) {
			renderAir(context, airX, airY);
			isAirVisible = true;
		}
		else {
			isAirVisible = false;
		}
		if (client.player.getArmor() != 0) {
			renderArmor(context, armorX, armorY);
			isArmorVisible = true;
		}
		else {
			isArmorVisible = false;
		}
		renderHealth(context, healthX, healthY);
		renderHunger(context, hungerX, hungerY);
		renderExperience(context, experienceX, experienceY);
	}

	public static void renderPlayerHead(DrawContext context, SkinTextures skinTextures, int x, int y, int size) {
		PlayerSkinDrawer.draw(context, skinTextures, x, y, size);
	}

	public static void renderPlayerName(DrawContext context, String playerName, int x, int y) {
		MinecraftClient client = MinecraftClient.getInstance();
		TextRenderer textRenderer = client.textRenderer;
		context.drawText(textRenderer, playerName, x, y, 0xFFFFFFFF, false);
	}

	public static void renderLine(DrawContext context, int x, int y, int width, int height) {
		context.fill(x, y, x + width, y + height, 0xFFFFFFFF);
	}

	private void renderHealth(DrawContext context, int x, int y) {
		MinecraftClient client = MinecraftClient.getInstance();
		int health = (int) Math.ceil(client.player.getHealth() + client.player.getAbsorptionAmount());
		float maxHealth = client.player.getMaxHealth() + client.player.getMaxAbsorption();
		int currentBarWidth = (int) ((health / maxHealth) * barWidth);
		if (client.player.hasStatusEffect(StatusEffects.POISON)) {
			if (isHardcoreMode(client.getServer())) {
				context.drawTexture(hardPoisFull, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
			}
			else {
				context.drawTexture(poisFull, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
			}
			context.drawTexture(hpPoisBackground, x, y, 0,0, 120, 5, 120, 5);
			context.drawTexture(notcheBackgroud, x, y, 0,0, 120, 5, 120, 5);
			context.drawTexture(hpPoisBar, x, y, 0,0, currentBarWidth, 5, 120, 5);
			context.drawTexture(notchedProgress, x, y, 0,0, currentBarWidth, 5, 120, 5);
			context.drawText(client.textRenderer, String.valueOf(health), x + barWidth + 2, y - 2, 0xFFFFFFFF, false);
			return;
		}
		else if (client.player.hasStatusEffect(StatusEffects.WITHER)) {
			if (isHardcoreMode(client.getServer())) {
				context.drawTexture(hardWitFull, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
			}
			else {
				context.drawTexture(witFull, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
			}
			context.drawTexture(hpWitBackground, x, y, 0,0, 120, 5, 120, 5);
			context.drawTexture(notcheBackgroud, x, y, 0,0, 120, 5, 120, 5);
			context.drawTexture(hpWitBar, x, y, 0,0, currentBarWidth, 5, 120, 5);
			context.drawTexture(notchedProgress, x, y, 0,0, currentBarWidth, 5, 120, 5);
			context.drawText(client.textRenderer, String.valueOf(health), x + barWidth + 2, y - 2, 0xFFFFFFFF, false);
			return;
		}
		else if (isHardcoreMode(client.getServer())) {
			context.drawTexture(hardFull, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
			context.drawTexture(hpBackground, x, y, 0,0, 120, 5, 120, 5);
			context.drawTexture(notcheBackgroud, x, y, 0,0, 120, 5, 120, 5);
			context.drawTexture(hpBar, x, y, 0,0, currentBarWidth, 5, 120, 5);
			context.drawTexture(notchedProgress, x, y, 0,0, currentBarWidth, 5, 120, 5);
			context.drawText(client.textRenderer, String.valueOf(health), x + barWidth + 2, y - 2, 0xFFFFFFFF, false);
			return;
		}
		context.drawTexture(hpFull, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
		context.drawTexture(hpBackground, x, y, 0,0, 120, 5, 120, 5);
		context.drawTexture(notcheBackgroud, x, y, 0,0, 120, 5, 120, 5);
		context.drawTexture(hpBar, x, y, 0,0, currentBarWidth, 5, 120, 5);
		context.drawTexture(notchedProgress, x, y, 0,0, currentBarWidth, 5, 120, 5);
		context.drawText(client.textRenderer, String.valueOf(health), x + barWidth + 2, y - 2, 0xFFFFFFFF, false);
	}

	private void renderAir(DrawContext context, int x, int y) {
		MinecraftClient client = MinecraftClient.getInstance();
		int air = (int) Math.ceil(client.player.getAir());
		double maxAir = 300.0;
		int currentBarWidth = (int) (((air / maxAir) * barWidth));
		if (air <= 0) {
			context.drawTexture(air_bursting, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
			context.drawTexture(airBackground, x, y, 0,0, 120, 5, 120, 5);
			context.drawTexture(notcheBackgroud, x, y, 0,0, 120, 5, 120, 5);
			return;
		}
		context.drawTexture(airFull, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
		context.drawTexture(airBackground, x, y, 0,0, 120, 5, 120, 5);
		context.drawTexture(notcheBackgroud, x, y, 0,0, 120, 5, 120, 5);
		context.drawTexture(airBar, x, y, 0,0, currentBarWidth, 5, 120, 5);
		context.drawTexture(notchedProgress, x, y, 0,0, currentBarWidth, 5, 120, 5);
	}

	private void renderArmor(DrawContext context, int x, int y) {
		MinecraftClient client = MinecraftClient.getInstance();
		int armor = (int) Math.ceil(client.player.getArmor());
		int maxArmor = (int) 20;
		int currentBarWidth = (int) (((armor / (float) maxArmor) * barWidth));
		context.drawTexture(armorIcon, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
		context.drawTexture(armorBackground, x, y, 0,0, 120, 5, 120, 5);
		context.drawTexture(notcheBackgroud, x, y, 0,0, 120, 5, 120, 5);
		context.drawTexture(armorBar, x, y, 0,0, currentBarWidth, 5, 120, 5);
		context.drawTexture(notchedProgress, x, y, 0,0, currentBarWidth, 5, 120, 5);
		context.drawText(client.textRenderer, String.valueOf(armor), x + barWidth + 2, y - 2, 0xFFFFFFFF, false);
	}

	private void renderHunger(DrawContext context, int x, int y) {
		MinecraftClient client = MinecraftClient.getInstance();
		int hunger = client.player.getHungerManager().getFoodLevel();
		int maxHunger = 20;
		int currentBarWidth = (int) ((hunger / (float) maxHunger) * barWidth);
		if (client.player.hasStatusEffect(StatusEffects.HUNGER)) {
			context.drawTexture(hungerHFull, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
		}
		else {
			context.drawTexture(hungerFull, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
		}
		context.drawTexture(hungerBackground, x, y, 0,0, 120, 5, 120, 5);
		context.drawTexture(notcheBackgroud, x, y, 0,0, 120, 5, 120, 5);
		context.drawTexture(hungerBar, x, y, 0,0, currentBarWidth, 5, 120, 5);
		context.drawTexture(notchedProgress, x, y, 0,0, currentBarWidth, 5, 120, 5);
		context.drawText(client.textRenderer, String.valueOf(hunger), x + barWidth + 2, y - 2, 0xFFFFFFFF, false);
	}

	private void renderExperience(DrawContext context, int x, int y) {
		MinecraftClient client = MinecraftClient.getInstance();
		int experienceLevel = client.player.experienceLevel;
		float experienceProgress = client.player.experienceProgress;
		int currentBarWidth = (int) (experienceProgress * barWidth);
		context.drawTexture(xp, textX + 3, y - 2, 0, 0, 9, 9, 9, 9);
		context.drawTexture(experienceBackground, x, y, 0,0, 120, 5, 120, 5);
		context.drawTexture(notcheBackgroud, x, y, 0,0, 120, 5, 120, 5);
		context.drawTexture(experienceBar, x, y, 0,0, currentBarWidth, 5, 120, 5);
		context.drawTexture(notchedProgress, x, y, 0,0, currentBarWidth, 5, 120, 5);
		context.drawText(client.textRenderer, String.valueOf(experienceLevel), x + barWidth + 2, y - 2, 0xFFFFFFFF, false);
	}
	public static boolean isHardcoreMode(MinecraftServer server) {
		ServerWorld world = server.getWorld(server.getOverworld().getRegistryKey());
		return world.getLevelProperties().isHardcore();
	}
}