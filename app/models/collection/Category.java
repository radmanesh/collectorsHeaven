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
import play.i18n.Messages;

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
    
    public List<Category> getChain(){
        List<Category> chain = new ArrayList<>();
        Category c = this;
        chain.add(c);
        while(!c.isRoot()) {
            c=c.parent;
            chain.add(0, c);
        }
        return chain;
    }
    
    public String getNameChain() {
        StringBuilder sb = new StringBuilder();
        for(Category c:getChain()) {
            sb.append(Messages.get(c.name)).append(" - ");
        }
        if(sb.toString().endsWith(" - ")) {
            return sb.toString().substring(0, sb.length()-3);
        }
        return sb.toString();
    }
    
}
