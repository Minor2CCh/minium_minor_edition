package com.github.Minor2CCh.minium_me.handler;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class DoubleClickHandler {

    // プレイヤーごとの最終クリック時刻を記録
    private static final HashMap<UUID, Long> lastClickTimes = new HashMap<>();
    private static final HashMap<UUID, Long> doubleClickedTimes = new HashMap<>();

    public static void initialize() {
        UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) -> {
            if (world.isClient) return ActionResult.PASS;

            UUID uuid = player.getUuid();
            long currentTime = System.currentTimeMillis();

            long lastTime = lastClickTimes.getOrDefault(uuid, 0L);
            long delta = currentTime - lastTime;

            if (delta < 180 && delta > 1) { // 180ms以内 = ダブルクリック
                //System.out.println("\nlasttime\t:"+lastTime+"\ncurrentTime\t:"+currentTime);
                // ダブルクリック時の処理をここに記述
                lastClickTimes.remove(uuid); // 連続検知防止のため削除
                doubleClickedTimes.put(uuid, currentTime);
            } else {
                //System.out.println("記録：" + player.getName().getString());
                lastClickTimes.put(uuid, currentTime);
            }

            return ActionResult.PASS;
        });
    }
    public static boolean doubleClicked(PlayerEntity player){
        UUID uuid = player.getUuid();
        long currentTime = System.currentTimeMillis();
        long lastTime = doubleClickedTimes.getOrDefault(uuid, 0L);
        return currentTime - lastTime >= 0 && currentTime - lastTime < 2;
    }
}