/*
 * 
 */
package models.collection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
public class Category extends Model {
    
    @ManyToOne
    public Category parent;
    
    @OneToMany(mappedBy="parent")
    public List<Category> categories;
    
    @Unique
    public String name;
    
    public boolean isRoot() {
        return (parent == null);
    }
    
    public boolean isLeaf() {
        return (categories==null || categories.isEmpty());
    }
    
    public Category(String name) {
        categories = new ArrayList<>();
        this.name=name;
    }
    
    public Category(Category parent,String name) {
        categories = new ArrayList<>();
        this.name=name;
        this.parent = parent;
    }
    
    public Category addCategory(String name) {
        return new Category(this,name);
    }
    
}
