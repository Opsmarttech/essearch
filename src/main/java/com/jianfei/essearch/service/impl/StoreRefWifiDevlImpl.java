package com.jianfei.essearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianfei.essearch.mapper.StoreRefWifiDevMapper;
import com.jianfei.essearch.model.StoreRefWifiDev;
import com.jianfei.essearch.service.IStoreRefWifiDevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreRefWifiDevlImpl extends ServiceImpl<StoreRefWifiDevMapper, StoreRefWifiDev> implements IStoreRefWifiDevService {

    @Autowired
    StoreRefWifiDevMapper storeRefWifiDevMapper;

    @Override
    public List<StoreRefWifiDev> getApList() {
        return storeRefWifiDevMapper.getApList();
    }



}
