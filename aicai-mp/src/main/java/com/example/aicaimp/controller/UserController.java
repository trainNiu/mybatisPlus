package com.example.aicaimp.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.aicaiframework.demos.object.Result;
import com.example.aicaiframework.demos.redis.RedisTemplateAdapter;
import com.example.aicaiframework.demos.utils.ValidationUtil;
import com.example.aicaimp.entity.UserEntity;
import com.example.aicaimp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
    @Autowired
    private RedisTemplateAdapter redisTemplateAdapter;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "根据ID查询用户信息")
    @GetMapping("/getById")
    public Result<UserEntity> getById(){
        UserEntity userEntity = userService.getById(1);
        return Result.success(userEntity);
    }

    @ApiOperation(value = "根据ID查询用户信息")
    @GetMapping("/getCacheById")
    public Result<UserEntity> getCacheById(){
        Object o = redisTemplateAdapter.get("user:1");
        if (o != null){
            return Result.success(JSONObject.parseObject(o.toString(),UserEntity.class));
        }else {
            UserEntity userEntity = userService.getById(1);
            if (!ValidationUtil.isEmpty(userEntity)){
                redisTemplateAdapter.set("user:1",JSONObject.toJSONString(userEntity));
            }
            return Result.success(userEntity);
        }
    }

}

