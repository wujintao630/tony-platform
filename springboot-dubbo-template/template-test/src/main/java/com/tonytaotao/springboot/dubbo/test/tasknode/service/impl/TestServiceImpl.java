package com.tonytaotao.springboot.dubbo.test.tasknode.service.impl;

import com.tonytaotao.springboot.dubbo.test.tasknode.service.TaskNodeService;
import com.tonytaotao.springboot.dubbo.test.tasknode.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TaskNodeService taskNodeService;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    @Transactional
    public int testWebNode() throws Exception{

        taskNodeService.saveWebNode("1", "节点1", 0);

        FutureTask<Integer> futureTask = new FutureTask<>(new ThreadTransaction(taskNodeService, platformTransactionManager));

        new Thread(futureTask).start();


        int result = futureTask.get().intValue();

        if (result != 1) {
            throw new RuntimeException("子线程异常");
        }

        return 0;

    }
}

class ThreadTransaction implements Callable<Integer> {

    private TaskNodeService taskNodeService;

    private PlatformTransactionManager platformTransactionManager;

    public ThreadTransaction(TaskNodeService taskNodeService, PlatformTransactionManager platformTransactionManager) {
        this.taskNodeService = taskNodeService;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public Integer call() throws Exception {

        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();

        // 为了安全，新起事务
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);

        try {
            taskNodeService.saveWebNode("2", "节点2", 0);
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            e.printStackTrace();
            platformTransactionManager.rollback(transactionStatus);
            return 0;
        }

        return 1;
    }
}
