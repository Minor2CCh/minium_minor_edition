package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.entity.EnergyBulletEntity;
import com.github.Minor2CCh.minium_me.sound.MiniumSoundsEvent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;


public class MiniumGunItem extends Item{
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
        boolean bl = (itemStack == user.getEquippedStack(EquipmentSlot.OFFHAND));//オフハンドのエネルギーガンか否か
        if(!bl && !user.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty()){
            bl = reloadEnergy(itemStack, EComp, user);
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
                EnergyBulletEntity energybulletEntity = new EnergyBulletEntity(world, user, energyType);
                energybulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
                world.spawnEntity(energybulletEntity);
                user.getItemCooldownManager().set(this, (Objects.equals(energyType, MiniumModComponent.ENERGY_REDSTONE) ? 7 : ((Objects.equals(energyType, MiniumModComponent.ENERGY_ALUMINIUM) ? 5 : 10))));

            }
        }
        /*
        ItemStack itemStackoffhand = user.getEquippedStack(EquipmentSlot.OFFHAND);
        if(itemStackoffhand.isIn(ENERGY_OSMIUM) && false){//対象のItemStackに該当のタグがあるか確認
            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ITEM_TRIDENT_THUNDER,
                    SoundCategory.NEUTRAL,
                    0.75F,
                    1.0F//音を鳴らす
            );
            //デバッグタイプチェンジ
            if(user.isCreative()) {
                switch (energyType) {
                    case minium_modcomponent.ENERGY_EMPTY:
                        energyType = minium_modcomponent.ENERGY_COAL;
                        break;
                    case minium_modcomponent.ENERGY_COAL:
                        energyType = minium_modcomponent.ENERGY_IRON;
                        break;
                    case minium_modcomponent.ENERGY_IRON:
                        energyType = minium_modcomponent.ENERGY_COPPER;
                        break;
                    case minium_modcomponent.ENERGY_COPPER:
                        energyType = minium_modcomponent.ENERGY_GOLD;
                        break;
                    case minium_modcomponent.ENERGY_GOLD:
                        energyType = minium_modcomponent.ENERGY_LAPIS;
                        break;
                    case minium_modcomponent.ENERGY_LAPIS:
                        energyType = minium_modcomponent.ENERGY_REDSTONE;
                        break;
                    case minium_modcomponent.ENERGY_REDSTONE:
                        energyType = minium_modcomponent.ENERGY_DIAMOND;
                        break;
                    case minium_modcomponent.ENERGY_DIAMOND:
                        energyType = minium_modcomponent.ENERGY_EMERALD;
                        break;
                    case minium_modcomponent.ENERGY_EMERALD:
                        energyType = minium_modcomponent.ENERGY_QUARTZ;
                        break;
                    case minium_modcomponent.ENERGY_QUARTZ:
                        energyType = minium_modcomponent.ENERGY_GLOWSTONE;
                        break;
                    case minium_modcomponent.ENERGY_GLOWSTONE:
                        energyType = minium_modcomponent.ENERGY_NETHERITE;
                        break;
                    case minium_modcomponent.ENERGY_NETHERITE:
                        energyType = minium_modcomponent.ENERGY_AMETHYST;
                        break;
                    case minium_modcomponent.ENERGY_AMETHYST:
                        energyType = minium_modcomponent.ENERGY_MINIUM;
                        break;
                    case minium_modcomponent.ENERGY_MINIUM:
                        energyType = minium_modcomponent.ENERGY_C_MINIUM;
                        break;
                    case minium_modcomponent.ENERGY_C_MINIUM:
                        energyType = minium_modcomponent.ENERGY_OSMIUM;
                        break;
                    case minium_modcomponent.ENERGY_OSMIUM:
                        energyType = minium_modcomponent.ENERGY_IRIS_QUARTZ;
                        break;
                    case minium_modcomponent.ENERGY_IRIS_QUARTZ:
                        energyType = minium_modcomponent.ENERGY_COAL;
                        break;
                }
            }

            itemStack.set(minium_modcomponent.REMAIN_ENERGY, new minium_modcomponent.EnergyComponent(remain, energyType));
        }*/


        return TypedActionResult.success(itemStack, world.isClient());//itemStackの部分を変えるとアイテムがそれに化ける
    }
    private boolean reloadEnergy(ItemStack itemStack, MiniumModComponent.EnergyComponent EComp, PlayerEntity user){
        ItemStack offHandStack = user.getEquippedStack(EquipmentSlot.OFFHAND);
        int remain = EComp != null ? EComp.remain() : 0;
        boolean bl = false;
        String energyType = (EComp != null && remain > 0) ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;
        //石炭
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_COAL)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_COAL)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_COAL;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_COAL_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_COAL;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //鉄
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_IRON)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_IRON)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_IRON;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_IRON_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_IRON;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //銅
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_COPPER)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_COPPER)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_COPPER;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_COPPER_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_COPPER;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //金
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_GOLD)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_GOLD)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_GOLD;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_GOLD_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_GOLD;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //ラピスラズリ
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_LAPIS)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_LAPIS)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_LAPIS;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_LAPIS_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_LAPIS;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //レッドストーン
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_REDSTONE)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_REDSTONE)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_REDSTONE;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_REDSTONE_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_REDSTONE;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //ダイヤモンド
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_DIAMOND)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_DIAMOND)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_DIAMOND;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_DIAMOND_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_DIAMOND;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //エメラルド
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_EMERALD)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_EMERALD)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_EMERALD;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_EMERALD_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_EMERALD;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //ネザークォーツ
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_QUARTZ)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_QUARTZ)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_QUARTZ;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //グロウストーン
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_GLOWSTONE)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_GLOWSTONE)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_GLOWSTONE;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //ネザライト
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_NETHERITE)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_NETHERITE)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_NETHERITE;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_NETHERITE_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_NETHERITE;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //アメジスト
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_AMETHYST)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_AMETHYST)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_AMETHYST;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //マイニウム
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_MINIUM)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_MINIUM)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_MINIUM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_MINIUM_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_MINIUM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //濃縮マイニウム
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_C_MINIUM)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_C_MINIUM)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_C_MINIUM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_C_MINIUM_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_C_MINIUM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //オスミウム
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_OSMIUM)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_OSMIUM)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_OSMIUM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_OSMIUM_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_OSMIUM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //虹水晶
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_IRIS_QUARTZ)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_IRIS_QUARTZ)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_IRIS_QUARTZ;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_IRIS_QUARTZ_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_IRIS_QUARTZ;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //製錬鉄
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_REFINED_IRON)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_REFINED_IRON)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_REFINED_IRON;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_REFINED_IRON_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_REFINED_IRON;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //ソースジェム
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_SOURCE_GEM)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_SOURCE_GEM)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_SOURCE_GEM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_SOURCE_GEM_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 5);
                energyType = MiniumModComponent.ENERGY_SOURCE_GEM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //アルミニウム
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_ALUMINIUM)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_ALUMINIUM)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_ALUMINIUM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_ALUMINIUM_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_ALUMINIUM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //鉛
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_LEAD)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_LEAD)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_LEAD;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_LEAD_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_LEAD;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //ニッケル
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_NICKEL)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_NICKEL)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_NICKEL;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_NICKEL_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_NICKEL;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //銀
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_SILVER)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_SILVER)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_SILVER;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_SILVER_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_SILVER;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //錫
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_TIN)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_TIN)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_TIN;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_TIN_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_TIN;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //ウラン
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_URANIUM)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_URANIUM)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_URANIUM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_URANIUM_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_URANIUM;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //亜鉛
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_ZINC)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_ZINC)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_ZINC;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_ZINC_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_ZINC;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //青銅
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_BRONZE)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_BRONZE)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_BRONZE;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_BRONZE_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_BRONZE;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }
        //鋼鉄
        if(Objects.equals(energyType, MiniumModComponent.ENERGY_EMPTY) || Objects.equals(energyType, MiniumModComponent.ENERGY_STEEL)){
            if(offHandStack.isIn(MiniumItemTag.ENERGY_STEEL)){
                remain = addRemain(remain, offHandStack.getCount());
                energyType = MiniumModComponent.ENERGY_STEEL;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }else if(offHandStack.isIn(MiniumItemTag.ENERGY_STEEL_STORAGE_BLOCKS)){
                remain = addRemain(remain, offHandStack.getCount() * 10);
                energyType = MiniumModComponent.ENERGY_STEEL;
                offHandStack.decrementUnlessCreative(offHandStack.getCount(), user);
                bl = true;
            }
        }





        itemStack.set(MiniumModComponent.REMAIN_ENERGY, new MiniumModComponent.EnergyComponent(remain, energyType));
        return bl;
    }
    private boolean isRightEnergy(ItemStack itemStack){
        MiniumModComponent.EnergyComponent EComp = itemStack.get(MiniumModComponent.REMAIN_ENERGY);
        String energyType = EComp != null ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;
        return switch (energyType) {
            case MiniumModComponent.ENERGY_EMPTY, MiniumModComponent.ENERGY_COAL, MiniumModComponent.ENERGY_IRON,
                 MiniumModComponent.ENERGY_COPPER, MiniumModComponent.ENERGY_GOLD, MiniumModComponent.ENERGY_LAPIS,
                 MiniumModComponent.ENERGY_REDSTONE, MiniumModComponent.ENERGY_DIAMOND,
                 MiniumModComponent.ENERGY_EMERALD, MiniumModComponent.ENERGY_QUARTZ,
                 MiniumModComponent.ENERGY_GLOWSTONE, MiniumModComponent.ENERGY_NETHERITE,
                 MiniumModComponent.ENERGY_AMETHYST, MiniumModComponent.ENERGY_MINIUM,
                 MiniumModComponent.ENERGY_C_MINIUM, MiniumModComponent.ENERGY_OSMIUM,
                 MiniumModComponent.ENERGY_IRIS_QUARTZ, MiniumModComponent.ENERGY_REFINED_IRON,
                 MiniumModComponent.ENERGY_SOURCE_GEM, MiniumModComponent.ENERGY_ALUMINIUM,
                 MiniumModComponent.ENERGY_LEAD, MiniumModComponent.ENERGY_NICKEL, MiniumModComponent.ENERGY_SILVER,
                 MiniumModComponent.ENERGY_TIN, MiniumModComponent.ENERGY_URANIUM, MiniumModComponent.ENERGY_ZINC,
                 MiniumModComponent.ENERGY_BRONZE, MiniumModComponent.ENERGY_STEEL ->
                    true;
            default -> false;
        };
    }
    private int addRemain(int remain, int stackMaterial){
        long calcRemain = (long) remain + (long) stackMaterial;
        if(calcRemain > 2147483647){
            return 2147483647;
        }
        return (remain + stackMaterial);
    }

    private int energyConsumption(String energyType){
        return switch (energyType) {
            case MiniumModComponent.ENERGY_COPPER, MiniumModComponent.ENERGY_BRONZE -> 2;
            default -> 1;
        };
    }
    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        MiniumModComponent.EnergyComponent EComp = itemStack.get(MiniumModComponent.REMAIN_ENERGY);
        int remain = EComp != null ? EComp.remain() : 0;
        String energyType = EComp != null ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;
        if(itemStack.contains(MiniumModComponent.REMAIN_ENERGY)){
            tooltip.add(Text.translatable("item.minium_me.energy.remain", remain).formatted(Formatting.GOLD));
            int color = 0xFFFFFF;
            String energyName = "item.minium_me.energy.type.empty";
            switch(energyType){
                case MiniumModComponent.ENERGY_EMPTY:
                    //energyName = "item.minium_me.energy.type.empty";
                    //formatting = Formatting.WHITE;
                    break;
                case MiniumModComponent.ENERGY_COAL:
                    energyName = "item.minium_me.energy.type.coal";
                    color = 0x555555;
                    break;
                case MiniumModComponent.ENERGY_IRON:
                    energyName = "item.minium_me.energy.type.iron";
                    color = 0xBFC9C8;
                    break;
                case MiniumModComponent.ENERGY_COPPER:
                    energyName = "item.minium_me.energy.type.copper";
                    color = 0xB4684D;
                    break;
                case MiniumModComponent.ENERGY_GOLD:
                    energyName = "item.minium_me.energy.type.gold";
                    color = 0xECD93F;
                    break;
                case MiniumModComponent.ENERGY_LAPIS:
                    energyName = "item.minium_me.energy.type.lapis";
                    color = 0x1C4D9C;
                    break;
                case MiniumModComponent.ENERGY_REDSTONE:
                    energyName = "item.minium_me.energy.type.redstone";
                    color = 0x971607;
                    break;
                case MiniumModComponent.ENERGY_DIAMOND:
                    energyName = "item.minium_me.energy.type.diamond";
                    color = 0x6EFCF2;
                    break;
                case MiniumModComponent.ENERGY_EMERALD:
                    energyName = "item.minium_me.energy.type.emerald";
                    color = 0x0EC754;
                    break;
                case MiniumModComponent.ENERGY_QUARTZ:
                    energyName = "item.minium_me.energy.type.quartz";
                    color = 0xF6EADF;
                    break;
                case MiniumModComponent.ENERGY_GLOWSTONE:
                    energyName = "item.minium_me.energy.type.glowstone";
                    color = 0xFBDA74;
                    break;
                case MiniumModComponent.ENERGY_NETHERITE:
                    energyName = "item.minium_me.energy.type.netherite";
                    color = 0x443A3B;
                    break;
                case MiniumModComponent.ENERGY_AMETHYST:
                    energyName = "item.minium_me.energy.type.amethyst";
                    color = 0x9A5CC5;
                    break;
                case MiniumModComponent.ENERGY_MINIUM:
                    energyName = "item.minium_me.energy.type.minium";
                    color = 0x2BD8B3;
                    break;
                case MiniumModComponent.ENERGY_C_MINIUM:
                    energyName = "item.minium_me.energy.type.concentrated_minium";
                    color = 0x009866;
                    break;
                case MiniumModComponent.ENERGY_OSMIUM:
                    energyName = "item.minium_me.energy.type.osmium";
                    color = 0xDDEFFD;
                    break;
                case MiniumModComponent.ENERGY_IRIS_QUARTZ:
                    energyName = "item.minium_me.energy.type.iris_quartz";
                    //color = 0xFFFFFF;
                    break;
                case MiniumModComponent.ENERGY_REFINED_IRON:
                    energyName = "item.minium_me.energy.type.refined_iron";
                    color = 0xD8DEFF;
                    break;
                case MiniumModComponent.ENERGY_SOURCE_GEM:
                    energyName = "item.minium_me.energy.type.source_gem";
                    color = 0xCC66FF;
                    break;
                case MiniumModComponent.ENERGY_ALUMINIUM:
                    energyName = "item.minium_me.energy.type.aluminium";
                    color = 0xE7EAEA;
                    break;
                case MiniumModComponent.ENERGY_LEAD:
                    energyName = "item.minium_me.energy.type.lead";
                    color = 0x8CA7A3;
                    break;
                case MiniumModComponent.ENERGY_NICKEL:
                    energyName = "item.minium_me.energy.type.nickel";
                    color = 0xB0A075;
                    break;
                case MiniumModComponent.ENERGY_SILVER:
                    energyName = "item.minium_me.energy.type.silver";
                    color = 0x9FADB4;
                    break;
                case MiniumModComponent.ENERGY_TIN:
                    energyName = "item.minium_me.energy.type.tin";
                    color = 0xFBEFE3;
                    break;
                case MiniumModComponent.ENERGY_URANIUM:
                    energyName = "item.minium_me.energy.type.uranium";
                    color = 0xB1FAB3;
                    break;
                case MiniumModComponent.ENERGY_ZINC:
                    energyName = "item.minium_me.energy.type.zinc";
                    color = 0xF0F1EF;
                    break;
                case MiniumModComponent.ENERGY_BRONZE:
                    energyName = "item.minium_me.energy.type.bronze";
                    color = 0xC68754;
                    break;
                case MiniumModComponent.ENERGY_STEEL:
                    energyName = "item.minium_me.energy.type.steel";
                    color = 0xA2A2A2;
                    break;
                default:
                    energyName = "item.minium_me.energy.type.error";
                    color = 0x7F7F7F;
                    break;
            }
            //tooltip.add(Text.translatable("item.minium_me.energy.type", Text.translatable(energyName)).formatted(formatting));
            tooltip.add(Text.translatable("item.minium_me.energy.type", Text.translatable(energyName)).withColor(color));
            tooltip.add(Text.translatable(energyName +".explain").formatted(Formatting.WHITE));


        }
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
            int color = 0xFFFFFF;
            switch(energyType){
                case MiniumModComponent.ENERGY_EMPTY:
                    break;
                case MiniumModComponent.ENERGY_COAL:
                    color = 0x555555;
                    break;
                case MiniumModComponent.ENERGY_IRON:
                    color = 0xBFC9C8;
                    break;
                case MiniumModComponent.ENERGY_COPPER:
                    color = 0xB4684D;
                    break;
                case MiniumModComponent.ENERGY_GOLD:
                    color = 0xECD93F;
                    break;
                case MiniumModComponent.ENERGY_LAPIS:
                    color = 0x1C4D9C;
                    break;
                case MiniumModComponent.ENERGY_REDSTONE:
                    color = 0x971607;
                    break;
                case MiniumModComponent.ENERGY_DIAMOND:
                    color = 0x6EFCF2;
                    break;
                case MiniumModComponent.ENERGY_EMERALD:
                    color = 0x0EC754;
                    break;
                case MiniumModComponent.ENERGY_QUARTZ:
                    color = 0xF6EADF;
                    break;
                case MiniumModComponent.ENERGY_GLOWSTONE:
                    color = 0xFBDA74;
                    break;
                case MiniumModComponent.ENERGY_NETHERITE:
                    color = 0x443A3B;
                    break;
                case MiniumModComponent.ENERGY_AMETHYST:
                    color = 0x9A5CC5;
                    break;
                case MiniumModComponent.ENERGY_MINIUM:
                    color = 0x2BD8B3;
                    break;
                case MiniumModComponent.ENERGY_C_MINIUM:
                    color = 0x009866;
                    break;
                case MiniumModComponent.ENERGY_OSMIUM:
                    color = 0xDDEFFD;
                    break;
                case MiniumModComponent.ENERGY_IRIS_QUARTZ:
                    //color = 0xFFFFFF;
                    break;
                case MiniumModComponent.ENERGY_REFINED_IRON:
                    color = 0xD8DEFF;
                    break;
                case MiniumModComponent.ENERGY_SOURCE_GEM:
                    color = 0xCC66FF;
                    break;
                case MiniumModComponent.ENERGY_ALUMINIUM:
                    color = 0xE7EAEA;
                    break;
                case MiniumModComponent.ENERGY_LEAD:
                    color = 0x8CA7A3;
                    break;
                case MiniumModComponent.ENERGY_NICKEL:
                    color = 0xB0A075;
                    break;
                case MiniumModComponent.ENERGY_SILVER:
                    color = 0x9FADB4;
                    break;
                case MiniumModComponent.ENERGY_TIN:
                    color = 0xFBEFE3;
                    break;
                case MiniumModComponent.ENERGY_URANIUM:
                    color = 0xB1FAB3;
                    break;
                case MiniumModComponent.ENERGY_ZINC:
                    color = 0xF0F1EF;
                    break;
                case MiniumModComponent.ENERGY_BRONZE:
                    color = 0xC68754;
                    break;
                case MiniumModComponent.ENERGY_STEEL:
                    color = 0xA2A2A2;
                    break;
                default:
                    color = 0x7F7F7F;
            }
            return color;
        }else{
            return 0;
        }
    }

}
