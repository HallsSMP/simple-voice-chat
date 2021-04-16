package de.maxhenkel.voicechat.voice.common;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.PacketByteBuf;

import javax.annotation.Nullable;

public class PlayerState {

    private boolean disabled;
    private boolean disconnected;
    private GameProfile gameProfile;
    @Nullable
    private String group;

    public PlayerState(boolean disabled, boolean disconnected, GameProfile gameProfile) {
        this.disabled = disabled;
        this.disconnected = disconnected;
        this.gameProfile = gameProfile;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisconnected() {
        return disconnected;
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public void setGameProfile(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
    }

    @Nullable
    public String getGroup() {
        return group;
    }

    /**
     * @param group the group name (Max 16 characters)
     */
    public void setGroup(@Nullable String group) {
        this.group = group;
    }

    public boolean hasGroup() {
        return group != null;
    }

    public static PlayerState fromBytes(PacketByteBuf buf) {
        PlayerState state = new PlayerState(buf.readBoolean(), buf.readBoolean(), NbtHelper.toGameProfile(buf.readCompoundTag()));

        if (buf.readBoolean()) {
            state.setGroup(buf.readString(16));
        }

        return state;
    }

    public void toBytes(PacketByteBuf buf) {
        buf.writeBoolean(disabled);
        buf.writeBoolean(disconnected);
        buf.writeCompoundTag(NbtHelper.fromGameProfile(new CompoundTag(), gameProfile));
        buf.writeBoolean(hasGroup());
        if (hasGroup()) {
            buf.writeString(group, 16);
        }
    }

}
