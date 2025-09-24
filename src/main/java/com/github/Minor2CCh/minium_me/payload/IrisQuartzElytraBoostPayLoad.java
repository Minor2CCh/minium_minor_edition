package com.github.Minor2CCh.minium_me.payload;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class IrisQuartzElytraBoostPayLoad implements CustomPayload {
    public static final Id<IrisQuartzElytraBoostPayLoad> ID = new Id<>(Identifier.of(Minium_me.MOD_ID, "boost_iris_quartz_elytra"));
    public static final IrisQuartzElytraBoostPayLoad INSTANCE = new IrisQuartzElytraBoostPayLoad();
    public static final PacketCodec<RegistryByteBuf,IrisQuartzElytraBoostPayLoad > CODEC = PacketCodec.unit(INSTANCE);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
