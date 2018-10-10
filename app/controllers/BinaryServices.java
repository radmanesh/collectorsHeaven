package controllers;

import java.util.HashMap;
import java.util.Map;

import models.FileAttachment;
import models.collection.json.JqueryFileInput;
import play.Logger;
import play.data.Upload;
import play.mvc.*;
import play.mvc.Http.Response;
import play.vfs.VirtualFile;

public class BinaryServices extends Controller {

    public static void uploadImages(Upload[] files) {
        // Http.Response.current().headers.put("Content-Length", new
        // Http.Header("Content-Length",
        // String.valueOf(files.get(1).asFile().length())));
        JqueryFileInput jfi = new JqueryFileInput();
        for (Upload u : files) {
            try {
                models.FileAttachment fa = new FileAttachment(u.asFile());
                fa.save();
                Map<String, Object> args = new HashMap<>();
                args.put("id", fa.id);
                jfi.addSuccessFile(fa.name, fa.attachment.length(), Router.getFullUrl("Collectors.showImage", args),
                        Router.getFullUrl("Collectors.showImage", args), "", "DELETE");

                Http.Response.current().setHeader("Content-Length", String.valueOf(files[0].asFile().length()));
            } catch (Throwable e) {
                Logger.error(e, "uploadImages");
                jfi.addErrorFile(u.getFileName(), u.getSize(), e.getMessage());
            }
            // jfi.
            Logger.info("file: %s", u.getFileName() + ":" + u.getContentType() + ":" + u.getFileName());
        }
        Response.current().setContentTypeIfNotSet("application/json");
        renderJSON(jfi.buildJson());
    }

    public static void renderFileAttachment(Long id) {
        FileAttachment fa = FileAttachment.findById(id);
        if (fa == null || fa.attachment == null)
            renderBinary(VirtualFile.fromRelativePath("/public/img/general-noImage.jpg").getRealFile());
        renderBinary(fa.attachment.get());
    }
    
    public static void renderImage(String model,String imageField, Long id) {
        
    }

}
