package controllers.collector;

import java.util.List;

import models.collection.Collection;
import models.collection.CollectionItem;
import models.collection.Tag;
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
                for (String t : tags) {
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


}
