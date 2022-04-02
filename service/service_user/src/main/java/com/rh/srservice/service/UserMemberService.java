package com.rh.srservice.service;

import com.rh.srservice.entity.UserMember;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
