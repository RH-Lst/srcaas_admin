package com.rh.calservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CalVo {

    @ApiModelProperty(value = "太阳天顶角")
    private Double sza;

    @ApiModelProperty(value = "观测天顶角")
    private Double vza;

    @ApiModelProperty(value = "相对方位角")
    private Double azim;

    @ApiModelProperty(value = "雪粒径")
    private Double dSnow;

    @ApiModelProperty(value = "污染物浓度")
    private Double cst;

    @ApiModelProperty(value = "雪粒径形状对应参数，5.10 分型雪粒形状， 6.50 球形雪粒形状")
    private Double shape;

}
