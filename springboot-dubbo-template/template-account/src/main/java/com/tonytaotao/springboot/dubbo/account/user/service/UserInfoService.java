package com.tonytaotao.springboot.dubbo.account.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tonytaotao.springboot.dubbo.account.user.entity.UserInfo;

/**
 * <p>
 *  服务接口类
 * </p>
 *
 * @author tonytaotao
 * @since 2019-10-25
 */
public interface UserInfoService extends IService<UserInfo> {

    void saveOrUpdateUserInfo(UserInfo entity);

    Boolean deleteUserInfoById(Long id);
}
