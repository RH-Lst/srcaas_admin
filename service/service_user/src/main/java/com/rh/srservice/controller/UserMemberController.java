package com.rh.srservice.controller;


import com.rh.commonutils.R;
import com.rh.servicebase.ExceptionHandler.MyException;
import com.rh.srservice.entity.UserMember;
import com.rh.srservice.service.UserMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RH
 * @since 2022-04-01
 */
@Api(description = "用户管理")
@RestController
@RequestMapping("/srservice/user-member")
@CrossOrigin
public class UserMemberController {

    @Autowired
    private UserMemberService userMemberService;

    //新增用户
    //TODO

    @ApiOperation(value = "新增用户")
    @PostMapping("adduser")
    public R adduser(@RequestBody UserMember userMember){

        boolean result = userMemberService.adduser(userMember);

        if (result){
            return R.ok();
        }else {
            return R.error();
        }
    }


    //查询用户
    //TODO

    //分页查询用户
    //TODO

    //条件查询用户
    //TODO

    //修改用户
    //TODO

    //删除用户
    //TODO
}

