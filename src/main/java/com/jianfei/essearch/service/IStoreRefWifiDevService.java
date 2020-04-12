package com.jianfei.essearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jianfei.essearch.model.StoreRefWifiDev;

import java.util.List;

public interface IStoreRefWifiDevService extends IService<StoreRefWifiDev> {

    List<StoreRefWifiDev> getApList();

}
