package com.rh.srservice.controller;


import com.rh.commonutils.R;
import com.rh.srservice.entity.Banner;
import com.rh.srservice.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RH
 * @since 2022-04-08
 */
@RestController
@RequestMapping("/bannerservice/banner")
@CrossOrigin
public class BannerController {

    @Autowired
    private BannerService bannerService;

    //获取所有banner
    @GetMapping("getallBanner")
    public R getallBanner(){

        List<Banner> bannerlist = bannerService.getallBanner();

        return R.ok().data("bannerlist",bannerlist);
    }

}

