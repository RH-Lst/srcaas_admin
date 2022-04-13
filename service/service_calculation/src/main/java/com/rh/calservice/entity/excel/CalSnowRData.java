package com.rh.calservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CalSnowRData {
    @ExcelProperty(value = "波长(nm)", index = 0)
    private Double wave;
    @ExcelProperty(value = "", index = 1)
    private Double r;
}
