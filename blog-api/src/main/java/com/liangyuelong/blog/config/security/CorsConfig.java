package com.liangyuelong.blog.config.security;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Spring Security 跨域配置
 *
 * @author yuelong.liang
 */
@Configuration
@ConfigurationProperties(prefix = "cors")
@Slf4j
@Data
public class CorsConfig {

    private List<String> headers;

    private List<String> origins;

    private List<String> methods;


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        log.info("cors origins: " + origins);
        for (String origin : origins) {
            log.info(origin);
        }
        if (!StringUtils.isEmpty(origins)) {
            // 允许携带 cookie
            corsConfiguration.setAllowCredentials(Boolean.TRUE);
            corsConfiguration.setAllowedMethods(methods);
            // 允许的跨域源，要看请求端 request 的 origin
            // 一般为 协议://域[:端口]
            corsConfiguration.setAllowedOrigins(origins);
            corsConfiguration.setAllowedHeaders(headers);
            // 有效时间
            corsConfiguration.setMaxAge(3600L);
        }
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
