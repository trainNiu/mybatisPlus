package com.example.aicaiuser.controller;


import com.example.aicaiframework.demos.object.Result;
import com.example.aicaiuser.entity.UserEntity;
import com.example.aicaiuser.rabbitmq.MessageWrapper;
import com.example.aicaiuser.rabbitmq.RabbitSendService;
import com.example.aicaiuser.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.example.aicaiframework.demos.entity.base.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nh
 * @since 2024-12-30
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/userEntity")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private RabbitSendService rabbitSendService;

    @ApiOperation(value = "根据ID查询用户信息")
    @GetMapping("/getById")
    public Result<UserEntity> getById(){
        UserEntity userEntity = userService.getById(1);
        //发送消息同步老系统的员工档案信息
        rabbitSendService.send("user-change","userBindKey", MessageWrapper.wrap(userEntity));
        return Result.success(userEntity);
    }
}

