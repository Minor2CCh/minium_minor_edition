package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.block.MiniumBlockTag;
import com.github.Minor2CCh.minium_me.enchantment.MiniumEnchantmentTags;
import com.github.Minor2CCh.minium_me.handler.DoubleClickHandler;
import com.github.Minor2CCh.minium_me.mixin.TrialSpawnerAccessor;
import com.google.common.collect.BiMap;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.mixin.content.registry.AxeItemAccessor;
import net.fabricmc.fabric.mixin.content.registry.ShovelItemAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.TrialSpawnerBlockEntity;
import net.minecraft.block.enums.TrialSpawnerState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;

import java.util.*;

public class MiniumMultiToolItem extends MiningToolItem implements HasCustomTooltip{
    private final ShovelItem internalShovel;
    private final AxeItem internalAxe;
    protected static final Map<Block, BlockState> PATH_STATES = ShovelItemAccessor.getPathStates();
    protected static final Map<Block, Block> STRIPPED_BLOCKS = AxeItemAccessor.getStrippedBlocks();


    public MiniumMultiToolItem(ToolMaterial material, Item.Settings settings) {
        super(material, MiniumBlockTag.MULTITOOL_MINEABLE, settings);
        this.internalShovel = (ShovelItem) Items.DIAMOND_SHOVEL;
        this.internalAxe = (AxeItem) Items.DIAMOND_AXE;
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        if (ToggleBulb(context, world, blockPos, playerEntity)) {
            return ActionResult.success(world.isClient);
        }
        if (accelerateTrialSpawner(context, world, blockPos, playerEntity)) {
            return ActionResult.success(world.isClient);
        }
        if (shouldCancelStripAttempt(context)) {
            return ActionResult.PASS;
        }
        if (beCryObsidian(context, world, blockPos, playerEntity)) {
            return ActionResult.success(world.isClient);
        }
        if (blockDisassemble(context, world, blockPos, playerEntity)) {
            return ActionResult.success(world.isClient);
        }

        if (useAxe(context, world, blockPos, playerEntity)) {
            return ActionResult.success(world.isClient);
        }
        if (rangeStoneBreak(context, world, blockPos, playerEntity)) {
            return ActionResult.success(world.isClient);
        }
        if (useShovel(context, world, blockPos, playerEntity)) {
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }
    //一括破壊(虹水晶マルチツール使用時限定)
    private boolean rangeStoneBreak(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity){
        BlockState blockState = world.getBlockState(blockPos);
        ItemStack itemStack = context.getStack();
        if (itemStack.isOf(MiniumItem.IRIS_QUARTZ_MULTITOOL) && (blockState.isIn(ConventionalBlockTags.STONES) || blockState.isIn(ConventionalBlockTags.NETHERRACKS) || blockState.isIn(ConventionalBlockTags.END_STONES))) {//石系ブロックorネザーラック
            if(playerEntity.isSneaking()){
                if (!world.isClient() && DoubleClickHandler.doubleClicked(playerEntity)) {
                    stoneBreakDrop(world, blockPos, playerEntity, itemStack);
                    loopStart:for(int i = 0;i < 7;i++){
                        for(int j = 0;j < 7;j++){
                            for(int k = 0;k < 7;k++) {
                                Direction direction = context.getSide();
                                BlockPos tempBlockPos = blockPos.add(i + (direction == Direction.EAST ? -6 : (direction == Direction.WEST ? 0 : -3)), j - 3, k + (direction == Direction.SOUTH ? -6 : (direction == Direction.NORTH ? 0 : -3)));
                                if (world.getBlockState(tempBlockPos).isIn(ConventionalBlockTags.STONES) || world.getBlockState(tempBlockPos).isIn(ConventionalBlockTags.NETHERRACKS) || world.getBlockState(tempBlockPos).isIn(ConventionalBlockTags.END_STONES)){
                                    stoneBreakDrop(world, tempBlockPos, playerEntity, itemStack);
                                    if(Math.random() < 0.25F){
                                        context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                                        if(context.getStack().isEmpty()){
                                            break loopStart;
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                return true;

            }else{
                return false;

            }

        }
        return false;
    }
    private void stoneBreakDrop(World world, BlockPos blockPos, PlayerEntity playerEntity, ItemStack itemStack){
        BlockState blockState = world.getBlockState(blockPos);
        if(EnchantmentHelper.hasAnyEnchantmentsIn(itemStack, MiniumEnchantmentTags.TRANSFORMER_STONE)){
            Random rand = new Random();
            if(rand.nextInt(20) == 0){
                if(rand.nextInt(5) == 0){//1%の確率でランダムに鉱石を落とす
                    if(world.getBlockState(blockPos).isIn(ConventionalBlockTags.STONES)){
                        Block.dropStacks(MiniumBlock.STONE_ALCHEMY_BREAK_STONE.getDefaultState(), world, blockPos, null, playerEntity, itemStack);
                        // loot_table/blocks/stone_alchemy_break_stone.json
                    }else if(world.getBlockState(blockPos).isIn(ConventionalBlockTags.NETHERRACKS)){
                        Block.dropStacks(MiniumBlock.STONE_ALCHEMY_BREAK_NETHERRACK.getDefaultState(), world, blockPos, null, playerEntity, itemStack);
                        // loot_table/blocks/stone_alchemy_break_netherrack.json
                    }else if(world.getBlockState(blockPos).isIn(ConventionalBlockTags.END_STONES)){
                        Block.dropStacks(MiniumBlock.STONE_ALCHEMY_BREAK_END_STONE.getDefaultState(), world, blockPos, null, playerEntity, itemStack);
                        // loot_table/blocks/stone_alchemy_break_end_stone.json
                    }

                }else{//4%の確率で1経験値落とす
                    world.spawnEntity(new ExperienceOrbEntity(world, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 1));
                }
            }
        }else{
            Block.dropStacks(blockState, world, blockPos, null, playerEntity, itemStack);
        }
        world.breakBlock(blockPos, false, playerEntity);

    }
    //樹木剥がし
    private boolean useAxe(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        if(!FabricLoader.getInstance().isModLoaded("neoforge")){
            ActionResult actionResult = internalAxe.useOnBlock(context);
            return actionResult.isAccepted();
        }else{ //Connector環境で失敗するので旧式
            Optional<BlockState> optional = this.tryStrip(world, blockPos, playerEntity, world.getBlockState(blockPos));
            if (optional.isPresent()) {
                ItemStack itemStack = context.getStack();
                if (playerEntity instanceof ServerPlayerEntity) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) playerEntity, blockPos, itemStack);
                }
                BlockState newState = optional.get();
                world.setBlockState(blockPos, newState, 11);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, newState));
                if (playerEntity != null) {
                    itemStack.damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                }
                return true;
            }
            return false;
        }
    }
    //錆止めor錆落とし
    private Optional<BlockState> tryStrip(World world, BlockPos pos, @Nullable PlayerEntity player, BlockState state) {
        Optional<BlockState> optional = this.getStrippedState(state);
        if (optional.isPresent()) {
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return optional;
        } else {
            Optional<BlockState> optional2 = Oxidizable.getDecreasedOxidationState(state);
            if (optional2.isPresent()) {
                world.playSound(player, pos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.syncWorldEvent(player, 3005, pos, 0);
                return optional2;
            } else {
                Optional<BlockState> optional3 = Optional.ofNullable((Block) ((BiMap<?, ?>) HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()).get(state.getBlock())).map((block) -> block.getStateWithProperties(state));
                if (optional3.isPresent()) {
                    world.playSound(player, pos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.syncWorldEvent(player, 3004, pos, 0);
                    return optional3;
                } else {
                    return Optional.empty();
                }
            }
        }
    }
    private Optional<BlockState> getStrippedState(BlockState state) {
        return Optional.ofNullable(STRIPPED_BLOCKS.get(state.getBlock())).map((block) -> block.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
    }

    private boolean accelerateTrialSpawner(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity){
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof TrialSpawnerBlock trialSpawnerBlock) {
            // && trialSpawnerBlock.TRIAL_SPAWNER_STATE.equals(TrialSpawnerState.COOLDOWN)
            if(blockState.get(TrialSpawnerBlock.TRIAL_SPAWNER_STATE).equals(TrialSpawnerState.COOLDOWN)){
                TrialSpawnerBlockEntity trialSpawnerBlockEntity = (TrialSpawnerBlockEntity) world.getBlockEntity(blockPos);
                long oldCooldown = ((TrialSpawnerAccessor)(Objects.requireNonNull(trialSpawnerBlockEntity).getSpawner().getData())).cooldownEnd();//クライアントは0のみを返す
                //long cooldownLength = Objects.requireNonNull(trialSpawnerBlockEntity).getSpawner().getCooldownLength();
                long remainCooldown = oldCooldown - world.getTime();
                if(remainCooldown > 20) {
                    long newCooldown = (long)(remainCooldown * 0.9)+world.getTime();
                    if(!world.isClient()){
                        ((TrialSpawnerAccessor)(Objects.requireNonNull(trialSpawnerBlockEntity).getSpawner().getData())).setCooldownEnd(newCooldown);
                        /*
                        System.out.println(((TrialSpawnerAccessor)(Objects.requireNonNull(trialSpawnerBlockEntity).getSpawner().getData())).cooldownEnd());
                        System.out.println(oldCooldown);
                        System.out.println(world.getTime());
                        */
                    }
                    context.getStack().damage(10, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                }
                world.playSound(playerEntity, blockPos, SoundEvents.BLOCK_VAULT_INSERT_ITEM_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.playSound(playerEntity, blockPos, SoundEvents.BLOCK_TRIAL_SPAWNER_OMINOUS_ACTIVATE, SoundCategory.BLOCKS, 1.0F, 0.5F);
                Random random = new Random();
                for(int i=0;i<8;i++){
                    world.addParticle(ParticleTypes.TRIAL_SPAWNER_DETECTION, blockPos.getX()+random.nextDouble(-0.25, 1.25), blockPos.getY()+random.nextDouble(-0.25, 1.25), blockPos.getZ()+random.nextDouble(-0.25, 1.25), 0.0, random.nextDouble(0.05, 0.075), 0.0);
                    world.addParticle(ParticleTypes.TRIAL_SPAWNER_DETECTION_OMINOUS, blockPos.getX()+random.nextDouble(-0.25, 1.25), blockPos.getY()+random.nextDouble(-0.25, 1.25), blockPos.getZ()+random.nextDouble(-0.25, 1.25), 0.0, random.nextDouble(0.05, 0.075), 0.0);

                }
                return true;
            }

        }
        return false;
    }
    //電球切り替え
    private boolean ToggleBulb(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity){
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof BulbBlock && !playerEntity.shouldCancelInteraction()) {
            world.playSound(playerEntity, blockPos, blockState.get(BulbBlock.LIT) ? SoundEvents.BLOCK_COPPER_BULB_TURN_OFF : SoundEvents.BLOCK_COPPER_BULB_TURN_ON, SoundCategory.BLOCKS, 1.0F, 1.0F);
            BlockState blockState2 = blockState.with(BulbBlock.LIT, !blockState.get(BulbBlock.LIT));
            updateBlockState(world, blockPos, blockState2, playerEntity, context);
            return true;
        }
        return false;
    }
    //泣く黒曜石錬成
    private boolean beCryObsidian(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity){
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.OBSIDIAN)) {
            if(world.getBlockState(blockPos.add(-1, 0, 0)).isOf(Blocks.NETHER_PORTAL)
                || world.getBlockState(blockPos.add(0, 0, -1)).isOf(Blocks.NETHER_PORTAL)
                || world.getBlockState(blockPos.add(0, 0, -1)).isOf(Blocks.NETHER_PORTAL)
                || world.getBlockState(blockPos.add(0, 0, 1)).isOf(Blocks.NETHER_PORTAL)
                || world.getBlockState(blockPos.add(0, -1, 0)).isOf(Blocks.NETHER_PORTAL)
                || world.getBlockState(blockPos.add(0, 1, 0)).isOf(Blocks.NETHER_PORTAL)) {
                return false;
            }else{
                world.playSound(playerEntity, blockPos, SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                BlockState blockState2 = Blocks.CRYING_OBSIDIAN.getDefaultState();
                updateBlockState2(world, blockPos, blockState2, playerEntity, context);
                return true;
            }
        }
        return false;
    }
    //一部ブロックの分解
    private boolean blockDisassemble(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity){
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.GLOWSTONE)) {
            if(!world.isClient()){
                dropItemInventory(Items.GLOWSTONE_DUST, 4, playerEntity);
                world.breakBlock(blockPos, false, playerEntity);
                context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
            }
            return true;
        }
        if (blockState.isOf(Blocks.SEA_LANTERN)) {
            if(!world.isClient()) {
                dropItemInventory(Items.PRISMARINE_SHARD, 4, playerEntity);
                dropItemInventory(Items.PRISMARINE_CRYSTALS, 5, playerEntity);
                world.breakBlock(blockPos, false, playerEntity);
                context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
            }
            return true;
        }
        if (blockState.isOf(Blocks.HONEYCOMB_BLOCK) && !playerEntity.shouldCancelInteraction()) {
            if(!world.isClient()) {
                dropItemInventory(Items.HONEYCOMB, 4, playerEntity);
                world.breakBlock(blockPos, false, playerEntity);
                context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
            }
            return true;
        }
        return false;
    }
    private void dropItemInventory(Item lootItem, int count, PlayerEntity playerEntity){
        ItemStack dropStack = lootItem.getDefaultStack();
        dropStack.setCount(count);
        if (!playerEntity.getInventory().insertStack(dropStack)) {
            playerEntity.dropItem(dropStack, false);
        }
    }

    private void dropItemFromBlock(World world, BlockPos blockPos, Item lootItem, int count){
        ItemStack dropStack = lootItem.getDefaultStack();
        dropStack.setCount(count);
        Block.dropStack(world, blockPos, dropStack);
    }
    //道生成
    private boolean useShovel(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity){
        if(!FabricLoader.getInstance().isModLoaded("neoforge")){
            ActionResult actionResult = internalShovel.useOnBlock(context);
            return actionResult.isAccepted();
        }else{ //Connector環境で失敗するので旧式
            if((context.getSide() == Direction.DOWN)){
                return false;
            }
            BlockState blockState = world.getBlockState(blockPos);
            BlockState blockState2 = PATH_STATES.get(blockState.getBlock());
            if (blockState2 != null && world.getBlockState(blockPos.up()).isAir()) {
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                updateBlockState(world, blockPos, blockState2, playerEntity, context);
                return true;
            }
            if (blockState.getBlock() instanceof CampfireBlock && blockState.get(CampfireBlock.LIT)) {
                if (!world.isClient()) {
                    world.syncWorldEvent(null, WorldEvents.FIRE_EXTINGUISHED, blockPos, 0);
                }

                CampfireBlock.extinguish(context.getPlayer(), world, blockPos, blockState);
                BlockState blockState3 = blockState.with(CampfireBlock.LIT, false);
                updateBlockState(world, blockPos, blockState3, playerEntity, context);
                return true;
            }
            return false;
        }
    }
    private void updateBlockState2(World world, BlockPos blockPos, BlockState newState, PlayerEntity playerEntity, ItemUsageContext context) {
        world.setBlockState(blockPos, newState, 11);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, newState));
        if (playerEntity != null) {
            context.getStack().damage(50, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
        }
    }
    private void updateBlockState(World world, BlockPos blockPos, BlockState newState, PlayerEntity playerEntity, ItemUsageContext context) {
        world.setBlockState(blockPos, newState, 11);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, newState));
        if (playerEntity != null) {
            context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
        }
    }

    private boolean shouldCancelStripAttempt(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        return context.getHand().equals(Hand.MAIN_HAND) && Objects.requireNonNull(playerEntity).getOffHandStack().isOf(Items.SHIELD) && !playerEntity.shouldCancelInteraction();
    }
    @Override
    public void customTooltip(ItemStack itemStack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, boolean hasShiftDown) {
        if (Objects.equals(this, MiniumItem.IRIS_QUARTZ_MULTITOOL)) {
            HasCustomTooltip.super.customTooltip(itemStack, context, tooltip, type, hasShiftDown);
        }

    }

}
//Reference Source Code
//BrandCraft様のAdapaxelsを参考にさせていただきました。
//https://www.curseforge.com/minecraft/mc-mods/adapaxels

