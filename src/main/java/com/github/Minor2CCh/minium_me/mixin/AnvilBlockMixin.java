package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {
        @Inject(method = "getLandingState(Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/BlockState;", at = @At("HEAD"), cancellable = true)
        private static void compatibleMiniumAnvil(BlockState fallingState, CallbackInfoReturnable<BlockState> cir){
            if (fallingState.isOf(MiniumBlock.MINIUM_ANVIL)) {
                cir.setReturnValue(MiniumBlock.CHIPPED_MINIUM_ANVIL.getDefaultState().with(AnvilBlock.FACING, fallingState.get(AnvilBlock.FACING)));
            } else if (fallingState.isOf(MiniumBlock.CHIPPED_MINIUM_ANVIL)) {
                cir.setReturnValue(MiniumBlock.DAMAGED_MINIUM_ANVIL.getDefaultState().with(AnvilBlock.FACING, fallingState.get(AnvilBlock.FACING)));
            } else if (fallingState.isOf(MiniumBlock.DAMAGED_MINIUM_ANVIL)) {
                cir.setReturnValue(null);
            }
        }
}
