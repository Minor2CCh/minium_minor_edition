package com.github.Minor2CCh.minium_me.block;

import net.minecraft.block.BulbBlock;

public class MiniumBulbBlock extends BulbBlock {
    public MiniumBulbBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, Boolean.TRUE).with(POWERED, Boolean.FALSE));
    }
}
