package com.rh.calservice.controller;


import com.rh.calservice.entity.vo.CalVo;
import com.rh.calservice.service.CalculationService;
import com.rh.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RH
 * @since 2022-04-08
 */
@RestController
@RequestMapping("/calservice/calculation")
@CrossOrigin
public class CalculationController {

    @Autowired
    private CalculationService calculationService;

    //测试读取excel
    @PostMapping("getexceldata")
    public R getexceldata(MultipartFile file){
        calculationService.getexceldata(file);
        return R.ok();
    }

    //根据条件计算积雪反射率，插入数据库
    @PostMapping("calSnowR")
    public R calSnowR(@RequestBody CalVo calVo){

        Map<String,Object> Rmap = calculationService.calSnowR(calVo);

        return R.ok().data("Rmap",Rmap);
    }

    //其他条件给定，计算观测天顶角0°-90°（每隔10°）
    @PostMapping("calSnowRLimitvza")
    public R calSnowRLimitvza(@RequestBody CalVo calVo){

        Map<String,Object> Rmap = calculationService.calSnowRLimitvza(calVo);

        return R.ok().data("Rmap",Rmap);
    }

}

