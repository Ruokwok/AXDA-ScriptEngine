package net.axda.se.api.nbt;

import cn.nukkit.nbt.tag.CompoundTag;

public class NbtCompound {

    private CompoundTag nbt;

    public NbtCompound(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public CompoundTag getNbt() {
        return nbt;
    }

}
