package net.axda.se.api.game;

import cn.nukkit.block.Block;
import net.axda.se.api.API;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyExecutable;
import org.graalvm.polyglot.proxy.ProxyObject;

public class ScriptBlock extends API implements ProxyObject {

    private Block block;

    public ScriptBlock(Block block) {
        this.block = block;
    }

    public Block getNukkitBlock() {
        return block;
    }

    @Override
    public Object getMember(String key) {
        switch (key) {
            case "..nukkit_block..": return block;
            //方块对象属性
            case "name": return null;
            case "type": return null;
            case "id": return null;
            case "pos": return null;
            case "tileData": return null;
            case "variant": return null;
            case "translucency": return null;
            case "thickness": return null;
            case "isAir": return null;
            case "isBounceBlock": return null;
            case "isButtonBlock": return null;
            case "isCropBlock": return null;
            case "isDoorBlock": return null;
            case "isFenceBlock": return null;
            case "isFenceGateBlock": return null;
            case "isThinFenceBlock": return null;
            case "isHeavyBlock": return null;
            case "isStemBlock": return null;
            case "isSlabBlock": return null;
            case "isUnbreakable": return null;
            case "isWaterBlockingBlock": return null;

            //方块对象函数
            case "destroy": return (ProxyExecutable) null;
            case "getNbt": return (ProxyExecutable) null;
            case "setNbt": return (ProxyExecutable) null;
            case "getBlockState": return (ProxyExecutable) null;
            case "hasContainer": return (ProxyExecutable) null;
            case "getContainer": return (ProxyExecutable) null;
            case "hasBlockEntity": return (ProxyExecutable) null;
            case "getBlockEntity": return (ProxyExecutable) null;
            case "removeBlockEntity": return (ProxyExecutable) null;
        }
        return null;
    }

    @Override
    public Object getMemberKeys() {
        return null;
    }

    @Override
    public boolean hasMember(String key) {
        return false;
    }

    @Override
    public void putMember(String key, Value value) {

    }
}
