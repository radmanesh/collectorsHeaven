package models.collection;

import play.db.jpa.*;

import javax.persistence.*;

import models.users.User;

import java.util.*;

@Entity
public class Collector extends Model {
    
    @ManyToOne
    public User user;
    
    @OneToMany(mappedBy="collector")
    public List<Collection> collections = new ArrayList<>();
    
    public static Collector connectedCollector() {
        return Collector.find("user=?1", User.connectedUser()).first();
    }
}
