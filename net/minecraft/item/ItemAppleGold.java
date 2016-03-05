package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;

public class ItemAppleGold extends ItemFood
{
    public ItemAppleGold(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        this.setHasSubtypes(true);
    }

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack)
    {
        return stack.getMetadata() == 0 ? EnumRarity.RARE : EnumRarity.EPIC;
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
            if (stack.getMetadata() > 0)
            {
                player.triggerAchievement(AchievementList.field_187980_M);
                player.addPotionEffect(new PotionEffect(MobEffects.regeneration, 400, 1));
                player.addPotionEffect(new PotionEffect(MobEffects.resistance, 6000, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.fireResistance, 6000, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.absorption, 2400, 3));
            }
            else
            {
                player.addPotionEffect(new PotionEffect(MobEffects.regeneration, 100, 1));
                player.addPotionEffect(new PotionEffect(MobEffects.absorption, 2400, 0));
            }
        }
    }
}
