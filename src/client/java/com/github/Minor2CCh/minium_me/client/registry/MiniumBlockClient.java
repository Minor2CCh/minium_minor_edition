package com.github.Minor2CCh.minium_me.client.registry;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.client.block.AdvancedGrindingBlockClient;
import com.github.Minor2CCh.minium_me.client.block.GrindingBlockClient;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class MiniumBlockClient {
    public static void initialize(){
        if(!FabricLoader.getInstance().isModLoaded("neoforge")){//Sinytra環境だとワールドを閉じだときに確実に落ちるのでneoforgeではやらない
            MiniumBlock.EASY_GRINDER = MiniumBlock.register(new GrindingBlockClient(Block.Settings.copy(MiniumBlock.EASY_GRINDER)), MiniumBlock.getBlockId(MiniumBlock.EASY_GRINDER), new Item.Settings());
            MiniumBlock.ADVANCED_GRINDER = MiniumBlock.register(new AdvancedGrindingBlockClient(Block.Settings.copy(MiniumBlock.ADVANCED_GRINDER)), MiniumBlock.getBlockId(MiniumBlock.ADVANCED_GRINDER), new Item.Settings());
        }
    }

}
