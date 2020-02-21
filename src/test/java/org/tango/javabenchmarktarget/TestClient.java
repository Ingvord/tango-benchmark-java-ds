package org.tango.javabenchmarktarget;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.TangoAttribute;
import org.junit.Ignore;
import org.junit.Test;
import org.tango.utils.DevFailedUtils;

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
    public void testUpgradeProtocolSingle() throws Exception {
        DeviceProxy proxy;
        try {
            proxy = new DeviceProxy("tango://hzgxenvtest:10000/development/benchmark/0");
            proxy.upgradeProtocol("http");

        } catch (DevFailed devFailed) {
            DevFailedUtils.printDevFailed(devFailed);
            return;
        }


        try {
            proxy.writeAttribute("BenchmarkScalarAttribute", 3.14);
        } catch (DevFailed e) {
            DevFailedUtils.printDevFailed(e);
        }
    }

    @Test
    @Ignore
    public void testUpgradeProtocol() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CLIENTS);

        final AtomicLong counter = new AtomicLong(0L);
        final AtomicLong errors = new AtomicLong(0L);

        final AtomicBoolean finish = new AtomicBoolean(false);


        for (int i = 0; i < NUMBER_OF_CLIENTS; ++i) {
            executorService.submit(() -> {
                DeviceProxy proxy;
                try {
                    proxy = new DeviceProxy("tango://hzgxenvtest:10000/development/benchmark/0");
                    proxy.upgradeProtocol("zmq");

                } catch (DevFailed devFailed) {
                    errors.incrementAndGet();
                    DevFailedUtils.printDevFailed(devFailed);
                    return;
                }


                while (!finish.get()) {
                    try {
                        proxy.writeAttribute("BenchmarkScalarAttribute", 3.14);
                        counter.incrementAndGet();
                    } catch (DevFailed e) {
                        DevFailedUtils.printDevFailed(e);
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
                DeviceProxy proxy = null;
                try {
                    proxy = new DeviceProxy("tango://hzgxenvtest:10000/development/benchmark/0");
                } catch (DevFailed e) {
                    errors.incrementAndGet();
                    return;
                }

                DeviceAttribute attribute = new DeviceAttribute("BenchmarkScalarAttribute", 3.14);

                while (!finish.get()) {
                    try {
                        proxy.write_attribute(attribute);
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
