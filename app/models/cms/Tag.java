/*
 * 
 */
package models.cms;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Tag extends Model implements Comparable<Tag> {

    @Required
    public String name;

    private Tag(String name) {
        this.name = name;
    }

    public static Tag findOrCreateByName(String name) {
        Tag tag = Tag.find("name", name).first();
        if (tag == null) {
            tag = new Tag(name);
            tag.save();
        }
        return tag;
    }

    public static <T extends Model> List<Map> getCloud(Taggable<T> parent) {
        List<Map> result =
                Tag.find("select new map(t.name as tag, count(p.id) as pound) from "+parent.getName()+" p join p.tags as t group by t.name").fetch();
        return result;
    }

    public String toString() {
        return name;
    }

    public int compareTo(Tag otherTag) {
        return name.compareTo(otherTag.name);
    }

}
