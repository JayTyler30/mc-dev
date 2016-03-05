package net.minecraft.potion;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

public class PotionUtils
{
    public static List<PotionEffect> getEffectsFromStack(ItemStack stack)
    {
        return getEffectsFromTag(stack.getTagCompound());
    }

    public static List<PotionEffect> func_185186_a(PotionType p_185186_0_, Collection<PotionEffect> p_185186_1_)
    {
        List<PotionEffect> list = Lists.<PotionEffect>newArrayList();
        list.addAll(p_185186_0_.getEffects());
        list.addAll(p_185186_1_);
        return list;
    }

    public static List<PotionEffect> getEffectsFromTag(NBTTagCompound tag)
    {
        List<PotionEffect> list = Lists.<PotionEffect>newArrayList();
        list.addAll(func_185187_c(tag).getEffects());
        func_185193_a(tag, list);
        return list;
    }

    public static List<PotionEffect> func_185190_b(ItemStack p_185190_0_)
    {
        return func_185192_b(p_185190_0_.getTagCompound());
    }

    public static List<PotionEffect> func_185192_b(NBTTagCompound p_185192_0_)
    {
        List<PotionEffect> list = Lists.<PotionEffect>newArrayList();
        func_185193_a(p_185192_0_, list);
        return list;
    }

    public static void func_185193_a(NBTTagCompound p_185193_0_, List<PotionEffect> p_185193_1_)
    {
        if (p_185193_0_ != null && p_185193_0_.hasKey("CustomPotionEffects", 9))
        {
            NBTTagList nbttaglist = p_185193_0_.getTagList("CustomPotionEffects", 10);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);

                if (potioneffect != null)
                {
                    p_185193_1_.add(potioneffect);
                }
            }
        }
    }

    public static int func_185181_a(Collection<PotionEffect> p_185181_0_)
    {
        int i = 3694022;

        if (p_185181_0_.isEmpty())
        {
            return 3694022;
        }
        else
        {
            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            int j = 0;

            for (PotionEffect potioneffect : p_185181_0_)
            {
                if (potioneffect.func_188418_e())
                {
                    int k = potioneffect.func_188419_a().getLiquidColor();
                    int l = potioneffect.getAmplifier() + 1;
                    f += (float)(l * (k >> 16 & 255)) / 255.0F;
                    f1 += (float)(l * (k >> 8 & 255)) / 255.0F;
                    f2 += (float)(l * (k >> 0 & 255)) / 255.0F;
                    j += l;
                }
            }

            if (j == 0)
            {
                return 0;
            }
            else
            {
                f = f / (float)j * 255.0F;
                f1 = f1 / (float)j * 255.0F;
                f2 = f2 / (float)j * 255.0F;
                return (int)f << 16 | (int)f1 << 8 | (int)f2;
            }
        }
    }

    public static PotionType func_185191_c(ItemStack p_185191_0_)
    {
        return func_185187_c(p_185191_0_.getTagCompound());
    }

    public static PotionType func_185187_c(NBTTagCompound p_185187_0_)
    {
        return p_185187_0_ == null ? PotionTypes.water : PotionType.getPotionTypeForName(p_185187_0_.getString("Potion"));
    }

    public static ItemStack func_185188_a(ItemStack p_185188_0_, PotionType p_185188_1_)
    {
        ResourceLocation resourcelocation = (ResourceLocation)PotionType.REGISTRY.getNameForObject(p_185188_1_);

        if (resourcelocation != null)
        {
            NBTTagCompound nbttagcompound = p_185188_0_.hasTagCompound() ? p_185188_0_.getTagCompound() : new NBTTagCompound();
            nbttagcompound.setString("Potion", resourcelocation.toString());
            p_185188_0_.setTagCompound(nbttagcompound);
        }

        return p_185188_0_;
    }

    public static ItemStack func_185184_a(ItemStack p_185184_0_, Collection<PotionEffect> p_185184_1_)
    {
        if (p_185184_1_.isEmpty())
        {
            return p_185184_0_;
        }
        else
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)Objects.firstNonNull(p_185184_0_.getTagCompound(), new NBTTagCompound());
            NBTTagList nbttaglist = nbttagcompound.getTagList("CustomPotionEffects", 9);

            for (PotionEffect potioneffect : p_185184_1_)
            {
                nbttaglist.appendTag(potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }

            nbttagcompound.setTag("CustomPotionEffects", nbttaglist);
            p_185184_0_.setTagCompound(nbttagcompound);
            return p_185184_0_;
        }
    }
}
