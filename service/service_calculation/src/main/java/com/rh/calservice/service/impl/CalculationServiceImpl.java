package com.rh.calservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.rh.calservice.entity.Calculation;
import com.rh.calservice.entity.excel.WaveX;
import com.rh.calservice.entity.vo.CalVo;
import com.rh.calservice.listener.ExcelListener;
import com.rh.calservice.mapper.CalculationMapper;
import com.rh.calservice.service.CalculationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.servicebase.ExceptionHandler.MyException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author RH
 * @since 2022-04-08
 */
@Service
public class CalculationServiceImpl extends ServiceImpl<CalculationMapper, Calculation> implements CalculationService {

    @Override
    public void getexceldata(MultipartFile file) {
        try {
            InputStream in = file.getInputStream();
            ExcelListener listener = new ExcelListener(this);
            EasyExcel.read(in, WaveX.class,listener).sheet().doRead();
            List<WaveX> waveXList = listener.getDatas();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //根据条件，计算积雪反射率
    @Override
    public Map<String, Object> calSnowR(CalVo calVo) {

        Map<String, Object> resultmap = new HashMap<>();

        try{
            //读取波段和对应波段的冰的复折射率的虚部
            File file = new File("D:/Java_workspace/srcaas_admin/service/service_user/src/main/resources/excel/x01.xlsx");
            InputStream in = new FileInputStream(file);
            ExcelListener listener = new ExcelListener(this);
            EasyExcel.read(in, WaveX.class,listener).sheet().doRead();
            //获取返回值
            List<WaveX> waveXList = listener.getDatas();
            //定义返回数据的map
            List<Double> wavedata = new ArrayList<>();
            List<Double> rdata = new ArrayList<>();
            for (WaveX waveX : waveXList) {
                //添加波长nm
                wavedata.add(waveX.getWave());
                //添加反射率
                rdata.add(CalR(calVo,waveX));
            }
            resultmap.put("wave",wavedata);
            resultmap.put("reflectivity",rdata);
        }catch (Exception e){
            //e.printStackTrace();
            throw new MyException(20001,"计算积雪反射率出错");
        }
        System.out.println(resultmap);
        return resultmap;
    }

    //具体计算方法
    private Double CalR(CalVo calVo,WaveX waveX){
        Double R,R0,α,F,γ,aef,P;//定义R积雪反射率，R0弱吸收表面双向反射因子
        Double θs,θv,ψ,d,Cst; //定义θs太阳天顶角，θv观测天顶角，ψ相对方位角，雪粒径，污染物浓度
        Double Ks,Kv;//F参数
        Double λ,χ; //定义波长，冰的复折射率的虚部
        Double Pai = Math.PI/180; //角度转弧度参数
        Double A=1.247,B=1.186,C=5.517; //R0常数项
        Double Ashape;//雪粒径形状对应参数，5.10 分型雪粒形状， 6.50 球形雪粒形状

        θs = calVo.getSza() * Pai;
        θv = calVo.getVza() * Pai;
        ψ = calVo.getAzim() * Pai;
        d = calVo.getDSnow();
        Cst = calVo.getCst();
        Ashape = calVo.getShape();
        λ = waveX.getWave()/1000; //传入的值是nm，计算按μm进行
        χ = waveX.getXnum();


        Double cos_s = Math.cos(θs); //cosθs
        Double cos_v = Math.cos(θv); //cosθv
        Double sin_s = Math.sin(θs); //sinθs
        Double sin_v = Math.sin(θv); //sinθv
        Double sin_s2 = Math.pow(sin_s,2);
        Double sin_v2 = Math.pow(sin_v,2);
        Double cos_ψ = Math.cos(ψ); //cosψ
        Double cos_sv = cos_s*cos_v; //cosθs * cosθv
        Double sqrt_sv = Math.sqrt(sin_s2*sin_v2);//计算根号下1-cos_s方*根号下1-cos_v方

        /**开始计算R0**/
        P = Math.acos(-cos_sv + sqrt_sv * cos_ψ);
        R0 = (A+B*(cos_s+cos_v)+C*cos_s*cos_v+P)/(4*(cos_s+cos_v));

        /**开始计算F**/
        Ks = 3 * (1+2*cos_v) /7;
        Kv = 3 * (1+2*cos_s) /7;
        F = Ks * Kv / R0;

        /**开始计算α**/
        γ = (4 *Math.PI * (χ+Cst))/λ;
        aef =  13 * d;
        α = Ashape * Math.sqrt(γ * aef);
        R = R0 * (1/Math.pow(Math.E,α*F));

        return R;
    }
}
