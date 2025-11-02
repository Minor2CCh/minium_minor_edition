package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.block.TemporallyBlock;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.enchantment.MiniumEnchantmentTags;
import com.github.Minor2CCh.minium_me.enchantment.MiniumEnchantments;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TemporallyBlockPlacerItem extends Item implements HasCustomTooltip{
    public final int maxCharge;
    private final Block placeBlock;
    public TemporallyBlockPlacerItem(Settings settings, int maxCharge, Block block) {
        super(settings.component(DataComponentTypes.TOOL, createToolComponent()));
        this.maxCharge = maxCharge;
        this.placeBlock = block;
    }
    public static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(
                        ToolComponent.Rule.of(List.of(MiniumBlock.TEMPORALLY_BLOCK), 1000.0F)
                ),
                1.0F,
                1
        );
    }
    public float getMiningSpeed(ItemStack stack, BlockState state) {
        if(!(state.getBlock() instanceof TemporallyBlock
                || state.getBlock().getHardness() == 0.0F)){
            return 0.0F;
        }
        ToolComponent toolComponent = stack.get(DataComponentTypes.TOOL);
        return toolComponent != null ? toolComponent.getSpeed(state) : 1.0F;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // クライアントでも視線を取れるが、実際の設置はサーバー側で行う
        BlockHitResult hit = raycast(world, user, RaycastContext.FluidHandling.NONE);

        if (hit.getType() == HitResult.Type.MISS) {
            if (placeableTemporallyBlock(user, stack)) {
                // 空中クリック → プレイヤーの視線方向に仮想的な設置位置を決定
                Vec3d eyePos = user.getCameraPosVec(1.0F);
                Vec3d lookVec = user.getRotationVec(1.0F);
                Vec3d target = eyePos.add(lookVec.multiply(user.getAttributeValue(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE)));

                BlockPos placePos = BlockPos.ofFloored(target);
                BlockState state = getBlock().getDefaultState();
                BlockSoundGroup blockSoundGroup = state.getSoundGroup();

                // 空気中にも強制設置
                if (placePos.getY() >= world.getDimension().minY() && placePos.getY() < (world.getDimension().height() + world.getDimension().minY()) &&
                        (world.getBlockState(placePos).isAir()
                                || world.isWater(placePos)
                                || world.getBlockState(placePos).isReplaceable())) {
                    if(world.canPlace(state, placePos, ShapeContext.of(user))){
                        if(!world.isClient()){
                            int extendTime = EnchantmentHelper.hasAnyEnchantmentsIn(stack, MiniumEnchantmentTags.FREEZABLE_TEMPORALLY_BLOCK) ? 0 : MiniumEnchantments.getEnchantmentLevel(user, stack, Enchantments.FORTUNE);
                            world.setBlockState(placePos, state.with(Properties.WATERLOGGED, world.getFluidState(placePos).getFluid() == Fluids.WATER).with(TemporallyBlock.FREEZE, EnchantmentHelper.hasAnyEnchantmentsIn(stack, MiniumEnchantmentTags.FREEZABLE_TEMPORALLY_BLOCK)).with(TemporallyBlock.EXTEND, extendTime));
                        }
                        world.playSound(
                                user,
                                placePos,
                                this.getPlaceSound(state),
                                SoundCategory.BLOCKS,
                                (blockSoundGroup.getVolume() + 1.0F) / 2.0F,
                                blockSoundGroup.getPitch() * 0.8F
                        );
                        if (!user.isCreative())
                            useTemporallyBlock(user, stack);
                        return TypedActionResult.success(stack, world.isClient);
                    }
                }
            }
            return TypedActionResult.fail(stack);
        }

        // 通常ブロッククリック時は普通に設置
        return super.use(world, user, hand);
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ActionResult actionResult = this.place(new ItemPlacementContext(context));
        if (!actionResult.isAccepted() && context.getStack().contains(DataComponentTypes.FOOD)) {
            ActionResult actionResult2 = super.use(context.getWorld(), context.getPlayer(), context.getHand()).getResult();
            return actionResult2 == ActionResult.CONSUME ? ActionResult.CONSUME_PARTIAL : actionResult2;
        } else {
            return actionResult;
        }
    }

    public ActionResult place(ItemPlacementContext context) {
        if (!placeBlock.isEnabled(context.getWorld().getEnabledFeatures())) {
            return ActionResult.FAIL;
        }
        else if (!placeableTemporallyBlock(context.getPlayer(), context.getStack())) {
            return ActionResult.FAIL;
        }else if (!context.canPlace()) {
            return ActionResult.FAIL;
        } else {
            ItemPlacementContext itemPlacementContext = this.getPlacementContext(context);
            if (itemPlacementContext == null) {
                return ActionResult.FAIL;
            } else {
                BlockState blockState = this.getPlacementState(itemPlacementContext);
                if (blockState == null) {
                    return ActionResult.FAIL;
                }
                int extendTime;
                if (context.getWorld().isClient() || context.getPlayer() == null){
                    extendTime = 0;
                } else {
                    extendTime = EnchantmentHelper.hasAnyEnchantmentsIn(context.getStack(), MiniumEnchantmentTags.FREEZABLE_TEMPORALLY_BLOCK) ? 0 : MiniumEnchantments.getEnchantmentLevel(context.getPlayer(), context.getStack(), Enchantments.FORTUNE);
                }
                blockState = blockState.with(TemporallyBlock.EXTEND, extendTime);
                if(EnchantmentHelper.hasAnyEnchantmentsIn(context.getStack(), MiniumEnchantmentTags.FREEZABLE_TEMPORALLY_BLOCK)){
                    blockState = blockState.with(TemporallyBlock.FREEZE, true);
                }
                if (!this.place(itemPlacementContext, blockState)) {
                    return ActionResult.FAIL;
                } else {
                    BlockPos blockPos = itemPlacementContext.getBlockPos();
                    World world = itemPlacementContext.getWorld();
                    PlayerEntity playerEntity = itemPlacementContext.getPlayer();
                    ItemStack itemStack = itemPlacementContext.getStack();
                    BlockState blockState2 = world.getBlockState(blockPos);
                    if (blockState2.isOf(blockState.getBlock())) {
                        blockState2 = this.placeFromNbt(blockPos, world, itemStack, blockState2);
                        this.postPlacement(blockPos, world, playerEntity, itemStack, blockState2);
                        copyComponentsToBlockEntity(world, blockPos, itemStack);
                        blockState2.getBlock().onPlaced(world, blockPos, blockState2, playerEntity, itemStack);
                        if (playerEntity instanceof ServerPlayerEntity) {
                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
                        }
                    }

                    BlockSoundGroup blockSoundGroup = blockState2.getSoundGroup();
                    world.playSound(
                            playerEntity,
                            blockPos,
                            this.getPlaceSound(blockState2),
                            SoundCategory.BLOCKS,
                            (blockSoundGroup.getVolume() + 1.0F) / 2.0F,
                            blockSoundGroup.getPitch() * 0.8F
                    );
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(playerEntity, blockState2));
                    useTemporallyBlock(playerEntity, itemStack);
                    return ActionResult.success(world.isClient);
                }
            }
        }
    }
    protected boolean place(ItemPlacementContext context, BlockState state) {
        return context.getWorld().setBlockState(context.getBlockPos(), state, Block.NOTIFY_ALL_AND_REDRAW);
    }
    protected SoundEvent getPlaceSound(BlockState state) {
        return state.getSoundGroup().getPlaceSound();
    }

    @Nullable
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        return context;
    }
    @Nullable
    protected BlockState getPlacementState(ItemPlacementContext context) {
        BlockState blockState = placeBlock.getPlacementState(context);
        return blockState != null && this.canPlace(context, blockState) ? blockState : null;
    }
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        PlayerEntity playerEntity = context.getPlayer();
        ShapeContext shapeContext = playerEntity == null ? ShapeContext.absent() : ShapeContext.of(playerEntity);
        return (!this.checkStatePlacement() || state.canPlaceAt(context.getWorld(), context.getBlockPos()))
                && context.getWorld().canPlace(state, context.getBlockPos(), shapeContext);
    }
    protected boolean checkStatePlacement() {
        return true;
    }
    private BlockState placeFromNbt(BlockPos pos, World world, ItemStack stack, BlockState state) {
        BlockStateComponent blockStateComponent = stack.getOrDefault(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT);
        if (blockStateComponent.isEmpty()) {
            return state;
        } else {
            BlockState blockState = blockStateComponent.applyToState(state);
            if (blockState != state) {
                world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            }

            return blockState;
        }
    }
    private static void copyComponentsToBlockEntity(World world, BlockPos pos, ItemStack stack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            blockEntity.readComponents(stack);
            blockEntity.markDirty();
        }
    }
    @SuppressWarnings("all")
    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        return writeNbtToBlockEntity(world, player, pos, stack);
    }
    public static boolean writeNbtToBlockEntity(World world, @Nullable PlayerEntity player, BlockPos pos, ItemStack stack) {
        MinecraftServer minecraftServer = world.getServer();
        if(minecraftServer != null) {
            NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.BLOCK_ENTITY_DATA, NbtComponent.DEFAULT);
            if (!nbtComponent.isEmpty()) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity != null) {
                    if (world.isClient || !blockEntity.copyItemDataRequiresOperator() || player != null && player.isCreativeLevelTwoOp()) {
                        return nbtComponent.applyToBlockEntity(blockEntity, world.getRegistryManager());
                    }
                }
            }
        }
        return false;
    }
    public Block getBlock() {
        return this.placeBlock;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world instanceof ServerWorld serverWorld) {
            int efficiencyLevel = MiniumEnchantments.getEnchantmentLevel(serverWorld, stack, Enchantments.EFFICIENCY);
            if (serverWorld.getRandom().nextInt((200 / (1 + efficiencyLevel) <= 0) ? 1 : (200 / (1 + efficiencyLevel))) == 0) {
                Integer tempRemain = stack.get(MiniumModComponent.TEMPORALLY_REMAIN);
                if(tempRemain != null && tempRemain < this.maxCharge){
                    stack.set(MiniumModComponent.TEMPORALLY_REMAIN, tempRemain + 1);
                }
            }
        }
    }
    public void useTemporallyBlock(@Nullable PlayerEntity player, ItemStack itemStack){
        if(player != null && !player.isCreative() && !isUnbreakable(itemStack)){
            Integer tempRemain = itemStack.get(MiniumModComponent.TEMPORALLY_REMAIN);
            if(tempRemain != null && tempRemain > 0){
                if(!player.getWorld().isClient()){
                    int unbreakingLevel = MiniumEnchantments.getEnchantmentLevel(player, itemStack, Enchantments.UNBREAKING);
                    if(player.getWorld().getRandom().nextInt(unbreakingLevel + 1) == 0){
                        itemStack.set(MiniumModComponent.TEMPORALLY_REMAIN, tempRemain - 1);
                    }
                }
            }
        }
    }
    private static boolean isUnbreakable(ItemStack stack){
        UnbreakableComponent unbreakable = stack.getComponents().get(DataComponentTypes.UNBREAKABLE);
        if(unbreakable == null){
            return false;
        }
        return unbreakable.equals(new UnbreakableComponent(true));
    }
    public boolean placeableTemporallyBlock(@Nullable PlayerEntity player, ItemStack itemStack){
        if(player != null){
            Integer tempRemain = itemStack.get(MiniumModComponent.TEMPORALLY_REMAIN);
            if(tempRemain != null){
                return player.isCreative() || tempRemain > 0;
            }
        }
        return false;
    }
    public static void recoverTemporallyBlock(@Nullable PlayerEntity player, ItemStack itemStack){
        if(player != null){
            if(itemStack.getItem() instanceof TemporallyBlockPlacerItem item){
                Integer tempRemain = itemStack.get(MiniumModComponent.TEMPORALLY_REMAIN);
                if(tempRemain != null && tempRemain < item.maxCharge){
                    itemStack.set(MiniumModComponent.TEMPORALLY_REMAIN, tempRemain + 1);
                }

            }
        }
    }

    @Override
    public void customTooltip(ItemStack itemStack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, boolean hasShiftDown) {
        Integer tempRemain = itemStack.get(MiniumModComponent.TEMPORALLY_REMAIN);
        if(tempRemain == null){
            tooltip.add(Text.translatable(this.getTranslationKey()+".desc.error").formatted(Formatting.RED));
        }else{
            tooltip.add(Text.translatable(this.getTranslationKey()+".desc.remain", tempRemain).withColor(0x2BD8B3));
        }
        if (hasShiftDown) {
            for(int i = 0;i < 5;i++){
                tooltip.add(Text.translatable(this.getTranslationKey()+".desc."+i).formatted(Formatting.WHITE));

            }
        } else {
            tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
        }

    }
    @Override
    public int getItemBarStep(ItemStack stack) {
        Integer tempRemain = stack.get(MiniumModComponent.TEMPORALLY_REMAIN);
        if(tempRemain == null){
            return 0;
        }
        return MathHelper.clamp(Math.round((float)tempRemain / this.maxCharge * 13.0F), 0, 13);
    }
    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }
    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0x2BD8B3;
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
}
