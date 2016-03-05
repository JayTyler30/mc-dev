package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRecord extends Item
{
    private static final Map<SoundEvent, ItemRecord> RECORDS = Maps.<SoundEvent, ItemRecord>newHashMap();
    private final SoundEvent field_185076_b;
    private final String field_185077_c;

    protected ItemRecord(String p_i46742_1_, SoundEvent p_i46742_2_)
    {
        this.field_185077_c = "item.record." + p_i46742_1_ + ".desc";
        this.field_185076_b = p_i46742_2_;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabMisc);
        RECORDS.put(this.field_185076_b, this);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand side, EnumFacing hitX, float hitY, float hitZ, float p_180614_9_)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getBlock() == Blocks.jukebox && !((Boolean)iblockstate.getValue(BlockJukebox.HAS_RECORD)).booleanValue())
        {
            if (!worldIn.isRemote)
            {
                ((BlockJukebox)Blocks.jukebox).insertRecord(worldIn, pos, iblockstate, stack);
                worldIn.playAuxSFXAtEntity((EntityPlayer)null, 1010, pos, Item.getIdFromItem(this));
                --stack.stackSize;
                playerIn.triggerAchievement(StatList.field_188092_Z);
            }

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.PASS;
        }
    }

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }
}
