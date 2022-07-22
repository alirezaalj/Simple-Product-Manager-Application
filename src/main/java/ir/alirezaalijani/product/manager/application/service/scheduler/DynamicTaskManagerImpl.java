package ir.alirezaalijani.product.manager.application.service.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DynamicTaskManagerImpl implements DynamicTaskManager{

    private final Map<SchedulersIds,SchedulerContent> schedulerContentMap;
    private final TaskScheduler taskScheduler;

    public DynamicTaskManagerImpl(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
        this.schedulerContentMap=new HashMap<>();
    }

    public void startTask(SchedulersIds taskId,Runnable runnable ,long period) {

        if (schedulerContentMap.containsKey(taskId)){
            SchedulerContent content= schedulerContentMap.get(taskId);
            if (content.getFuture().isCancelled()){
                content.setFuture(taskScheduler.scheduleAtFixedRate(content.getRunnable(),period));
            }
        }else {
            SchedulerContent content= SchedulerFactory.getContent(taskId);
            if (content!=null){
                content.setRunnable(runnable);
                content.setFuture(taskScheduler.scheduleAtFixedRate(content.getRunnable(),period));
                schedulerContentMap.put(taskId,content);
            }
        }
        //        try{
//            //The get() method is used to get the execution result. This method will block until the task is completed;
//            //get() of future sets the timeout
//            Object o = future.get(3900, TimeUnit.MILLISECONDS);
//            System.out.println("===>>>  "+o);
//            Object o1 = future.get();
//            System.out.println("===>>>  "+o1);
//        }
//        catch ( Exception e){
//            System.out.println(e);
//        }
    }

    public void startTask(SchedulersIds taskId,long period){
        if (schedulerContentMap.containsKey(taskId)){
            SchedulerContent content= schedulerContentMap.get(taskId);
            if (content.getFuture().isCancelled()){
                content.setFuture(taskScheduler.scheduleAtFixedRate(content.getRunnable(),period));
            }
        }
    }

    public void stopTask(SchedulersIds taskId) {
        if (schedulerContentMap.containsKey(taskId)){
            SchedulerContent content=schedulerContentMap.get(taskId);
            if (content.getFuture()!=null&& !content.getFuture().isCancelled()){
                content.getFuture().cancel(true);
            }
        }
    }

    @Override
    public void changeTaskPeriod(SchedulersIds taskId,long period) {
        stopTask(taskId);
        startTask(taskId,period);
    }
}
