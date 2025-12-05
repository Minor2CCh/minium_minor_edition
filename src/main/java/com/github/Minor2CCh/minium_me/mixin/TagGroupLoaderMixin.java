package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import com.google.common.collect.ImmutableSet;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagGroupLoader;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(TagGroupLoader.class)
public class TagGroupLoaderMixin {
    @Shadow
    @Final
    private String dataType;

    @Inject(method = "buildGroup(Ljava/util/Map;)Ljava/util/Map;", at = @At(value = "RETURN"))
    public <T> void build(Map<Identifier, List<TagGroupLoader.TrackedEntry>> map, CallbackInfoReturnable<Map<Identifier, Collection<T>>> cir) {

        Map<TagKey<Item>, List<TagKey<Item>>> blackLists =
                Map.of(MiniumItemTag.REINFORCEABLE_TOOL, List.of(MiniumItemTag.REINFORCE_BLACKLIST),
                        MiniumItemTag.REINFORCEABLE_WEAPON, List.of(MiniumItemTag.REINFORCE_BLACKLIST),
                        MiniumItemTag.REINFORCEABLE_ARMOR, List.of(MiniumItemTag.REINFORCE_BLACKLIST),
                        MiniumItemTag.REINFORCEABLE_AXE, List.of(MiniumItemTag.REINFORCE_BLACKLIST));
        if (RegistryKeys.getTagPath(RegistryKeys.ITEM).equals(dataType)) {
            for (Map.Entry<TagKey<Item>, List<TagKey<Item>>> entry : blackLists.entrySet()) {
                Map<Identifier, Collection<T>> result = cir.getReturnValue();
                List<T> excluded = new ArrayList<>();
                for (TagKey<Item> tag : entry.getValue()) {
                    Optional<Collection<T>> exclusion = result.entrySet().stream().filter(e -> e.getKey().equals(tag.id())).findFirst().map(Map.Entry::getValue);
                    exclusion.ifPresent(excluded::addAll);
                }
                Optional<Collection<T>> vanilla = result.entrySet().stream().filter(e -> e.getKey().equals(entry.getKey().id())).findFirst().map(Map.Entry::getValue);
                if (!excluded.isEmpty() && vanilla.isPresent()) {
                    Set<T> modified = new HashSet<>(vanilla.get());
                    modified.removeIf(excluded::contains);
                    result.put(entry.getKey().id(), ImmutableSet.copyOf(modified));
                }
            }
        }
    }
}
