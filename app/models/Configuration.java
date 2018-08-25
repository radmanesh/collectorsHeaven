/*******************************************************************************
 *      Author: Arman Radmanesh <radmanesh@gmail.com>
 *  Created on: Feb 28, 2018
 *     Project: bongamonga
 *   Copyright: See the file "LICENSE" for the full license governing this code.
 * Description: 
 *******************************************************************************/

package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.db.jpa.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class Configuration.
 */
@Entity
public class Configuration extends Model {

    /** The key. */
    @Column(name="CONF_KEY")
    public String key;
    
    /** The value. */
    @Column(name="CONF_VAL")
    public String value;
    
    /** The name. */
    @Column(name="CONF_NAME")
    private String name;

    /**
     * Instantiates a new configuration.
     */
    public Configuration() {

    }

    /**
     * Main constructor.
     *
     * @param name the name
     * @param key the key
     * @param value the value
     */
    public Configuration(String name, String key, String value) {
        this.name = name;
        this.key = key;
        this.value = value;
    }

    public String getName() {
        if (this.name != null) {
            return this.name;
        }
        return this.key;
    }

    /**
     * Returns value of configuration with the desired key.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the string
     */
    public static String get(String key, String defaultValue) {
        Configuration c = find("key = ?1", key).first();
        if (c != null) {
            return c.value;
        }
        return defaultValue;
    }

    /**
     * Returns integer value of configuration with the desired key.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return Integer
     */
    public static Integer get(String key, Integer defaultValue) {
        Configuration c = find("key = ?1", key).first();
        if (c != null) {
            return Integer.valueOf(c.value);
        }
        return defaultValue;
    }

    /**
     * Sets the.
     *
     * @param name the name
     * @param key the key
     * @param value the value
     */
    public static void set(String name, String key, String value) {
        Configuration c = find("key = ?1", key).first();
        if (c == null) {
            c = new Configuration(name, key, value);
        }
        else {
            c.name = name;
            c.value = value;
        }
        c.save();
    }

    /* (non-Javadoc)
     * @see play.db.jpa.JPABase#toString()
     */
    @Override
    public String toString() {
        return "Configuration[" + key + "]=" + value + "";
    }
}
