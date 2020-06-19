package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.UmsMemberMapper;
import org.csu.ouostore.model.bo.MemberDetails;
import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.entity.UmsMember;
import org.csu.ouostore.model.entity.UmsMemberLoginLog;
import org.csu.ouostore.model.query.UmsMemberCompleteParam;
import org.csu.ouostore.security.util.JwtUtil;
import org.csu.ouostore.service.RedisService;
import org.csu.ouostore.service.UmsMemberLoginLogService;
import org.csu.ouostore.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 *
 * 会员表 服务实现类
 *
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
    @Autowired
    RedisService redisService;
    @Value("${redis.key.digits}")
    private String REDIS_KEY_DIGITS;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsMember member = getByUsername(username);
        if (member != null) {
            return new MemberDetails(member);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    public UserDetails loadUserByPhone(String phone) {
        UmsMember member = getByPhone(phone);
        if (member != null) {
            return new MemberDetails(member);
        }
        throw new UsernameNotFoundException("用户不存在");
    }

    public UmsMember getByPhone(String phoneNumber){
        List<UmsMember> members = this.list(new QueryWrapper<UmsMember>().eq("phone",phoneNumber));
        if (CollectionUtil.isNotEmpty(members)) {
            return members.get(0);
        }
        return null;
    }

    @Override
    public UmsMember getByUsername(String username) {
        return this.getOne(new QueryWrapper<UmsMember>().eq("username", username).last("limit 1"));
    }

    @Override
    public JwtDto sign(String telephone, String authCode) {
        JwtDto jwtDto = new JwtDto();
        if (!verifyAuthCode(authCode, telephone)) {
            throw new ApiException("验证码错误");
        }
        try {
            UmsMember member = getByPhone(telephone);
            if (member != null && member.getStatus() == 0) {
                throw new ApiException("用户被禁用");
            }
            //如果是首次登入
            if (member == null) {
                UmsMember umsMember = new UmsMember();
                umsMember.setPhone(telephone);
                //需完善信息
                umsMember.setStatus(2);
                umsMember.setCreateTime(LocalDateTime.now());
                this.save(umsMember);
                member = umsMember;
            }
            UserDetails details = loadUserByPhone(telephone);
            String token = jwtUtil.generateToken(details);
            jwtDto.setAccessToken(token);
            jwtDto.setExpiresIn(jwtUtil.getExpiration());
            jwtDto.setTokenType(jwtUtil.getTokenHead());
            insertLoginLog(details.getUsername());
            //销毁验证码
            delAuthCode(member.getPhone());
        } catch (AuthenticationException e) {
            log.warn("登入异常:{}", e.getMessage());
        }
        return jwtDto;
    }

    @Override
    public boolean patch(UmsMemberCompleteParam param) {
        UmsMember currentMember = getCurrentMember();
        if (currentMember == null) {
            throw new ApiException("请先登入");
        }
        List<UmsMember> existMembers = this.list(new QueryWrapper<UmsMember>().eq("username", param.getUsername()));
        if (!existMembers.isEmpty()) {
            throw new ApiException("用户名重复");
        }
        BeanUtil.copyProperties(param, currentMember);
        currentMember.setStatus(1);
        currentMember.setPassword(passwordEncoder.encode(currentMember.getPassword()));
        this.save(currentMember);
        return true;
    }

    @Override
    public void updatePassword(String telephone, String password, String authCode) {
        UserDetails userDetails = loadUserByPhone(telephone);
        if (!verifyAuthCode(authCode, telephone)) {
            System.out.println("验证码错误");
            throw new BadCredentialsException("验证码错误");
        }
        UmsMember member = getByUsername(userDetails.getUsername());
        member.setPassword(passwordEncoder.encode(password));
        memberMapper.updateById(member);
    }

    /**
     * 检验验证码是否有效和正确
     */
    private boolean verifyAuthCode(String authCode, String phone) {
        String code = (String) redisService.get(REDIS_KEY_DIGITS + ":" + phone);
        if (code == null) {
            return false;
        }
        return code.equals(authCode);
    }

    /**
     * 从redis中删除验证码
     */
    private void delAuthCode(String phone) {
        redisService.del(REDIS_KEY_DIGITS + ":" + phone);
    }

    @Override
    public UmsMember getById(Long id) {
        return this.getById(id);
    }

    @Override
    public UmsMember getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
        return memberDetails.getUmsMember();
    }

    /**
     * 随机产生六位验证码
     */
    @Override
    public String generateAuthCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 添加登录记录
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
