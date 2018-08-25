package models;

import play.db.jpa.*;

import javax.persistence.*;

@Entity
public class ImageAttachment extends Model {
    public String title;
    
    public String label;
    
    public String description;
    
    public String type;
    
    public Blob attachment;
}
