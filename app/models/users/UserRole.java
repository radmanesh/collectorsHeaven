/*******************************************************************************
 *      Author: Arman Radmanesh <radmanesh@gmail.com>
 *  Created on: Feb 28, 2018
 *     Project: bongamonga
 *   Copyright: See the file "LICENSE" for the full license governing this code.
 * Description: 
 *******************************************************************************/

package models.users;

import java.util.List;

import javax.persistence.Entity;

import models.deadbolt.Role;
import play.data.validation.Required;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import play.i18n.Messages;

// TODO: Auto-generated Javadoc
/**
 * The Class UserRole.
 */
@Entity
public class UserRole extends Model implements Role {
    
    /** The name. */
    @Required
    public String name;

    /**
     * Instantiates a new user role.
     *
     * @param name the name
     */
    public UserRole(String name) {
        this.name = name;
    }

    @Override
    public String getRoleName() {
        return name;
    }

    /* (non-Javadoc)
     * @see play.db.jpa.JPABase#toString()
     */
    @Override
    public String toString() {
        return Messages.get(name);
    }
    
    /**
     * Gets the by name.
     *
     * @param name the name
     * @return the by name
     */
    public static UserRole getByName(String name) {
        return GenericModel.find("byName", name).first();
    }
    
    public static UserRole getOrCreate(String name) {
        UserRole role=UserRole.getByName(name);
        if(role!=null)
            return role; 
        role=new UserRole(name);
        role.create();
        role.save();
        return role;
    }
    
    public static List<UserRole> allRoles(){
        return findAll();
    }
}
