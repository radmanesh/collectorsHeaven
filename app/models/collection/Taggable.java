/**
 * 
 */
package models.collection;

import java.util.List;
import java.util.Set;

import models.users.User;
import play.db.jpa.Model;

/**
 * @author arman
 *
 */
public interface Taggable<T extends Model> {
    public Set<Tag> getTags();
    public String getName();

    public default List<T> findTaggedWith(String tag) {
        return ((T) this).find("select distinct p from " + this.getName()
                + " p join p.tags as t where t.name = ?1", tag).fetch();
    }
    public default List<T> findTaggedWith(String... tags) {
        return ((T) this).find("select distinct p.id from " + this.getName()
                + " p join p.tags as t where t.name in (:tags) group by p.id having count(t.id) = :size")
                .bind("tags", tags).bind("size", tags.length).fetch();
    }
    
    public default T tagItWith(String name) {
        getTags().add(Tag.findOrCreateByName(name));
        return (T)this;
    }
}
