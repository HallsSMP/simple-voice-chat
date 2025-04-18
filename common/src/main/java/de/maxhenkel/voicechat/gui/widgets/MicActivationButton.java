package de.maxhenkel.voicechat.gui.widgets;

import de.maxhenkel.voicechat.VoicechatClient;
import de.maxhenkel.voicechat.voice.client.MicrophoneActivationType;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class MicActivationButton extends AbstractButton {

    private static final Component PTT = new TranslatableComponent("message.voicechat.activation_type.ptt");
    private static final Component VOICE = new TranslatableComponent("message.voicechat.activation_type.voice");

    private MicrophoneActivationType type;
    private VoiceActivationSlider voiceActivationSlider;

    public MicActivationButton(int xIn, int yIn, int widthIn, int heightIn, VoiceActivationSlider voiceActivationSlider) {
        super(xIn, yIn, widthIn, heightIn, TextComponent.EMPTY);
        this.voiceActivationSlider = voiceActivationSlider;
        type = VoicechatClient.CLIENT_CONFIG.microphoneActivationType.get();
        updateText();
    }

    private void updateText() {
        if (MicrophoneActivationType.PTT.equals(type)) {
            setMessage(new TranslatableComponent("message.voicechat.activation_type", PTT));
            voiceActivationSlider.visible = false;
        } else if (MicrophoneActivationType.VOICE.equals(type)) {
            setMessage(new TranslatableComponent("message.voicechat.activation_type", VOICE));
            voiceActivationSlider.visible = true;
        }
    }

    @Override
    public void onPress() {
        type = MicrophoneActivationType.values()[(type.ordinal() + 1) % MicrophoneActivationType.values().length];
        VoicechatClient.CLIENT_CONFIG.microphoneActivationType.set(type).save();
        updateText();
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }
}
