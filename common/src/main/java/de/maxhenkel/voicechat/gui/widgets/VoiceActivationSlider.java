package de.maxhenkel.voicechat.gui.widgets;

import de.maxhenkel.voicechat.VoicechatClient;
import de.maxhenkel.voicechat.voice.common.Utils;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class VoiceActivationSlider extends AbstractSlider {

    public VoiceActivationSlider(int x, int y, int width, int height) {
        super(x, y, width, height, new StringTextComponent(""), Utils.dbToPerc(VoicechatClient.CLIENT_CONFIG.voiceActivationThreshold.get().floatValue()));
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        long db = Math.round(Utils.percToDb(value));
        setMessage(new TranslationTextComponent("message.voicechat.voice_activation", db));
    }

    @Override
    protected void applyValue() {
        VoicechatClient.CLIENT_CONFIG.voiceActivationThreshold.set(Utils.percToDb(value)).save();
    }

}
