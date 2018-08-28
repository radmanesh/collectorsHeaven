package controllers;

import java.util.List;

import controllers.security.Security;
import models.collection.Collection;
import models.users.User;
import models.users.UserProfile;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.Util;

public class Application extends Controller {

    @Before
    public static void setArgs() {
        if(Security.isConnected()) {
            User user = User.connectedUser();
            renderArgs.put("user", user);
        }
    }
    
    public static void index() {
        List<Collection> collections = Collection.all().fetch();
        render(collections);
    }
    
    @Util
    public static void checkLoggedIn() {
        flash.put("url", request.url);
        if(!Security.isConnected()) {
            flash.keep("url");
            redirect(Router.getFullUrl("Secure.login"));
        }
        renderArgs.put("user", User.connectedUser());
    }
    
    public static void accountSettings() {
        checkLoggedIn();
        if(request.method.equalsIgnoreCase("GET")) {
            render();
        }else if("POST".equalsIgnoreCase(request.method)) {
            UserProfile profile = User.connectedUser().profile;
            profile.edit(params.getRootParamNode(), "object");
            validation.valid(profile);
            if(validation.hasErrors()) {
                validation.keep();
                params.flash();
                accountSettings();
            }
            profile.save();
            flash.success("flash.success");
            render();
        }
    }
}