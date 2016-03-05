package net.minecraft.item;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFirework extends Item
{
    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand side, EnumFacing hitX, float hitY, float hitZ, float p_180614_9_)
    {
        if (!worldIn.isRemote)
        {
            EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(worldIn, (double)((float)pos.getX() + hitY), (double)((float)pos.getY() + hitZ), (double)((float)pos.getZ() + p_180614_9_), stack);
            worldIn.spawnEntityInWorld(entityfireworkrocket);

            if (!playerIn.capabilities.isCreativeMode)
            {
                --stack.stackSize;
            }
        }

        return EnumActionResult.SUCCESS;
    }
}
