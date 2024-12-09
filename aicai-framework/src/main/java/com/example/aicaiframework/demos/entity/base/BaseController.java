package com.example.aicaiframework.demos.entity.base;



import com.whzp.framework.redis.RedisTemplateAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;


/**
 * 基础类
 * @author 小鱼
 */
@Slf4j
public class BaseController {

    protected HttpServletRequest req;

    protected HttpServletResponse resp;

    @Resource(name = "redisTemplateAdapter")
    public RedisTemplateAdapter<String> redisTemplate;


    @ModelAttribute
    protected void setHttpObjects(HttpServletRequest request, HttpServletResponse response) {
        this.req = request;
        this.resp = response;
    }

    /**
     * 设置 HttpServletResponse 的返回Json
     * 401	Unauthorized	所请求的页面需要用户名和密码。
     * 400	SC_BAD_REQUEST	服务器不理解请求。
     * @param response
     * @param msg
     */
    public static void result(HttpServletResponse response,String code, String msg) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("msg", URLEncoder.encode(msg, "UTF-8"));
            response.getOutputStream().write(String.format("{\"code\":%d,\"msg\":\"%s\"}", code, msg).getBytes("UTF-8"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }



}
