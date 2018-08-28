package controllers;

import java.util.List;

import models.collection.Collection;
import models.collection.Collector;
import play.mvc.*;

public class Collectors extends Controller {

    public static void index() {
        List<Collection> collections = Collection.all().fetch();
        render(collections);
    }
    
    public static void createCollection() {
        render();
    }
    
    public static void editCollection() {
        Collection collection = new Collection();
        collection.edit(params.getRootParamNode(), "collection");
        collection.collector = Collector.connectedCollector();
        validation.valid(collection);
        if(validation.hasErrors()) {
            validation.keep();
            params.flash();
            flash.error("flash.error.invalid.args");
            render(collection);
        }
        collection.save();
        flash.success("flash.success");
        render(collection);
    }

    public static void updateCollection(Long id) {
        Collection collection= Collection.findById(id);
        notFoundIfNull(collection);
        collection.edit(params.getRootParamNode(), "collection");
        validation.valid(collection);
        if(validation.hasErrors()) {
            validation.keep();
            params.flash();
            flash.error("flash.error.invalid.args");
            updateCollection(collection.id);
        }
        collection.save();
        flash.success("flash.success");
        index();
    }

    public static void deleteCollection(Long id) {
        render();
    }

    public static void showCollection(Long id) {
        Collection collection= Collection.findById(id);
        render(collection);
    }
    
    public static void addItemToCollection(Long CollectionId) {
        render();
    }
    
    public static void remoteItemFromCollection(Long itemId, Long CollectionId) {
        render();
    }
    
    public static void editItem(Long id) {
        render();
    }

    public static void addCategoryToCollection(Long CollectionId) {
        
    }

    public static void removeCategoryToCollection(Long categoryId,Long CollectionId) {
        
    }



}
