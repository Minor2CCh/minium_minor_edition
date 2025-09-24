package com.github.Minor2CCh.minium_me.mixin.compat;

import com.illusivesoulworks.elytraslot.common.SimpleCompatibilityProvider;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SimpleCompatibilityProvider.class)
public interface SimpleCompatibilityProviderAccessor {
    @Accessor(value = "ID_TO_TEXTURE", remap = false)
    static Map<String, Identifier> getIdToTexture() {
        throw new AssertionError();
    }

}
