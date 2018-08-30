/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * Settings.java
 * Created 9:44:39 AM
 */

package controllers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.deadbolt.Restrictions;
import models.Configuration;
import play.Logger;
import play.Play;
import play.db.jpa.GenericModel;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.With;
import utils.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class Settings.
 */
@With(Deadbolt.class)
@Restrictions({ @Restrict("admin"), @Restrict("superuser") })
public class Settings extends Controller {

    /**
     * Index.
     */
    public static void index() {
        List<Configuration> configs = GenericModel.findAll();
        for (Configuration config : configs) {
            String key = /* CONFIG_PREFIX + "." + */config.key;
            renderArgs.put(config.getName(), Configuration.get(key, ""));
        }
        render();
    }

    /**
     * All.
     */
    public static void all() {
        List<Configuration> configs = GenericModel.findAll();
        render(configs);
    }

    /**
     * Delete all.
     */
    public static void deleteAll() {
        GenericModel.deleteAll();

        flash.success(Messages.get("bongamonga.settings.SettingsSaved"));
        flash.keep();
        index();
    }

    /**
     * Submit.
     */
    public static void submit() {
        for (Map.Entry<String, String> entry : params.allSimple().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Ignore common Play! request parameters (body, controller, action).
            if (!("body".equalsIgnoreCase(key) || "controller".equalsIgnoreCase(key) || "action".equals(key))) {

                // Append application name as prefix to the config keys.
                String CONFIG_PREFIX = Play.configuration.getProperty("application.name");
                key = CONFIG_PREFIX + "." + key;

                Configuration.set(entry.getKey(), key, value);

                // Update language on changing its value
                if ("language".equalsIgnoreCase(entry.getKey()))
                    updateCurrentLanguage();
                // Update log level on submit TODO: don't update when is'nt changed
                if ("loglevel".equalsIgnoreCase(entry.getKey())) {
                    Logger.warn("setting play logger level to: %s  ,  %s", entry.getValue(),
                            String.valueOf(Logger.isEnabledFor(entry.getValue())));
                    Logger.info("juli level %s", Logger.log4j.getLevel().toString());
                    play.Logger.setUp(entry.getValue());

                }
            }
        }

        flash.success(Messages.get("bongamonga.settings.SettingsSaved"));
        flash.keep();
        index();
    }

    /**
     * Update current language.
     */
    private static void updateCurrentLanguage() {
        String appName = Play.configuration.getProperty("application.name");
        String lang = Configuration.get(appName + ".language", Constants.defaultAppLang);
        if (lang.equalsIgnoreCase("fa")) {
            Locale persianLocale = new Locale.Builder().setLanguage("fa").setRegion("IR")
                    .setExtension(Locale.UNICODE_LOCALE_EXTENSION, "nu-arab").build();
            Logger.info("locale lang :%s , toString: %s", persianLocale.getLanguage(), persianLocale.toString());
            play.i18n.Lang.change(persianLocale.getLanguage());
        }
        else {
            Locale loc = new Locale.Builder().setLanguage("en").setRegion("US").clearExtensions().build();
            play.i18n.Lang.change(loc.getLanguage());
        }
    }
}
