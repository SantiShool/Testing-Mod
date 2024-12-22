package com.nukateam.nukacraft.common.data.utils;

import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.nukacraft.common.foundation.items.frame.ArmorPart;
import com.nukateam.nukacraft.common.foundation.items.misc.PipBoyItem;
import net.minecraft.world.item.ItemStack;

import static com.nukateam.ntgl.common.util.util.ResourceUtils.resourceExists;
import static com.nukateam.nukacraft.common.data.utils.Resources.nukaResource;

public class PaintHelper {
    public static boolean hasTexture(ItemStack slotItem, String id) {
        var isGun = slotItem.getItem() instanceof GunItem gunItem
                && hasTexture(gunItem.getModifiedGun(slotItem), id);
        var isArmor = slotItem.getItem() instanceof ArmorPart armorPart
                && hasTexture(armorPart, id);
        var isPipBoy = slotItem.getItem() instanceof PipBoyItem
                && hasPipBoyTexture(id);

        return isGun || isArmor || isPipBoy;
    }

    private static boolean hasTexture(ArmorPart armorPart, String id) {
        return armorPart.getConfig().getTexture(id) != null;
    }

    private static boolean hasTexture(Gun gun, String id) {
        return gun.getTextures().containsKey(id);
    }

    private static boolean hasPipBoyTexture(String id) {
        var texture = nukaResource("textures/item/pipboy/pipboy2000_" + id + ".png");
        return resourceExists(texture);
    }
}
