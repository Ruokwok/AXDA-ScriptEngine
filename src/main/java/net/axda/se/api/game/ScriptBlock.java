package net.axda.se.api.game;

import cn.nukkit.block.Block;
import net.axda.se.api.API;
import net.axda.se.api.ProxyAPI;
import net.axda.se.api.ProxyField;
import net.axda.se.api.game.data.IntPos;
import net.axda.se.exception.UnsupportedMemberException;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyExecutable;
import org.graalvm.polyglot.proxy.ProxyObject;

public class ScriptBlock extends API implements ProxyAPI {

    private Block block;

    public ScriptBlock(Block block) {
        this.block = block;
    }

    public Block getNukkitBlock() {
        return block;
    }

    @Override
    public Object getOrigin() {
        return block;
    }

    @ProxyField
    public String name() {
        return block.getName();
    }

    @ProxyField
    public String type() {
        return block.getSaveId();
    }

    @ProxyField
    public int id() {
        return block.getId();
    }

    @ProxyField
    public IntPos pos() {
        return new IntPos(block, block.getLevel());
    }

    @ProxyField
    public int tileData() {
        throw new UnsupportedMemberException("tileData");
    }

    @ProxyField
    public int variant() {
        throw new UnsupportedMemberException("variant");
    }

    @ProxyField
    public int translucency() {
        throw new UnsupportedMemberException("translucency");
    }

    @ProxyField
    public int thickness() {
        throw new UnsupportedMemberException("thickness");
    }

    @ProxyField
    public boolean isAir() {
        return block.getId() == Block.AIR;
    }

    @ProxyField
    public boolean isBounceBlock() {
        throw new UnsupportedMemberException("isBounceBlock");
    }

    @ProxyField
    public boolean isButtonBlock() {
        throw new UnsupportedMemberException("isButtonBlock");
    }

    @ProxyField
    public boolean isCropBlock() {
        throw new UnsupportedMemberException("isCropBlock");
    }

    @ProxyField
    public boolean isDoorBlock() {
        throw new UnsupportedMemberException("isDoorBlock");
    }

    @ProxyField
    public boolean isFenceBlock() {
        throw new UnsupportedMemberException("isFenceBlock");
    }

    @ProxyField
    public boolean isFenceGateBlock() {
        throw new UnsupportedMemberException("isFenceGateBlock");
    }

    @ProxyField
    public boolean isHeavyBlock() {
        throw new UnsupportedMemberException("isHeavyBlock");
    }

    @ProxyField
    public boolean isStemBlock() {
        throw new UnsupportedMemberException("isStemBlock");
    }

    @ProxyField
    public boolean isSlabBlock() {
        throw new UnsupportedMemberException("isSlabBlock");
    }

    @ProxyField
    public boolean isUnbreakable() {
        throw new UnsupportedMemberException("isUnbreakable");
    }

    @ProxyField
    public boolean isWaterBlockingBlock() {
        throw new UnsupportedMemberException("isWaterBlockingBlock");
    }

    @HostAccess.Export
    public boolean destroy(Value... args) {
        throw new UnsupportedMemberException("destroy");
    }

    @HostAccess.Export
    public Object getNbt(Value... args) {
        throw new UnsupportedMemberException("getNbt");
    }

    @ProxyField
    public boolean setNbt(Value... args) {
        throw new UnsupportedMemberException("setNbt");
    }

    @HostAccess.Export
    public Object getBlockState(Value... args) {
        throw new UnsupportedMemberException("getBlockState");
    }

    @HostAccess.Export
    public boolean hasContainer(Value... args) {
        throw new UnsupportedMemberException("hasContainer");
    }

    @HostAccess.Export
    public Object getContainer(Value... args) {
        throw new UnsupportedMemberException("getContainer");
    }

    @HostAccess.Export
    public boolean hasBlockEntity(Value... args) {
        throw new UnsupportedMemberException("hasBlockEntity");
    }

    @HostAccess.Export
    public Object getBlockEntity(Value... args) {
        throw new UnsupportedMemberException("getBlockEntity");
    }

    @HostAccess.Export
    public boolean removeBlockEntity(Value... args) {
        throw new UnsupportedMemberException("removeBlockEntity");
    }
}
