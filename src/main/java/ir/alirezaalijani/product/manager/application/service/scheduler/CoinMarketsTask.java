package ir.alirezaalijani.product.manager.application.service.scheduler;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

public class CoinMarketsTask implements SchedulerContent {

    private final String schedulerName;
    private Runnable runnable;
    private ScheduledFuture<?> future;


    public CoinMarketsTask(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    @Override
    public String getSchedulerName() {
        return this.schedulerName;
    }

    @Override
    public Runnable getRunnable() {
        return this.runnable;
    }

    @Override
    public Future<?> getFuture() {
        return this.future;
    }

    @Override
    public void setFuture(Future<?> future) {
        this.future= (ScheduledFuture<?>) future;
    }

    @Override
    public void setRunnable(Runnable runnable) {
        this.runnable=runnable;
    }

}
