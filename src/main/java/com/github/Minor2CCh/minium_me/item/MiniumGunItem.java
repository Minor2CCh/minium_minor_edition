package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.entity.EnergyBulletEntity;
import com.github.Minor2CCh.minium_me.sound.MiniumSoundsEvent;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class MiniumGunItem extends Item implements HasCustomTooltip{
    private final HashMap<UUID, ItemStack> prevStack = new HashMap<>();
    private final HashMap<UUID, ItemStack> prevStackClient = new HashMap<>();
    private final HashMap<UUID, Long> prevReloadTime = new HashMap<>();
    private final HashMap<UUID, Long> prevReloadTimeClient = new HashMap<>();
    public MiniumGunItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        MiniumModComponent.EnergyComponent EComp = itemStack.get(MiniumModComponent.REMAIN_ENERGY);
        int remain = EComp != null ? EComp.remain() : 0;
        String energyType = EComp != null ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;
        if(remain <= 0 && !Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY)){
            energyType = MiniumModComponent.ENERGY_EMPTY;
            itemStack.set(MiniumModComponent.REMAIN_ENERGY, new MiniumModComponent.EnergyComponent(remain, energyType));
            if(remain < -1){
                remain = 0;
            }
        }
        if(!isRightEnergy(itemStack)) {
            remain = 0;
            energyType = MiniumModComponent.ENERGY_EMPTY;
            itemStack.set(MiniumModComponent.REMAIN_ENERGY, new MiniumModComponent.EnergyComponent(remain, energyType));
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
        }
        //装填されている種類と同じ弾がオフハンドあるor空の場合は装填、ただしオフハンドで持っている場合は装填できない
        if(itemStack != user.getEquippedStack(EquipmentSlot.OFFHAND)){//オフハンドのエネルギーガンか否か
            boolean bl = false;
            if(!user.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty()){
                setPrevReloadTimeAndStack(user);
                bl = reloadEnergy(itemStack, user.getEquippedStack(EquipmentSlot.OFFHAND), user);
            }else if(world.getTime() - getPrevReloadTime(user) < 10){
                PlayerInventory inventory = user.getInventory();
                ItemStack preIngredient = getPrevStack(user);
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack useStack = inventory.getStack(i);
                    if(preIngredient != null){
                        if (!useStack.isEmpty() && ItemStack.areItemsAndComponentsEqual(useStack, preIngredient)) {
                            if(reloadEnergy(itemStack, useStack, user)){
                                bl = true;
                                removePrevReloadTimeAndStack(user);
                            }
                        }
                    }

                }
            }
            if(bl) {
                world.playSound(
                        null,
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



        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || remain <= (energyConsumption(energyType) - 1)){
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
                remain -= energyConsumption(energyType);
            }
            itemStack.set(MiniumModComponent.REMAIN_ENERGY, new MiniumModComponent.EnergyComponent(remain, energyType));
            if (!world.isClient) {
                EnergyBulletEntity energybulletEntity = new EnergyBulletEntity(world, user, itemStack);
                energybulletEntity.setPitch(user.getPitch());
                energybulletEntity.setYaw(user.getYaw());
                energybulletEntity.setVelocity(0.0, 0.0, 0.0);
                energybulletEntity.setVelocity(energybulletEntity, energybulletEntity.getPitch(), energybulletEntity.getYaw(), 0.0F, 1.5F, 1.0F);
                world.spawnEntity(energybulletEntity);
                user.getItemCooldownManager().set(this, (Objects.equals(energyType, MiniumModComponent.ENERGY_REDSTONE) ? 7 : ((Objects.equals(energyType, MiniumModComponent.ENERGY_ALUMINIUM) ? 5 : 10))));

            }
        }


        return TypedActionResult.success(itemStack, world.isClient());//itemStackの部分を変えるとアイテムがそれに化ける
    }
    private void setPrevReloadTimeAndStack(PlayerEntity player){
        if(player.getWorld().isClient()){
            prevReloadTimeClient.put(player.getUuid(), player.getWorld().getTime());
            prevStackClient.put(player.getUuid(), player.getEquippedStack(EquipmentSlot.OFFHAND).copy());
        }else{
            prevReloadTime.put(player.getUuid(), player.getWorld().getTime());
            prevStack.put(player.getUuid(), player.getEquippedStack(EquipmentSlot.OFFHAND).copy());
        }
    }
    private void removePrevReloadTimeAndStack(PlayerEntity player){
        if(player.getWorld().isClient()){
            prevReloadTimeClient.remove(player.getUuid());
            prevStackClient.remove(player.getUuid());
        }else{
            prevReloadTime.remove(player.getUuid());
            prevStack.remove(player.getUuid());
        }
    }
    private long getPrevReloadTime(PlayerEntity player){
        if(player.getWorld().isClient()){
            return prevReloadTimeClient.getOrDefault(player.getUuid(), 0L);
        }else{
            return prevReloadTime.getOrDefault(player.getUuid(), 0L);
        }
    }
    private ItemStack getPrevStack(PlayerEntity player){
        if(player.getWorld().isClient()){
            return prevStackClient.getOrDefault(player.getUuid(), null);
        }else{
            return prevStack.getOrDefault(player.getUuid(), null);
        }
    }



    private boolean reloadEnergy(ItemStack itemStack, ItemStack consumeStack, PlayerEntity user){
        //System.out.println(consumeStack);
        MiniumModComponent.EnergyComponent EComp = itemStack.get(MiniumModComponent.REMAIN_ENERGY);
        int remain = EComp != null ? EComp.remain() : 0;
        boolean bl = false;
        String energyType = (EComp != null && remain > 0) ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;
        for (String energy : MiniumModComponent.ENERGY_LIST) {
            if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, energy)){
                if(MiniumModComponent.getEnergyMaterial(energy) != null && consumeStack.isIn(MiniumModComponent.getEnergyMaterial(energy))){
                    int count = remainCount(remain, consumeStack.getCount(), 1);
                    remain += count;
                    energyType = energy;
                    consumeStack.decrementUnlessCreative(count, user);
                    bl = (count > 0);
                    break;
                }else if(MiniumModComponent.getEnergyMaterialSB(energy) != null && consumeStack.isIn(MiniumModComponent.getEnergyMaterialSB(energy))){
                    int count = remainCount(remain, consumeStack.getCount(), MiniumModComponent.getEnergyRemainSB(energy));
                    remain += count * MiniumModComponent.getEnergyRemainSB(energy);
                    energyType = energy;
                    consumeStack.decrementUnlessCreative(count, user);
                    bl = (count > 0);
                    break;
                }
            }

        }
        itemStack.set(MiniumModComponent.REMAIN_ENERGY, new MiniumModComponent.EnergyComponent(remain, energyType));
        return bl;
    }
    protected boolean isRightEnergy(ItemStack itemStack){
        MiniumModComponent.EnergyComponent EComp = itemStack.get(MiniumModComponent.REMAIN_ENERGY);
        String energyType = EComp != null ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;
        return energyType.equals(MiniumModComponent.ENERGY_EMPTY) || MiniumModComponent.ENERGY_LIST.contains(energyType);

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

    protected int energyConsumption(String energyType){
        if(energyType.equals(MiniumModComponent.ENERGY_COPPER) || energyType.equals(MiniumModComponent.ENERGY_BRONZE)){
            return 2;
        }
        return 1;
    }
    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        MiniumModComponent.EnergyComponent EComp = stack.get(MiniumModComponent.REMAIN_ENERGY);
        //int remain = EComp != null ? EComp.remain() : 0;
        String energyType = EComp != null ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;
        return !Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) && isRightEnergy(stack);
    }
    @Override
    public int getItemBarStep(ItemStack stack) {
        MiniumModComponent.EnergyComponent EComp = stack.get(MiniumModComponent.REMAIN_ENERGY);
        int remain = EComp != null ? EComp.remain() : 0;
        String energyType = EComp != null ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;

        return MathHelper.clamp(Math.round(remain / 64.0F * 13.0F) + (Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) ? 13 : 0), 0, 13);
    }
    @Override
    public int getItemBarColor(ItemStack stack) {
        if(stack.contains(MiniumModComponent.REMAIN_ENERGY)){
            MiniumModComponent.EnergyComponent EComp = stack.get(MiniumModComponent.REMAIN_ENERGY);
            String energyType = EComp != null ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;
            return MiniumModComponent.getEnergyColor(energyType);
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
        MiniumModComponent.EnergyComponent EComp = itemStack.get(MiniumModComponent.REMAIN_ENERGY);
        int remain = EComp != null ? EComp.remain() : 0;
        String energyType = EComp != null ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;
        if(itemStack.contains(MiniumModComponent.REMAIN_ENERGY)){
            tooltip.add(Text.translatable("item.minium_me.energy.remain", remain).formatted(Formatting.GOLD));
            int color = MiniumModComponent.getEnergyColor(energyType);
            String energyName = MiniumModComponent.getEnergyKey(energyType);
            tooltip.add(Text.translatable("item.minium_me.energy.type", Text.translatable(energyName)).withColor(color));


            if (hasShiftDown) {
                tooltip.add(Text.translatable(energyName +".desc").formatted(Formatting.WHITE));
                var matchingItems = Registries.ITEM.iterateEntries(MiniumModComponent.getEnergyMaterial(energyType));
                var matchingItemsSB = Registries.ITEM.iterateEntries(MiniumModComponent.getEnergyMaterialSB(energyType));
                if(!matchingItems.iterator().hasNext() && !matchingItemsSB.iterator().hasNext() && MiniumModComponent.ENERGY_LIST.contains(energyType)){
                    tooltip.add(Text.translatable("item.minium_me.energy.unusable"));

                }
            } else {
                tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
            }

        }
    }

}
