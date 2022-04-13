package com.rh.calservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CalSnowRLimitzaimData {
    @ExcelProperty(value = "波长(nm)", index = 0)
    private Double wave;
    @ExcelProperty(value = "θψ=0°", index = 1)
    private Double θψ_0;
    @ExcelProperty(value = "θψ=45°", index = 2)
    private Double θψ_45;
    @ExcelProperty(value = "θψ=90°", index = 3)
    private Double θψ_90;
    @ExcelProperty(value = "θψ=135°", index = 4)
    private Double θψ_135;
    @ExcelProperty(value = "θψ=180°", index = 5)
    private Double θψ_180;
    @ExcelProperty(value = "θψ=225°", index = 6)
    private Double θψ_225;
    @ExcelProperty(value = "θψ=270°", index = 7)
    private Double θψ_270;
    @ExcelProperty(value = "θψ=315°", index = 8)
    private Double θψ_315;
    @ExcelProperty(value = "θψ=360°", index = 9)
    private Double θψ_360;
}
