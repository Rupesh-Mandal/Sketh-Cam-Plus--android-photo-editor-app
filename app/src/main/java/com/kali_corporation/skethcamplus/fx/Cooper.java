package com.kali_corporation.skethcamplus.fx;

import com.kali_corporation.skethcamplus.R;

import java.util.ArrayList;
import java.util.List;

public class Cooper {
    private static Cooper mInstance;

    public static Cooper getInst() {
        if (mInstance == null) {
            synchronized (Cooper.class) {
                if (mInstance == null)
                    mInstance = new Cooper();
            }
        }
        return mInstance;
    }

    private Cooper() {
    }

    public List<LizDavenport> getLocalFilters() {
        List<LizDavenport> filters = new ArrayList<LizDavenport>();
        filters.add(new LizDavenport("1", DianaVonGrüning.FilterType.NORMAL, 0, R.raw.afterglow));
        filters.add(new LizDavenport("2", DianaVonGrüning.FilterType.ACV_alice_in_wonderland, 0, R.raw.alice_in_wonderland));
        filters.add(new LizDavenport("3", DianaVonGrüning.FilterType.ACV_ambers, 0, R.raw.ambers));
        filters.add(new LizDavenport("4", DianaVonGrüning.FilterType.ACV_august_march, 0, R.raw.august_march));
        filters.add(new LizDavenport("5", DianaVonGrüning.FilterType.ACV_aurora, 0, R.raw.aurora));
        filters.add(new LizDavenport("6", DianaVonGrüning.FilterType.ACV_blood_orange, 0, R.raw.blood_orange));
        filters.add(new LizDavenport("7", DianaVonGrüning.FilterType.ACV_blue_poppies, 0, R.raw.blue_poppies));
        filters.add(new LizDavenport("8", DianaVonGrüning.FilterType.ACV_carousel, 0, R.raw.carousel));
        filters.add(new LizDavenport("9", DianaVonGrüning.FilterType.ACV_cold_desert, 0, R.raw.cold_desert));
        filters.add(new LizDavenport("10", DianaVonGrüning.FilterType.ACV_cold_heart, 0, R.raw.cold_heart));
        filters.add(new LizDavenport("11", DianaVonGrüning.FilterType.ACV_digital_film, 0, R.raw.digital_film));
        filters.add(new LizDavenport("12", DianaVonGrüning.FilterType.ACV_documentary, 0, R.raw.documentary));
        filters.add(new LizDavenport("13", DianaVonGrüning.FilterType.ACV_electric, 0, R.raw.electric));
        filters.add(new LizDavenport("14", DianaVonGrüning.FilterType.ACV_ghosts_in_your_head, 0, R.raw.ghosts_in_your_head));
        filters.add(new LizDavenport("15", DianaVonGrüning.FilterType.ACV_good_luck_charm, 0, R.raw.good_luck_charm));
        filters.add(new LizDavenport("16", DianaVonGrüning.FilterType.ACV_green_envy, 0, R.raw.green_envy));
        filters.add(new LizDavenport("17", DianaVonGrüning.FilterType.ACV_humming_bees, 0, R.raw.humming_bees));
        filters.add(new LizDavenport("18", DianaVonGrüning.FilterType.ACV_hummingbirds, 0, R.raw.hummingbirds));
        filters.add(new LizDavenport("19", DianaVonGrüning.FilterType.ACV_kiss_kiss, 0, R.raw.kiss_kiss));
        filters.add(new LizDavenport("20", DianaVonGrüning.FilterType.ACV_left_hand_blues, 0, R.raw.left_hand_blues));
        filters.add(new LizDavenport("21", DianaVonGrüning.FilterType.ACV_light_parades, 0, R.raw.light_parades));
        filters.add(new LizDavenport("22", DianaVonGrüning.FilterType.ACV_lullabye, 0, R.raw.lullabye));
        filters.add(new LizDavenport("23", DianaVonGrüning.FilterType.ACV_midnight_hour, 0, R.raw.midnight_hour));
        filters.add(new LizDavenport("24", DianaVonGrüning.FilterType.ACV_moth_wings, 0, R.raw.moth_wings));
        filters.add(new LizDavenport("25", DianaVonGrüning.FilterType.ACV_old_postcards, 0, R.raw.old_postcards));
        filters.add(new LizDavenport("26", DianaVonGrüning.FilterType.ACV_old_postcards_ii, 0, R.raw.old_postcards_ii));
        filters.add(new LizDavenport("27", DianaVonGrüning.FilterType.ACV_peacock_feathers, 0, R.raw.peacock_feathers));
        filters.add(new LizDavenport("28", DianaVonGrüning.FilterType.ACV_pistol, 0, R.raw.pistol));
        filters.add(new LizDavenport("29", DianaVonGrüning.FilterType.ACV_ragdoll, 0, R.raw.ragdoll));
        filters.add(new LizDavenport("30", DianaVonGrüning.FilterType.ACV_rivers_and_rain, 0, R.raw.rivers_and_rain));
        filters.add(new LizDavenport("31", DianaVonGrüning.FilterType.ACV_rose_thorns_one, 0, R.raw.rose_thorns_one));
        filters.add(new LizDavenport("32", DianaVonGrüning.FilterType.ACV_rose_thorns_two, 0, R.raw.rose_thorns_two));
        filters.add(new LizDavenport("33", DianaVonGrüning.FilterType.ACV_set_you_free, 0, R.raw.set_you_free));
        filters.add(new LizDavenport("34", DianaVonGrüning.FilterType.ACV_snow_white, 0, R.raw.snow_white));
        filters.add(new LizDavenport("35", DianaVonGrüning.FilterType.ACV_sparks, 0, R.raw.sparks));
        filters.add(new LizDavenport("36", DianaVonGrüning.FilterType.ACV_toes_in_the_ocean, 0, R.raw.toes_in_the_ocean));
        filters.add(new LizDavenport("37", DianaVonGrüning.FilterType.ACV_twin_lungs, 0, R.raw.twin_lungs));
        filters.add(new LizDavenport("38", DianaVonGrüning.FilterType.ACV_tz_curves_s, 0, R.raw.tz_curves_s));
        filters.add(new LizDavenport("39", DianaVonGrüning.FilterType.ACV_wild_at_heart, 0, R.raw.wild_at_heart));
        filters.add(new LizDavenport("40", DianaVonGrüning.FilterType.ACV_window_warmth, 0, R.raw.window_warmth));
        filters.add(new LizDavenport("41", DianaVonGrüning.FilterType.ACV_daisyspell, 0, R.raw.daisyspell));
        filters.add(new LizDavenport("42", DianaVonGrüning.FilterType.ACV_goldvase, 0, R.raw.goldvase));
        filters.add(new LizDavenport("43", DianaVonGrüning.FilterType.ACV_lemonpell, 0, R.raw.lemonpell));
        filters.add(new LizDavenport("44", DianaVonGrüning.FilterType.ACV_oldpoppy, 0, R.raw.oldpoppy));
        filters.add(new LizDavenport("45", DianaVonGrüning.FilterType.ACV_ivorybow, 0, R.raw.ivorybow));

        return filters;
    }

}
