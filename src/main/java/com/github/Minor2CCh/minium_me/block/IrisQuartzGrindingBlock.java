package com.github.Minor2CCh.minium_me.block;

import com.github.Minor2CCh.minium_me.registry.MiniumDamageTypes;
import com.github.Minor2CCh.minium_me.enchantment.MiniumEnchantments;
import com.github.Minor2CCh.minium_me.item.FDItems;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class IrisQuartzGrindingBlock extends GrindingBlock{
    private Supplier<ItemStack> attackStack = () -> FabricLoader.getInstance().isModLoaded("farmersdelight") ? new ItemStack(FDItems.IRIS_QUARTZ_KNIFE.get()) : new ItemStack(MiniumItem.IRIS_QUARTZ_SWORD);
    public IrisQuartzGrindingBlock(Settings settings) {
        super(settings);
        GRIND_DAMAGE = 15.0F;
    }
    @Override
    protected void damageEntity(World world, LivingEntity livingEntity, BlockPos pos) {
        FakePlayer fakePlayer = FakePlayer.get((ServerWorld) world);
        if(!this.attackStack.get().hasEnchantments() && world instanceof ServerWorld){
            ItemStack tempStack = attackStack.get().copy();
            MiniumEnchantments.giveCustomEnchantment((ServerWorld) world, tempStack, 10, Enchantments.LOOTING);
            attackStack = () -> tempStack;
        }
        fakePlayer.setStackInHand(Hand.MAIN_HAND, attackStack.get().copy());
        fakePlayer.setPos(pos.getX()+0.5, pos.getY(), pos.getZ()+0.5);
        DamageSource damageSource = fakePlayer.getDamageSources().create(MiniumDamageTypes.MINIUM_GRINDING, fakePlayer);
        livingEntity.damage(damageSource, GRIND_DAMAGE);
    }
}
