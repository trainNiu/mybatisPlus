package com.example.aicaimp.service.impl;

import com.example.aicaimp.entity.UserEntity;
import com.example.aicaimp.mapper.UserMapper;
import com.example.aicaimp.service.IUserService;
import com.example.aicaiframework.demos.entity.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nh
 * @since 2024-12-10
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserEntity> implements IUserService {

}
