/*******************************************************************************
 *      Author: Arman Radmanesh <radmanesh@gmail.com>
 *  Created on: Feb 28, 2018
 *     Project: bongamonga
 *   Copyright: See the file "LICENSE" for the full license governing this code.
 * Description: 
 *******************************************************************************/

package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;

import controllers.security.Security;
import play.data.validation.IPv4Address;
import play.data.validation.Required;
import play.db.jpa.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class Log.
 */
@Entity
public class Log extends Model {

    /**
     * The Enum LogType.
     */
    public enum LogType {
        
        /** The system. */
        SYSTEM, 
 /** The user. */
 USER, 
 /** The misc. */
 MISC, 
 /** The frontend. */
 FRONTEND, 
 /** The backend. */
 BACKEND
    }

    /** The type. */
    @Required
    public LogType type;

    /**
     * Instantiates a new log.
     */
    public Log() {

    }

    /**
     * Instantiates a new log.
     *
     * @param type the type
     * @param title the title
     * @param message the message
     */
    public Log(LogType type, String title, String message) {
        this.actor = Security.connected();
        this.timestamp = new Date();
        this.type = type;
        this.title = title;
        this.message = message;
        this.deleted = false;
    }

    /** The timestamp. */
    public Date timestamp = new Date();

    /** The title. */
    @Required
    public String title;

    /** The message. */
    @Lob
    public String message;

    /** The actor. */
    public String actor;

    /** The actor ip. */
    @IPv4Address
    public String actorIp;

    /** The deleted. */
    public Boolean deleted = false;
}
