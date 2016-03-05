package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemFishingRod extends Item
{
    public ItemFishingRod()
    {
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.addPropertyOverride(new ResourceLocation("cast"), new IItemPropertyGetter()
        {
        });
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (playerIn.fishEntity != null)
        {
            int i = playerIn.fishEntity.handleHookRetraction();
            itemStackIn.damageItem(i, playerIn);
            playerIn.swingArm(hand);
        }
        else
        {
            worldIn.func_184148_a((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.entity_bobber_throw, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!worldIn.isRemote)
            {
                worldIn.spawnEntityInWorld(new EntityFishHook(worldIn, playerIn));
            }

            playerIn.swingArm(hand);
            playerIn.triggerAchievement(StatList.func_188057_b(this));
        }

        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }

    /**
     * Checks isDamagable and if it cannot be stacked
     */
    public boolean isItemTool(ItemStack stack)
    {
        return super.isItemTool(stack);
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 1;
    }
}
