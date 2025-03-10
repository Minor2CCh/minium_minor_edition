package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.List;

public class MiniumSmithingTemplateItem extends SmithingTemplateItem {
//Identifier.of(Minium_me.MOD_ID, "minium_upgrade")
    private static final Formatting TITLE_FORMATTING = Formatting.GRAY;
    private static final Formatting DESCRIPTION_FORMATTING = Formatting.BLUE;
    private static final Text APPLIES_TO_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.ofVanilla("smithing_template.applies_to"))).formatted(TITLE_FORMATTING);
    private static final Text INGREDIENTS_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.ofVanilla("smithing_template.ingredients"))).formatted(TITLE_FORMATTING);
    private static final Text MINIUM_UPGRADE_TEXT = Text.translatable(Util.createTranslationKey("upgrade", Identifier.of(Minium_me.MOD_ID, "minium_upgrade"))).formatted(TITLE_FORMATTING);
    private static final Text MINIUM_UPGRADE_APPLIES_TO_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(Minium_me.MOD_ID, "smithing_template.minium_upgrade.applies_to"))).formatted(DESCRIPTION_FORMATTING);
    private static final Text MINIUM_UPGRADE_INGREDIENTS_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(Minium_me.MOD_ID, "smithing_template.minium_upgrade.ingredients"))).formatted(DESCRIPTION_FORMATTING);
    private static final Text MINIUM_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(Minium_me.MOD_ID, "smithing_template.minium_upgrade.base_slot_description")));
    private static final Text MINIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(Minium_me.MOD_ID, "smithing_template.minium_upgrade.additions_slot_description")));
    private static final Text IRIS_QUARTZ_UPGRADE_TEXT = Text.translatable(Util.createTranslationKey("upgrade", Identifier.of(Minium_me.MOD_ID, "iris_quartz_upgrade"))).formatted(TITLE_FORMATTING);
    private static final Text IRIS_QUARTZ_UPGRADE_APPLIES_TO_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(Minium_me.MOD_ID, "smithing_template.iris_quartz_upgrade.applies_to"))).formatted(DESCRIPTION_FORMATTING);
    private static final Text IRIS_QUARTZ_UPGRADE_INGREDIENTS_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(Minium_me.MOD_ID, "smithing_template.iris_quartz_upgrade.ingredients"))).formatted(DESCRIPTION_FORMATTING);
    private static final Text IRIS_QUARTZ_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(Minium_me.MOD_ID, "smithing_template.iris_quartz_upgrade.base_slot_description")));
    private static final Text IRIS_QUARTZ_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(Minium_me.MOD_ID, "smithing_template.iris_quartz_upgrade.additions_slot_description")));
    private static final Identifier EMPTY_ARMOR_SLOT_HELMET_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_helmet");
    private static final Identifier EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_chestplate");
    private static final Identifier EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_leggings");
    private static final Identifier EMPTY_ARMOR_SLOT_BOOTS_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_boots");
    private static final Identifier EMPTY_SLOT_HOE_TEXTURE = Identifier.ofVanilla("item/empty_slot_hoe");
    private static final Identifier EMPTY_SLOT_AXE_TEXTURE = Identifier.ofVanilla("item/empty_slot_axe");
    private static final Identifier EMPTY_SLOT_SWORD_TEXTURE = Identifier.ofVanilla("item/empty_slot_sword");
    private static final Identifier EMPTY_SLOT_SHOVEL_TEXTURE = Identifier.ofVanilla("item/empty_slot_shovel");
    private static final Identifier EMPTY_SLOT_PICKAXE_TEXTURE = Identifier.ofVanilla("item/empty_slot_pickaxe");
    private static final Identifier EMPTY_SLOT_INGOT_TEXTURE = Identifier.ofVanilla("item/empty_slot_ingot");
    private final Text appliesToText;
    private final Text ingredientsText;
    private final Text titleText;
    private final Text baseSlotDescriptionText;
    private final Text additionsSlotDescriptionText;
    private final List<Identifier> emptyBaseSlotTextures;
    private final List<Identifier> emptyAdditionsSlotTextures;

    public MiniumSmithingTemplateItem(Text appliesToText, Text ingredientsText, Text titleText, Text baseSlotDescriptionText, Text additionsSlotDescriptionText, List<Identifier> emptyBaseSlotTextures, List<Identifier> emptyAdditionsSlotTextures, Text appliesToText1, Text ingredientsText1, Text titleText1, Text baseSlotDescriptionText1, Text additionsSlotDescriptionText1, List<Identifier> emptyBaseSlotTextures1, List<Identifier> emptyAdditionsSlotTextures1, FeatureFlag... requiredFeatures) {
        super(appliesToText, ingredientsText, titleText, baseSlotDescriptionText, additionsSlotDescriptionText, emptyBaseSlotTextures, emptyAdditionsSlotTextures, requiredFeatures);
        this.appliesToText = appliesToText1;
        this.ingredientsText = ingredientsText1;
        this.titleText = titleText1;
        this.baseSlotDescriptionText = baseSlotDescriptionText1;
        this.additionsSlotDescriptionText = additionsSlotDescriptionText1;
        this.emptyBaseSlotTextures = emptyBaseSlotTextures1;
        this.emptyAdditionsSlotTextures = emptyAdditionsSlotTextures1;
    }

    public static SmithingTemplateItem createMiniumUpgrade() {
        return new SmithingTemplateItem(MINIUM_UPGRADE_APPLIES_TO_TEXT, MINIUM_UPGRADE_INGREDIENTS_TEXT, MINIUM_UPGRADE_TEXT, MINIUM_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT, MINIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT, getMiniumUpgradeEmptyBaseSlotTextures(), getMiniumUpgradeEmptyAdditionsSlotTextures(), new FeatureFlag[0]);
    }

    public static SmithingTemplateItem createIrisQuartzUpgrade() {
        return new SmithingTemplateItem(IRIS_QUARTZ_UPGRADE_APPLIES_TO_TEXT, IRIS_QUARTZ_UPGRADE_INGREDIENTS_TEXT, IRIS_QUARTZ_UPGRADE_TEXT, IRIS_QUARTZ_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT, IRIS_QUARTZ_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT, getMiniumUpgradeEmptyBaseSlotTextures(), getMiniumUpgradeEmptyAdditionsSlotTextures(), new FeatureFlag[0]);
    }
    private static List<Identifier> getMiniumUpgradeEmptyBaseSlotTextures() {
        return List.of(EMPTY_ARMOR_SLOT_HELMET_TEXTURE, EMPTY_SLOT_SWORD_TEXTURE, EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE, EMPTY_SLOT_PICKAXE_TEXTURE, EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE, EMPTY_SLOT_AXE_TEXTURE, EMPTY_ARMOR_SLOT_BOOTS_TEXTURE, EMPTY_SLOT_HOE_TEXTURE, EMPTY_SLOT_SHOVEL_TEXTURE);
    }

    private static List<Identifier> getMiniumUpgradeEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_INGOT_TEXTURE);
    }
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(this.titleText);
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(APPLIES_TO_TEXT);
        tooltip.add(ScreenTexts.space().append(this.appliesToText));
        tooltip.add(INGREDIENTS_TEXT);
        tooltip.add(ScreenTexts.space().append(this.ingredientsText));
    }


}
