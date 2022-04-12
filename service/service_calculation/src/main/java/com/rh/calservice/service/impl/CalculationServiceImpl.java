package com.rh.calservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.rh.calservice.entity.Calculation;
import com.rh.calservice.entity.excel.WaveX;
import com.rh.calservice.entity.vo.CalVo;
import com.rh.calservice.entity.vo.inversionVo;
import com.rh.calservice.listener.ExcelListener;
import com.rh.calservice.mapper.CalculationMapper;
import com.rh.calservice.service.CalculationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.servicebase.ExceptionHandler.MyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
            File file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/x01.xlsx");
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

    //计算观测天顶角0°-90°积雪反射率（每隔10°）
    @Override
    public Map<String, Object> calSnowRLimitvza(CalVo calVo) {

        Map<String, Object> resultmap = new HashMap<>();
        int limitvza = 0;
        try{
            //读取波段和对应波段的冰的复折射率的虚部
            File file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/x01.xlsx");
            InputStream in = new FileInputStream(file);
            ExcelListener listener = new ExcelListener(this);
            EasyExcel.read(in, WaveX.class,listener).sheet().doRead();
            //获取返回值
            List<WaveX> waveXList = listener.getDatas();
            //定义返回数据的map
            while (limitvza <= 90) {
                //每次循环创建新的集合存放数据，防止数据丢失
                List<Double> wavedata = new ArrayList<>();
                List<Double> rdata = new ArrayList<>();
                //设置观测天顶角
                calVo.setVza(limitvza*1.0);
                for (WaveX waveX : waveXList) {
                    //添加波长nm
                    wavedata.add(waveX.getWave());
                    //添加反射率
                    rdata.add(CalR(calVo,waveX));
                }
                resultmap.put("wave"+limitvza,wavedata);
                resultmap.put("reflectivity"+limitvza,rdata);
                //每隔10°进行计算
                limitvza+=10;
            }

        }catch (Exception e){
            //e.printStackTrace();
            throw new MyException(20001,"计算积雪反射率出错");
        }
        return resultmap;
    }

    //计算相对方位角0°-360°的积雪反射率（每隔45°）
    @Override
    public Map<String, Object> calSnowRLimitzaim(CalVo calVo) {
        Map<String, Object> resultmap = new HashMap<>();
        //设置初始相对方位角为0
        int limitazim = 0;
        try{
            //读取波段和对应波段的冰的复折射率的虚部
            File file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/x01.xlsx");
            InputStream in = new FileInputStream(file);
            ExcelListener listener = new ExcelListener(this);
            EasyExcel.read(in, WaveX.class,listener).sheet().doRead();
            //获取返回值
            List<WaveX> waveXList = listener.getDatas();
            //定义返回数据的map
            while (limitazim <= 360) {
                //每次循环创建新的集合存放数据，防止数据丢失
                List<Double> wavedata = new ArrayList<>();
                List<Double> rdata = new ArrayList<>();
                //设置相对方位角
                calVo.setAzim(limitazim*1.0);
                for (WaveX waveX : waveXList) {
                    //添加波长nm
                    wavedata.add(waveX.getWave());
                    //添加反射率
                    rdata.add(CalR(calVo,waveX));
                }
                resultmap.put("wave"+limitazim,wavedata);
                resultmap.put("reflectivity"+limitazim,rdata);
                //每隔45°进行计算
                limitazim+=45;
            }

        }catch (Exception e){
            e.printStackTrace();
            //throw new MyException(20001,"计算积雪反射率出错");
        }
        return resultmap;
    }

    //计算雪粒径100μm，200μm，500μm，1000μm，2000μm
    @Override
    public Map<String, Object> calSnowRLimitd(CalVo calVo) {
        Map<String, Object> resultmap = new HashMap<>();
        //设置雪粒径范围
        int[] arrd = {100,200,500,1000,2000};
        try{
            //读取波段和对应波段的冰的复折射率的虚部
            File file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/x01.xlsx");
            InputStream in = new FileInputStream(file);
            ExcelListener listener = new ExcelListener(this);
            EasyExcel.read(in, WaveX.class,listener).sheet().doRead();
            //获取返回值
            List<WaveX> waveXList = listener.getDatas();
            //定义返回数据的map
            for (int i = 0; i <=arrd.length-1 ; i++) {
                //每次循环创建新的集合存放数据，防止数据丢失
                List<Double> wavedata = new ArrayList<>();
                List<Double> rdata = new ArrayList<>();
                //设置相对方位角
                calVo.setD(arrd[i]*1.0);
                for (WaveX waveX : waveXList) {
                    //添加波长nm
                    wavedata.add(waveX.getWave());
                    //添加反射率
                    rdata.add(CalR(calVo,waveX));
                }
                resultmap.put("wave"+arrd[i],wavedata);
                resultmap.put("reflectivity"+arrd[i],rdata);
            }

        }catch (Exception e){
            //e.printStackTrace();
            throw new MyException(20001,"计算积雪反射率出错");
        }
        return resultmap;
    }

    //计算污染物浓度0ppb，100ppb，1000ppb，3000ppb
    @Override
    public Map<String, Object> calSnowRLimitcst(CalVo calVo) {
        Map<String, Object> resultmap = new HashMap<>();
        //设置雪粒径范围
        int[] arrcst = {0,100,1000,3000};
        try{
            //读取波段和对应波段的冰的复折射率的虚部
            File file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/x01.xlsx");
            InputStream in = new FileInputStream(file);
            ExcelListener listener = new ExcelListener(this);
            EasyExcel.read(in, WaveX.class,listener).sheet().doRead();
            //获取返回值
            List<WaveX> waveXList = listener.getDatas();
            //定义返回数据的map
            for (int i = 0; i <=arrcst.length-1 ; i++) {
                //每次循环创建新的集合存放数据，防止数据丢失
                List<Double> wavedata = new ArrayList<>();
                List<Double> rdata = new ArrayList<>();
                //设置相对方位角
                calVo.setCst(arrcst[i]*1.0);
                for (WaveX waveX : waveXList) {
                    //添加波长nm
                    wavedata.add(waveX.getWave());
                    //添加反射率
                    rdata.add(CalR(calVo,waveX));
                }
                resultmap.put("wave"+arrcst[i],wavedata);
                resultmap.put("reflectivity"+arrcst[i],rdata);
            }

        }catch (Exception e){
            //e.printStackTrace();
            throw new MyException(20001,"计算积雪反射率出错");
        }
        return resultmap;
    }

    //根据已知条件反演雪粒径
    @Override
    public Double inversiond(inversionVo inversionVo) {

        Double R,R0,α,F,γ,aef,P;//定义R积雪反射率，R0弱吸收表面双向反射因子
        Double θs,θv,ψ,d = 0.0,Cst; //定义θs太阳天顶角，θv观测天顶角，ψ相对方位角，雪粒径，污染物浓度
        Double Ks,Kv;//F参数
        Integer wave;//定义波长
        Double λ,χ; //定义冰的复折射率的虚部
        Double Pai = Math.PI/180; //角度转弧度参数
        Double A=1.247,B=1.186,C=5.517; //R0常数项
        Double Ashape;//雪粒径形状对应参数，5.10 分型雪粒形状， 6.50 球形雪粒形状

        θs = inversionVo.getSza() * Pai;
        θv = inversionVo.getVza() * Pai;
        ψ = inversionVo.getAzim() * Pai;
        //Cst = inversionVo.getCst()/10000000000.0;//单位ppb，数值1ppb = 10^-9
        Ashape = inversionVo.getShape();
        wave = inversionVo.getW();
        λ =wave/1000.0; //传入的值是nm，计算按μm进行
        R = inversionVo.getR();//获取传入的积雪反射率

        try {
            //读取波段和对应波段的冰的复折射率的虚部
            File file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/x01.xlsx");
            InputStream in = new FileInputStream(file);
            ExcelListener listener = new ExcelListener(this);
            EasyExcel.read(in, WaveX.class,listener).sheet().doRead();
            //获取返回值
            List<WaveX> waveXList = listener.getDatas();
            //定义返回数据的list
            List<Double> xdata = new ArrayList<>();
            for (WaveX waveX : waveXList) {
                //添加x
                xdata.add(waveX.getXnum());
            }
            χ = xdata.get(inversionVo.getW()-350);//获取波长对应的虚部，最小波长350nm

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

            Double R0R = R0/R;
            Double t1,t2,t3;//存储log(R0/R)，存储A*F，存储公式第一项
            t1 = Math.log(R0R);
            t2 = 1/(F*Ashape);
            t3 = Math.pow(t1*t2,2);

            d = (t3*λ)/(13 * 4 *Math.PI *χ);


        }catch (Exception e){
            e.printStackTrace();
            //throw new MyException(20001,"反演雪粒径出错");
        }

        return d;
    }

    @Override
    public Double inversioncst(inversionVo inversionVo) {
        Double R,R0,α,F,γ,aef,P;//定义R积雪反射率，R0弱吸收表面双向反射因子
        Double θs,θv,ψ,d = 0.0,Cst = 0.0; //定义θs太阳天顶角，θv观测天顶角，ψ相对方位角，雪粒径，污染物浓度
        Double Ks,Kv;//F参数
        Integer wave;//定义波长
        Double λ,χ; //定义冰的复折射率的虚部
        Double Pai = Math.PI/180; //角度转弧度参数
        Double A=1.247,B=1.186,C=5.517; //R0常数项
        Double Ashape;//雪粒径形状对应参数，5.10 分型雪粒形状， 6.50 球形雪粒形状

        θs = inversionVo.getSza() * Pai;
        θv = inversionVo.getVza() * Pai;
        ψ = inversionVo.getAzim() * Pai;
        Ashape = inversionVo.getShape();
        wave = inversionVo.getW();
        λ =wave/1000.0; //传入的值是nm，计算按μm进行
        R = inversionVo.getR();//获取传入的积雪反射率
        d = inversionVo.getD();//获取传入的雪粒径大小

        try {
            //读取波段和对应波段的冰的复折射率的虚部
            File file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/x01.xlsx");
            InputStream in = new FileInputStream(file);
            ExcelListener listener = new ExcelListener(this);
            EasyExcel.read(in, WaveX.class,listener).sheet().doRead();
            //获取返回值
            List<WaveX> waveXList = listener.getDatas();
            //定义返回数据的list
            List<Double> xdata = new ArrayList<>();
            for (WaveX waveX : waveXList) {
                //添加x
                xdata.add(waveX.getXnum());
            }
            χ = xdata.get(inversionVo.getW()-350);//获取波长对应的虚部，最小波长350nm

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

            Double R0R = R0/R;
            Double t1,t2,t3;//存储log(R0/R)，存储A*F，存储公式第一项
            t1 = Math.log(R0R);
            t2 = 1/(F*Ashape);
            t3 = Math.pow(t1*t2,2);

            Cst = (t3*λ)/(13 * d * 4 *Math.PI *χ) - χ;


        }catch (Exception e){
            e.printStackTrace();
            //throw new MyException(20001,"反演雪粒径出错");
        }

        return Cst;
    }

    @Override
    public List<Double> check(CalVo calVo) {
        Map<String, Object> resultmap = new HashMap<>();

        List<Double> checkList = new ArrayList();
        List<Double> errorList = new ArrayList<>();
        checkList.add(1.023); //490
        checkList.add(0.984); //565
        checkList.add(0.953); //570
        checkList.add(0.949); //765
        checkList.add(0.879); //865
        checkList.add(0.783); //1020

        try{
            //读取波段和对应波段的冰的复折射率的虚部
            File file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/x01.xlsx");
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

            errorList.add(Math.abs(checkList.get(0)-rdata.get(140)));
            errorList.add(Math.abs(checkList.get(1)-rdata.get(215)));
            errorList.add(Math.abs(checkList.get(2)-rdata.get(320)));
            errorList.add(Math.abs(checkList.get(3)-rdata.get(415)));
            errorList.add(Math.abs(checkList.get(4)-rdata.get(515)));
            errorList.add(Math.abs(checkList.get(5)-rdata.get(670)));


        }catch (Exception e){
            //e.printStackTrace();
            throw new MyException(20001,"计算积雪反射率出错");
        }

        return errorList;
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
        d = calVo.getD();
        Cst = calVo.getCst()/10000000000.0;//单位ppb，数值1ppb = 10^-9
        Ashape = calVo.getShape();
        λ = waveX.getWave()/1000; //传入的值是nm，计算按μm进行
        χ = waveX.getXnum();

/*        System.out.println("太阳天顶角："+θs);
        System.out.println("观测天顶角："+θv);
        System.out.println("相对方位角："+ψ);
        System.out.println("雪粒径："+d);
        System.out.println("污染物浓度："+Cst);
        System.out.println("形状："+Ashape);*/

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
