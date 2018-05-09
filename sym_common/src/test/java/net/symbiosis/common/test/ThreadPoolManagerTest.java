package net.symbiosis.common.test;

import net.symbiosis.common.configuration.ThreadPoolManager;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.AssertJUnit.assertTrue;

/**
 * ***************************************************************************
 * *
 * Created:     29 / 09 / 2015                                             *
 * Platform:    Red Hat Linux 9                                            *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 * ****************************************************************************
 */

@Test
public class ThreadPoolManagerTest {

    static volatile long lastTime = 0;

    @Test
    public void testSchedule() {
        //make sure we are running parallel processes correctly

        //record start time
        final long startTime = new Date().getTime();

        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            ThreadPoolManager.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Running thread " + finalI);
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //record only the thread that ended last
                    long endDate = new Date().getTime();
                    if (endDate > lastTime) lastTime = endDate;
                }
            });
        }

        //make sure all threads have completed
        if (!ThreadPoolManager.waitForThreadCompletion(11000L))
            assertTrue(false);

        //end time must be less than 10 seconds if they all ran asynchronously
        long timeDiff = (lastTime - startTime) / 1000;
        System.out.println("All threads took " + timeDiff + " seconds");
        assertTrue(timeDiff < 3);
    }
}
