package models.widgets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;

import ext.JalaliExtentions;
import play.Logger;
import play.db.jpa.Model;

/**
 * DataTales structure used with GsonBuilder for building dataTables ready JSON .
 *
 * @version: 0 //TODO:BETA! needs refactoring
 */
public class DataTablesModel {

    /**
     * Instantiates a new data tables model.
     */
    public DataTablesModel() {
        data = new ArrayList<Map<String, Object>>();
        columns = new ArrayList<Map<String, String>>();
    }

    public Collection<Map<String, Object>> data;
    public Collection<Map<String, String>> columns;

    public DataTablesModel addColumn(String title, String data) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("title", title);
        m.put("data", data);
        columns.add(m);
        return this;
    }

    public DataTableDataSerieCreator createSerie() {
        DataTableDataSerieCreator dtc = new DataTableDataSerieCreator(this);
        return dtc;
    }

    public class DataTableDataSerieCreator {
        Map<String, Object> d;
        DataTablesModel dtm;

        public DataTableDataSerieCreator(DataTablesModel model) {
            dtm = model;
            d = new HashMap<String, Object>();
        }

        public DataTableDataSerieCreator insertData(String key, Object value) {
            if (key == null)
                return this;
            this.d.put(key, (value == null ? " " : value));
            return this;
        }

        public Map<String, Object> create() {
            dtm.data.add(d);
            return d;
        }
    }

    public String toJsonString() {
        return new GsonBuilder().disableHtmlEscaping().serializeNulls().create().toJson(this);
    }

    public static <T extends Model> DataTablesModel initializeFromModel(List<T> objects) {
        DataTablesModel dtm = new DataTablesModel();
        if (objects == null || objects.isEmpty())
            return dtm;

        Model firstObj = (Model) objects.get(0);
        for (Field f : firstObj.getClass().getDeclaredFields()) {
            String fName = null;
            if (f.getAnnotation(com.google.gson.annotations.SerializedName.class) != null) {
                fName = f.getAnnotation(com.google.gson.annotations.SerializedName.class).value();
            }
            if (fName == null)
                fName = f.getName();
            if (f.getType().equals(String.class)) {
                dtm.addColumn(f.getName(), f.getName());
            }else if (f.getType().equals(Integer.class)) {
                dtm.addColumn(f.getName(), f.getName());
            }else if (f.getType().equals(Double.class)) {
                dtm.addColumn(f.getName(), f.getName());
            }else if (f.getType().equals(Long.class)) {
                dtm.addColumn(f.getName(), f.getName());
            }else if (f.getType().isEnum()) {
                dtm.addColumn(f.getName(), f.getName());
            }else if (f.getType().equals(Date.class)) {
                dtm.addColumn(f.getName(), f.getName());
            }else if (f.getType().isAssignableFrom(Model.class)) {
                dtm.addColumn(f.getName(), f.getName());
            }
        }

        for (T obj : objects) {
            DataTableDataSerieCreator dtdsc = dtm.createSerie();
            for (Field f : (obj).getClass().getDeclaredFields()) {
                String fName = null;
                if (f.getAnnotation(com.google.gson.annotations.SerializedName.class) != null) {
                    fName = f.getAnnotation(com.google.gson.annotations.SerializedName.class).value();
                }
                if (fName == null)
                    fName = f.getName();
                try {
                    Object fObj = f.get(obj);
                    if (fObj == null) {
                        dtdsc.insertData(f.getName(), null);
                    }else if (f.getType().equals(String.class)) {
                        dtdsc.insertData(f.getName(), fObj.toString());
                    }else if (f.getType().equals(Integer.class)) {
                        dtdsc.insertData(f.getName(), fObj.toString());
                    }else if (f.getType().equals(Double.class)) {
                        dtdsc.insertData(f.getName(), fObj.toString());
                    }else if (f.getType().equals(Long.class)) {
                        dtdsc.insertData(f.getName(), fObj.toString());
                    }else if (f.getType().isEnum()) {
                        dtdsc.insertData(f.getName(), fObj.toString());
                    }else if (f.getType().equals(Date.class)) {
                        dtdsc.insertData(f.getName(), JalaliExtentions.jalali((Date) fObj));
                    }else if (f.getType().isAssignableFrom(Model.class)) {
                        dtdsc.insertData(f.getName(), ((Model) fObj)._key() + " : " + fObj.toString());
                    }
                }catch (Exception e) {
                    Logger.error(e, "DataTableModel.initFromModel");
                }
            }
            dtdsc.create();
        }
        return dtm;
    }
    
    public static <T extends Model> DataTablesModel initializeFromModel(List<T> objects,String ...fields) {
        DataTablesModel dtm = new DataTablesModel();
        final List<String> fieldsList = Arrays.asList(fields);
        if (objects == null || objects.isEmpty())
            return dtm;

        Model firstObj = (Model) objects.get(0);
        for (Field f : firstObj.getClass().getDeclaredFields()) {
            String fName = null;
            if (f.getAnnotation(com.google.gson.annotations.SerializedName.class) != null) {
                fName = f.getAnnotation(com.google.gson.annotations.SerializedName.class).value();
            }
            if (fName == null)
                fName = f.getName();
            if(fieldsList.contains(fName.toLowerCase())){
                if (f.getType().equals(String.class)) {
                    dtm.addColumn(fName, f.getName());
                }else if (f.getType().equals(Integer.class)) {
                    dtm.addColumn(fName, f.getName());
                }else if (f.getType().equals(Double.class)) {
                    dtm.addColumn(fName, f.getName());
                }else if (f.getType().equals(Long.class)) {
                    dtm.addColumn(fName, f.getName());
                }else if (f.getType().isEnum()) {
                    dtm.addColumn(fName, f.getName());
                }else if (f.getType().equals(Date.class)) {
                    dtm.addColumn(fName, f.getName());
                }else if (f.getType().isAssignableFrom(Model.class)) {
                    dtm.addColumn(fName, f.getName());
                }
            }
        }

        for (T obj : objects) {
            DataTableDataSerieCreator dtdsc = dtm.createSerie();
            for (Field f : (obj).getClass().getDeclaredFields()) {
                String fName = null;
                if (f.getAnnotation(com.google.gson.annotations.SerializedName.class) != null) {
                    fName = f.getAnnotation(com.google.gson.annotations.SerializedName.class).value();
                }
                if (fName == null)
                    fName = f.getName();
                if(fieldsList.contains(fName.toLowerCase())){
                    try {
                        Object fObj = f.get(obj);
                        if (fObj == null) {
                            dtdsc.insertData(f.getName(), null);
                        }else if (f.getType().equals(String.class)) {
                            dtdsc.insertData(f.getName(), fObj.toString());
                        }else if (f.getType().equals(Integer.class)) {
                            dtdsc.insertData(f.getName(), fObj.toString());
                        }else if (f.getType().equals(Double.class)) {
                            dtdsc.insertData(f.getName(), fObj.toString());
                        }else if (f.getType().equals(Long.class)) {
                            dtdsc.insertData(f.getName(), fObj.toString());
                        }else if (f.getType().isEnum()) {
                            dtdsc.insertData(f.getName(), fObj.toString());
                        }else if (f.getType().equals(Date.class)) {
                            dtdsc.insertData(f.getName(), JalaliExtentions.jalali((Date) fObj));
                        }else if (f.getType().isAssignableFrom(Model.class)) {
                            dtdsc.insertData(f.getName(), ((Model) fObj)._key() + " : " + fObj.toString());
                        }
                    }catch (Exception e) {
                        Logger.error(e, "DataTableModel.initFromModel");
                    }
                }
            }
            dtdsc.create();
        }
        return dtm;
    }    
}
