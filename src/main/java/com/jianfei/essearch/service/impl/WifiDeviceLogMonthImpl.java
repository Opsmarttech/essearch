package com.jianfei.essearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianfei.essearch.mapper.WifiDeviceLogMonthMapper;
import com.jianfei.essearch.model.WifiDeviceLogMonth;
import com.jianfei.essearch.service.IWifiDeviceLogMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WifiDeviceLogMonthImpl extends ServiceImpl<WifiDeviceLogMonthMapper, WifiDeviceLogMonth> implements IWifiDeviceLogMonthService {

    @Autowired
    private WifiDeviceLogMonthMapper wifiDeviceLogMonthMapper;

    @Override
    public void saveWifiDevLogsMonth(WifiDeviceLogMonth log) {
        wifiDeviceLogMonthMapper.saveWifiDevLogsMonth(log);
    }

}
