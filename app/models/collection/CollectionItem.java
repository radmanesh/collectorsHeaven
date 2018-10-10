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
import models.users.User;
import play.data.validation.MaxSize;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class CollectionItem extends Model implements Taggable<CollectionItem>, Likeable, Viewable, Commentable {

    @ManyToOne
    public Collector itemOwner;

    public String name;

    @Lob
    @Column(length = 4096)
    @MaxSize(4096)
    public String description;

    @OneToMany
    public List<FileAttachment> imageAttachments = new ArrayList<>();

    public Blob icon;

    @ElementCollection
    public List<Blob> images = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    public Set<Tag> tags = new HashSet<>();

    @ManyToOne
    public Category category;

    public Date dateAdded = new Date();

    public String creationDate;

    public enum Condition {
        EXCELLENT, VERY_GOOD, GOOD, MEDIUM, SLIGHTLY_DAMAGED, POOR
    }

    public Condition condition;

    public String mainMaterial;

    @OneToMany
    public Set<User> likers = new HashSet<>();

    @ManyToOne(optional = true)
    public Collection collection;

    public boolean hasImage() {
        return (images != null && images.size() > 0) && hasIcon();
    }

    public boolean hasIcon() {
        return (icon != null && icon.exists());
    }

    /*
     * (non-Javadoc)
     * @see models.collection.Commentable#getComments()
     */
    @Override
    public List<? extends Commentable> getComments() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see models.collection.Commentable#comment(models.users.User, java.lang.String)
     */
    @Override
    public <T extends Commentable> T comment(User writer, String message) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see models.collection.Likeable#getLikers()
     */
    @Override
    public Set<User> getLikers() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see models.collection.Likeable#like(models.users.User)
     */
    @Override
    public void like(User liker) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see models.collection.Likeable#dislike(models.users.User)
     */
    @Override
    public void dislike(User liker) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see models.collection.Taggable#getTags()
     */
    @Override
    public Set<Tag> getTags() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see models.collection.Taggable#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    public static List<CollectionItem> findAllTaggedWith(String tag) {
        return CollectionItem.find("select distinct ci from CollectionItem ci join ci.tags as t where t.name = ?1", tag).fetch();
    }


    public static List<CollectionItem> findAllTaggedWith(String... tags) {
        return CollectionItem.find(
                "select distinct ci.id from CollectionItem ci join ci.tags as t where t.name in (:tags) group by ci.id having count(t.id) = :size")
                .bind("tags", tags).bind("size", tags.length).fetch();
    }

    @Override
    public CollectionItem tagItWith(String name) {
        // TODO Auto-generated method stub
        return Taggable.super.tagItWith(name);
    }
    
    public static List<CollectionItem> findWithCategory(Category cat) {
        String q =
                "select distinct ci from CollectionItem ci, Category cat,Category cat2 where cat = ci.category and cat.parent = cat2 and (cat = ?1 or cat2=?1)";
        return CollectionItem.find(q, cat).fetch();
    }


}
