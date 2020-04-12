package com.jianfei.essearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jianfei.essearch.model.WifiDeviceLogMonth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WifiDeviceLogMonthMapper extends BaseMapper<WifiDeviceLogMonth> {

    public void saveWifiDevLogsMonth(@Param("log") WifiDeviceLogMonth log);

}
