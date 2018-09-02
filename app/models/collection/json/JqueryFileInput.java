package models.collection.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class JqueryFileInput {

    public List<? super GenericFile> files;

    private transient DeleteFile df = null;

    public JqueryFileInput() {
        files = new ArrayList<>();
    }

    public void addSuccessFile(String name, long l, String url, String thumbnailUrl, String deleteUrl,
            String deleteType) {
        SuccessFile sf = new SuccessFile(name, l, url, thumbnailUrl, deleteUrl, deleteType);
        files.add(sf);
    }
    
    public void addErrorFile(String name, long size, String error) {
        ErrorFile ef = new ErrorFile(name, size, error);
        files.add(ef);
    }
    
    public void addDeleteFile(String file, Boolean stat) {
        if(df==null) {
            this.df = new DeleteFile();
            files.add(df);
        }
        df.addDeleteFile(file, stat);
    }
    
    public String buildJson() {
        return new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(this);
    }
    
    public abstract class GenericFile{
        
    }

    class SuccessFile extends GenericFile {
        public String name;
        public long size;
        public String url;
        public String thumbnailUrl;
        public String deleteUrl;
        public String deleteType;

        /**
         * @param name
         * @param size
         * @param url
         * @param thumbnailUrl
         * @param deleteUrl
         * @param deleteType
         */
        public SuccessFile(String name, long size, String url, String thumbnailUrl, String deleteUrl,
                String deleteType) {
            this.name = name;
            this.size = size;
            this.url = url;
            this.thumbnailUrl = thumbnailUrl;
            this.deleteUrl = deleteUrl;
            this.deleteType = deleteType;
        }

    }

    class ErrorFile  extends GenericFile{
        public String name;
        public long size;
        public String error;
        /**
         * @param name
         * @param size
         * @param error
         */
        public ErrorFile(String name, long size, String error) {
            this.name = name;
            this.size = size;
            this.error = error;
        }
    }

    class DeleteFile  extends GenericFile{
        public Map<String, Boolean> files;

        /**
         * @param file
         */
        public DeleteFile() {
            files = new HashMap<>();
        }
        
        public void addDeleteFile(String file, Boolean stat) {
            files.put(file, stat);
        }
    }
    
    public static void main(String[] args) {
        JqueryFileInput jfi = new JqueryFileInput();
        jfi.addSuccessFile("icon.jpg", 123321, "http://sdf.com", "http://thumb.png", "http://deleteurl", "_DELETE");
        jfi.addSuccessFile("icon2.jpg", 12321, "http://sdf2.com", "http://thumb2.png", "http://deleteur2l", "_DELETE");
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(jfi));
        jfi = new JqueryFileInput();
        jfi.addErrorFile("name1", 10000, "can't read");
        jfi.addErrorFile("name2", 40000, "connection err");
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(jfi));
        
    }
}
