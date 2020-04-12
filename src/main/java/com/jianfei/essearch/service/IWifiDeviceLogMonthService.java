package com.jianfei.essearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jianfei.essearch.model.WifiDeviceLogMonth;

public interface IWifiDeviceLogMonthService extends IService<WifiDeviceLogMonth> {

    public void saveWifiDevLogsMonth(WifiDeviceLogMonth log);

}
