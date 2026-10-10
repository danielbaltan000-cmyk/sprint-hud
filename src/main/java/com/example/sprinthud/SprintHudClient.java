package com.example.sprinthud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;

public class SprintHudClient implements ClientModInitializer {
    // Text and look. Change these to taste.
    private static final String TEXT = "sprinting toggled";
    private static final float SCALE = 1.5f;   // 1.0 = vanilla size
    private static final int X = 6;
    private static final int Y = 6;
    private static final int COLOR = 0xFFFFFFFF; // ARGB, white

    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.MISC_OVERLAYS,
                Identifier.fromNamespaceAndPath("sprinthud", "label"),
                (graphics, tickCounter) -> render(graphics)
        );
    }

    private static void render(GuiGraphics graphics) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        // "Walking" = any movement key held (works whether or not you are sprinting).
        boolean walking = mc.options.keyUp.isDown()
                || mc.options.keyDown.isDown()
                || mc.options.keyLeft.isDown()
                || mc.options.keyRight.isDown();
        if (!walking) return;

        graphics.pose().pushMatrix();
        graphics.pose().scale(SCALE, SCALE);
        graphics.drawString(mc.font, TEXT, (int) (X / SCALE), (int) (Y / SCALE), COLOR, true);
        graphics.pose().popMatrix();
    }
}
