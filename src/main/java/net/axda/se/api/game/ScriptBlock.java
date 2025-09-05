package net.axda.se.api.game;

import cn.nukkit.block.*;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.inventory.InventoryHolder;
import net.axda.se.api.API;
import net.axda.se.api.ProxyAPI;
import net.axda.se.api.ProxyField;
import net.axda.se.api.game.data.IntPos;
import net.axda.se.exception.UnsupportedMemberException;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

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
        return block.getDamage();
    }

    @ProxyField
    public int variant() {
        //抄的PNX-LLSE-Lib，他们也没弄懂这是什么。
        return -1;
    }

    @ProxyField
    public int translucency() {
        throw new UnsupportedMemberException("translucency");
    }

    @ProxyField
    public int thickness() {
        return (int) (block.getMaxY() - block.getMinY());
    }

    @ProxyField
    public boolean isAir() {
        return block.isAir();
    }

    @ProxyField
    public boolean isBounceBlock() {
        return block.getId() == 165;
    }

    @ProxyField
    public boolean isButtonBlock() {
        return block instanceof BlockButton;
    }

    @ProxyField
    public boolean isCropBlock() {
        return block instanceof BlockCrops;
    }

    @ProxyField
    public boolean isDoorBlock() {
        return block instanceof BlockDoor;
    }

    @ProxyField
    public boolean isFenceBlock() {
        return block instanceof BlockFence;
    }

    @ProxyField
    public boolean isFenceGateBlock() {
        return block instanceof BlockFenceGate;
    }

    @ProxyField
    public boolean isHeavyBlock() {
        return false;
    }

    @ProxyField
    public boolean isStemBlock() {
        return block instanceof BlockFlower || block instanceof BlockGrass || block instanceof BlockTallGrass;
    }

    @ProxyField
    public boolean isSlabBlock() {
        return block instanceof BlockSlab;
    }

    @ProxyField
    public boolean isUnbreakable() {
        return block.getHardness() == -1;
    }

    @ProxyField
    public boolean isWaterBlockingBlock() {
        return block instanceof BlockTransparentMeta;
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
        BlockEntity blockEntity = block.getLevelBlockEntity();
        if (blockEntity == null) return false;
        return blockEntity instanceof InventoryHolder;
    }

    @HostAccess.Export
    public Container getContainer(Value... args) {
        BlockEntity blockEntity = block.getLevelBlockEntity();
        if (blockEntity instanceof InventoryHolder inventoryHolder) {
            return new Container(inventoryHolder.getInventory());
        }
        return null;
    }

    @HostAccess.Export
    public boolean hasBlockEntity(Value... args) {
        BlockEntity blockEntity = block.getLevelBlockEntity();
        return blockEntity != null;
    }

    @HostAccess.Export
    public Object getBlockEntity(Value... args) {
        if (hasBlockEntity()) {
            return new ScriptBlockEntity(block.getLevelBlockEntity());
        }
        return null;
    }

    @HostAccess.Export
    public boolean removeBlockEntity(Value... args) {
        throw new UnsupportedMemberException("removeBlockEntity");
    }
}
