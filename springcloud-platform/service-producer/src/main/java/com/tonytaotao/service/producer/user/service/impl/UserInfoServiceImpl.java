package com.tonytaotao.service.producer.user.service.impl;

import com.tonytaotao.service.producer.user.entity.UserInfo;
import com.tonytaotao.service.producer.user.mapper.UserInfoMapper;
import com.tonytaotao.service.producer.user.service.UserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务接口实现类
 * </p>
 *
 * @author wujintao
 * @since 2018-08-11
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public void saveOrUpdateUserInfo(UserInfo entity) {
        this.insertOrUpdate(entity);
    }

    @Override
    public Boolean deleteUserInfoById(Long id) {
        return this.deleteById(id);
    }

}
