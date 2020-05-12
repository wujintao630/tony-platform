package com.tonytaotao.springboot.dubbo.test.tasknode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tonytaotao.springboot.dubbo.test.tasknode.entity.TaskNode;
import com.tonytaotao.springboot.dubbo.test.tasknode.mapper.TaskNodeMapper;
import com.tonytaotao.springboot.dubbo.test.tasknode.service.TaskNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务接口实现类
 * </p>
 *
 * @author tonytaotao
 * @since 2019-10-25
 */
@Service
@Slf4j
public class TaskNodeServiceImpl extends ServiceImpl<TaskNodeMapper, TaskNode> implements TaskNodeService {

    @Autowired
    private TaskNodeMapper taskNodeMapper;

    @Override
    @Transactional
    public boolean saveWebNode(String id, String remark, Integer type) {
        TaskNode taskNode = new TaskNode();
        taskNode.setId(id);
        taskNode.setRemark(remark);

        int result = taskNodeMapper.insert(taskNode);

        if (type.intValue() == 1) {
            throw new RuntimeException("主动抛出异常");
        }

        return true;
    }

}

