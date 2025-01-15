package com.example.aicaiorder.service.impl;

import com.example.aicaiorder.entity.OrderEntity;
import com.example.aicaiorder.mapper.OrderMapper;
import com.example.aicaiorder.service.IOrderService;
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
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, OrderEntity> implements IOrderService {

}
