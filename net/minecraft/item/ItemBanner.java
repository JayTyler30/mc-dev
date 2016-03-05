package net.minecraft.item;

import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemBanner extends ItemBlock
{
    public ItemBanner()
    {
        super(Blocks.standing_banner);
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand side, EnumFacing hitX, float hitY, float hitZ, float p_180614_9_)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        boolean flag = iblockstate.getBlock().isReplaceable(worldIn, pos);

        if (hitX != EnumFacing.DOWN && (iblockstate.getMaterial().isSolid() || flag) && (!flag || hitX == EnumFacing.UP))
        {
            pos = pos.offset(hitX);

            if (playerIn.canPlayerEdit(pos, hitX, stack) && Blocks.standing_banner.canPlaceBlockAt(worldIn, pos))
            {
                if (worldIn.isRemote)
                {
                    return EnumActionResult.SUCCESS;
                }
                else
                {
                    pos = flag ? pos.down() : pos;

                    if (hitX == EnumFacing.UP)
                    {
                        int i = MathHelper.floor_double((double)((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                        worldIn.setBlockState(pos, Blocks.standing_banner.getDefaultState().withProperty(BlockStandingSign.ROTATION, Integer.valueOf(i)), 3);
                    }
                    else
                    {
                        worldIn.setBlockState(pos, Blocks.wall_banner.getDefaultState().withProperty(BlockWallSign.FACING, hitX), 3);
                    }

                    --stack.stackSize;
                    TileEntity tileentity = worldIn.getTileEntity(pos);

                    if (tileentity instanceof TileEntityBanner)
                    {
                        ((TileEntityBanner)tileentity).setItemValues(stack);
                    }

                    return EnumActionResult.SUCCESS;
                }
            }
            else
            {
                return EnumActionResult.FAIL;
            }
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        String s = "item.banner.";
        EnumDyeColor enumdyecolor = getBaseColor(stack);
        s = s + enumdyecolor.getUnlocalizedName() + ".name";
        return I18n.translateToLocal(s);
    }

    public static EnumDyeColor getBaseColor(ItemStack p_179225_0_)
    {
        NBTTagCompound nbttagcompound = p_179225_0_.getSubCompound("BlockEntityTag", false);
        EnumDyeColor enumdyecolor = null;

        if (nbttagcompound != null && nbttagcompound.hasKey("Base"))
        {
            enumdyecolor = EnumDyeColor.byDyeDamage(nbttagcompound.getInteger("Base"));
        }
        else
        {
            enumdyecolor = EnumDyeColor.byDyeDamage(p_179225_0_.getMetadata());
        }

        return enumdyecolor;
    }
}
