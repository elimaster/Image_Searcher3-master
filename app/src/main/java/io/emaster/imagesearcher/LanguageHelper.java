package io.emaster.imagesearcher;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by Hackeru on 04/05/2016.
 */



public class LanguageHelper {
    public static void changeLocale(Resources res, String locale) {

        Configuration config;
        config = new Configuration(res.getConfiguration());


        switch (locale) {
            case "es":
                config.locale = new Locale("es");
                break;
            case "fi":
                config.locale = new Locale("fi");
                break;
            case "fr":
                config.locale = Locale.FRENCH;
                break;
            default:
                config.locale = Locale.ENGLISH;
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
        // reload files from assets directory
    }
}
