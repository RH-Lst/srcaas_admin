package com.rh.calservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class WaveX {

    //属性对应表头
    @ExcelProperty(index = 0)
    private double wave;
    @ExcelProperty(index = 1)
    private double xnum;

}
