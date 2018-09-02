/*
 * 
 */
package models.collection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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
public class Collection extends Model {

    @ManyToOne
    public Collector collector;
    
    @OneToMany(mappedBy="collection")
    public List<CollectionItem> items = new ArrayList<>();
    
    public String name;
    
    /** The about me. */
    @Lob
    @Column(length=1024)
    @MaxSize(1024)
    public String description;
    
    public Blob icon;
    
    @ManyToOne
    public FileAttachment collectionIcon;
    
    @ManyToMany
    public List<Tag> tags = new ArrayList<>();
    
    @ManyToOne
    public Category category;
}
