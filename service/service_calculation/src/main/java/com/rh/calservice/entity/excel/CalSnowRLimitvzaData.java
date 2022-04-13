package com.rh.calservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CalSnowRLimitvzaData {
    @ExcelProperty(value = "波长(nm)", index = 0)
    private Double wave;
    @ExcelProperty(value = "θv=0°", index = 1)
    private Double θv_0;
    @ExcelProperty(value = "θv=10°", index = 2)
    private Double θv_10;
    @ExcelProperty(value = "θv=20°", index = 3)
    private Double θv_20;
    @ExcelProperty(value = "θv=30°", index = 4)
    private Double θv_30;
    @ExcelProperty(value = "θv=40°", index = 5)
    private Double θv_40;
    @ExcelProperty(value = "θv=50°", index = 6)
    private Double θv_50;
    @ExcelProperty(value = "θv=60°", index = 7)
    private Double θv_60;
    @ExcelProperty(value = "θv=70°", index = 8)
    private Double θv_70;
    @ExcelProperty(value = "θv=80°", index = 9)
    private Double θv_80;
    @ExcelProperty(value = "θv=90°", index = 10)
    private Double θv_90;
}
