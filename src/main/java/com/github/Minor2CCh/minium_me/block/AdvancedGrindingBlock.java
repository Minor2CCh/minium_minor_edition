package com.github.Minor2CCh.minium_me.block;

import com.github.Minor2CCh.minium_me.damage_type.MiniumDamageType;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class AdvancedGrindingBlock extends GrindingBlock{
    protected static final float GRIND_DAMAGE = 15.0F;
    public AdvancedGrindingBlock(Settings settings) {
        super(settings);
    }
    @Override
    protected void damageEntity(World world, LivingEntity livingEntity, BlockPos pos) {
        FakePlayer fakePlayer = FakePlayer.get((ServerWorld) world);
        fakePlayer.setPos(pos.getX(), pos.getY(), pos.getZ());
        DamageSource damageSource = new DamageSource(
                world.getRegistryManager()
                        .get(RegistryKeys.DAMAGE_TYPE)
                        .entryOf(MiniumDamageType.MINIUM_GRINDING), fakePlayer);
        livingEntity.damage(damageSource, GRIND_DAMAGE);
    }
    @Override
    public void customTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options, boolean hasShiftDown) {
        if (hasShiftDown) {
            tooltip.add(Text.translatable(stack.getItem().getTranslationKey()+".desc", GRIND_DAMAGE).formatted(Formatting.WHITE));
        } else {
            tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
        }
    }
}
