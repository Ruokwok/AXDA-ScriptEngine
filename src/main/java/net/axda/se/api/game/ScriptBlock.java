package net.axda.se.api.game;

import cn.nukkit.block.Block;
import net.axda.se.api.API;
import net.axda.se.api.ProxyAPI;
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
}
