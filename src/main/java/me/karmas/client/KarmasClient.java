package me.karmas.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class KarmasClient implements ClientModInitializer {

    public static final String MOD_ID = "karmas-client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * Brand color used for all custom-rendered text: #B0C4DE (Light Steel Blue).
     * Stored as a packed ARGB int (alpha = 0xFF).
     */
    public static final int BRAND_COLOR = 0xFFB0C4DE;

    @Override
    public void onInitializeClient() {
        LOGGER.info("[Karmas Client] Initialized — cosmetics only, zero gameplay changes.");
    }
}
