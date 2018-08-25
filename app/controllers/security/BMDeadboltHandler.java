/*******************************************************************************
 * Author: Arman Radmanesh <radmanesh@gmail.com>
 * Created on: Feb 28, 2018
 * Project: bongamonga
 * Copyright: See the file "LICENSE" for the full license governing this code.
 * Description:
 *******************************************************************************/

package controllers.security;

import controllers.Secure;
import controllers.deadbolt.DeadboltHandler;
import controllers.deadbolt.ExternalizedRestrictionsAccessor;
import controllers.deadbolt.RestrictedResourcesHandler;
import models.deadbolt.ExternalizedRestrictions;
import models.deadbolt.RoleHolder;
import models.users.User;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Util;

public class BMDeadboltHandler extends Controller implements DeadboltHandler {
    @Override
    public void beforeRoleCheck() {
        if (!Security.isConnected()) {
            try {
                if (!session.contains("username")) {
                    flash.put("url", "GET".equals(request.method) ? request.url : "/");
                    Secure.login();
                }
            } catch (Throwable t) {
                Logger.warn(t, "beforeRoleCheck %s",t.getMessage());
            }
        }
    }

    @Override
    @Util
    public RoleHolder getRoleHolder() {
        String userName = Security.connected();
        return User.getByUserName(userName);
    }

    @Override
    public void onAccessFailure(String controllerClassName) {
        forbidden();
    }

    @Override
    @Util
    public ExternalizedRestrictionsAccessor getExternalizedRestrictionsAccessor() {
        return new ExternalizedRestrictionsAccessor() {
            @Override
            public ExternalizedRestrictions getExternalizedRestrictions(String name) {
                return null;
            }
        };
    }

    @Override
    @Util
    public RestrictedResourcesHandler getRestrictedResourcesHandler() {
        return null;
    }
}
