package com.github.Minor2CCh.minium_me.compat.Jade;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.block.TemporallyBlock;
import com.github.Minor2CCh.minium_me.block.entity.TemporallyBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum TemporallyBlockProvider implements
        IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    private static final Identifier TEMPORALLY_BLOCK_TOOLTIP = Minium_me.of("temporally_block");
    @Override
    public void appendTooltip(
            ITooltip tooltip,
            BlockAccessor accessor,
            IPluginConfig config
    ) {
        if(!config.get(getUid())){
            return;
        }
        // 色付きテキストを生成
        boolean permanent = accessor.getBlockState().get(TemporallyBlock.FREEZE);
        Text text = Text.translatable("jade.plugin_minium_me.temporally_block.tooltip."+permanent)
                .formatted(permanent ? Formatting.GREEN : Formatting.RED);
        tooltip.add(Text.translatable("jade.plugin_minium_me.temporally_block.tooltip", text));
        if (accessor.getServerData().contains("placedTime")) {
            if(!accessor.getBlockState().get(TemporallyBlock.FREEZE)){
                tooltip.add(
                        Text.translatable(
                                "jade.plugin_minium_me.temporally_block.tooltip.disappear",
                                getDisappearTime(accessor.getServerData().getLong("placedTime"), accessor.getBlockState())
                        )
                );

            }
        }
    }
    private long getDisappearTime(long placedTime, BlockState state){
        if(state.get(TemporallyBlock.FREEZE)){
            return -1;
        }
        int extendMul = state.get(TemporallyBlock.EXTEND) + 1;
        return ((200L * extendMul) - placedTime) / 20 + 1;
    }

    @Override
    public void appendServerData(NbtCompound nbt, BlockAccessor accessor) {
        TemporallyBlockEntity be = (TemporallyBlockEntity) accessor.getBlockEntity();
        nbt.putLong("placedTime", be.placedTime);

    }

    @Override
    public Identifier getUid() {
        return TEMPORALLY_BLOCK_TOOLTIP;
    }
}
