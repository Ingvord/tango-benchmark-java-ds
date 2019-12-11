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
    @Test
    @Ignore
    public void test() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        final AtomicLong counter = new AtomicLong(0L);
        final AtomicLong errors = new AtomicLong(0L);

        final AtomicBoolean finish = new AtomicBoolean(false);

        executorService.submit(()->{
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


        Thread.sleep(3_600_000);
        finish.set(true);

        executorService.shutdownNow();

        System.out.println(String.format("Total writes count: %d", counter.get()));
        System.out.println(String.format("Total errors count: %d", errors.get()));
    }

}
