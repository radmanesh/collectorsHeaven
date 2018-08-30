/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * Shutdown.java
 * Created 9:44:40 AM
 */

package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStop;

// TODO: Auto-generated Javadoc
/**
 * The Class Shutdown.
 */
@OnApplicationStop
public class Shutdown extends Job {

    /*
     * (non-Javadoc)
     *
     * @see play.jobs.Job#doJob()
     */
    @Override
    public void doJob() throws Exception {
    }
}
