package me.karmas.client.mixin;

import me.karmas.client.KarmasClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prepends a brand-coloured "Ka " prefix to every player nametag.
 *
 * <p>The injection targets {@code renderLabelIfPresent} which is the single
 * method responsible for drawing nametag text above entities. We replace the
 * incoming {@link Text} with a composite that puts the prefix first, then the
 * original name in vanilla white, so the player's name is never altered.</p>
 *
 * <p>This works in first person, third person, and spectator mode because
 * Minecraft calls this method for all visible players.</p>
 */
@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin
        extends LivingEntityRenderer<PlayerEntity, PlayerEntityModel<PlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx,
                                     PlayerEntityModel<PlayerEntity> model,
                                     float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    /**
     * Intercepts the label text before it is measured and drawn.
     * We build: §<brandColor>Ka §<reset><originalName>
     */
    @Inject(
        method = "renderLabelIfPresent(Lnet/minecraft/entity/player/PlayerEntity;" +
                 "Lnet/minecraft/text/Text;" +
                 "Lnet/minecraft/client/util/math/MatrixStack;" +
                 "Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void karmas$prefixNametag(PlayerEntity player, Text text,
                                      MatrixStack matrices,
                                      VertexConsumerProvider vertexConsumers,
                                      int light,
                                      CallbackInfo ci) {

        // Build the prefixed label: "Ka " in brand colour + original name.
        MutableText prefix = Text.literal("Ka ")
                .setStyle(Style.EMPTY.withColor(0xB0C4DE));

        // Keep the original text exactly as vanilla would render it.
        MutableText combined = prefix.append(text.copy());

        // Delegate back to the parent renderer with our modified text.
        super.renderLabelIfPresent(player, combined, matrices, vertexConsumers, light);

        // Cancel the original call so the name isn't drawn twice.
        ci.cancel();
    }
}
