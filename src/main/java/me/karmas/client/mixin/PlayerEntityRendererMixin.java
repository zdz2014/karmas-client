package me.karmas.client.mixin;

import me.karmas.client.KarmasClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {

    @Inject(
        method = "renderLabelIfPresent",
        at = @At("HEAD"),
        cancellable = true
    )
    private void karmas$prefixNametag(PlayerEntity player, Text text,
                                      MatrixStack matrices,
                                      VertexConsumerProvider vertexConsumers,
                                      int light, float tickDelta,
                                      CallbackInfo ci) {

        MutableText prefix = Text.literal("Ka ")
                .setStyle(Style.EMPTY.withColor(0xB0C4DE));

        MutableText combined = prefix.append(text.copy());

        ci.cancel();

        net.minecraft.client.MinecraftClient mc =
                net.minecraft.client.MinecraftClient.getInstance();
        if (mc.getEntityRenderDispatcher().getRenderShadows()) {
            ((net.minecraft.client.render.entity.EntityRenderer)
                    (Object) this).renderLabelIfPresent(
                    player, combined, matrices, vertexConsumers, light, tickDelta);
        }
    }
}
