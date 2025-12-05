package com.github.Minor2CCh.minium_me.client.keybinds;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.event.IrisQuartzElytraBoostEvent;
import com.github.Minor2CCh.minium_me.payload.IrisQuartzElytraBoostPayLoad;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings("all")
public class ClientIrisQuartzElytraBoostEvent {
    public static final KeyBinding KEY_BOOST_IRIS_QUARTZ_ELYTRA = register("iris_quartz_elytra", GLFW.GLFW_KEY_G);

    private static KeyBinding register(String id, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + Minium_me.MOD_ID + "." + id, key, "key.categories." + Minium_me.MOD_ID));
    }
    public static void initialize(){
        /*
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBoostIrisQuartzElytra.isPressed()) {
                Objects.requireNonNull(client.player).sendMessage(Text.literal("Gキーが押されました！"), false);
            }
        });*/

        ClientTickEvents.START_WORLD_TICK.register(world -> {
            if (KEY_BOOST_IRIS_QUARTZ_ELYTRA.wasPressed()) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player == null)
                    return;
                ItemStack beltStack = IrisQuartzElytraBoostEvent.getWindExploderItem(client.player);
                if(beltStack != null){
                    ClientPlayNetworking.send(IrisQuartzElytraBoostPayLoad.INSTANCE);
                    return;
                }
                ItemStack itemStack = Minium_me.ACCESSORY_PLATFORM.getIrisQuartzElytraStack(client.player);
                if(itemStack == null)
                    return;
                if (client.player.getItemCooldownManager().getCooldownProgress(itemStack.getItem(), 0.0f) < 0.8 && client.player.isFallFlying()) {
                    ClientPlayNetworking.send(IrisQuartzElytraBoostPayLoad.INSTANCE);
                }
            }
        });

    }
}
