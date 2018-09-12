/**
 * 
 */
package models.collection;

import java.util.List;
import java.util.Set;

import models.users.User;

/**
 * @author arman
 *
 */
public interface Likeable {
    public Set<User> getLikers();

    public void like(User liker);

    public void dislike(User liker);
}
