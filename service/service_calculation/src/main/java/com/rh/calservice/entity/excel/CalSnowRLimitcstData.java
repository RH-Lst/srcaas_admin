package com.rh.calservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CalSnowRLimitcstData {
    @ExcelProperty(value = "波长(nm)", index = 0)
    private Double wave;
    @ExcelProperty(value = "cst=0", index = 1)
    private Double cst_0;
    @ExcelProperty(value = "cst=100", index = 2)
    private Double cst_100;
    @ExcelProperty(value = "cst=1000", index = 3)
    private Double cst_1000;
    @ExcelProperty(value = "cst=3000", index = 4)
    private Double cst_3000;

}
