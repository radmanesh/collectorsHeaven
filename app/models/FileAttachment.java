/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * ImageAttachment.java
 * Created 9:44:40 AM
 */
package models;

import java.io.File;
import java.io.FileInputStream;

import javax.persistence.Entity;

import org.apache.commons.lang.NullArgumentException;

import play.db.jpa.Blob;
import play.db.jpa.Model;
import play.libs.MimeTypes;
import play.mvc.Scope.Params;
import utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageAttachment.
 */
@Entity
public class FileAttachment extends Model {
    
    /** The name. */
    public String name;

    /** The description. */
    public String description;
    
    /** The attachment. */
    public Blob attachment;

    /**
     * Instantiates a new image attachment.
     */
    public FileAttachment() {

    }

    /**
     * Instantiates a new image attachment.
     *
     * @param file
     *            the file
     * @throws Throwable
     *             the throwable
     * @throws NullArgumentException
     *             the null argument exception
     */
    public FileAttachment(File file) throws Throwable, NullArgumentException {
        if (file == null || !file.exists()) {
            throw new NullArgumentException("file");
        }
        name = file.getName();
        attachment = new Blob();
        attachment.set(new FileInputStream(file), MimeTypes.getContentType(file.getName()));
        description = Utils.nullSafeString(Params.current().get("description"));
    }
}
