package org.csu.ouostore.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.entity.UmsAdmin;
import org.csu.ouostore.model.entity.UmsAdminLoginLog;
import org.csu.ouostore.model.entity.UmsMember;
import org.csu.ouostore.mapper.UmsMemberMapper;
import org.csu.ouostore.model.entity.UmsMemberLoginLog;
import org.csu.ouostore.security.util.JwtUtil;
import org.csu.ouostore.service.UmsMemberLoginLogService;
import org.csu.ouostore.service.UmsMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
@Slf4j
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UmsMemberLoginLogService umsMemberLoginLogService;

    public UmsMember getMemberByUsername(String username) {
        UmsMember member = new UmsMember();
        member.setUsername(username);
        List<UmsMember> members = this.list(new QueryWrapper<>(member));
        if (CollectionUtil.isNotEmpty(members)) {
            return members.get(0);
        }
        return null;
    }//todo:3

    @Override
    public JwtDto signIn(String username, String password){
        JwtDto jwtDto = new JwtDto();
        UmsMember member = getMemberByUsername(username);
        if (ObjectUtil.isNotNull(member)) {
            if (!passwordEncoder.matches(password, member.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            insertLoginLog(username);
        }
        return jwtDto;
    }

    @Override
    public JwtDto signUp(String username, String password){
        List<UmsMember> umsMembers = this.list(new QueryWrapper<UmsMember>().eq("username", username));
        if (umsMembers.size() > 0) { //已有同名用户
            throw new ApiException("用户名重复");
        }
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(username);
        umsMember.setCreateTime(LocalDateTime.now());
//        umsMember.setStatus(1);
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(password);
        umsMember.setPassword(encodePassword);
        this.save(umsMember);
        return signIn(username, password);
    }

    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsMember member = getMemberByUsername(username);
        if (member == null)
            return;
        UmsMemberLoginLog loginLog = new UmsMemberLoginLog();
        loginLog.setMemberId(member.getId());
        loginLog.setCreateTime(LocalDateTime.now());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        umsMemberLoginLogService.save(loginLog);
    }
}
