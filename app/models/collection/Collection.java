package models.collection;

import play.data.validation.MaxSize;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

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
    
    @ManyToMany
    public List<Tag> tags = new ArrayList<>();
    
    @ManyToOne
    public Category category;
}
