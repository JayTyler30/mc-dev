package net.minecraft.stats;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.ScoreCriteriaStat;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;

public class StatBase
{
    /** The Stat ID */
    public final String statId;

    /** The Stat name */
    private final ITextComponent statName;
    public boolean isIndependent;
    private final IStatType type;
    private final IScoreCriteria objectiveCriteria;
    private Class <? extends IJsonSerializable > field_150956_d;
    private static NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.US);
    public static IStatType simpleStatType = new IStatType()
    {
    };
    private static DecimalFormat decimalFormat = new DecimalFormat("########0.00");
    public static IStatType timeStatType = new IStatType()
    {
    };
    public static IStatType distanceStatType = new IStatType()
    {
    };
    public static IStatType field_111202_k = new IStatType()
    {
    };

    public StatBase(String statIdIn, ITextComponent statNameIn, IStatType typeIn)
    {
        this.statId = statIdIn;
        this.statName = statNameIn;
        this.type = typeIn;
        this.objectiveCriteria = new ScoreCriteriaStat(this);
        IScoreCriteria.INSTANCES.put(this.objectiveCriteria.getName(), this.objectiveCriteria);
    }

    public StatBase(String statIdIn, ITextComponent statNameIn)
    {
        this(statIdIn, statNameIn, simpleStatType);
    }

    /**
     * Initializes the current stat as independent (i.e., lacking prerequisites for being updated) and returns the
     * current instance.
     */
    public StatBase initIndependentStat()
    {
        this.isIndependent = true;
        return this;
    }

    /**
     * Register the stat into StatList.
     */
    public StatBase registerStat()
    {
        if (StatList.field_188093_a.containsKey(this.statId))
        {
            throw new RuntimeException("Duplicate stat id: \"" + ((StatBase)StatList.field_188093_a.get(this.statId)).statName + "\" and \"" + this.statName + "\" at id " + this.statId);
        }
        else
        {
            StatList.allStats.add(this);
            StatList.field_188093_a.put(this.statId, this);
            return this;
        }
    }

    /**
     * Returns whether or not the StatBase-derived class is a statistic (running counter) or an achievement (one-shot).
     */
    public boolean isAchievement()
    {
        return false;
    }

    public ITextComponent getStatName()
    {
        ITextComponent itextcomponent = this.statName.createCopy();
        itextcomponent.getChatStyle().setColor(TextFormatting.GRAY);
        itextcomponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new TextComponentString(this.statId)));
        return itextcomponent;
    }

    /**
     * 1.8.9
     */
    public ITextComponent createChatComponent()
    {
        ITextComponent itextcomponent = this.getStatName();
        ITextComponent itextcomponent1 = (new TextComponentString("[")).appendSibling(itextcomponent).appendText("]");
        itextcomponent1.setChatStyle(itextcomponent.getChatStyle());
        return itextcomponent1;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass())
        {
            StatBase statbase = (StatBase)p_equals_1_;
            return this.statId.equals(statbase.statId);
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return this.statId.hashCode();
    }

    public String toString()
    {
        return "Stat{id=" + this.statId + ", nameId=" + this.statName + ", awardLocallyOnly=" + this.isIndependent + ", formatter=" + this.type + ", objectiveCriteria=" + this.objectiveCriteria + '}';
    }

    /**
     * 1.8.9
     */
    public IScoreCriteria getCriteria()
    {
        return this.objectiveCriteria;
    }

    public Class <? extends IJsonSerializable > func_150954_l()
    {
        return this.field_150956_d;
    }

    public StatBase func_150953_b(Class <? extends IJsonSerializable > p_150953_1_)
    {
        this.field_150956_d = p_150953_1_;
        return this;
    }
}
