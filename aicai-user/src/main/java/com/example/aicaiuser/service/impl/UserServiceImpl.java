package com.example.aicaiuser.service.impl;

import com.example.aicaiuser.entity.UserEntity;
import com.example.aicaiuser.mapper.UserMapper;
import com.example.aicaiuser.service.IUserService;
import com.example.aicaiframework.demos.entity.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nh
 * @since 2024-12-30
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserEntity> implements IUserService {

}
