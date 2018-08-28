package controllers.admin;

import org.apache.log4j.Level;

import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.deadbolt.Restrictions;
import play.Logger;
import play.mvc.Controller;
import play.mvc.With;
import utils.Utils;

@With(Deadbolt.class)
@Restrictions({ @Restrict("admin"), @Restrict("superuser") })
public class Dashboard extends Controller {

    public static void index() {
        String level = Logger.log4j.getLevel().toString();
        renderArgs.put("loglevel", level);
        render();
    }
    
    public static void save() {
        String level = params.get("loglevel");
        if(!Utils.isEmptyString(level)) {
            Level logLevel = Level.toLevel(level, Logger.log4j.getLevel());
            Logger.setUp(logLevel.toString());
        }
        flash.success("Log level set to %s", Logger.log4j.getLevel());
        Logger.info("Log level set to %s", Logger.log4j.getLevel());
        index();        
    }
}
