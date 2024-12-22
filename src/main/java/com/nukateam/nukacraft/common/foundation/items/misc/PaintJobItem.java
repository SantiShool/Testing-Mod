package com.nukateam.nukacraft.common.foundation.items.misc;

import com.jetug.chassis_core.common.foundation.item.StackUtils;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.nukacraft.common.data.utils.PaintHelper;
import com.nukateam.nukacraft.common.foundation.items.frame.ArmorPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import java.util.Objects;

import static com.nukateam.ntgl.common.util.util.ResourceUtils.resourceExists;
import static com.nukateam.nukacraft.common.data.constants.Nbt.CLEAN;
import static com.nukateam.nukacraft.common.data.utils.Resources.nukaResource;

public class PaintJobItem extends Item {
    private final String paintId;
    public PaintJobItem(String paintId, Properties pProperties) {
        super(pProperties);
        this.paintId = paintId;
    }
    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        var slotItem = pSlot.getItem();

        if (pAction == ClickAction.SECONDARY) {
            var isClean = Objects.equals(StackUtils.getVariant(slotItem), CLEAN);
            if (PaintHelper.hasTexture(slotItem, paintId) && isClean) {
                StackUtils.setVariant(slotItem, paintId);
                if(!pPlayer.isCreative()) {
                    if (stack.getDamageValue() == 12)
                        stack.shrink(1);
                    else stack.setDamageValue(stack.getDamageValue() + 1);
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
