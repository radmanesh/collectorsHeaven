/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * Application.java
 * Created 9:44:39 AM
 */
package controllers;

import java.util.List;

import controllers.security.Security;
import models.collection.Collection;
import models.users.User;
import models.users.UserProfile;
import play.data.validation.Validation;
import play.db.jpa.GenericModel;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class Application.
 */
public class Application extends Controller {

    /**
     * Sets the args.
     */
    @Before
    public static void setArgs() {
        if (Security.isConnected()) {
            User user = User.connectedUser();
            renderArgs.put("user", user);
        }
    }

    /**
     * Index.
     */
    public static void index() {
        List<Collection> collections = GenericModel.all().fetch();
        render(collections);
    }

    /**
     * Check logged in.
     */
    @Util
    public static void checkLoggedIn() {
        flash.put("url", request.url);
        if (!Security.isConnected()) {
            flash.keep("url");
            redirect(Router.getFullUrl("Secure.login"));
        }
        renderArgs.put("user", User.connectedUser());
    }

    /**
     * Account settings.
     */
    public static void accountSettings() {
        checkLoggedIn();
        if (request.method.equalsIgnoreCase("GET")) {
            render();
        }
        else if ("POST".equalsIgnoreCase(request.method)) {
            UserProfile profile = User.connectedUser().profile;
            profile.edit(params.getRootParamNode(), "object");
            validation.valid(profile);
            if (Validation.hasErrors()) {
                Validation.keep();
                params.flash();
                accountSettings();
            }
            profile.save();
            flash.success("flash.success");
            render();
        }
    }
}