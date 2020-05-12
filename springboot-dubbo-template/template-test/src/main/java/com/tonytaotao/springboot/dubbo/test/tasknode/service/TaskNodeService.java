package com.tonytaotao.springboot.dubbo.test.tasknode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tonytaotao.springboot.dubbo.test.tasknode.entity.TaskNode;

/**
 * <p>
 *  服务接口类
 * </p>
 *
 * @author tonytaotao
 * @since 2019-10-25
 */
public interface TaskNodeService extends IService<TaskNode> {

    boolean saveWebNode(String id, String remark, Integer type);

}
