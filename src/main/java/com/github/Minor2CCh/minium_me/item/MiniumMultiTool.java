package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.block.MiniumBlockTag;
import com.github.Minor2CCh.minium_me.enchantment.MiniumEnchantmentTags;
import com.google.common.collect.BiMap;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.mixin.content.registry.AxeItemAccessor;
import net.fabricmc.fabric.mixin.content.registry.ShovelItemAccessor;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.TrialSpawnerBlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static net.minecraft.block.Block.dropStacks;
import static net.minecraft.block.Block.getDroppedStacks;
import static net.minecraft.block.BulbBlock.LIT;
import static net.minecraft.enchantment.EnchantmentHelper.hasAnyEnchantmentsIn;

public class MiniumMultiTool extends MiningToolItem {
    private static final Map<Block, BlockState> PATH_STATES;
    private static final Map<Block, Block> STRIPPED_BLOCKS;

    public MiniumMultiTool(ToolMaterial material, Item.Settings settings) {

        super(material, MiniumBlockTag.MULTITOOL_MINEABLE, settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        if (ToggleBulb(context, world, blockPos, playerEntity)) {
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

        if (stripBlock(context, world, blockPos, playerEntity)) {
            return ActionResult.success(world.isClient);
        }
        if (rangeStoneBreak(context, world, blockPos, playerEntity)) {
            return ActionResult.success(world.isClient);
        }
        if (context.getSide() == Direction.DOWN) {
            return ActionResult.PASS;
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
        if (itemStack.isOf(MiniumItem.IRIS_QUARTZ_MULTITOOL) && blockState.isIn(ConventionalBlockTags.STONES)) {//石系ブロック
            if(playerEntity.isSneaking()){
                if (!world.isClient()) {
                    stoneBreakDrop(world, blockPos, playerEntity, itemStack);
                    loopStart:for(int i = 0;i < 7;i++){
                        for(int j = 0;j < 7;j++){
                            for(int k = 0;k < 7;k++) {
                                Direction direction = context.getSide();
                                BlockPos tempBlockPos = blockPos.add(i + (direction == Direction.EAST ? -6 : (direction == Direction.WEST ? 0 : -3)), j - 3, k + (direction == Direction.SOUTH ? -6 : (direction == Direction.NORTH ? 0 : -3)));
                                if (world.getBlockState(tempBlockPos).isIn(ConventionalBlockTags.STONES)){
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
        if(hasAnyEnchantmentsIn(itemStack, MiniumEnchantmentTags.TRANSFORMER_STONE)){
            if((int) (Math.random() * 20) == 0){
                if((int) (Math.random() * 5) == 0){//1%の確率でランダムに鉱石を落とす
                    int seed = (int) (Math.random() * 100);
                    if(seed < 10){//10%で石炭
                        dropItemFromBlock(world, blockPos, Items.COAL, 1);
                    }
                    else if(seed < 20){//10%で鉄の原石
                        dropItemFromBlock(world, blockPos, Items.RAW_IRON, 1);
                    }
                    else if(seed < 30){//10%で銅の原石
                        dropItemFromBlock(world, blockPos, Items.RAW_COPPER, 1);
                    }
                    else if(seed < 40){//10%で金の原石
                        dropItemFromBlock(world, blockPos, Items.RAW_GOLD, 1);
                    }
                    else if(seed < 50){//10%でマイニウムの原石
                        dropItemFromBlock(world, blockPos, MiniumItem.R_MINIUM, 1);
                    }
                    else if(seed < 60){//10%でオスミウムの原石
                        dropItemFromBlock(world, blockPos, MiniumItem.RAW_OSMIUM_FROM_MEKANISM, 1);
                    }
                    else if(seed < 70){//10%でレッドストーンダスト
                        dropItemFromBlock(world, blockPos, Items.REDSTONE, 1);
                    }
                    else if(seed < 80){//10%でラピスラズリ
                        dropItemFromBlock(world, blockPos, Items.LAPIS_LAZULI, 1);
                    }
                    else if(seed < 90){//10%でエメラルド
                        dropItemFromBlock(world, blockPos, Items.EMERALD, 1);
                    }
                    else if(seed < 99){//9%でダイヤモンド
                        dropItemFromBlock(world, blockPos, Items.DIAMOND, 1);
                    }else{//1%で虹水晶
                        dropItemFromBlock(world, blockPos, MiniumItem.IRIS_QUARTZ, 1);
                    }//これによりマイニウム&オスミウム(&虹水晶)は再生可能資源となる

                }else{//4%の確率で1経験値落とす
                    world.spawnEntity(new ExperienceOrbEntity(world, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 1));
                }
            }
        }else{
            dropStacks(blockState, world, blockPos, null, playerEntity, itemStack);
        }
        world.breakBlock(blockPos, false, playerEntity);

    }
    //樹木剥がし
    private boolean stripBlock(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity) {
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
                //playerEntity.dropItem(itemStack, false);
                itemStack.damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
            }

            return true;
        }
        return false;
    }
    //WIP
    private boolean accelerateTrialSpawner(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity){
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof TrialSpawnerBlock trialSpawnerBlock) {
            //BlockEntity blockEntity = world.getBlockEntity(blockPos);
            //ComponentMap componentMap = Objects.requireNonNull(blockEntity).getComponents();

            TrialSpawnerBlockEntity trialSpawnerBlockEntity = (TrialSpawnerBlockEntity) world.getBlockEntity(blockPos);
            //BlockState blockState2 = blockState.with(TrialSpawnerBlock., !blockState.get(LIT));
            //updateBlockState(world, blockPos, blockState2, playerEntity, context);
            return true;
        }
        return false;
    }
    //電球切り替え
    private boolean ToggleBulb(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity){
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof BulbBlock && !playerEntity.shouldCancelInteraction()) {
            world.playSound(playerEntity, blockPos, blockState.get(LIT) ? SoundEvents.BLOCK_COPPER_BULB_TURN_OFF : SoundEvents.BLOCK_COPPER_BULB_TURN_ON, SoundCategory.BLOCKS, 1.0F, 1.0F);
            BlockState blockState2 = blockState.with(BulbBlock.LIT, !blockState.get(LIT));
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
            world.playSound(playerEntity, blockPos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            dropItemFromBlock(world, blockPos, Items.GLOWSTONE_DUST, 4);
            world.removeBlock(blockPos, false);
            context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
            return true;
        }
        if (blockState.isOf(Blocks.SEA_LANTERN)) {
            world.playSound(playerEntity, blockPos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            dropItemFromBlock(world, blockPos, Items.PRISMARINE_SHARD, 4);
            dropItemFromBlock(world, blockPos, Items.PRISMARINE_CRYSTALS, 5);
            world.removeBlock(blockPos, false);
            context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
            return true;
        }
        if (blockState.isOf(Blocks.HONEYCOMB_BLOCK) && !playerEntity.shouldCancelInteraction()) {
            world.playSound(playerEntity, blockPos, SoundEvents.BLOCK_CORAL_BLOCK_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            dropItemFromBlock(world, blockPos, Items.HONEYCOMB, 4);
            world.removeBlock(blockPos, false);
            context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
            return true;
        }
        return false;
    }
    private void dropItemFromBlock(World world, BlockPos blockPos, Item lootItem, int count){
        ItemStack dropStack = lootItem.getDefaultStack();
        if(count > 1){
            dropStack.increment(count - 1);
        }
        Block.dropStack(world, blockPos, dropStack);
    }
    //道生成
    private boolean useShovel(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity){
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

    private static boolean shouldCancelStripAttempt(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        return context.getHand().equals(Hand.MAIN_HAND) && Objects.requireNonNull(playerEntity).getOffHandStack().isOf(Items.SHIELD) && !playerEntity.shouldCancelInteraction();
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
                Optional<BlockState> optional3 = Optional.ofNullable((Block) ((BiMap) HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()).get(state.getBlock())).map((block) -> block.getStateWithProperties(state));
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
    static{
        STRIPPED_BLOCKS = AxeItemAccessor.getStrippedBlocks();
    }
    static {
        PATH_STATES = ShovelItemAccessor.getPathStates();
    }
    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(itemStack, context, tooltip, type);
        if(itemStack.isOf(MiniumItem.IRIS_QUARTZ_MULTITOOL)){
            tooltip.add(Text.translatable("item.minium_me.iris_quartz_multitool.explain"));
        }

    }

}
//Reference Source Code
//BrandCraft様のAdapaxelsを参考にさせていただきました。
//https://www.curseforge.com/minecraft/mc-mods/adapaxels

