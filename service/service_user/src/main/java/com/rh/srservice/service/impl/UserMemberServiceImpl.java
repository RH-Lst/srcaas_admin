package com.rh.srservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rh.commonutils.JwtUtils;
import com.rh.commonutils.MD5;
import com.rh.servicebase.ExceptionHandler.MyException;
import com.rh.srservice.entity.UserMember;
import com.rh.srservice.entity.vo.LoginVo;
import com.rh.srservice.entity.vo.RegisterVo;
import com.rh.srservice.mapper.UserMemberMapper;
import com.rh.srservice.service.UserMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author RH
 * @since 2022-04-01
 */
@Service
public class UserMemberServiceImpl extends ServiceImpl<UserMemberMapper, UserMember> implements UserMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean adduser(UserMember userMember) {

        int i;

        if (userMember == null){
            throw new MyException(20001,"新建用户不能为空");
        }else {
            i = baseMapper.insert(userMember);
        }

        if (i == 0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //判断手机密码不为空
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new MyException(20001,"error");
        }

        //获取用户
        UserMember userMember = baseMapper.selectOne(new QueryWrapper<UserMember>().eq("mobile", mobile));
        if(null == userMember) {
            throw new MyException(20001,"error");
        }

        //校验密码
        if(!MD5.encrypt(password).equals(userMember.getPassword())) {
            throw new MyException(20001,"error");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(userMember.getId(), userMember.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new MyException(20001,"error");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobleCode)) {
            throw new MyException(20001,"error");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UserMember>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new MyException(20001,"error");
        }

        //添加注册信息到数据库
        UserMember userMember = new UserMember();
        userMember.setNickname(nickname);
        userMember.setMobile(mobile);
        userMember.setPassword(MD5.encrypt(password));
        userMember.setAvatar("https://edu-guli-img0216.oss-cn-beijing.aliyuncs.com/UserDefaultImg/5B3368070F8BCF3A45CE1E8D0BE1BEEC.gif");

        baseMapper.insert(userMember);
    }
}
