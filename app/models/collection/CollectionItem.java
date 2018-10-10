/*
 * 
 */
package models.collection;

import java.util.Date;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.FileAttachment;
import models.users.Collector;
import play.data.validation.MaxSize;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class CollectionItem extends Model {

    @ManyToOne
    public Collector itemOwner;
    
    public String name;
    
    @Lob
    @Column(length=4096)
    @MaxSize(4096)
    public String description;
    
    @OneToMany
    public List<FileAttachment> imageAttachments = new ArrayList<>();

    public Blob icon;
    
    @ElementCollection
    public List<Blob> images = new ArrayList<>();
    
    @ManyToMany(cascade=CascadeType.ALL)
    public Set<Tag> tags = new HashSet<>();
    
    @ManyToOne
    public Category category;
    
    public Date dateAdded = new Date();
    
    public String creationDate;
    
    public enum Condition{
        EXCELLENT,VERY_GOOD,GOOD,MEDIUM,SLIGHTLY_DAMAGED,POOR
    }
    
    public Condition condition;
    
    public String mainMaterial;

    @ManyToOne(optional=true)
    public Collection collection;
    
    public boolean hasImage() {
        return (images!=null && images.size()>0);
    }
}
