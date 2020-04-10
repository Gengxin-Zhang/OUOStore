package org.csu.ouostore.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.UmsAdminMapper;
import org.csu.ouostore.mapper.UmsAdminRoleRelationMapper;
import org.csu.ouostore.model.bo.AdminUserDetails;
import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.entity.UmsAdmin;
import org.csu.ouostore.model.entity.UmsAdminLoginLog;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.security.util.JwtUtil;
import org.csu.ouostore.service.UmsAdminLoginLogService;
import org.csu.ouostore.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
@Slf4j
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UmsAdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    UmsAdminLoginLogService adminLoginLogService;
    @Value("${jwt.expiration}")
    private Long expiration;

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        return adminRoleRelationMapper.getResourceList(adminId);
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdmin query = new UmsAdmin();
        query.setUsername(username);
        List<UmsAdmin> admins = this.list(new QueryWrapper<>(query));
        if (CollectionUtil.isNotEmpty(admins)) {
            return admins.get(0);
        }
        return null;
    }

    @Override
    public UserDetails loadUserDetailsByUsername(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (ObjectUtil.isNotNull(admin)) {
            List<UmsResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public JwtDto signIn(String username, String password) {
        JwtDto jwtDto = new JwtDto();
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserDetailsByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtil.generateToken(userDetails);
            jwtDto.setAccessToken(token);
            jwtDto.setExpiresIn(jwtUtil.getExpiration());
            jwtDto.setTokenType(jwtUtil.getTokenHead());
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return jwtDto;
    }

    @Override
    public JwtDto signUp(String username, String password) {
        List<UmsAdmin> umsAdmins = this.list(new QueryWrapper<UmsAdmin>().eq("username", username));
        if (umsAdmins.size() > 0) { //已有同名用户
            throw new ApiException("用户名重复");
        }
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setUsername(username);
        umsAdmin.setCreateTime(LocalDateTime.now());
        umsAdmin.setStatus(1);
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(password);
        umsAdmin.setPassword(encodePassword);
        this.save(umsAdmin);
        return signIn(username, password);
    }

    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (admin == null)
            return;
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(LocalDateTime.now());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        adminLoginLogService.save(loginLog);
    }
}
