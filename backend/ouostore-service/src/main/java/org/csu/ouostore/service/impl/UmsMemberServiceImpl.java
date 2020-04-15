package org.csu.ouostore.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.model.bo.MemberDetails;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
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
    @Autowired
    UmsMemberMapper memberMapper;


    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsMember member = getByUsername(username);
        if(member!=null){
            return new MemberDetails(member);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    public UmsMember getByPhone(String phoneNumber){
        UmsMember member = new UmsMember();
        member.setPhone(phoneNumber);
        List<UmsMember> members = this.list(new QueryWrapper<UmsMember>().eq("phone",phoneNumber));
        if (CollectionUtil.isNotEmpty(members)) {
            return members.get(0);
        }
        return null;
    }

    public UmsMember getByUsername(String username) {
        UmsMember member = new UmsMember();
        member.setUsername(username);
        List<UmsMember> members = this.list(new QueryWrapper<>(member));
        if (CollectionUtil.isNotEmpty(members)) {
            return members.get(0);
        }
        return null;
    }

    @Override
    public JwtDto signIn(String username, String password){
        JwtDto jwtDto = new JwtDto();
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
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
    public JwtDto SignUp(String username, String password, String telephone, String authCode) {
        JwtDto jwtDto = new JwtDto();
        //验证验证码
        if(!verifyAuthCode(authCode,telephone)){
//            throw new BadCredentialsException("验证码错误");
            System.out.println("验证码错误");
        }
        if (ObjectUtil.isNotNull(getByPhone(telephone))){
//            throw new BadCredentialsException("验证码错误");
            System.out.println("注册手机号已存在");
        }
        if (ObjectUtil.isNotNull(getByUsername(username))){
            System.out.println("用户名已存在");
        }
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(username);
        umsMember.setPhone(telephone);
        umsMember.setPassword(passwordEncoder.encode(password));
        umsMember.setStatus(1);
//        umsMember.setCreateTime(new LocalDateTime());//todo:注册时间

        memberMapper.insert(umsMember);
        return jwtDto;
    }

    @Override
    public UmsMember getById(Long id) {
        return this.getById(id);
    }

    @Override
    public void updatePassword(String telephone, String password, String authCode) {

    }



    @Override
    public UmsMember getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
        return memberDetails.getUmsMember();
    }

    //对输入的验证码进行校验
    private boolean verifyAuthCode(String authCode, String telephone){
        if(StringUtils.isEmpty(authCode)){
            return false;
        }
        //todo:验证码校验
        return false;
    }

    @Override
    public String generateAuthCode(String telephone) {
        return null;
    }

    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsMember member = getByUsername(username);
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
