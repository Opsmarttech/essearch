package com.jianfei.essearch.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

//@Data
@Builder
@Document(indexName="wifidevicelog-2019.10.12", type="fluentd", shards = 3, replicas = 1, refreshInterval = "-1")
public class WifiDeviceLog {

    @Id
    public String _id;
    public String StaMac;
    public String ApSerial;
    public String UserAgent;
    public long CustomerId;
    public long SiteId;
    public String AcSerial;
    public String StaBssid;
    public String StaEssid;
    public long StaRssi;
    public String Radiolongf;
    public long WlanId;
    public long StaStatus;
    public long Timestamp;
    public long StaConnlongerval;
    public long StaConnTime;
    public long StaDisconnTime;
    public String StaIpAddr;
    public String HostName;
    public long StaTxFrameCount;
    public long StaRxFrame;
    public long StaTxByte;
    public long StaRxByte;
    public long StaDeltaTxByte;
    public long StaDeltaRxByte;
    public long StaTxRatePerEcho;
    public long StaRxRatePerEcho;
    public long StaTxUCast;
    public long StaTxFailures;
    public long StaRxErrors;
    public long StaRxMicErrors;
    public long StaRxDecryptErrors;
    public long StaRxrate;
    public long txrate;
    public long StaChannel;
    public String AuthSessionId;

    public WifiDeviceLog() {}

