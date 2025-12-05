package com.github.Minor2CCh.minium_me.registry;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Collection;

public class MiniumCommands {
    public static void initialize(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            dispatcher.register(
                    CommandManager.literal(Minium_me.MOD_ID)
                            .then(CommandManager.literal("resethealthboost")
                                    .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                            .requires(source -> source.hasPermissionLevel(2))
                                            .executes(ctx -> {
                                                Collection<? extends Entity> targets =
                                                        EntityArgumentType.getEntities(ctx, "targets");
                                                int count = 0;
                                                Text lastTargetName = Text.of("");
                                                for (Entity e : targets) {
                                                    if(healthBoostReset(e)){
                                                        count++;
                                                        lastTargetName = e.getName();
                                                    }
                                                }
                                                final int resultCount = count;
                                                final Text resultName = lastTargetName;
                                                if(count == 0){
                                                    ctx.getSource().sendFeedback(
                                                            () -> Text.translatable("commands.minium_me.resethealthboost.fail").formatted(Formatting.RED),
                                                            true
                                                    );
                                                    return 0;

                                                }else if(count > 1){
                                                    ctx.getSource().sendFeedback(
                                                            () -> Text.translatable("commands.minium_me.resethealthboost.success.some", resultCount),
                                                            true
                                                    );
                                                    return 1;

                                                }else{
                                                    ctx.getSource().sendFeedback(
                                                            () -> Text.translatable("commands.minium_me.resethealthboost.success", resultName),
                                                            true
                                                    );
                                                    return 1;
                                                }
                                            })
                                    )
                            )
            );

        });
    }

    private static boolean healthBoostReset(Entity entity) {
        if(entity instanceof LivingEntity livingEntity){
            livingEntity.getDataTracker().set(MiniumTrackDatas.getMaxHealthBoostTracker(), 0);
            return true;
        }
        return false;
    }
}
