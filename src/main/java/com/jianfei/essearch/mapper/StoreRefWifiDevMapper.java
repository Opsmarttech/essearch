package com.jianfei.essearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jianfei.essearch.model.StoreRefWifiDev;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRefWifiDevMapper extends BaseMapper<StoreRefWifiDev> {

    List<StoreRefWifiDev> getApList();

}
