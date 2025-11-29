package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.item.MiniumMultiToolItem;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;

public class MultiToolDurabilityMultiplicationEvent {
    public static void initialize(){
        DefaultItemComponentEvents.MODIFY.register(context -> {
            modifyDurability(context, (MiniumMultiToolItem) MiniumItem.MINIUM_MULTITOOL, 3);
            modifyDurability(context, (MiniumMultiToolItem) MiniumItem.C_MINIUM_MULTITOOL, 3);
            modifyDurability(context, (MiniumMultiToolItem) MiniumItem.IRIS_QUARTZ_MULTITOOL, 3);
        });
        // ToolItemはMaterialで耐久値が上書きされるので自分のMODのものでも同じマテリアル内で変えたい場合は上書きする
    }

    private static void modifyDurability(DefaultItemComponentEvents.ModifyContext context, MiniumMultiToolItem item, @SuppressWarnings("all")int mul){
        context.modify(item, (builder) -> builder.add(DataComponentTypes.MAX_DAMAGE, (int)Math.min(((long)item.getMaterial().getDurability())*mul, 2147483647)));
    }
}
