package com.github.Minor2CCh.minium_me.entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;

public class EntityRaycastUtil {
    public static HitResult raycastIncludingEntities(PlayerEntity player, double blockReach, double entityReach, float tickDelta) {

        // プレイヤーの視点ベクトル
        Vec3d start = player.getCameraPosVec(tickDelta);
        Vec3d look = player.getRotationVec(tickDelta);
        Vec3d end = start.add(look.multiply(entityReach));

        // ① ブロック判定
        BlockHitResult blockHit = (BlockHitResult) player.raycast(blockReach, tickDelta, false);

        // ② エンティティ判定（バニラと同じ幅を持たせる）
        Box searchBox = player.getBoundingBox()
                .stretch(look.multiply(entityReach))
                .expand(1.0D + player.getTargetingMargin());  // ←重要！

        EntityHitResult entityHit = ProjectileUtil.getEntityCollision(
                player.getWorld(),
                player,
                start,
                end,
                searchBox,
                entity -> !entity.isSpectator() && entity.isAlive() && entity.canHit()
        );
        // ③ どちらが近いかで返す
        if (entityHit != null && (blockHit == null
                || entityHit.getPos().squaredDistanceTo(start) < blockHit.getPos().squaredDistanceTo(start))) {
            return entityHit;
        } else {
            return blockHit;
        }
    }
}
