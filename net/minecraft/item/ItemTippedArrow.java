package net.minecraft.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemTippedArrow extends ItemArrow
{
    public EntityArrow func_185052_a(World worldIn, ItemStack p_185052_2_, EntityLivingBase shooter)
    {
        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(worldIn, shooter);
        entitytippedarrow.func_184555_a(p_185052_2_);
        return entitytippedarrow;
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return I18n.translateToLocal(PotionUtils.func_185191_c(stack).func_185174_b("tipped_arrow.effect."));
    }
}
