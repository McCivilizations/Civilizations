package com.mccivilizations.civilizations.api.date;

import com.mccivilizations.civilizations.api.CivilizationsAPI;

public class MinecraftDate {
    public static final long ticksInYear = 8766000;
    public static final long ticksInMonth = 720000;
    public static final long ticksInDay = 24000;

    private final long year;
    private final long month;
    private final long day;

    private final long ticks;

    public MinecraftDate(long ticks) {
        this.ticks = ticks;

        this.year = ticks / ticksInYear;
        long ticksRemaining = ticks % ticksInYear;

        this.month = ticksRemaining / ticksInMonth;
        ticksRemaining = ticksRemaining % ticksInMonth;

        this.day = ticksRemaining / ticksInDay;
    }

    public long getYear() {
        return this.year;
    }

    public long getMonth() {
        return this.month;
    }

    public String getMonthName() {
        return CivilizationsAPI.getInstance().getLocalizationHandler().format("civilizations.date.month." + this.getMonth());
    }

    public long getDay() {
        return this.day;
    }

    public long getTicks() {
        return this.ticks;
    }

    public String toString() {
        return this.getYear() + "/" + this.getMonth() + "/" + this.getDay();
    }


}
