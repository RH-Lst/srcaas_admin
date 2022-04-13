package com.rh.msm.controller;

import com.rh.commonutils.R;
import com.rh.msm.service.MsmService;
import com.rh.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("servicemsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("sendmsg/{phonenum}")
    public R sendmsg(@PathVariable String phonenum){

        String code = redisTemplate.opsForValue().get(phonenum);
        if(!StringUtils.isEmpty(code)) {return R.ok().message("请5分钟后再尝试");}

        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);

        boolean isSend = msmService.sendmsg(phonenum, "a09602b817fd47e59e7c6e603d3f088d", param);


        if(isSend) {
            redisTemplate.opsForValue().set(phonenum, code,5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("发送短信失败");
        }

    }


}
