/**
 *
 */
package models.cms;

import java.util.List;

import models.users.User;
import play.db.jpa.Model;

/**
 * @author arman
 *
 */
public interface Commentable<T extends Commentable> {
    public List<T> getComments();

    public T comment(User writer, String message);

}
