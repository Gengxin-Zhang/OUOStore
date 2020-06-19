package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.UmsAdminMapper;
import org.csu.ouostore.mapper.UmsAdminRoleRelationMapper;
import org.csu.ouostore.model.bo.AdminUserDetails;
import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.entity.*;
import org.csu.ouostore.model.query.UmsAdminSearchParam;
import org.csu.ouostore.model.query.UmsAdminSignUpParam;
import org.csu.ouostore.model.vo.UmsAdminDetailVo;
import org.csu.ouostore.model.vo.UmsAdminVo;
import org.csu.ouostore.model.vo.UmsMenuNodeVo;
import org.csu.ouostore.security.util.JwtUtil;
import org.csu.ouostore.service.UmsAdminLoginLogService;
import org.csu.ouostore.service.UmsAdminRoleRelationService;
import org.csu.ouostore.service.UmsAdminService;
import org.csu.ouostore.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    UmsAdminMapper adminMapper;
    @Autowired
    UmsAdminLoginLogService adminLoginLogService;
    @Autowired
    UmsAdminService adminService;
    @Autowired
    UmsAdminRoleRelationService adminRoleRelationService;
    @Autowired
    UmsRoleService roleService;
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
    public JwtDto signUp(UmsAdminSignUpParam adminSignUpParam) {
        UmsAdmin one = this.getOne(new QueryWrapper<UmsAdmin>().eq("username", adminSignUpParam.getUsername()).last("limit 1"));
        if (ObjectUtil.isNotNull(one)) {
            throw new ApiException("用户名重复");
        }
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtil.copyProperties(adminSignUpParam, umsAdmin);
        umsAdmin.setCreateTime(LocalDateTime.now());
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        this.save(umsAdmin);
        return signIn(umsAdmin.getUsername(), umsAdmin.getPassword());
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        adminService.removeById(id);
        adminRoleRelationService.remove(new QueryWrapper<UmsAdminRoleRelation>().eq("admin_id", id));
        return true;
    }

    @Override
    public IPage<UmsAdminVo> selectResourcePage(Page<UmsAdminVo> page, UmsAdminSearchParam adminSearchParam) {
        page.setCurrent(adminSearchParam.getPage());
        page.setSize(adminSearchParam.getPerPage());
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<UmsAdmin>()
                .like(StrUtil.isNotBlank(adminSearchParam.getUserNameKeyword()), "username", adminSearchParam.getUserNameKeyword())
                .like(StrUtil.isNotBlank(adminSearchParam.getNickNameKeyword()), "nick_name", adminSearchParam.getNickNameKeyword())
                .like(StrUtil.isNotBlank(adminSearchParam.getEmailKeyword()), "email", adminSearchParam.getEmailKeyword());
        adminMapper.selectPageVo(page, wrapper);
        return page;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return adminMapper.getRoleList(adminId);
    }

    @Override
    public UmsAdmin getCurrentAdmin() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        try {
            AdminUserDetails adminUserDetails = (AdminUserDetails) auth.getPrincipal();
            return adminUserDetails.getUmsAdmin();
        } catch (ClassCastException ex) {
            throw new ApiException("token无效或过期");
        }
    }

    @Override
    public UmsAdminDetailVo detail() {
        UmsAdmin admin =  adminService.getCurrentAdmin();
        if (admin == null) {
            throw new ApiException("token无效或过期");
        }
        List<UmsAdminRoleRelation> relations = adminRoleRelationService.list(new QueryWrapper<UmsAdminRoleRelation>()
                .eq("admin_id", admin.getId()));
        //角色
        ArrayList<UmsRole> roles = new ArrayList<>();
        //菜单
        ArrayList<UmsMenuNodeVo> menuNodeVos = new ArrayList<>();
        //资源(api)
        ArrayList<UmsResource> resources = new ArrayList<>();
        //查询用户拥有的所有资源
        for (UmsAdminRoleRelation relation : relations) {
            menuNodeVos.addAll(roleService.listMenu(relation.getRoleId()));
            resources.addAll(roleService.listResource(relation.getRoleId()));
            roles.add(roleService.getById(relation.getId()));
        }
        UmsAdminDetailVo vo = BeanUtil.copyProperties(admin, UmsAdminDetailVo.class);
        vo.setRoles(roles);
        vo.setMenus(menuNodeVos);
        vo.setResources(resources);
        return vo;
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
