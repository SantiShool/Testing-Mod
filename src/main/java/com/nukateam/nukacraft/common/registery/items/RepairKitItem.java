package com.nukateam.nukacraft.common.registery.items;

import com.jetug.chassis_core.common.foundation.item.StackUtils;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.nukacraft.NukaCraftMod;
import com.nukateam.nukacraft.common.foundation.items.frame.ArmorPart;
import com.nukateam.nukacraft.common.foundation.items.misc.SimpleMeleeWeapon;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Objects;

public class RepairKitItem extends Item {
    private int repairPerClick;

    public RepairKitItem(int repairPerClick, Properties pProperties) {
        super(pProperties);
        this.repairPerClick = repairPerClick;
    }
    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        ItemStack slotItem = pSlot.getItem();
        int targetDamage = slotItem.getDamageValue();
        int kitDurability = stack.getMaxDamage() - stack.getDamageValue();
        if (pAction == ClickAction.SECONDARY) {
            if (slotItem.getItem() instanceof ArmorPart || slotItem.getItem() instanceof SimpleMeleeWeapon || slotItem.getItem() instanceof ArmorItem) {
                if (!(Screen.hasControlDown())) {
                    if (kitDurability >= repairPerClick) {
                        if (targetDamage <= repairPerClick) {
                            stack.setDamageValue(stack.getDamageValue() + targetDamage);
                            slotItem.setDamageValue(slotItem.getDamageValue() - targetDamage);
                            return false;
                        } else {
                            stack.setDamageValue(stack.getDamageValue() + repairPerClick);
                            slotItem.setDamageValue(slotItem.getDamageValue() - repairPerClick);
                            return false;
                        }
                    } else {
                        if (targetDamage <= kitDurability) {
                            stack.setDamageValue(stack.getDamageValue() + targetDamage);
                            slotItem.setDamageValue(slotItem.getDamageValue() - targetDamage);
                            return false;
                        } else {
                            stack.setDamageValue(stack.getDamageValue() + kitDurability);
                            slotItem.setDamageValue(slotItem.getDamageValue() - kitDurability);
                            stack.shrink(1);
                            return false;
                        }
                    }
                } else {
                    if (targetDamage <= kitDurability) {
                        stack.setDamageValue(stack.getDamageValue() + targetDamage);
                        slotItem.setDamageValue(slotItem.getDamageValue() - targetDamage);
                        return false;
                    } else {
                        stack.setDamageValue(stack.getDamageValue() + kitDurability);
                        slotItem.setDamageValue(slotItem.getDamageValue() - kitDurability);
                        stack.shrink(1);
                        return false;
                    }
                }
            }
        }

        return false;
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
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
