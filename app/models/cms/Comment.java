package models.cms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.users.User;
import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
public class Comment<T extends Commentable> extends Model implements Commentable, Likeable {

    //public T target;

    @Required
    @ManyToOne
    public User author;

    @Lob
    @Required
    public String content;

    public boolean confirmed = false;

    public boolean deleted = false;

    public Date postedAt;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    public List<Comment> comments;

    @ManyToOne
    public Comment parent;

    @OneToMany
    public Set<User> likers = new HashSet<>();

//    public Comment(T target, User author, String content) {
//        this.target = target;
//        this.author = author;
//        this.content = content;
//        this.postedAt = new Date();
//        comments = new ArrayList<Comment>();
//    }

    public Comment(Comment parent, User author, String content) {
        this.parent = parent;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
        this.comments = new ArrayList<Comment>();
    }

    public String toString() {
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }

    public User getAuthor() {
        // Player p = Player.findByName(author);
        return author;
    }

    public Comment reply(String content) {
        Comment reply = new Comment(this, User.connectedUser(), content);
        this.comments.add(reply);
        reply.save();
        return reply;
    }

    public Comment getRoot() {
        if (parent != null) {
            return parent.getRoot();
        }else {
            return this;
        }
    }

//    public Commentable getTarget() {
//        if (target != null)
//            return target;
//        return getRoot().target;
//    }

    public List<Comment> allDescendantComments() {
        List<Comment> allComments = new ArrayList<Comment>();
        if (comments == null || comments.isEmpty())
            return allComments;
        for (Comment c : comments) {
            if (!c.deleted) {
                allComments.add(c);
                allComments.addAll(c.allDescendantComments());
            }
        }
        return allComments;
    }

    public List<Comment> allDescendantConfirmedComments() {
        List<Comment> result = new ArrayList<Comment>();
        if (comments == null || comments.isEmpty())
            return result;

        for (Comment c : comments) {
            if (!c.deleted) {
                if (c.confirmed) { // Comment may have children
                    result.add(c);
                    result.addAll(c.allDescendantConfirmedComments());
                }
            }
        }
        return result;
    }

    public List<Comment> allDescendantPendingComments() {
        List<Comment> result = new ArrayList<Comment>();
        if (comments == null || comments.isEmpty())
            return result;

        for (Comment c : comments) {
            if (!c.deleted) {
                if (c.confirmed) { // Comment may have children
                    result.addAll(c.allDescendantPendingComments());
                }else { // Comment cannot have children
                    result.add(c);
                }
            }
        }
        return result;
    }

    @Override
    public Set<User> getLikers() {
        return likers;
    }

    @Override
    public void like(User liker) {
        likers.add(liker);
    }

    @Override
    public void dislike(User liker) {
        likers.remove(liker);
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public Comment comment(User writer, String message) {
        return this.reply(message);
    }
}
