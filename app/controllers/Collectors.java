/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * Collectors.java
 * Created 9:44:40 AM
 */
package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.FileAttachment;
import models.collection.Collection;
import models.collection.CollectionItem;
import models.collection.json.JqueryFileInput;
import play.Logger;
import play.data.Upload;
import play.data.validation.Validation;
import play.db.jpa.Blob;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Response;
import play.mvc.Router;
import play.vfs.VirtualFile;

// TODO: Auto-generated Javadoc
/**
 * The Class Collectors.
 */
public class Collectors extends Controller {

    /**
     * Index.
     */
    public static void index() {
        List<Collection> collections = Collection.all().fetch();
        render(collections);
    }

    /**
     * Creates the collection.
     */
    public static void createCollection() {
        if(request.method.equalsIgnoreCase("post")) {
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
        }else {
            render();
        }
    }
    
    public static void uploadImages(Upload[] files) {
        //Http.Response.current().headers.put("Content-Length", new Http.Header("Content-Length", String.valueOf(files.get(1).asFile().length())));
        JqueryFileInput jfi = new JqueryFileInput();
        for(Upload u:files) {
            try {
                models.FileAttachment fa = new FileAttachment(u.asFile());
                fa.save();
                Map<String, Object> args = new HashMap<>();
                args.put("id", fa.id);
                jfi.addSuccessFile(fa.name, fa.attachment.length(), Router.getFullUrl("Collectors.showImage", args),Router.getFullUrl("Collectors.showImage", args), "", "DELETE");
                
                Http.Response.current().setHeader("Content-Length", String.valueOf(files[0].asFile().length()));
            }catch (Throwable e) {
                Logger.error(e, "uploadImages");
                jfi.addErrorFile(u.getFileName(), u.getSize(), e.getMessage());
            }
            //jfi.
            Logger.info("file: %s", u.getFileName()+":"+u.getContentType()+":"+u.getFileName());
        }
        Response.current().setContentTypeIfNotSet("application/json");
        renderJSON(jfi.buildJson());
    }
    
    public static void showImage(Long id) {
        FileAttachment fa = FileAttachment.findById(id);
        if(fa==null || fa.attachment==null)
            renderBinary(VirtualFile.fromRelativePath("/public/img/general-noImage.jpg").getRealFile());
        renderBinary(fa.attachment.get());
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
     * Edits the collection items.
     *
     * @param id
     *            the id
     */
    public static void editCollectionItems(Long id) {
        Collection collection = Collection.findById(id);
        notFoundIfNull(collection);
        render(collection);
    }

    /**
     * Adds the item to collection.
     *
     * @param collectionId
     *            the collection id
     */
    public static void addItemToCollection(Long collectionId) {
        Collection collection = Collection.findById(collectionId);
        notFoundIfNull(collection);
        CollectionItem item = new CollectionItem();
        item.edit(params.getRootParamNode(), "item");
        item.collection = collection;
        validation.valid(item);
        if (Validation.hasErrors()) {
            Validation.keep();
            params.flash();
            flash.error("flash.error");
            editCollectionItems(collectionId);
        }
        item.save();
        collection.save();
        flash.success("flash.success");
        editCollectionItems(collectionId);
    }

    /**
     * Edits the item.
     *
     * @param id
     *            the id
     */
    public static void editItem(Long id) {
        CollectionItem item = CollectionItem.findById(id);
        notFoundIfNull(item);
        if (request.isLoopback) { // Its a

        }
        render();
    }

    /**
     * Upload image.
     *
     * @param id
     *            the id
     * @param image
     *            the image
     * @throws Throwable
     *             the throwable
     */
    public static void uploadImage(Long id, Blob image) throws Throwable {
        CollectionItem item = CollectionItem.findById(id);
        notFoundIfNull(item);
        if (!image.exists()) {
            flash.error("error in uploading image");
            editCollectionItems(id);
        }

        redirect(Router.reverse("controllers.agency.AgencyProperties.editImages").url);
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
     * Remote item from collection.
     *
     * @param itemId
     *            the item id
     * @param CollectionId
     *            the collection id
     */
    public static void remoteItemFromCollection(Long itemId, Long CollectionId) {
        render();
    }


    /**
     * Adds the category to collection.
     *
     * @param CollectionId
     *            the collection id
     */
    public static void addCategoryToCollection(Long CollectionId) {

    }

    /**
     * Removes the category to collection.
     *
     * @param categoryId
     *            the category id
     * @param CollectionId
     *            the collection id
     */
    public static void removeCategoryToCollection(Long categoryId, Long CollectionId) {

    }


}
