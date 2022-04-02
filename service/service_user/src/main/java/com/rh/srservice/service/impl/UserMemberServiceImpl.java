package com.rh.srservice.service.impl;

import com.rh.servicebase.ExceptionHandler.MyException;
import com.rh.srservice.entity.UserMember;
import com.rh.srservice.mapper.UserMemberMapper;
import com.rh.srservice.service.UserMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
