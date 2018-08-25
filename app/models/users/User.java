/*******************************************************************************
 *      Author: Arman Radmanesh <radmanesh@gmail.com>
 *  Created on: Feb 28, 2018
 *     Project: bongamonga
 *   Copyright: See the file "LICENSE" for the full license governing this code.
 * Description: 
 *******************************************************************************/

package models.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import controllers.security.Security;
import models.Configuration;
import models.deadbolt.Role;
import models.deadbolt.RoleHolder;
import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;
import play.libs.Codec;
import utils.Constants;
import utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
@Table(name = "user_acc")
@Entity(name="user_acc")
public class User extends Model implements RoleHolder {
    
    /** The user name. */
    @Required
    @Unique
    public String userName;

    /** The full name. */
    public String fullName;

    /** The email. */
    @Email
    @Required
    @Unique
    public String email;

    /** The password hash. */
    @Password
    @Required
    public String passwordHash;

    @Transient
    //@Required
    //@MinSize(6)
    public String password;
    
    @Transient
    //@Required
    //@Equals("password")
    public String passwordConfirm;
    
    /** The confirmed. */
    @Column(columnDefinition = "tinyint(1) default 0")
    public boolean confirmed = false;

    /** The deleted. */
    @Column(columnDefinition = "tinyint(1) default 0")
    public boolean deleted = false;
    
    @Pattern(regexp="09\\d{9}")
    public String mobileNo;

    public String needEmailConfirmation;
    
    public Date lastEmailConfirmationRequested;
    
    public String needRecoveryConfirmation;
    
    public Date lastRecoveryConfirmationRequested;
    
    public Date registeredAt = new Date();
    
    /** User specific configurations. */
    @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
    public List<Configuration> configs = new ArrayList<Configuration>();
    
    public String userLang = Constants.defaultUserLang;

    /** The roles. */
    @Required
    @ManyToMany
    public List<UserRole> roles;
    

    /** The profile. */
    @OneToOne(mappedBy = "user", cascade=CascadeType.ALL, orphanRemoval=true)
    public UserProfile profile;
    
    /**
     * Instantiates a new user.
     */
    public User() {
        roles=new ArrayList<>();
        //roles.add(UserRole.getOrCreate(Constants.defaultUserRoleName));
        registeredAt=new Date();
    }

    /**
     * Instantiates a new user.
     *
     * @param userName the user name
     * @param fullName the full name
     * @param role the role
     */
    public User(String userName, String email, UserRole role) {
        this.userName = userName;
        this.email = email;
        roles=new ArrayList<>();
        roles.add(role);
        registeredAt=new Date();
    }

    /**
     * Gets the by user name.
     *
     * @param userName the user name
     * @return the by user name
     */
    public static User getByUserName(String userName) {
        return find("byUserName", userName).first();
    }

    /**
     * Sets the password.
     *
     * @param password the password
     * @return true, if successful
     */
    public boolean setPassword(String password) {
        if (password == null || password.isEmpty())
            return false;
        this.passwordHash = Codec.hexSHA1(password);
        return true;
    }

    /**
     * Verifies user password.
     *
     * @param password the password
     * @return {@code true} if password matches, {@code false} if password doesn't
     *         match
     */
    public boolean checkPassword(String password) {
        return passwordHash.equals(Codec.hexSHA1(password));
    }
    
    public boolean isConfirmed() {
        return (confirmed && Utils.isEmptyString(needEmailConfirmation) );
    }

    /**
     * Finds a user by email address.
     *
     * @param email            user email address
     * @return A {@code User}
     */
    public static User findByEmail(String email) {
        return find("byEmail", email.toLowerCase()).first();
    }

    /**
     * Checks if the given email is available for sign up.
     *
     * @param email the email
     * @return {@code true} if there is no existing user with the given email,
     *         {@code false} otherwise.
     */
    public static boolean isEmailAvailable(String email) {
        return findByEmail(email.toLowerCase()) == null;
    }

    /**
     * Checks if the given email is available for sign up.
     *
     * @param email the email
     * @return {@code true} if there is no existing user with the given email,
     *         {@code false} otherwise.
     */
    public static boolean isUsernameAvailable(String username) {
        return getByUserName(username) == null;
    }

    public static User connectedUser() {
        if(Security.isConnected()) {
            return getByUserName(Security.connected());
        }
        return null;
    }

    /* (non-Javadoc)
     * @see play.db.jpa.JPABase#toString()
     */
    @Override
    public String toString() {
        return this.userName;
    }

    /**
     * Adds the role.
     *
     * @param role the role
     * @return true, if successful
     */
    public boolean addRole(UserRole role) {
        return roles.add(role);
    }

    /**
     * Removes the role.
     *
     * @param role the role
     * @return true, if successful
     */
    public boolean removeRole(UserRole role) {
        if (roles == null || !roles.contains(role) || roles.size() < 2)
            return false;
        return roles.remove(role);
    }

    @Override
    public List<? extends Role> getRoles() {
        return roles;
    }

}
