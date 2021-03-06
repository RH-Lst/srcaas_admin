package com.rh.calservice.service;

import com.rh.calservice.entity.Calculation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.calservice.entity.vo.CalVo;
import com.rh.calservice.entity.vo.inversionVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author RH
 * @since 2022-04-08
 */
public interface CalculationService extends IService<Calculation> {

    void getexceldata(MultipartFile file);

    Map<String, Object> calSnowR(CalVo calVo);

    Map<String, Object> calSnowRLimitvza(CalVo calVo);

    Map<String, Object> calSnowRLimitzaim(CalVo calVo);

    Map<String, Object> calSnowRLimitd(CalVo calVo);

    Map<String, Object> calSnowRLimitcst(CalVo calVo);

    Double inversiond(inversionVo calVo);

    Double inversioncst(inversionVo calVo);

    List<Double> check(CalVo calVo);
}
