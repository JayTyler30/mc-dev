package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDye extends Item
{
    public static final int[] dyeColors = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

    public ItemDye()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack)
    {
        int i = stack.getMetadata();
        return super.getUnlocalizedName() + "." + EnumDyeColor.byDyeDamage(i).getUnlocalizedName();
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand side, EnumFacing hitX, float hitY, float hitZ, float p_180614_9_)
    {
        if (!playerIn.canPlayerEdit(pos.offset(hitX), hitX, stack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());

            if (enumdyecolor == EnumDyeColor.WHITE)
            {
                if (applyBonemeal(stack, worldIn, pos))
                {
                    if (!worldIn.isRemote)
                    {
                        worldIn.playAuxSFX(2005, pos, 0);
                    }

                    return EnumActionResult.SUCCESS;
                }
            }
            else if (enumdyecolor == EnumDyeColor.BROWN)
            {
                IBlockState iblockstate = worldIn.getBlockState(pos);
                Block block = iblockstate.getBlock();

                if (block == Blocks.log && iblockstate.getValue(BlockOldLog.VARIANT) == BlockPlanks.EnumType.JUNGLE)
                {
                    if (hitX != EnumFacing.DOWN && hitX != EnumFacing.UP)
                    {
                        pos = pos.offset(hitX);

                        if (worldIn.isAirBlock(pos))
                        {
                            IBlockState iblockstate1 = Blocks.cocoa.onBlockPlaced(worldIn, pos, hitX, hitY, hitZ, p_180614_9_, 0, playerIn);
                            worldIn.setBlockState(pos, iblockstate1, 10);

                            if (!playerIn.capabilities.isCreativeMode)
                            {
                                --stack.stackSize;
                            }
                        }

                        return EnumActionResult.SUCCESS;
                    }

                    return EnumActionResult.FAIL;
                }

                return EnumActionResult.FAIL;
            }

            return EnumActionResult.PASS;
        }
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target)
    {
        IBlockState iblockstate = worldIn.getBlockState(target);

        if (iblockstate.getBlock() instanceof IGrowable)
        {
            IGrowable igrowable = (IGrowable)iblockstate.getBlock();

            if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote))
            {
                if (!worldIn.isRemote)
                {
                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate))
                    {
                        igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
                    }

                    --stack.stackSize;
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand p_111207_4_)
    {
        if (target instanceof EntitySheep)
        {
            EntitySheep entitysheep = (EntitySheep)target;
            EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());

            if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != enumdyecolor)
            {
                entitysheep.setFleeceColor(enumdyecolor);
                --stack.stackSize;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
