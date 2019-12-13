package org.tango.javabenchmarktarget;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.TangoAttribute;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 11.12.2019
 */
public class TestClient {

    public static final int NUMBER_OF_CLIENTS = 64;

    public static final int HOUR = 3_600_000;
    public static final int FIFTEEN_SECONDS = 15_000;
    public static final int MINUTE = 60_000;
    public static final int FIFTEEN_MINUTE = 900_000;

    @Test @Ignore
    public void test() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CLIENTS);

        final AtomicLong counter = new AtomicLong(0L);
        final AtomicLong errors = new AtomicLong(0L);

        final AtomicBoolean finish = new AtomicBoolean(false);

        for(int i = 0; i< NUMBER_OF_CLIENTS; ++i) {
            executorService.submit(() -> {
                TangoAttribute attribute = null;
                try {
                    attribute = new TangoAttribute("tango://hzgxenvtest:10000/development/benchmark/0/BenchmarkScalarAttribute");
                } catch (DevFailed devFailed) {
                    errors.incrementAndGet();
                    return;
                }

                while (!finish.get()) {
                    try {
                        attribute.write(3.14);
                        counter.incrementAndGet();
                    } catch (DevFailed e) {
                        errors.incrementAndGet();
                    }
                }
            });
        }

        Thread.sleep(FIFTEEN_SECONDS);
        finish.set(true);

        executorService.shutdownNow();

        System.out.println(String.format("Total writes count: %d", counter.get()));
        System.out.println(String.format("Total errors count: %d", errors.get()));
    }

    @Test
    @Ignore
    public void test_sys_tg_test() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CLIENTS);

        final AtomicLong counter = new AtomicLong(0L);
        final AtomicLong errors = new AtomicLong(0L);

        final AtomicBoolean finish = new AtomicBoolean(false);

        for(int i = 0; i< NUMBER_OF_CLIENTS; ++i) {
            executorService.submit(() -> {
                TangoAttribute attribute = null;
                try {
                    attribute = new TangoAttribute("tango://hzgxenvtest:10000/sys/tg_test/0/double_scalar_w");
                } catch (DevFailed devFailed) {
                    errors.incrementAndGet();
                    return;
                }


                while (!finish.get()) {
                    try {
                        attribute.write(3.14);
                        counter.incrementAndGet();
                    } catch (DevFailed e) {
                        errors.incrementAndGet();
                    }
                }
            });
        }


        Thread.sleep(FIFTEEN_SECONDS);
        finish.set(true);

        executorService.shutdownNow();

        System.out.println(String.format("Total writes count: %d", counter.get()));
        System.out.println(String.format("Total errors count: %d", errors.get()));
    }

}
