package com.tonytaotao.springboot.dubbo.account.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tonytaotao.springboot.dubbo.account.user.entity.UserInfo;
import com.tonytaotao.springboot.dubbo.account.user.mapper.UserInfoMapper;
import com.tonytaotao.springboot.dubbo.account.user.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public void saveOrUpdateUserInfo(UserInfo entity) {
        this.saveOrUpdate(entity);
    }

    @Override
    public Boolean deleteUserInfoById(Long id) {
        return this.removeById(id);
    }

}
