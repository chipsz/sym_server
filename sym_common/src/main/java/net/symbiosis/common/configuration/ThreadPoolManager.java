package net.symbiosis.common.configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_THREAD_CORE_POOL_SIZE;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_THREAD_MAX_POOL_SIZE;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;

/**
 * Created by tsungai.kaviya on 2015-09-07.
 */
public class ThreadPoolManager {
    private static final Integer MAX_THREADS = parseInt(getSymConfigDao().getConfig(CONFIG_THREAD_MAX_POOL_SIZE));
    private static final Integer KEEP_ALIVE_TIME = 20;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
        parseInt(getSymConfigDao().getConfig(CONFIG_THREAD_CORE_POOL_SIZE)),
        MAX_THREADS, KEEP_ALIVE_TIME, SECONDS, new ArrayBlockingQueue<>(MAX_THREADS));

    private static ThreadPoolManager threadPoolManager = null;

    private ThreadPoolManager() {
    }

    public static ThreadPoolManager getInstance() {
        if (threadPoolManager == null)
            threadPoolManager = new ThreadPoolManager();
        return threadPoolManager;
    }

    public static void schedule(Runnable newTask) {
        getInstance().threadPoolExecutor.execute(newTask);
    }

    public static boolean waitForThreadCompletion(Long timeout) {
        try {
            getInstance().threadPoolExecutor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
