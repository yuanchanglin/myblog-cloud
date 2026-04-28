package com.yuanchanglin.articleservice.feign.factory;

import com.yuanchanglin.articleservice.feign.UserServiceFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserServiceFeignClient> {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceFallbackFactory.class);

    @Override
    public UserServiceFeignClient create(Throwable cause) {
        // 记录熔断触发的原因
        logger.error("Feign client fallback for user-service, reason: ", cause);
        return new UserServiceFeignClient() {


            @Override
            public String t1() {
                logger.error("调用 user-service 的 t1 接口失败, 错误信息: {}",  cause.getMessage(), cause);
                // 返回降级后的默认值
                return "{\"error\": \"服务暂时不可用，请稍后再试\"}";
            }

            @Override
            public String p1(String s) {
                logger.error("调用 user-service 的 p1 接口失败, 错误信息: {}",  cause.getMessage(), cause);
                // 返回降级后的默认值
                return "{\"error\": \"服务暂时不可用，请稍后再试\"}";
            }

            @Override
            public String error() {
                logger.error("调用 user-service 的 error 接口失败, 错误信息: {}",  cause.getMessage(), cause);
                // 返回降级后的默认值
                return "{\"error\": \"服务暂时不可用，请稍后再试\"}";
            }

        };
    }
}
