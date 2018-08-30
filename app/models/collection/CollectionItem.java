/*
 * 
 */
package models.collection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.ImageAttachment;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class CollectionItem extends Model {
    
    @ManyToOne
    public Collection collection;
    
    public String name;
    
    public String description;
    
    @OneToMany
    public List<ImageAttachment> imageAttachments = new ArrayList<>();
    
    @ElementCollection
    public List<Blob> images = new ArrayList<>();
    
    @ManyToMany
    public List<Tag> tags = new ArrayList<>();
    
    public boolean hasImage() {
        return (images!=null && images.size()>0);
    }
}
