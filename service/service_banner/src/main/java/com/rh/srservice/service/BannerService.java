package com.rh.srservice.service;

import com.rh.srservice.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author RH
 * @since 2022-04-08
 */
public interface BannerService extends IService<Banner> {

    List<Banner> getallBanner();
}
