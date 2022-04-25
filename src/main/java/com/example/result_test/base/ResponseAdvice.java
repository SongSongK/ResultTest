package com.example.result_test.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import springfox.documentation.spring.web.json.Json;

/**
 * 拦截Controller方法的返回值，统一处理返回值/响应体
 * @author kss
 * @date 2022/04/25 11:58
 **/
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        String name = methodParameter.getMethod().getName();
        //swagger方法放行，返回值不进行封装
        if (name.equals("swaggerResources") || name.equals("getDocumentation")){
            return false;
        }
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(o instanceof String){
//            serverHttpResponse.getHeaders().set("Content-Type","application/json;charset=UTF-8");
            return objectMapper.writeValueAsString(ResultData.success(o));
        }
        if(o instanceof ResultData){
            return o;
        }
        //不对Swagger的api-docs接口做封装
        if (o instanceof Json && ((Json) o).value().contains("swagger")){
            return o;
        }
        return ResultData.success(o);
    }
}