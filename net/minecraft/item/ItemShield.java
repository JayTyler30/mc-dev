package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemShield extends Item
{
    public ItemShield()
    {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setMaxDamage(336);
        this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter()
        {
        });
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand side, EnumFacing hitX, float hitY, float hitZ, float p_180614_9_)
    {
        return super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ, p_180614_9_);
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        if (stack.getSubCompound("BlockEntityTag", false) != null)
        {
            String s = "item.shield.";
            EnumDyeColor enumdyecolor = ItemBanner.getBaseColor(stack);
            s = s + enumdyecolor.getUnlocalizedName() + ".name";
            return I18n.translateToLocal(s);
        }
        else
        {
            return I18n.translateToLocal("item.shield.name");
        }
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BLOCK;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        playerIn.func_184598_c(hand);
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == Item.getItemFromBlock(Blocks.planks) ? true : super.getIsRepairable(toRepair, repair);
    }
}
