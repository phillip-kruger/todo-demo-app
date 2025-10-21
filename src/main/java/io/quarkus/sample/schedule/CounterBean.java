package io.quarkus.sample.schedule;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class CounterBean {

    private final AtomicInteger counter = new AtomicInteger();

    public int get() {  
        return counter.get();
    }

    @Scheduled(every="1000s")     
    void increment() {
        Log.debug("Scheduler increment " + counter.incrementAndGet());        
    }

    @Scheduled(cron="0 15 10 * * ?") 
    void cronJob(ScheduledExecution execution) {
        Log.debug(execution.getScheduledFireTime() + " " + counter.incrementAndGet());
    }

    @Scheduled(cron = "{cron.expr}") 
    void cronJobWithExpressionInConfig() {
       Log.debug("Cron expression configured in application.properties " + counter.incrementAndGet());
    }
}
