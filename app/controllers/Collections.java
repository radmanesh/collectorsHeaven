/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * Collections.java
 * Created 9:44:40 AM
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import models.collection.Category;
import models.collection.Collection;
import models.collection.CollectionItem;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Router;
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
        List<Collection> collections = Collection.all().fetch();
        render(collections);
    }

    /**
     * Show collection.
     *
     * @param id
     *            the id
     */
    public static void showCollection(Long id) {
        Collection collection = Collection.findById(id);
        render(collection);
    }

    public static void showItem(Long id) {
        CollectionItem item = CollectionItem.findById(id);
        notFoundIfNull(item);
        render(item);
    }
    
    public static void itemsWithTags(String ...tags) {
        List<CollectionItem> items = new ArrayList<>();
        if(!CollectionItem.findAllTaggedWith(tags).isEmpty())
            items.addAll(CollectionItem.findAllTaggedWith(tags));
        render(items,tags);
    }

    public static void itemsWithTag(String tag) {
        List<CollectionItem> items = CollectionItem.findAllTaggedWith(tag);
        if(items.isEmpty())
            items = new ArrayList<>();
        render(items,tag);
    }
    
    public static void itemsWithCategory(String cat) {
        Category category = Category.find("name", cat).first();
        notFoundIfNull(category);
        List<CollectionItem> items = CollectionItem.findWithCategory(category);
        if(items==null || items.isEmpty())
            items = new ArrayList<>();
        render(items,category);
    }

    
    /**
     * Render collection icon.
     *
     * @param id
     *            the id
     */
    public static void renderCollectionIcon(Long id) {
        Collection c = Collection.findById(id);
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
    public static void renderCollectionItemIcon(Long id,int index) {
        CollectionItem ci = CollectionItem.findById(id);
        if (ci == null || !ci.hasImage()) {
            renderBinary((VirtualFile.fromRelativePath(Constants.itemNoImagePath)).inputstream());
        }
        renderBinary(ci.images.get(index).get());
    }
    
    public static void renderCollectionItemIcon(Long id) {
        CollectionItem ci = CollectionItem.findById(id);
        Logger.info(" item : %s , icon: %s  -  %s", ci.name , ci.icon.getFile().getName() , ci.icon.exists());
        if (ci == null || ci.icon == null || !ci.icon.exists()) {
            renderBinary((VirtualFile.fromRelativePath(Constants.itemNoImagePath)).inputstream());
        }
        renderBinary(ci.icon.get());        
    }

}
