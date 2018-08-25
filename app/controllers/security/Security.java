/*******************************************************************************
 *      Author: Arman Radmanesh <radmanesh@gmail.com>
 *  Created on: Feb 28, 2018
 *     Project: bongamonga
 *   Copyright: See the file "LICENSE" for the full license governing this code.
 * Description: 
 *******************************************************************************/

package controllers.security;

import controllers.Secure;
import models.users.User;
import models.users.UserRole;
import play.Logger;

public class Security extends Secure.Security {

    /**
     * This method is called during the authentication process. This is where you check if
     * the user is allowed to log in into the system. This is the actual authentication process
     * against a third party system (most of the time a DB).
     *
     * @param username
     * @param password
     * @return true if the authentication process succeeded
     */
    static boolean authenticate(String username, String password) {
        User user = User.getByUserName(username);
        if(user==null)
            return false;
        return user.checkPassword(password);
    }

    /**
     * This method checks that a profile is allowed to view this page/method. This method is called prior
     * to the method's controller annotated with the @Check method. 
     *
     * @param profile
     * @return true if you are allowed to execute this controller method.
     */
    static boolean check(String profile) {
        try {
            User user = User.connectedUser();
            return user.getRoles().contains(UserRole.getByName(profile));
        } catch (Exception e) {
            Logger.warn(e,"Security.check: %s", e.getMessage());
        }
        return false;
    }

    /**
     * This method returns the current connected username
     * @return
     */
    public static String connected() {
        return session.get("username");
    }

    /**
     * Indicate if a user is currently connected
     * @return  true if the user is connected
     * 
     * TODO: changed visiblity to public, is it safe?
     */
    public static boolean isConnected() {
        return session.contains("username");
    }

    /**
     * This method is called after a successful authentication.
     * You need to override this method if you with to perform specific actions (eg. Record the time the user signed in)
     */
    static void onAuthenticated() {
    }

     /**
     * This method is called before a user tries to sign off.
     * You need to override this method if you wish to perform specific actions (eg. Record the name of the user who signed off)
     */
    static void onDisconnect() {
    }

     /**
     * This method is called after a successful sign off.
     * You need to override this method if you wish to perform specific actions (eg. Record the time the user signed off)
     */
    static void onDisconnected() {
    }

    /**
     * This method is called if a check does not succeed. By default it shows the not allowed page (the controller forbidden method).
     * @param profile
     */
    static void onCheckFailed(String profile) {
        forbidden();
    }

}
