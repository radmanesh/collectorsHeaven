/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * Bootstrap.java
 * Created 9:44:39 AM
 */

package jobs;

import models.Configuration;
import play.db.jpa.GenericModel;
import play.i18n.Lang;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;
import utils.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class Bootstrap.
 */
@OnApplicationStart
public class Bootstrap extends Job {

    /*
     * (non-Javadoc)
     *
     * @see play.jobs.Job#doJob()
     */
    @Override
    public void doJob() throws Exception {
        if (!play.Play.id.matches("test")) {
            if (GenericModel.count() == 0)
                Fixtures.loadModels("initial_roles.yml");
            if (GenericModel.count() == 0)
                Fixtures.loadModels("initial_users.yml");
        }


        // Set language
        String lang = Configuration.get("framework.language", Constants.defaultAppLang);
        Lang.change(lang);

        // Logger.info("lang[0]: %s", play.Play.langs.get(0));
        // Logger.info(Locale.forLanguageTag("fa").toString(),"");
        // play.i18n.Lang.change(lang);

    }
}
