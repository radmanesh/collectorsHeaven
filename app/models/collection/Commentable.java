/**
 * 
 */
package models.collection;

import java.util.List;

import models.users.User;

/**
 * @author arman
 *
 */
public interface Commentable {
    public List<? extends Commentable> getComments();
    
    public <T extends Commentable> T comment(User writer, String message);

}
