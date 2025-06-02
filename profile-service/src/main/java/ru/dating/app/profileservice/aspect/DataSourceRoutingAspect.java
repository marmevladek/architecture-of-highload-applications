package ru.dating.app.profileservice.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.dating.app.profileservice.config.ReplicationRoutingDataSource;

@Aspect
@Component
@Order
public class DataSourceRoutingAspect {

    @Pointcut("@annotation(transactional)")
    public void transactionalMethods(org.springframework.transaction.annotation.Transactional transactional) {}

    @Before("transactionalMethods(transactional) && args()")
    public void beforeTransaction(org.aspectj.lang.JoinPoint joinPoint, org.springframework.transaction.annotation.Transactional transactional) {
        if (transactional.readOnly()) {
            ReplicationRoutingDataSource.setWrite(false);
        } else {
            ReplicationRoutingDataSource.setWrite(true);
        }
    }

    @After("transactionalMethods(transactional)")
    public void afterTransaction(org.aspectj.lang.JoinPoint joinPoint, org.springframework.transaction.annotation.Transactional transactional) {
        ReplicationRoutingDataSource.clear();
    }
}
