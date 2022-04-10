package com.rh.calservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.rh.calservice.entity.excel.WaveX;
import com.rh.calservice.service.CalculationService;
import com.rh.servicebase.ExceptionHandler.MyException;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener extends AnalysisEventListener<WaveX> {

    private List<WaveX> waveXList = new ArrayList<>();

    public CalculationService calculationService;

    public ExcelListener(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    public ExcelListener() {}

    @Override
    public void invoke(WaveX waveX, AnalysisContext analysisContext) {
        if(waveX == null) {
            throw new MyException(20001,"文件数据为空");
        }
        waveXList.add(waveX);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //System.out.println(waveXList);
    }

    public List<WaveX> getDatas() {
        return waveXList;
    }

    public void setDatas(List<WaveX> waveXList) {
        this.waveXList = waveXList;
    }

}
