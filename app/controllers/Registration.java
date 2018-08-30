/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * Registration.java
 * Created 9:44:40 AM
 */
package controllers;

import java.util.Date;

import controllers.security.Security;
import models.users.User;
import models.users.UserRole;
import notifiers.AuthNotifier;
import play.Logger;
import play.data.validation.Validation;
import play.db.jpa.GenericModel;
import play.libs.Codec;
import play.mvc.Before;
import play.mvc.Controller;
import utils.Constants;
import utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class Registration.
 */
public class Registration extends Controller {

    /**
     * Before log.
     */
    @Before
    public static void beforeLog() {
        Logger.info("params : %s", Utils.mapToString(params.allSimple()));
        Logger.info("header : %s", Utils.mapToString(request.args));
    }

    /**
     * Register.
     */
    public static void register() {
        if (Security.isConnected()) {
            flash.error("registration.alreadyConnected", User.connectedUser());
            Application.index();
        }
        render();
    }

    /**
     * Submit register.
     *
     * @param user
     *            the user
     */
    public static void submitRegister(User user) {
        User u = new User();
        u.edit(params.getRootParamNode(), "user");
        u.addRole(UserRole.getOrCreate(Constants.defaultUserRoleName));
        u.setPassword(u.password);
        validation.valid(u);
        if (Validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            Validation.keep(); // keep the errors for the next request
            register();
        }
        u.needEmailConfirmation = Codec.UUID();
        u.lastEmailConfirmationRequested = new Date();
        u.save();
        AuthNotifier.confirmEmail(u);
        flash.success("registration.success.confirmEmail");
        emailConfirmationPending(u.id);
    }

    /**
     * Email confirmation pending.
     *
     * @param id
     *            the id
     */
    public static void emailConfirmationPending(Long id) {
        User user = GenericModel.findById(id);
        notFoundIfNull(user);
        if (user.isConfirmed()) {
            flash.error("registration.email.confirmation.pending");
            Application.index();
        }
        render(user);
    }

    /**
     * Collector register.
     */
    public static void collectorRegister() {
        if (!Security.isConnected()) {
            flash.error("registration.collectorRegister.notConnected");
            Registration.register();
        }
        User u = User.connectedUser();
        if (u.getRoles().contains(Constants.defaultCollectorRole)) {
            flash.error("registration.agencyRegister.alreadyAgency");
            Application.index();
        }
        render();
    }

    /**
     * Submit collector register.
     */
    public static void submitCollectorRegister() {
        renderText("TBI");
    }


    /**
     * Confirm email.
     *
     * @param id
     *            the id
     * @param confirmationCode
     *            the confirmation code
     */
    public static void confirmEmail(Long id, String confirmationCode) {
        User user = GenericModel.findById(id);
        notFoundIfNull(user);
        notFoundIfNull(confirmationCode);
        Logger.info("code: %s , %s", user.needEmailConfirmation, confirmationCode);
        if (!confirmationCode.trim().toLowerCase().equals(user.needEmailConfirmation.toLowerCase())) {
            flash.error("registration.error.confirmEmail");
            register();
        }

        user.confirmed = true;
        user.needEmailConfirmation = null;
        user.save();
        AuthNotifier.welcome(user);
        flash.success("registration.success.emailConfirmed");
        flash.success("registration.email.confirmed", user.userName);
        Application.index();
    }

    /**
     * Resend veirification email.
     *
     * @param id
     *            the id
     */
    public static void resendVeirificationEmail(Long id) {
        User user = GenericModel.findById(id);
        notFoundIfNull(user);
        if (Utils.isEmptyString(user.needEmailConfirmation)) {
            flash.error("registration.error.emailAlreadyConfirmed");
            Application.index();
        }
        user.lastEmailConfirmationRequested = new Date();
        user.needEmailConfirmation = Codec.UUID();
        user.save();
        AuthNotifier.confirmEmail(user);
        flash.success("registration.success.confirmEmail");
        emailConfirmationPending(id);
    }

    /**
     * Confirm mobile.
     */
    public static void confirmMobile() {
    }

    /**
     * Resend verification SMS.
     */
    public static void resendVerificationSMS() {

    }

    /**
     * Change passwor.
     */
    public static void changePasswor() {
    }

    /**
     * Recover password.
     */
    public static void recoverPassword() {
    }

}
