package controllers;

import java.util.List;

import models.collection.Collection;
import models.collection.CollectionItem;
import play.mvc.*;

public class Gallery extends Controller {

    public static void index() {
        List<Collection> collections = Collection.all().fetch(); 
        render(collections);
    }
    
    public static void showCollection(Long id) {
        Collection collection= Collection.findById(id);
        render(collection);
    }
    
    public static void showCollectionItem(Long id) {
        CollectionItem collectionItem = CollectionItem.findById(id);
        render(collectionItem);
    }

}
