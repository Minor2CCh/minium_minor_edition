package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.component.EnergyComponent;
import com.github.Minor2CCh.minium_me.component.GunReloadComponent;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.entity.EnergyBulletEntity;
import com.github.Minor2CCh.minium_me.sound.MiniumSoundsEvent;
import com.github.Minor2CCh.minium_me.util.HasCustomTooltip;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;


public class MiniumGunItem extends Item implements HasCustomTooltip {
    public static final DispenserBehavior DISPENSER_BEHAVIOR = new ItemDispenserBehavior() {
        @Override
        protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            dispenseGun(pointer, stack);
            return stack;
        }
        @Override
        protected void playSound(BlockPointer pointer) {
        }
    };
    public MiniumGunItem(Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    public static void dispenseGun(BlockPointer pointer, ItemStack stack) {
        EnergyComponent EComp = EnergyComponent.getEnergyComponent(stack);
        int remain = EComp.remain();
        EnergyComponent.EnergyType energyType = EComp.energyType();
        World world = pointer.world();
        if(EComp.isEmpty() || remain <= (energyConsumption(energyType) - 1)){
            world.playSound(
                    null,
                    pointer.pos().getX(),
                    pointer.pos().getY(),
                    pointer.pos().getZ(),
                    MiniumSoundsEvent.ENERGY_GUN_EMPTY_EVENT,
                    SoundCategory.NEUTRAL,
                    1.0F,
                    1.0F//音を鳴らす
            );
        }else{
            world.playSound(
                    null,
                    pointer.pos().getX(),
                    pointer.pos().getY(),
                    pointer.pos().getZ(),
                    MiniumSoundsEvent.SHOOT_ENERGY_BULLET_EVENT,
                    SoundCategory.NEUTRAL,
                    1.0F,
                    1.0F//音を鳴らす
            );
            if(!Objects.equals(EComp.energyType(), EnergyComponent.EnergyType.ENERGY_PLATINUM) || world.getRandom().nextInt(5) == 0){
                remain -= energyConsumption(energyType);
            }
            stack.set(MiniumModComponent.REMAIN_ENERGY, new EnergyComponent(remain, energyType));
            if (!world.isClient) {
                EnergyBulletEntity energybulletEntity = new EnergyBulletEntity(world, stack, pointer.centerPos());
                Direction dir = pointer.state().get(DispenserBlock.FACING);
                Vec3i v = dir.getVector();
                /*
                double extraX = pointer.state().get(DispenserBlock.FACING) == Direction.EAST ? 0.5 : (pointer.state().get(DispenserBlock.FACING) == Direction.WEST ? -0.5 : 0);
                double extraY = pointer.state().get(DispenserBlock.FACING) == Direction.UP ? 0.5 : (pointer.state().get(DispenserBlock.FACING) == Direction.DOWN ? -0.5 : 0);
                double extraZ = pointer.state().get(DispenserBlock.FACING) == Direction.NORTH ? -0.5 : (pointer.state().get(DispenserBlock.FACING) == Direction.SOUTH ? 0.5 : 0);
                Vec3d dispensePos = new Vec3d(pointer.centerPos().getX()+extraX, pointer.centerPos().getY()+extraY, pointer.centerPos().getZ()+extraZ);
                 */
                double x = v.getX();
                double y = v.getY();
                double z = v.getZ();
                float yaw = (float) (Math.atan2(-x, z) * 180.0F / Math.PI);
                float pitch = (float) (Math.atan2(-y, Math.sqrt(x * x + z * z)) * 180.0F / Math.PI);
                energybulletEntity.setVelocity(energybulletEntity, pitch, yaw, 0.0F, 1.0F, 0.25F);
                energybulletEntity.setPos(pointer.centerPos().getX(), pointer.centerPos().getY(), pointer.centerPos().getZ());
                world.spawnEntity(energybulletEntity);

            }
        }

    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        EnergyComponent EComp = EnergyComponent.getEnergyComponent(itemStack);
        int remain = EComp.remain();
        EnergyComponent.EnergyType energyType = EComp.energyType();
        if(remain <= 0 && energyType.isEmpty()){
            itemStack.set(MiniumModComponent.REMAIN_ENERGY, new EnergyComponent(remain, energyType));
            if(remain < -1){
                remain = 0;
            }
        }
        //装填されている種類と同じ弾がオフハンドあるor空の場合は装填、ただしオフハンドで持っている場合は装填できない
        if(itemStack != user.getEquippedStack(EquipmentSlot.OFFHAND)){//オフハンドのエネルギーガンか否か
            boolean bl = false;
            ItemStack prevStack = GunReloadComponent.getPrevStack(itemStack);
            long prevTime = GunReloadComponent.getPrevTime(itemStack);
            if(!user.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty()){
                setPrevReloadTimeAndStack(user, itemStack);
                bl = reloadEnergy(itemStack, user.getEquippedStack(EquipmentSlot.OFFHAND), user);
            }else if(world.getTime() - prevTime >= 0 && world.getTime() - prevTime < 10 && !prevStack.isEmpty()){
                PlayerInventory inventory = user.getInventory();
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack useStack = inventory.getStack(i);
                    if (!useStack.isEmpty() && ItemStack.areItemsAndComponentsEqual(useStack, prevStack)) {
                        if(reloadEnergy(itemStack, useStack, user)){
                            bl = true;
                        }
                    }


                }
            }
            if(bl) {
                world.playSound(null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        MiniumSoundsEvent.RELOAD_ENERGY_GUN_EVENT,
                        SoundCategory.NEUTRAL,
                        1.0F,
                        1.0F//音を鳴らす
                );
                return TypedActionResult.success(itemStack, world.isClient());//itemStackの部分を変えるとアイテムがそれに化ける
            }
        }



        if(EComp.isEmpty() || remain <= (energyConsumption(energyType) - 1)){
            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    MiniumSoundsEvent.ENERGY_GUN_EMPTY_EVENT,
                    SoundCategory.NEUTRAL,
                    1.0F,
                    1.0F//音を鳴らす
            );
            return TypedActionResult.success(itemStack, world.isClient());//itemStackの部分を変えるとアイテムがそれに化ける
        }else{
            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    MiniumSoundsEvent.SHOOT_ENERGY_BULLET_EVENT,
                    SoundCategory.NEUTRAL,
                    1.0F,
                    1.0F//音を鳴らす
            );
            if(!user.isCreative()) {
                if(!Objects.equals(EComp.energyType(), EnergyComponent.EnergyType.ENERGY_PLATINUM) || world.getRandom().nextInt(5) == 0){
                    remain -= energyConsumption(energyType);
                }
            }
            itemStack.set(MiniumModComponent.REMAIN_ENERGY, new EnergyComponent(remain, energyType));
            if (!world.isClient) {
                EnergyBulletEntity energybulletEntity = new EnergyBulletEntity(world, user, itemStack);
                energybulletEntity.setVelocity(energybulletEntity, user.getPitch(), user.getYaw(), 0.0F, 1.0F, 1.0F);
                energybulletEntity.setPos(energybulletEntity.getX(), user.getEyeY() - 0.1F, energybulletEntity.getZ());
                world.spawnEntity(energybulletEntity);
                user.getItemCooldownManager().set(this, (Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_REDSTONE) ? 7 : ((Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_ALUMINIUM) ? 5 : 10))));

            }
        }


        return TypedActionResult.success(itemStack, world.isClient());//itemStackの部分を変えるとアイテムがそれに化ける
    }
    private void setPrevReloadTimeAndStack(PlayerEntity player, ItemStack stack){
        stack.set(MiniumModComponent.GUN_RELOAD, new GunReloadComponent(player.getWorld().getTime(), player.getEquippedStack(EquipmentSlot.OFFHAND).copy()));
    }



    private boolean reloadEnergy(ItemStack itemStack, ItemStack consumeStack, PlayerEntity user){
        //System.out.println(consumeStack);
        EnergyComponent EComp = EnergyComponent.getEnergyComponent(itemStack);
        int remain = EComp.remain();
        boolean bl = false;
        EnergyComponent.EnergyType energyType = (remain > 0) ? EComp.energyType() : EnergyComponent.EnergyType.ENERGY_EMPTY;
        for (EnergyComponent.EnergyType forType : EnergyComponent.EnergyType.values()) {
            if(forType.isEmpty())continue;
            if(energyType.isEmpty() || Objects.equals(energyType, forType)){
                if(forType.getMaterial() != null && consumeStack.isIn(forType.getMaterial())){
                    int count = remainCount(remain, consumeStack.getCount(), 1);
                    remain += count;
                    energyType = forType;
                    consumeStack.decrementUnlessCreative(count, user);
                    bl = (count > 0);
                    break;
                }else if(forType.getMaterialSB() != null && consumeStack.isIn(forType.getMaterialSB())){
                    int count = remainCount(remain, consumeStack.getCount(), forType.getRemainSB());
                    remain += count * forType.getRemainSB();
                    energyType = forType;
                    consumeStack.decrementUnlessCreative(count, user);
                    bl = (count > 0);
                    break;
                }
            }

        }
        EnergyComponent.setEnergyComponent(itemStack, new EnergyComponent(remain, energyType));
        return bl;
    }
    private int remainCount(int baseRemain, int materialCount, int energyRemain){
        for(int i=materialCount;i>0;i--){
            long calcRemain = (long) baseRemain + ((long) i * energyRemain);
            if(calcRemain <= 2147483647){
                return i;
            }
        }
        return 0;
    }

    protected static int energyConsumption(EnergyComponent.EnergyType energyType){
        if(energyType.equals(EnergyComponent.EnergyType.ENERGY_COPPER) || energyType.equals(EnergyComponent.EnergyType.ENERGY_BRONZE) || energyType.equals(EnergyComponent.EnergyType.ENERGY_BRASS)){
            return 2;
        }
        return 1;
    }
    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        EnergyComponent EComp = EnergyComponent.getEnergyComponent(stack);
        return !EComp.isEmpty();
    }
    @Override
    public int getItemBarStep(ItemStack stack) {
        EnergyComponent EComp = EnergyComponent.getEnergyComponent(stack);

        return MathHelper.clamp(Math.round(EComp.remain() / 64.0F * 13.0F) + (EComp.isEmpty() ? 13 : 0), 0, 13);
    }
    @Override
    public int getItemBarColor(ItemStack stack) {
        if(stack.contains(MiniumModComponent.REMAIN_ENERGY)){
            EnergyComponent EComp = EnergyComponent.getEnergyComponent(stack);
            return EComp.getColor();
        }else{
            return 0;
        }
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
    @Override
    public void customTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type, boolean hasShiftDown) {
        EnergyComponent EComp = EnergyComponent.getEnergyComponent(itemStack);
        int remain = EComp.remain();
        if(itemStack.contains(MiniumModComponent.REMAIN_ENERGY)){
            tooltip.add(Text.translatable("item.minium_me.energy.remain", remain).formatted(Formatting.GOLD));
            int color = EComp.getColor();
            String energyName = EComp.getEnergyKey();
            tooltip.add(Text.translatable("item.minium_me.energy.type", Text.translatable(energyName)).withColor(color));


            if (hasShiftDown) {
                tooltip.add(Text.translatable(energyName +".desc").formatted(Formatting.WHITE));
                var matchingItems = Registries.ITEM.iterateEntries(EComp.getMaterial());
                var matchingItemsSB = Registries.ITEM.iterateEntries(EComp.getMaterialSB());
                if(!matchingItems.iterator().hasNext() && !matchingItemsSB.iterator().hasNext() && !EComp.isEmpty()){
                    tooltip.add(Text.translatable("item.minium_me.energy.unusable"));

                }
            } else {
                tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
            }

        }
    }

}
