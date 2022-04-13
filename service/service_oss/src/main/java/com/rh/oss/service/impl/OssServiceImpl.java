package com.rh.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.rh.oss.service.OssService;
import com.rh.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadAvator(MultipartFile file) {

        //配置属性
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String Filename = file.getOriginalFilename();
            //使用uuid
            String uuid=UUID.randomUUID().toString().replace("-","");
            Filename=uuid+Filename;
            //创建时间路径
            String datepath = new DateTime().toString("yyyy/MM/dd");
            Filename="SRCASS/"+datepath+"/"+Filename;
            // 创建PutObject请求。
            ossClient.putObject(bucketName, Filename, inputStream);
            //关闭对象
            ossClient.shutdown();

            //返回路径
            //https://edu-guli-img0216.oss-cn-beijing.aliyuncs.com/src%3Dhttp___pic.qqtn.com_up_2018-2_15193565233304349.jpg%26refer%3Dhttp___pic.qqtn.png
            String url="https://"+bucketName+"."+endpoint+"/"+Filename;

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String uploadexcel(String choose) {

        //配置属性
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        File file = null;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            if (choose.equals("writeexcelsnowr")){
                 file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/demo.xlsx");
            }else if (choose.equals("writeexcelvza")){
                file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/vzademo.xlsx");
            }else if(choose.equals("writeexcelzaim")){
                file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/azimdemo.xlsx");
            }else if(choose.equals("writeexceld")){
                file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/ddemo.xlsx");
            }else {
                file = new File("D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/cstdemo.xlsx");
            }
            //获取上传文件输入流
            InputStream inputStream = new FileInputStream(file);
            //获取文件名称
            String Filename = file.getName();
            //使用uuid
            String uuid=UUID.randomUUID().toString().replace("-","");
            Filename=uuid+Filename;
            //创建时间路径
            String datepath = new DateTime().toString("yyyy/MM/dd");
            Filename="SRCASS/Excel/Data/"+datepath+"/"+Filename;
            // 创建PutObject请求。
            ossClient.putObject(bucketName, Filename, inputStream);
            //关闭对象
            ossClient.shutdown();
            //返回路径
            String url="https://"+bucketName+"."+endpoint+"/"+Filename;

            //D:/Java_workspace/srcaas_admin/service/service_calculation/src/main/resources/excel/vzademo.xlsx

            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
