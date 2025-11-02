package com.github.Minor2CCh.minium_me.entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class EntityRaycastUtil {
    public static HitResult raycastIncludingEntities(PlayerEntity player, double blockReach, double entityReach, float tickDelta) {

        // プレイヤーの視点ベクトル
        Vec3d start = player.getCameraPosVec(tickDelta);
        Vec3d look = player.getRotationVec(tickDelta);
        Vec3d end = start.add(look.multiply(entityReach));

        // ① ブロック判定
        BlockHitResult blockHit = (BlockHitResult) player.raycast(blockReach, tickDelta, false);

        // ② エンティティ判定
        Box searchBox = player.getBoundingBox().stretch(look.multiply(entityReach)).expand(1.0D);
        EntityHitResult entityHit = raycastEntity(player, start, end, searchBox, entityReach);

        // ③ どちらが近いかで返す
        if (entityHit != null && (blockHit == null
                || entityHit.getPos().squaredDistanceTo(start) < blockHit.getPos().squaredDistanceTo(start))) {
            return entityHit;
        } else {
            return blockHit;
        }
    }

    private static EntityHitResult raycastEntity(PlayerEntity player, Vec3d start, Vec3d end, Box box, double reach) {
        World world = player.getWorld();
        EntityHitResult result = ProjectileUtil.getEntityCollision(
                world,
                player,
                start,
                end,
                box,
                entity -> !entity.isSpectator() && entity.isAlive() && entity.canHit()
        );

        if (result != null) {
            double distance = start.squaredDistanceTo(result.getPos());
            if (distance <= reach * reach) {
                return result;
            }
        }
        return null;
    }
}
