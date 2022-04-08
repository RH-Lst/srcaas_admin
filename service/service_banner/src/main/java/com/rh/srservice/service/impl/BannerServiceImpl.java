package com.rh.srservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rh.srservice.entity.Banner;
import com.rh.srservice.mapper.BannerMapper;
import com.rh.srservice.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author RH
 * @since 2022-04-08
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    //新建getallBanner方法，方便做Redis缓存
    @Override
    public List<Banner> getallBanner() {

        QueryWrapper<Banner> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("id");

        wrapper.last("limit 2");

        List<Banner> banners = baseMapper.selectList(wrapper);

        return banners;
    }
}
