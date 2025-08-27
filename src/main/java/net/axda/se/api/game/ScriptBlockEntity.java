package net.axda.se.api.game;

import cn.nukkit.blockentity.BlockEntity;
import net.axda.se.api.ProxyAPI;
import net.axda.se.api.ProxyField;
import net.axda.se.api.game.data.IntPos;
import net.axda.se.exception.UnsupportedMemberException;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

public class ScriptBlockEntity implements ProxyAPI {

    private final BlockEntity blockEntity;

    public ScriptBlockEntity(BlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public Object getOrigin() {
        return blockEntity;
    }

    @ProxyField
    public String name() {
        return blockEntity.getName();
    }

    @ProxyField
    public IntPos pos() {
        return new IntPos(blockEntity, blockEntity.getLevel());
    }

    @ProxyField
    public int type() {
        return blockEntity.getBlock().getId();
    }

    @HostAccess.Export
    public Object getNbt(Value... args) {
        throw new UnsupportedMemberException("getNbt");
    }

    @HostAccess.Export
    public boolean setNbt(Value... args) {
        throw new UnsupportedMemberException("setNbt");
    }

    @HostAccess.Export
    public ScriptBlock getBlock(Value... args) {
        return new ScriptBlock(blockEntity.getBlock());
    }
}
