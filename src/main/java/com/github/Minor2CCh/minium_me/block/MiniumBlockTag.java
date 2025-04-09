package com.github.Minor2CCh.minium_me.block;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class MiniumBlockTag {

    public static final TagKey<Block> MULTITOOL_MINEABLE = of("mineable/multitool");
    public static final TagKey<Block> DEEPSLATE_MINER_CAN_BREAK = of("deepslate_miner_can_break");

        private MiniumBlockTag() {
        }

        private static TagKey<Block> of(String id) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Minium_me.MOD_ID, id));
        }
}
