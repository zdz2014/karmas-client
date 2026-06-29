package me.karmas.client.mixin;

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
                                      int light, CallbackInfo ci) {
        ci.cancel();

        MutableText prefix = Text.literal("Ka ")
                .setStyle(Style.EMPTY.withColor(0xB0C4DE));
        MutableText combined = prefix.append(text.copy());

        net.minecraft.client.MinecraftClient.getInstance()
                .getEntityRenderDispatcher()
                .getRenderer(player)
                .renderLabelIfPresent(player, combined, matrices, vertexConsumers, light);
    }
}
