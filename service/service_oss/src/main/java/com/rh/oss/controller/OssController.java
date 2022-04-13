package com.rh.oss.controller;

import com.rh.commonutils.R;
import com.rh.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/sross/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public R uploadAvator(MultipartFile file){

        String url = ossService.uploadAvator(file);
        return R.ok().data("url",url);
    }

    @PostMapping("uploadexcel/{choose}")
    public R uploadexcel(@PathVariable String choose){

        String url = ossService.uploadexcel(choose);
        return R.ok().data("url",url);
    }

}
