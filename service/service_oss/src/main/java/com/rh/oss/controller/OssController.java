package com.rh.oss.controller;

import com.rh.commonutils.R;
import com.rh.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("sross/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public R uploadAvator(MultipartFile file){

        String url = ossService.uploadAvator(file);
        return R.ok().data("url",url);
    }

}
