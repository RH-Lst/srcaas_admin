package com.rh.calservice.client;

import com.rh.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("sr-oss")
public interface OssClient {
    @PostMapping("/sross/fileoss/uploadexcel/{choose}")
    public R uploadexcel(@PathVariable("choose") String choose);
}

