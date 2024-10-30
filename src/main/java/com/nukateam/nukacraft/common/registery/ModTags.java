package com.nukateam.nukacraft.common.registery;

import com.nukateam.nukacraft.NukaCraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> CROWBARS = TagKey.create(Registries.ITEM, createResourceLocation("tools/crowbars"));

    public static ResourceLocation createResourceLocation(String tagName)
    {
        return new ResourceLocation(NukaCraftMod.MOD_ID, tagName);
    }
}
