package com.rh.srservice.controller;

import com.rh.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("srservice/user")
@CrossOrigin
public class UserLoginController {

    /**
     * 登录
     * @return
     */
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","admin").data("name","admin").data("avatar","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201606%2F11%2F20160611144035_vcL3u.thumb.400_0.gif&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1647345104&t=23d4e228e2fd19a93da2b4bd22151b5c");
    }


}
