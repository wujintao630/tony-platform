package com.tonytaotao.service.producer.user.service;

import com.tonytaotao.service.producer.user.entity.UserInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务接口类
 * </p>
 *
 * @author tonytaotao
 * @since 2018-08-11
 */
public interface UserInfoService extends IService<UserInfo> {

    void saveOrUpdateUserInfo(UserInfo entity);

    Boolean deleteUserInfoById(Long id);
}
