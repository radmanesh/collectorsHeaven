package models.collection;

import play.db.jpa.*;

import javax.persistence.*;

import models.ImageAttachment;

import java.util.*;

@Entity
public class CollectionItem extends Model {
    
    @ManyToOne
    public Collection collection;
    
    public String name;
    
    @OneToMany
    public List<ImageAttachment> images = new ArrayList<>();
    
    @ManyToMany
    public List<Tag> tags = new ArrayList<>();
}
