package com.nukateam.nukacraft.common.foundation.items.misc;

import com.jetug.chassis_core.common.foundation.item.StackUtils;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.nukacraft.common.data.utils.PaintHelper;
import com.nukateam.nukacraft.common.foundation.items.frame.ArmorPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

import static com.nukateam.nukacraft.common.data.constants.Nbt.CLEAN;

public class IndustrialAbraxoItem extends Item {


    public IndustrialAbraxoItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        var slotItem = pSlot.getItem();
        var isNotClean = !(Objects.equals(StackUtils.getVariant(slotItem), CLEAN));

        if (pAction == ClickAction.SECONDARY) {
            if (PaintHelper.hasTexture(slotItem, CLEAN) && isNotClean) {
                StackUtils.setVariant(slotItem, CLEAN);
                if(!pPlayer.isCreative())
                    stack.shrink(1);
                return true;
            }
        }
        return false;
    }



}
