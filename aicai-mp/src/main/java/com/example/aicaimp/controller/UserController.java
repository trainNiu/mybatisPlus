package com.example.aicaimp.controller;


import com.example.aicaiframework.demos.object.Result;
import com.example.aicaimp.entity.UserEntity;
import com.example.aicaimp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.aicaiframework.demos.entity.base.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nh
 * @since 2024-12-10
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "根据ID查询用户信息")
    @GetMapping("/getById")
    public Result<UserEntity> getById(){
        UserEntity userEntity = userService.getById(1);
        return Result.success(userEntity);
    }

}