    public WifiDeviceLog(String _id, String staMac, String apSerial, String userAgent, long customerId, long siteId, String acSerial, String staBssid, String staEssid, long staRssi, String radiolongf, long wlanId, long staStatus, long timestamp, long staConnlongerval, long staConnTime, long staDisconnTime, String staIpAddr, String hostName, long staTxFrameCount, long staRxFrame, long staTxByte, long staRxByte, long staDeltaTxByte, long staDeltaRxByte, long staTxRatePerEcho, long staRxRatePerEcho, long staTxUCast, long staTxFailures, long staRxErrors, long staRxMicErrors, long staRxDecryptErrors, long staRxrate, long txrate, long staChannel, String authSessionId) {
        this._id = _id;
        StaMac = staMac;
        ApSerial = apSerial;
        UserAgent = userAgent;
        CustomerId = customerId;
        SiteId = siteId;
        AcSerial = acSerial;
        StaBssid = staBssid;
        StaEssid = staEssid;
        StaRssi = staRssi;
        Radiolongf = radiolongf;
        WlanId = wlanId;
        StaStatus = staStatus;
        Timestamp = timestamp;
        StaConnlongerval = staConnlongerval;
        StaConnTime = staConnTime;
        StaDisconnTime = staDisconnTime;
        StaIpAddr = staIpAddr;
        HostName = hostName;
        StaTxFrameCount = staTxFrameCount;
        StaRxFrame = staRxFrame;
        StaTxByte = staTxByte;
        StaRxByte = staRxByte;
        StaDeltaTxByte = staDeltaTxByte;
        StaDeltaRxByte = staDeltaRxByte;
        StaTxRatePerEcho = staTxRatePerEcho;
        StaRxRatePerEcho = staRxRatePerEcho;
        StaTxUCast = staTxUCast;
        StaTxFailures = staTxFailures;
        StaRxErrors = staRxErrors;
        StaRxMicErrors = staRxMicErrors;
        StaRxDecryptErrors = staRxDecryptErrors;
        StaRxrate = staRxrate;
        this.txrate = txrate;
        StaChannel = staChannel;
        AuthSessionId = authSessionId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStaMac() {
        return StaMac;
    }

    public void setStaMac(String StaMac) {
        this.StaMac = StaMac;
    }

    public String getApSerial() {
        return ApSerial;
    }

    public void setApSerial(String ApSerial) {
        this.ApSerial = ApSerial;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public void setUserAgent(String UserAgent) {
        this.UserAgent = UserAgent;
    }

    public long getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(long CustomerId) {
        this.CustomerId = CustomerId;
    }

    public long getSiteId() {
        return SiteId;
    }

    public void setSiteId(long SiteId) {
        this.SiteId = SiteId;
    }

    public String getAcSerial() {
        return AcSerial;
    }

    public void setAcSerial(String AcSerial) {
        this.AcSerial = AcSerial;
    }

    public String getStaBssid() {
        return StaBssid;
    }

    public void setStaBssid(String StaBssid) {
        this.StaBssid = StaBssid;
    }

    public String getStaEssid() {
        return StaEssid;
    }

    public void setStaEssid(String StaEssid) {
        this.StaEssid = StaEssid;
    }

    public long getStaRssi() {
        return StaRssi;
    }

    public void setStaRssi(long StaRssi) {
        this.StaRssi = StaRssi;
    }

    public String getRadiolongf() {
        return Radiolongf;
    }

    public void setRadiolongf(String Radiolongf) {
        this.Radiolongf = Radiolongf;
    }

    public long getWlanId() {
        return WlanId;
    }

    public void setWlanId(long WlanId) {
        this.WlanId = WlanId;
    }

    public long getStaStatus() {
        return StaStatus;
    }

    public void setStaStatus(long StaStatus) {
        this.StaStatus = StaStatus;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long Timestamp) {
        this.Timestamp = Timestamp;
    }

    public long getStaConnlongerval() {
        return StaConnlongerval;
    }

    public void setStaConnlongerval(long StaConnlongerval) {
        this.StaConnlongerval = StaConnlongerval;
    }

    public long getStaConnTime() {
        return StaConnTime;
    }

    public void setStaConnTime(long StaConnTime) {
        this.StaConnTime = StaConnTime;
    }

    public long getStaDisconnTime() {
        return StaDisconnTime;
    }

    public void setStaDisconnTime(long StaDisconnTime) {
        this.StaDisconnTime = StaDisconnTime;
    }

    public String getStaIpAddr() {
        return StaIpAddr;
    }

    public void setStaIpAddr(String StaIpAddr) {
        this.StaIpAddr = StaIpAddr;
    }

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String HostName) {
        this.HostName = HostName;
    }

    public long getStaTxFrameCount() {
        return StaTxFrameCount;
    }

    public void setStaTxFrameCount(long StaTxFrameCount) {
        this.StaTxFrameCount = StaTxFrameCount;
    }

    public long getStaRxFrame() {
        return StaRxFrame;
    }

    public void setStaRxFrame(long StaRxFrame) {
        this.StaRxFrame = StaRxFrame;
    }

    public long getStaTxByte() {
        return StaTxByte;
    }

    public void setStaTxByte(long StaTxByte) {
        this.StaTxByte = StaTxByte;
    }

    public long getStaRxByte() {
        return StaRxByte;
    }

    public void setStaRxByte(long StaRxByte) {
        this.StaRxByte = StaRxByte;
    }

    public long getStaDeltaTxByte() {
        return StaDeltaTxByte;
    }

    public void setStaDeltaTxByte(long StaDeltaTxByte) {
        this.StaDeltaTxByte = StaDeltaTxByte;
    }

    public long getStaDeltaRxByte() {
        return StaDeltaRxByte;
    }

    public void setStaDeltaRxByte(long StaDeltaRxByte) {
        this.StaDeltaRxByte = StaDeltaRxByte;
    }

    public long getStaTxRatePerEcho() {
        return StaTxRatePerEcho;
    }

    public void setStaTxRatePerEcho(long StaTxRatePerEcho) {
        this.StaTxRatePerEcho = StaTxRatePerEcho;
    }

    public long getStaRxRatePerEcho() {
        return StaRxRatePerEcho;
    }

    public void setStaRxRatePerEcho(long StaRxRatePerEcho) {
        this.StaRxRatePerEcho = StaRxRatePerEcho;
    }

    public long getStaTxUCast() {
        return StaTxUCast;
    }

    public void setStaTxUCast(long StaTxUCast) {
        this.StaTxUCast = StaTxUCast;
    }

    public long getStaTxFailures() {
        return StaTxFailures;
    }

    public void setStaTxFailures(long StaTxFailures) {
        this.StaTxFailures = StaTxFailures;
    }

    public long getStaRxErrors() {
        return StaRxErrors;
    }

    public void setStaRxErrors(long StaRxErrors) {
        this.StaRxErrors = StaRxErrors;
    }

    public long getStaRxMicErrors() {
        return StaRxMicErrors;
    }

    public void setStaRxMicErrors(long StaRxMicErrors) {
        this.StaRxMicErrors = StaRxMicErrors;
    }

    public long getStaRxDecryptErrors() {
        return StaRxDecryptErrors;
    }

    public void setStaRxDecryptErrors(long StaRxDecryptErrors) {
        this.StaRxDecryptErrors = StaRxDecryptErrors;
    }

    public long getStaRxrate() {
        return StaRxrate;
    }

    public void setStaRxrate(long StaRxrate) {
        this.StaRxrate = StaRxrate;
    }

    public long getTxrate() {
        return txrate;
    }

    public void setTxrate(long txrate) {
        this.txrate = txrate;
    }

    public long getStaChannel() {
        return StaChannel;
    }

    public void setStaChannel(long StaChannel) {
        this.StaChannel = StaChannel;
    }

    public String getAuthSessionId() {
        return AuthSessionId;
    }

    public void setAuthSessionId(String AuthSessionId) {
        this.AuthSessionId = AuthSessionId;
    }

    @Override
    public String toString() {
        return "WifiDeviceLog{" +
                "_id='" + _id + '\'' +
                ", StaMac='" + StaMac + '\'' +
                ", ApSerial='" + ApSerial + '\'' +
                ", UserAgent='" + UserAgent + '\'' +
                ", CustomerId=" + CustomerId +
                ", SiteId=" + SiteId +
                ", AcSerial='" + AcSerial + '\'' +
                ", StaBssid='" + StaBssid + '\'' +
                ", StaEssid='" + StaEssid + '\'' +
                ", StaRssi=" + StaRssi +
                ", Radiolongf='" + Radiolongf + '\'' +
                ", WlanId=" + WlanId +
                ", StaStatus=" + StaStatus +
                ", Timestamp=" + Timestamp +
                ", StaConnlongerval=" + StaConnlongerval +
                ", StaConnTime=" + StaConnTime +
                ", StaDisconnTime=" + StaDisconnTime +
                ", StaIpAddr='" + StaIpAddr + '\'' +
                ", HostName='" + HostName + '\'' +
                ", StaTxFrameCount=" + StaTxFrameCount +
                ", StaRxFrame=" + StaRxFrame +
                ", StaTxByte=" + StaTxByte +
                ", StaRxByte=" + StaRxByte +
                ", StaDeltaTxByte=" + StaDeltaTxByte +
                ", StaDeltaRxByte=" + StaDeltaRxByte +
                ", StaTxRatePerEcho=" + StaTxRatePerEcho +
                ", StaRxRatePerEcho=" + StaRxRatePerEcho +
                ", StaTxUCast=" + StaTxUCast +
                ", StaTxFailures=" + StaTxFailures +
                ", StaRxErrors=" + StaRxErrors +
                ", StaRxMicErrors=" + StaRxMicErrors +
                ", StaRxDecryptErrors=" + StaRxDecryptErrors +
                ", StaRxrate=" + StaRxrate +
                ", txrate=" + txrate +
                ", StaChannel=" + StaChannel +
                ", AuthSessionId='" + AuthSessionId + '\'' +
                '}';
    }
}
