package com.jianfei.essearch.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("store_ref_wifi_dev")
public class StoreRefWifiDev extends Model<StoreRefWifiDev> {

    @TableId(value = "_id", type = IdType.AUTO)
    private Integer id;
    @TableField("city")
    private String city;
    @TableField("brand")
    private String brand;
    @TableField("store_name")
    private String storeName;
    @TableField("ap_serials")
    private String apSerials;
    @TableField("net_type")
    private String netType;

}
