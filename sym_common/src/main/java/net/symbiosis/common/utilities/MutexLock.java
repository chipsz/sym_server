package net.symbiosis.common.utilities;

import net.symbiosis.common.configuration.Configuration;

import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * ***************************************************************************
 * *
 * Created:     25 / 09 / 2015                                             *
 * Platform:    Red Hat Linux 9                                            *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 * ****************************************************************************
 */


public class MutexLock {

    private static final Logger logger = Configuration.getNewLogger(MutexLock.class.getSimpleName());
    private static final Long DEFAULT_MUTEX_LOCK_WAIT_TIME = 10000L; //10 seconds
    private static final Long DEFAULT_MUTEX_LOCK_INTERVAL = 1000L;   //10 seconds
    private boolean locked = false;

    private Long waitTimeout = Long.parseLong(Configuration.getSymbiosisProperty("MutexLockWaitTime"));

    private Long waitInterval = Long.parseLong(Configuration.getSymbiosisProperty("MutexLockInterval"));

    public MutexLock() {
        this.waitTimeout = DEFAULT_MUTEX_LOCK_WAIT_TIME;
        this.waitInterval = DEFAULT_MUTEX_LOCK_INTERVAL;
    }

    public MutexLock(Long waitTimeout, Long waitInterval) {
        this.waitTimeout = waitTimeout;
        this.waitInterval = waitInterval;
    }

    public synchronized boolean waitForLock() {

        //wait for previous lock to be released
        while (this.locked && waitTimeout > 0) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitTimeout -= waitInterval;
        }

        return true;
    }

    public synchronized boolean acquireLock() {

        //wait for previous lock to be released
        while (this.locked && waitTimeout > 0) {
            logger.warning(format("Waiting for lock. Retrying in %d milliseconds", waitInterval));
            try {
                Thread.sleep(waitInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitTimeout -= waitInterval;
        }

        //acquire lock and return
        this.locked = true;

        return true;
    }

    public synchronized void unlock() {
        this.locked = false;
    }

    protected boolean isLocked() {
        return this.locked;
    }
}
