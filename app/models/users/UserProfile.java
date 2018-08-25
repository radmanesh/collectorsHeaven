/*******************************************************************************
 *      Author: Arman Radmanesh <radmanesh@gmail.com>
 *  Created on: Feb 28, 2018
 *     Project: bongamonga
 *   Copyright: See the file "LICENSE" for the full license governing this code.
 * Description: 
 *******************************************************************************/

package models.users;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class UserProfile.
 */
@Entity
public class UserProfile extends Model {

    /**
     * Instantiates a new user profile.
     *
     * @param user the user
     */
    public UserProfile(User user) {
        this.user = user;
    }

    /** The user. */
    @Required
    @OneToOne
    public User user;

    /** The first name. */
    public String firstName;

    /** The last name. */
    public String lastName;

    /** The birthday. */
    public Date birthday;

    /** The about me. */
    @Lob
    @Column(length=1024)
    @MaxSize(1024)
    public String aboutMe;
}
