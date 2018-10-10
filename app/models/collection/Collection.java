/*
 *
 */
package models.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
public class Collection extends Model implements Taggable<Collection> {

    @ManyToOne
    public Collector owner;

    @OneToMany(mappedBy="collection")
    public List<CollectionItem> items = new ArrayList<>();

    public String name;

    @Lob
    @Column(length=4096)
    @MaxSize(4096)
    public String description;

    public Blob icon;

    @ManyToOne
    public FileAttachment collectionIcon;

    @ManyToMany(cascade=CascadeType.PERSIST)
    public Set<Tag> tags;

    public boolean isDeleted = false;

    /* (non-Javadoc)
     * @see models.collection.Taggable#getTags()
     */
    @Override
    public Set<Tag> getTags() {
        return tags;
    }

    /* (non-Javadoc)
     * @see models.collection.Taggable#getName()
     */
    @Override
    public String getName() {
        return "collection";
    }
}
