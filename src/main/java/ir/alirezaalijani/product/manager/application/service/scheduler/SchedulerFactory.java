package ir.alirezaalijani.product.manager.application.service.scheduler;

public class SchedulerFactory {

    public static SchedulerContent getContent(SchedulersIds type){
        if (type.equals(SchedulersIds.COIN_MARKETS_FETCH_TASK)){
            return new CoinMarketsTask(type.name());
        }
        return null;
    }

    private SchedulerFactory() {}
}
