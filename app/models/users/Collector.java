/*
 * 
 */
package models.users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import models.collection.Collection;
import models.collection.CollectionItem;
import play.data.validation.Required;
import play.db.jpa.Model;
import utils.Utils;

@Entity
public class Collector extends Model {
    /**
     * Instantiates a new collector profile.
     *
     * @param user the user
     */
    public Collector(User user) {
        this.user = user;
    }

    /** The user. */
    @Required
    @OneToOne
    public User user;
    
    public boolean isApproved = false;
    
    public String contactEmail;
    
    public String contactPhone;
    
    @OneToMany(mappedBy="owner")
    public List<Collection> collections = new ArrayList<>();
    
    @OneToMany(mappedBy="itemOwner")
    public List<CollectionItem> items = new ArrayList<>();
    
    public static Collector connectedCollector() {
        return Collector.find("user=?1", User.connectedUser()).first();
    }
    
    public String getDisplayName() {
        if(!Utils.isEmptyString(user.fullName)) {
            return user.fullName;
        }
        return user.userName;
    }
}
