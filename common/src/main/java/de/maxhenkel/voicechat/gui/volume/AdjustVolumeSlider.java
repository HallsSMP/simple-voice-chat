package de.maxhenkel.voicechat.gui.volume;

import de.maxhenkel.voicechat.VoicechatClient;
import de.maxhenkel.voicechat.gui.widgets.DebouncedSlider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.UUID;

public class AdjustVolumeSlider extends DebouncedSlider {

    private static final Component MUTED = new TranslatableComponent("message.voicechat.muted");

    private static final float MAXIMUM = 4F;

    private final UUID player;

    public AdjustVolumeSlider(int xIn, int yIn, int widthIn, int heightIn, UUID player) {
        super(xIn, yIn, widthIn, heightIn, TextComponent.EMPTY, (player == null ? 1D : VoicechatClient.VOLUME_CONFIG.getVolume(player, 1D)) / MAXIMUM);
        this.player = player;
        if (player == null) {
            visible = false;
        }
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        if (value <= 0D) {
            setMessage(MUTED);
            return;
        }
        long amp = Math.round(value * MAXIMUM * 100F - 100F);
        setMessage(new TranslatableComponent("message.voicechat.volume_amplification", (amp > 0F ? "+" : "") + amp + "%"));
    }

    @Override
    public void applyDebounced() {
        VoicechatClient.VOLUME_CONFIG.setVolume(player, value * MAXIMUM);
        VoicechatClient.VOLUME_CONFIG.save();
    }
}
