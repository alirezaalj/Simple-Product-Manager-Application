package ir.alirezaalijani.product.manager.application.service.scheduler;

import java.util.concurrent.Future;

public interface SchedulerContent {
    String getSchedulerName();
    Runnable getRunnable();
    Future<?> getFuture();
    void setFuture(Future<?> future);
    void setRunnable(Runnable runnable);
}
