/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * Collections.java
 * Created 9:44:40 AM
 */
package controllers;

import java.util.List;

import models.collection.Collection;
import models.collection.CollectionItem;
import play.db.jpa.GenericModel;
import play.mvc.Controller;
import play.vfs.VirtualFile;
import utils.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class Collections.
 */
public class Collections extends Controller {

    /**
     * Index.
     */
    public static void index() {
        List<Collection> collections = GenericModel.all().fetch();
        render(collections);
    }

    /**
     * Show collection.
     *
     * @param id
     *            the id
     */
    public static void showCollection(Long id) {
        Collection collection = GenericModel.findById(id);
        render(collection);
    }

    /**
     * Show collection item.
     *
     * @param id
     *            the id
     */
    public static void showCollectionItem(Long id) {
        CollectionItem collectionItem = GenericModel.findById(id);
        render(collectionItem);
    }

    /**
     * Render collection icon.
     *
     * @param id
     *            the id
     */
    public static void renderCollectionIcon(Long id) {
        Collection c = GenericModel.findById(id);
        if (c == null || c.icon == null || !c.icon.exists()) {
            renderBinary(VirtualFile.fromRelativePath(Constants.collectionNoIconImagePath).inputstream());
        }
        renderBinary(c.icon.get());
    }

    /**
     * Render collection item icon.
     *
     * @param id
     *            the id
     */
    public static void renderCollectionItemIcon(Long id) {
        CollectionItem ci = GenericModel.findById(id);
        if (ci == null || !ci.hasImage()) {
            renderBinary(VirtualFile.fromRelativePath(Constants.itemNoImagePath).inputstream());
        }
        int index = 0;
        Integer i = params.get("index", Integer.class);
        if (i != null)
            index = i.intValue();
        renderBinary(ci.images.get(index).get());
    }

}
