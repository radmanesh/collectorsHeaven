package controllers.collector;

import java.util.HashSet;
import java.util.List;

import models.cms.Tag;
import models.collection.Collection;
import models.collection.CollectionItem;
import play.Logger;
import play.data.Upload;
import play.data.validation.Validation;
import play.db.jpa.Blob;
import play.mvc.*;

public class Collectors extends Controller {

    public static void index() {
        List<CollectionItem> items = CollectionItem.findAll();
        render(items);
    }

    public static void createItem() {
        if (request.method.equalsIgnoreCase("post")) {
            CollectionItem item = new CollectionItem();
            item.edit(params.getRootParamNode(), "item");
            validation.valid(item);
            if (Validation.hasErrors()) {
                Validation.keep();
                params.flash();
                flash.error("flash.error.invalid.args");
                render(item);
            }
            String[] tags = params.getAll("item.tags");
            if (tags != null && tags.length > 0) {
                if(item.tags == null)
                    item.tags = new HashSet<>();
                for (String t : tags) {
					Logger.info("tag: %s",t);
                    if (t.trim().length() > 0) {
                        item.tags.add(Tag.findOrCreateByName(t));
                    }
                }
            }
            item.save();
            showItem(item.id);
        }else {
            render();
        }
    }

    public static void editItem(Long id) {
        CollectionItem item = CollectionItem.findById(id);
        notFoundIfNull(item);
        render(item);
    }

    public static void updateItem(Long id, Upload[] images) {
        CollectionItem item = CollectionItem.findById(id);
        notFoundIfNull(item);
        item.edit(params.getRootParamNode(), "item");
        for (Upload f : images) {
            Logger.info(" f : %s", f.getSize());
            if(f!=null && f.getSize()!=null) {
                Blob b = new Blob();
                b.set(f.asStream(), f.getContentType());
                item.images.add(b);                
            }
        }
        String[] tags = params.getAll("item.tags");
        if (tags != null && tags.length > 0) {
            for (String t : tags) {
                if (t.trim().length() > 0) {
                    item.tags.add(Tag.findOrCreateByName(t));
                }
            }
        }
        validation.valid(item);
        if (Validation.hasErrors()) {
            Validation.keep();
            params.flash();
            flash.error("flash.error.invalid.args");
            renderTemplate("collector/Collectors/editItem.html", item);
        }
        item.save();
        Long collectionId = params.get("item.collection.id", Long.class);
        if (collectionId != null) {
            Collection collection = Collection.findById(collectionId);
            if (collection != null && collection.isPersistent()) {
                item.collection = collection;
                collection.items.add(item);
                item.save();
            }
        }
        flash.success("flash.success");
        index();
    }

    public static void showItem(Long id) {
        CollectionItem item = CollectionItem.findById(id);
        notFoundIfNull(item);
        render(item);
    }
    
    /**
     * Creates the collection.
     */
    public static void createCollection() {
        if (request.method.equalsIgnoreCase("post")) {
            Collection collection = new Collection();
            collection.edit(params.getRootParamNode(), "collection");
            validation.valid(collection);
            if (Validation.hasErrors()) {
                Validation.keep();
                params.flash();
                flash.error("flash.error.invalid.args");
                render(collection);
            }
            collection.save();
            editCollection(collection.id);
        }
        else {
            render();
        }
    }
    
    /**
     * Edits the collection.
     *
     * @param id
     *            the id
     */
    public static void editCollection(Long id) {
        Collection collection = Collection.findById(id);
        notFoundIfNull(collection);
        render(collection);
        // if(request.isNew) {
        // render(collection);
        // }
        // collection.edit(params.getRootParamNode(), "collection");
        // validation.valid(collection);
        // if(validation.hasErrors()) {
        // validation.keep();
        // params.flash();
        // flash.error("flash.error.invalid.args");
        // render(collection);
        // }
        // collection.save();
        // flash.success("flash.success");
        // index();
    }

    /**
     * Update collection.
     *
     * @param id
     *            the id
     */
    public static void updateCollection(Long id) {
        Collection collection = Collection.findById(id);
        notFoundIfNull(collection);
        collection.edit(params.getRootParamNode(), "collection");
        String[] tags = params.getAll("collection.tags");
        Logger.info("%s", tags.length);
        if (tags != null && tags.length > 0) {
            for (String t : tags) {
                if (t.trim().length() > 0) {
                    collection.tags.add(Tag.findOrCreateByName(t));
                }
            }
        }
        validation.valid(collection);
        if (Validation.hasErrors()) {
            Validation.keep();
            params.flash();
            flash.error("flash.error.invalid.args");
            renderTemplate("Collectors/editCollection", collection);
        }
        collection.save();
        flash.success("flash.success");
        index();
    }
    
    /**
     * Delete collection.
     *
     * @param id
     *            the id
     */
    public static void deleteCollection(Long id) {
        render();
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

    /**
     * Remove item from collection.
     *
     * @param itemId
     *            the item id
     * @param CollectionId
     *            the collection id
     */
    public static void removeItemFromCollection(Long itemId, Long CollectionId) {
        render();
    }


}
