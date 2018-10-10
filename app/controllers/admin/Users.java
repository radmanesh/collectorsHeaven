/*******************************************************************************
 * Author: Arman Radmanesh <radmanesh@gmail.com>
 * Created on: Feb 28, 2018
 * Project: bongamonga
 * Copyright: See the file "LICENSE" for the full license governing this code.
 * Description:
 *******************************************************************************/

package controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.deadbolt.Restrictions;
import models.users.User;
import models.users.UserRole;
import models.widgets.DataTablesModel;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.With;
import utils.Constants;

@With(Deadbolt.class)
@Restrictions({ @Restrict("admin"), @Restrict("superuser") })
public class Users extends Controller {

    public static void index() {
        List<User> userss = User.find("deleted=false").fetch();
        renderArgs.put("_updateUrl", Router.reverse("admin.Users.usersdatadable"));
        renderArgs.put("_tableName", "usersTable");
        renderArgs.put("_key", "all");
        renderTemplate("admin/Users/index.html", userss);
    }

    public static void usersdatadable() {
        List<User> users = User.find("deleted=false").fetch();
        DataTablesModel dtm = new DataTablesModel();
        dtm.addColumn("Username", "userName").addColumn("Email", "email").addColumn("FullName", "fullName")
                .addColumn("RegisteredAt", "registeredAt").addColumn("Confirmed", "confirmed")
                .addColumn("Actions", "actions");
        for (User u : users) {
            Map<String, Object> args = new HashMap();
            args.put("id", u.id);
            final String editLink = "<a href='" + Router.reverse("admin.users.show", args).url
                    + "' class='btn btn-success'><i class='fa fa-search'></i></a> " + "<a href='"
                    + Router.reverse("admin.Users.delete", args).url
                    + "' class='btn btn-danger' ><i class='fa fa-trash'></i></a>";
            dtm.createSerie().insertData("userName", u.userName).insertData("email", u.email)
                    .insertData("fullName", u.fullName).insertData("registeredAt", u.registeredAt)
                    .insertData("confirmed", u.confirmed).insertData("actions", editLink).create();
        }
        renderJSON(dtm.toJsonString());
    }

    public static void show(Long id) {
        User user = User.findById(id);
        notFoundIfNull(user);
        render(user);
    }

    public static void edit(Long id) {
        User user = User.findById(id);
        render(user);
    }

    public static void update(Long id) {
        User user = User.findById(id);
        if (user == null)
            notFound();

        user.edit(params.getRootParamNode(), "user");

        validation.valid(user);

        if (validation.hasErrors()) {
            for (play.data.validation.Error e : validation.errors()) {
                System.err.println(e.message());
            }
            params.flash();
            validation.keep();
            renderTemplate("admin/Users/edit.html", user);
        }

        // Update account.passwordHash if request[password] is not empty
        String password = params.get("password");
        if (password != null && password.trim().length() > 7) {
            user.setPassword(password);
        }

        user.save();

        flash.success(Messages.get("framework.AccountUpdated"));
        index();
    }

    public static void delete(Long id) {
        User user = User.findById(id);

        // if (user.role.ordinal() >= AccountRole.ADMINISTRATOR.ordinal()) {
        // flash.error(Messages.get("framework.AccountCannotBeDeleted"));
        // flash.keep();
        // index();
        // }

        // TODO @Check if current account or admin account
        user.deleted = true;
        user.save();

        flash.success(Messages.get("framework.AccountDeleted"));
        flash.keep();
        index();
    }

    public static void confirm(Long id) {
        User user = User.findById(id);
        // TODO @Check if current account or admin account
        user.confirmed = true;
        user.save();

        flash.success(Messages.get("framework.AccountConfirmed"));
        flash.keep();
        index();
    }

    public static void activate(Long id) {
        confirm(id);
    }

    public static void deactivate(Long id) {
        User user = User.findById(id);
        notFoundIfNull(user);
        // TODO @Check if current account or admin account
        if (user.id == User.connectedUser().id) {
            flash.error("admin.users.error.selfDeactivation.not.available");
            show(id);
        }
        if (user.getRoles().contains(UserRole.getByName(Constants.defaultAdminRoleName))) {
            flash.error("admin.users.error.admin.deactivation.not.allowed");
            show(id);
        }
        user.confirmed = false;
        user.save();

        flash.success(Messages.get("framework.AccountDeactivated"));
        flash.keep();
        index();
    }

    public static void roles() {
        List<UserRole> roles = UserRole.allRoles();
        render(roles);
    }

    public static void showRole(Long id) {
        UserRole role = UserRole.findById(id);
        notFoundIfNull(role);
        List<User> users = User.find("select distinct u from user_acc u join u.roles as ur where ur.id = :rid")
                .bind("rid", id).fetch();
        render(role, users);
    }

    public static void addRoleToUser(Long id) {
        UserRole role = UserRole.findById(params.get("roleId", Long.class));
        User user = User.findById(id);
        notFoundIfNull(user);
        notFoundIfNull(role);
        if (user.roles.contains(role)) {
            flash.error("admin.users.addRole.alreadyHas", role.name);
            show(id);
        }
        if (user.addRole(role)) {
            user.save();
            flash.success("flash.success");
            show(id);
        }
        flash.error("flash.error");
        show(id);
    }

    // TODO: set limitation on remove conditions
    public static void removeRoleFromUser(Long id, Long roleId) {
        UserRole role = UserRole.findById(roleId);
        User user = User.findById(id);
        notFoundIfNull(user);
        notFoundIfNull(role);
        if (!user.roles.contains(role)) {
            flash.error("admin.users.removeRole.noSuchRule", role.name);
            show(id);
        }
        if (user.removeRole(role)) {
            user.save();
            flash.success("flash.success");
            show(id);
        }
        flash.error("flash.error");
        show(id);
    }

}
