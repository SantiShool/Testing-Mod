package com.nukateam.nukacraft.common.foundation.items.misc;

import com.jetug.chassis_core.common.foundation.item.StackUtils;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.nukacraft.common.foundation.items.frame.ArmorPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Objects;

import static com.nukateam.nukacraft.common.data.constants.Nbt.CLEAN;

public class PaintJobItem extends Item {
    private final String paintId;
    public PaintJobItem(String paintId, Properties pProperties) {
        super(pProperties);
        this.paintId = paintId;
    }
    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        var slotItem = pSlot.getItem();
//        var isGun = slotItem.getItem() instanceof GunItem gunItem
//                && gunItem.getModifiedGun(stack).getTextures().containsKey(CLEAN);
//        var isArmor = slotItem.getItem() instanceof ArmorPart armorPart
//                && armorPart.getConfig().getTexture(CLEAN) != null;
//        var isPaintable = isArmor || isGun;
//        var isNotClean = !(Objects.equals(StackUtils.getVariant(slotItem), CLEAN));
//

        var isPaintable = (
                slotItem.getItem() instanceof ArmorPart ||
                slotItem.getItem() instanceof GunItem ||
                slotItem.getItem() instanceof PipBoyItem);

        if (pAction == ClickAction.SECONDARY) {

            if (isPaintable && (Objects.equals(StackUtils.getVariant(slotItem), CLEAN))) {
                StackUtils.setVariant(slotItem, paintId);
                if(!pPlayer.isCreative()) {
                    if (pStack.getDamageValue() == 12)
                        pStack.shrink(1);
                    else pStack.setDamageValue(pStack.getDamageValue() + 1);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}
