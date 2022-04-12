package com.rh.calservice.controller;


import com.rh.calservice.entity.vo.CalVo;
import com.rh.calservice.entity.vo.inversionVo;
import com.rh.calservice.service.CalculationService;
import com.rh.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    //其他条件给定，计算天顶角0°-360°相对方位角°（每隔45°）
    @PostMapping("calSnowRLimitzaim")
    public R calSnowRLimitzaim(@RequestBody CalVo calVo){

        Map<String,Object> Rmap = calculationService.calSnowRLimitzaim(calVo);

        return R.ok().data("Rmap",Rmap);
    }

    //其他条件给定，计算雪粒径100μm，200μm，500μm，1000μm，2000μm
    @PostMapping("calSnowRLimitd")
    public R calSnowRLimitd(@RequestBody CalVo calVo){

        Map<String,Object> Rmap = calculationService.calSnowRLimitd(calVo);

        return R.ok().data("Rmap",Rmap);
    }

    //其他条件给定，计算污染物浓度0ppb，100ppb，1000ppb，3000ppb
    @PostMapping("calSnowRLimitcst")
    public R calSnowRLimitcst(@RequestBody CalVo calVo){

        Map<String,Object> Rmap = calculationService.calSnowRLimitcst(calVo);

        return R.ok().data("Rmap",Rmap);
    }

    //已知其他条件，反演雪粒径
    @PostMapping("inversiond")
    public R inversiond(@RequestBody inversionVo inversionVo){

        Double d = calculationService.inversiond(inversionVo);

        return R.ok().data("d",d);
    }

    //已知其他条件，反演污染物浓度
    @PostMapping("inversioncst")
    public R inversioncst(@RequestBody inversionVo inversionVo){

        Double cst = calculationService.inversioncst(inversionVo);

        return R.ok().data("cst",cst);
    }

    //校验误差
    @PostMapping("check")
    public R check(@RequestBody CalVo calVo){

        List<Double> RList = calculationService.check(calVo);
        Double sumerror = 0.0;
        for (int i=0 ;i<=RList.size()-1;i++){
            sumerror += RList.get(i);
        }
        sumerror = (sumerror/6) *100;
        String str = sumerror+"%";

        return R.ok().data("RList",RList).data("error",str);
    }
}

