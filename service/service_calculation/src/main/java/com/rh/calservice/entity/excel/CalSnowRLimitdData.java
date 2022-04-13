package com.rh.calservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CalSnowRLimitdData {
    @ExcelProperty(value = "波长(nm)", index = 0)
    private Double wave;
    @ExcelProperty(value = "d=100", index = 1)
    private Double d_100;
    @ExcelProperty(value = "d=200", index = 2)
    private Double d_200;
    @ExcelProperty(value = "d=500", index = 3)
    private Double d_500;
    @ExcelProperty(value = "d=1000", index = 4)
    private Double d_1000;
    @ExcelProperty(value = "d=2000", index = 5)
    private Double d_2000;
}
