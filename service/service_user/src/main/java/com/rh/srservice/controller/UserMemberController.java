package com.rh.srservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.commonutils.JwtUtils;
import com.rh.commonutils.R;
import com.rh.commonutils.vo.UserMemberVo;
import com.rh.servicebase.ExceptionHandler.MyException;
import com.rh.srservice.entity.UserMember;
import com.rh.srservice.entity.vo.LoginVo;
import com.rh.srservice.entity.vo.RegisterVo;
import com.rh.srservice.service.UserMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @ApiOperation(value = "查询所有用户")
    @GetMapping("findAll")
    public R findAll(){

        List userMemberList = userMemberService.list(null);

        if (userMemberList == null){
            return R.error();
        }else {
            return R.ok().data("userMemberList",userMemberList);
        }
    }

    //分页查询用户
    @ApiOperation(value = "分页查询所有用户")
    @GetMapping("pageUser/{current}/{limit}")
    public R pageUser(@PathVariable long current,
                      @PathVariable long limit){

        Page<UserMember> page = new Page<>(current,limit);
        userMemberService.page(page,null);
        long total = page.getTotal();
        List<UserMember> recods = page.getRecords();
        return R.ok().data("total",total).data("recods",recods);
    }

    //根据ID查询用户
    @ApiOperation(value = "根据ID查询所有用户")
    @GetMapping("getUserById/{id}")
    public R getUserById(@PathVariable String id){

        UserMember member = userMemberService.getById(id);

        return R.ok().data("member",member);
    }

    //根据ID修改用户
    @ApiOperation(value = "根据ID修改用户所有用户")
    @PostMapping("updateUserById")
    public R updateUserById(@RequestBody UserMember userMember){

        boolean flag = userMemberService.updateById(userMember);

        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }

    //逻辑删除用户
    @ApiOperation(value = "删除用户")
    @DeleteMapping("deleteUser/{id}")
    public R deleteUser(@PathVariable String id){

        boolean flag = userMemberService.removeById(id);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //用户登录
    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = userMemberService.login(loginVo);
        return R.ok().data("token", token);
    }

    //用户注册
    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        userMemberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            UserMember userMember = userMemberService.getById(memberId);
            return R.ok().data("userinfo", userMember);
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(20001,"error");
        }
    }

    //根据token字符串获取用户信息
    @GetMapping("getInfoUc/{id}")
    public UserMemberVo getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        UserMember userMember = userMemberService.getById(id);
        UserMemberVo memeber = new UserMemberVo();
        BeanUtils.copyProperties(userMember,memeber);
        return memeber;
    }
}

