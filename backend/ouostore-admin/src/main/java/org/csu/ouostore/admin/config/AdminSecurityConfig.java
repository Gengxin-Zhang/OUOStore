package org.csu.ouostore.admin.config;

import cn.hutool.core.util.StrUtil;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.security.component.DynamicSecurityService;
import org.csu.ouostore.security.config.SecurityConfig;
import org.csu.ouostore.service.UmsAdminService;
import org.csu.ouostore.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 拓展securityConfig模块相关配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends SecurityConfig {

    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsResourceService resourceService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserDetailsByUsername(username);
    }

    /**
     * 用于加载admin系统的所有资源
     */
    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> resourceList = resourceService.listAll();
                for (UmsResource resource : resourceList) {
                    if (StrUtil.isNotEmpty(resource.getUrl())) {
                        map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                    }
                }
                return map;
            }
        };
    }
}
