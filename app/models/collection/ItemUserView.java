package models.collection;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;

import models.users.User;

import java.util.*;

@Entity
public class ItemUserView extends Model {

    @ManyToOne
    @Required
    public User viewer;

    @Required
    @OrderBy("viewedAt desc")
    public Date viewedAt;

    public ItemUserView(User viewer) {
        viewedAt = new Date();
        this.viewer = viewer;

    }
}
