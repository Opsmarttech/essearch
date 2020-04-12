package com.jianfei.essearch.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

@Data
@TableName("wifi_device_log_month")
public class WifiDeviceLogMonth extends Model<WifiDeviceLogMonth> {

    @TableId(value = "_id", type = IdType.AUTO)
    private Integer id;
    @TableField("ap_serial")
    private String apSerial;
    @TableField("connection_count")
    private Integer connectionCount;
    @TableField("auth_count")
    private Integer authCount;
    @TableField("device_count")
    private Integer deviceCount;
    @TableField("tx_rx_byte")
    private Double txRxByte;
    @TableField("log_date")
    private Date logDate;
    @TableField("store_id")
    private Integer storeId;

}
