package com.rh.srservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.commonutils.R;
import com.rh.srservice.entity.Banner;
import com.rh.srservice.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //后台分页查询所有banner
    @GetMapping("pageallBanner/{current}/{limit}")
    public R pageallBanner(@PathVariable long current,
                           @PathVariable long limit){

        Page<Banner> page = new Page<>(current,limit);
        bannerService.page(page,null);
        long total = page.getTotal();
        List<Banner> recods = page.getRecords();
        return R.ok().data("total",total).data("recods",recods);
    }

    //根据id查询banner
    @GetMapping("getBannerById/{id}")
    public R getBannerById(@PathVariable String id){

        Banner banner = bannerService.getById(id);

        return R.ok().data("banner",banner);
    }
    //后台修改banner
    @PostMapping("updateBannerById")
    public R updateBannerById(@RequestBody Banner banner){

        boolean flag = bannerService.updateById(banner);

        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //后台新增banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody Banner banner){

        boolean result = bannerService.save(banner);

        if (result){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //后台删除banner
    @DeleteMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id){

        boolean flag = bannerService.removeById(id);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}

