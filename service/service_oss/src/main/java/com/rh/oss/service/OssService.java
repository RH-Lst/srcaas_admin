package com.rh.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadAvator(MultipartFile file);
}
