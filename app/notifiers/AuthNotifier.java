/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * AuthNotifier.java
 * Created 9:44:40 AM
 */
package notifiers;

import models.users.User;
import play.mvc.Mailer;
import utils.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthNotifier.
 *
 * @author arman
 * @version 1
 * @date Mar 8, 2018
 */
public class AuthNotifier extends Mailer {

    /**
     * Confirm email.
     *
     * @param user
     *            the user
     * @return true, if successful
     */
    public static boolean confirmEmail(User user) {
        setSubject("Dear %s, Please confirm your email to complete your registration", user.userName);
        setFrom(Constants.defaultEmail);
        addRecipient(user.email);
        return sendAndWait(user);
    }

    /**
     * Welcome.
     *
     * @param user
     *            the user
     * @return true, if successful
     */
    public static boolean welcome(User user) {
        setSubject("Thanks %s, You have verified your email", user.userName);
        setFrom(Constants.defaultEmail);
        addRecipient(user.email);
        return sendAndWait(user);
    }

    /**
     * Test.
     *
     * @return true, if successful
     */
    public static boolean test() {
        setSubject("Test email");
        setFrom(Constants.defaultEmail);
        addRecipient("dashkootek@gmail.com");
        return sendAndWait();
    }

}
