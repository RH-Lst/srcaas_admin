package com.rh.srservice.service;

import com.rh.srservice.entity.UserMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.srservice.entity.vo.LoginVo;
import com.rh.srservice.entity.vo.RegisterVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author RH
 * @since 2022-04-01
 */
public interface UserMemberService extends IService<UserMember> {

    boolean adduser(UserMember userMember);

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);
}
