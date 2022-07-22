package ir.alirezaalijani.product.manager.application.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Objects;

public class ContextHolder {
    private ApplicationContext applicationContext;
    private GenericApplicationContext genericApplicationContext;

    private ContextHolder(){
    }

    private static class Helper {
        private static final ContextHolder viewHolder = new ContextHolder();
    }
    public static ContextHolder getInstance(){
        return Helper.viewHolder;
    }

    public synchronized void setApplicationContext(ApplicationContext applicationContext) {
        if (Objects.isNull(this.applicationContext)){
            this.applicationContext = applicationContext;
            this.genericApplicationContext= (GenericApplicationContext) applicationContext;
        }
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public <T> void registerObjectBean(String beanName, Class<T> beanClass,T o){
        this.genericApplicationContext.registerBean(beanName,beanClass,()-> o);
    }

    public <T> T getBean(String name,Class<T> tClass){
        return this.applicationContext.getBean(name,tClass);
    }
}
