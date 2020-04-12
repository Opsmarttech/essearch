package com.jianfei.essearch.controller;

import com.jianfei.essearch.dao.WifiDeviceLogRepository;
import com.jianfei.essearch.model.StoreRefWifiDev;
import com.jianfei.essearch.model.WifiDeviceLog;
import com.jianfei.essearch.model.WifiDeviceLogMonth;
import com.jianfei.essearch.service.IStoreRefWifiDevService;
import com.jianfei.essearch.service.IWifiDeviceLogMonthService;
import lombok.Data;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(path = "/")
public class ESSearchController {

    @Autowired
    private IStoreRefWifiDevService storeRefWifiDevService;

    @Autowired
    private IWifiDeviceLogMonthService wifiDeviceLogMonthService;

    @Autowired
    WifiDeviceLogRepository repository;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @RequestMapping(value = "/fooQuery/{year}/{month}", method = RequestMethod.GET)// e.g. ..../fooQuery/2019/03|09|11 ...
    @ResponseBody
    public List<ResultVo> getQueryInfo(@PathVariable("year") String year, @PathVariable("month") String month) {

        List<ResultVo> resList = new ArrayList<>();
        String[] wifiDevAperials = new String[]{"WAP2200000000ABF",
                "WAP2200000000A62",
                "WAP136000000030C",
                "WAP13600000003F6",
                "WAP1360000000318",
                "WAP2200000000A6C",
                "WAP2200000000A9B",
                "WAP2200000000AFA",
                "WAP22000000010AE",
                "WAP1360000000316",
                "WAP1360000000317",
                "WAP1360000000319",
                "WAP2200000000A76",
                "WAP220000000100F",
                "WAP2200000000E71",
                "WAP2200000000E98",
                "WAP2200000000FD1",
                "WAP2200000000E87",
                "WAP220000000109C",
                "WAP2200000000E25",
                "WAP2200000000EA8"};

        for(int k = 0; k < wifiDevAperials.length; k ++) {

            int monthLoop = 0;
            String ApSerial = wifiDevAperials[k];
            int monthFoo = Integer.valueOf(month);
            int yearFoo = Integer.valueOf(year);
            if(monthFoo == 1 || monthFoo == 3 || monthFoo == 5 || monthFoo == 7 || monthFoo == 8 || monthFoo == 10 || monthFoo == 12) {
                monthLoop = 31;
            } else if(monthFoo == 2) {
                if((yearFoo / 4 == 0 && yearFoo / 100 != 0) || (yearFoo / 400 == 0)) {
                    monthLoop = 29;
                } else {
                    monthLoop = 28;
                }
            } else {
                monthLoop = 30;
            }

            double totalTx = 0.0;
            double totalRx = 0.0;
            int totalDevices = 0;
            int totalConnected = 0;
            int totalDisConnected =  0;
            int totalAuth = 0;
            List<String> exceptionList = new ArrayList<>();


            for(int i = 1; i <= monthLoop; i ++) {

                long sumTx = 0l;
                long sumRx = 0l;
                String scrollId = "";

                Sort.Order order = new Sort.Order(Sort.Direction.ASC, "Timestamp");
                Sort sort = Sort.by(order);
                HashSet<String> set = new HashSet<>();
                List<String> disConnectList = new ArrayList<>();
                List<String> connectedList = new ArrayList<>();
                HashSet<String> authList = new HashSet<>();
                ScrolledPage<WifiDeviceLog> scroll = null;

                QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("ApSerial", ApSerial));

                String indexName = "wifidevicelog-" + year + "." +  month + "." + ((i < 10) ? "0" + i : i);
                System.out.println("索引名称：-------------------=" + indexName);

                SearchQuery searchQuery = new NativeSearchQueryBuilder()
                        .withIndices(indexName)//索引名
                        .withTypes("fluentd")//类型名
                        .withQuery(queryBuilder)//查询条件，这里简单使用term查询
                        .withPageable(PageRequest.of(0, 10, sort))//从0页开始查，每页10个结果
                        .withFields("ApSerial", "StaDeltaTxByte", "StaDeltaRxByte", "StaMac", "StaStatus", "Timestamp", "StaConnTime")//ES里该index内存的文档，可能存了很多我们不关心的字段，全返回没必要，所以指定有用的字段
                        .build();

                try {
                    scroll = (ScrolledPage<WifiDeviceLog>) elasticsearchRestTemplate.startScroll(3000, searchQuery, WifiDeviceLog.class, searchResultMapper);
                } catch (Exception e) {
                    exceptionList.add(indexName);
                    continue;
                }

                System.out.println("查询总命中数：" + scroll.getTotalElements());

                while (scroll.hasContent()) {
                    for (WifiDeviceLog dto : scroll.getContent()) {
                        //System.out.println(dto.getStaDeltaRxByte(), "---", dto.getStaDeltaRxByte());
                        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dto.getTimestamp()));
                        //System.out.println("--->Date:", result1);
                        set.add(dto.getStaMac());
                        if(dto.getStaStatus() == 4) authList.add(dto.getStaMac());
                        if(dto.getStaStatus() == 0) disConnectList.add(dto.getStaMac());
                        if(dto.getStaStatus() == 99) connectedList.add(dto.getStaMac());
                        sumTx += dto.getStaDeltaTxByte();
                        sumRx += dto.getStaDeltaRxByte();
                    }
                    //取下一页，scrollId在es服务器上可能会发生变化，需要用最新的。发起continueScroll请求会重新刷新快照保留时间
                    scroll = (ScrolledPage<WifiDeviceLog>) elasticsearchRestTemplate.continueScroll(scroll.getScrollId(), 3000, WifiDeviceLog.class, searchResultMapper);
                    scrollId = scroll.getScrollId();
                }

                //当日统计
                totalTx += sumTx / (1024.0*1024.0);
                System.out.println("sumTx -----------=" + String .format("%.2f", (sumTx / (1024.0*1024.0))));//+ new BigDecimal(sumTx / 1024.0*1024.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
                totalRx += sumRx / (1024.0*1024.0);
                System.out.println("sumRx -----------=" + String .format("%.2f", (sumRx / (1024.0*1024.0))));//new BigDecimal(sumRx / 1024.0*1024.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
                totalDevices += set.size();
                System.out.println("连接设备数：------= " + set.size());

                int connectTimes = connectedList.size();
                int disConnectTimes = disConnectList.size();

                totalConnected += connectTimes;
                System.out.println("连接次数：-------=" + connectTimes);

                totalDisConnected += disConnectTimes;
                System.out.println("断开连接次数：-------=" + disConnectTimes);

                totalAuth += authList.size();
                System.out.println("认证次数：-------=" + authList.size());

                //及时释放es服务器资源
                try {
                    elasticsearchRestTemplate.clearScroll(scroll.getScrollId());
                } catch(Exception e) {
                }
            }

            System.out.println("///////////////////////////////////////////////////////////////////////////////////////////////////");
            System.out.println("设备：" + ApSerial + " 时间： " + year + "-" + month);
            System.out.println("本月发送流量：" + totalTx + " MB");
            System.out.println("本月接收流量：" + totalRx + " MB");
            System.out.println("本月总连接数：" + totalConnected);
            System.out.println("本月总断开连接数：" + totalDisConnected);
            System.out.println("本月终端数量：" +  totalDevices);
            System.out.println("本月认证数量：" + totalAuth);
            System.out.println("无效索引：" + exceptionList.toString());
            System.out.println("///////////////////////////////////////////////////////////////////////////////////////////////////");

            try {
                File file = new File("D:\\workspace\\javawebpros\\essearch\\src\\main\\resources\\export\\" + year + "" + month + "-"+ ApSerial +".txt");
                file.createNewFile();

                try(FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);) {
                    bw.write("///////////////////////////////////////////////////////////////////////////////////////////////////");
                    bw.flush();
                    bw.newLine();
                    bw.write("设备：" + ApSerial + " 时间： " + year + "-" + month);
                    bw.flush();
                    bw.newLine();
                    bw.write("本月发送流量：" + totalTx + " MB");
                    bw.flush();
                    bw.newLine();
                    bw.write("本月接收流量：" + totalRx + " MB");
                    bw.flush();
                    bw.newLine();
                    bw.write("本月总连接数：" + totalConnected);
                    bw.flush();
                    bw.newLine();
                    bw.write("本月总断开连接数：" + totalDisConnected);
                    bw.flush();
                    bw.newLine();
                    bw.write("本月终端数量：" + totalDevices);
                    bw.flush();
                    bw.newLine();
                    bw.write("本月认证数量：" + totalAuth);
                    bw.flush();
                    bw.newLine();
                    bw.write("无效索引：" + exceptionList.toString());
                    bw.flush();
                    bw.newLine();
                    bw.write("///////////////////////////////////////////////////////////////////////////////////////////////////");
                    bw.flush();
                    bw.newLine();
                    bw.flush();
                } catch(Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            //excel process



            ResultVo resultVo = new ResultVo();
            resultVo.setTotalTx(totalTx);
            resultVo.setTotalRx(totalRx);
            resultVo.setTotalConnected(totalConnected);
            resultVo.setTotalDisConnected(totalDisConnected);
            resultVo.setTotalDevConnected(totalDevices);
            resultVo.setTotalAuth(totalAuth);
            resList.add(resultVo);

        }


        return resList;
    }

    @RequestMapping(value = "/fooQuery_V2/{year}/{month}", method = RequestMethod.POST)// e.g. ..../fooQuery/2019/03|09|11 ...
    @ResponseBody
    public List<ResultVo> getQueryInfo_V2(@PathVariable("year") String year, @PathVariable("month") String month) {

        List<StoreRefWifiDev> devList = storeRefWifiDevService.getApList();
        List<ResultVo> resList = new ArrayList<>();
        List<String> wifiDevAperials = new ArrayList<>();
        Map<String, Integer> apRefStore = new HashMap<>();

        for(int i = 0; i < devList.size(); i ++) {
            StoreRefWifiDev storeRefWifiDev = devList.get(i);
            String apSerials = storeRefWifiDev.getApSerials();
            String[] apsArr = apSerials.split(",");
            for(String serial : apsArr) {
                wifiDevAperials.add(serial);
                apRefStore.put(serial,storeRefWifiDev.getId());
            }
        }

        for(int k = 0; k < wifiDevAperials.size(); k ++) {

            int dayLoop = 0;
            String ApSerial = wifiDevAperials.get(k);
            int monthFoo = Integer.valueOf(month);
            int yearFoo = Integer.valueOf(year);
            if(monthFoo == 1 || monthFoo == 3 || monthFoo == 5 || monthFoo == 7 || monthFoo == 8 || monthFoo == 10 || monthFoo == 12) {
                dayLoop = 31;
            } else if(monthFoo == 2) {
                if((yearFoo / 4 == 0 && yearFoo / 100 != 0) || (yearFoo / 400 == 0)) {
                    dayLoop = 29;
                } else {
                    dayLoop = 28;
                }
            } else {
                dayLoop = 30;
            }

            double totalTx = 0.0;
            double totalRx = 0.0;
            int totalDevices = 0;
            int totalConnected = 0;
            int totalDisConnected =  0;
            int totalAuth = 0;
            List<String> exceptionList = new ArrayList<>();


            for(int day = 1; day <= dayLoop; day ++) {

                long sumTx = 0l;
                long sumRx = 0l;
                String scrollId = "";

                Sort.Order order = new Sort.Order(Sort.Direction.ASC, "Timestamp");
                Sort sort = Sort.by(order);
                HashSet<String> set = new HashSet<>();
                List<String> disConnectList = new ArrayList<>();
                List<String> connectedList = new ArrayList<>();
                HashSet<String> authList = new HashSet<>();
                ScrolledPage<WifiDeviceLog> scroll = null;

                QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("ApSerial", ApSerial));

                String indexName = "wifidevicelog-" + year + "." +  month + "." + ((day < 10) ? "0" + day : day);
                System.out.println("索引名称：-------------------=" + indexName);

                SearchQuery searchQuery = new NativeSearchQueryBuilder()
                        .withIndices(indexName)//索引名
                        .withTypes("fluentd")//类型名
                        .withQuery(queryBuilder)//查询条件，这里简单使用term查询
                        .withPageable(PageRequest.of(0, 10, sort))//从0页开始查，每页10个结果
                        .withFields("ApSerial", "StaDeltaTxByte", "StaDeltaRxByte", "StaMac", "StaStatus", "Timestamp", "StaConnTime")//ES里该index内存的文档，可能存了很多我们不关心的字段，全返回没必要，所以指定有用的字段
                        .build();

                try {
                    scroll = (ScrolledPage<WifiDeviceLog>) elasticsearchRestTemplate.startScroll(3000, searchQuery, WifiDeviceLog.class, searchResultMapper);
                } catch (Exception e) {
                    exceptionList.add(indexName);
                    continue;
                }

                System.out.println("查询总命中数：" + scroll.getTotalElements());

                while (scroll.hasContent()) {
                    for (WifiDeviceLog dto : scroll.getContent()) {
//                        WifiDeviceLog4mysql log = new WifiDeviceLog4mysql();
//                        log.setStaMac(dto.getStaMac());
//                        log.setApSerial(dto.getApSerial());
//                        log.setUserAgent(dto.getUserAgent());
//                        log.setCustomerId(dto.getCustomerId());
//                        log.setSiteId(dto.getSiteId());
//                        log.setAcSerial(dto.getAcSerial());
//                        log.setStaBssid(dto.getStaBssid());
//                        log.setStaEssid(dto.getStaEssid());
//                        log.setStaRssi(dto.getStaRssi());
//                        log.setRadiolongf(dto.getRadiolongf());
//                        log.setWlanId(dto.getWlanId());
//                        log.setStaStatus(dto.getStaStatus());
//                        log.setTimestamp(dto.getTimestamp());
//                        log.setStaConnlongerval(dto.getStaConnlongerval());
//                        log.setStaConnTime(dto.getStaConnTime());
//                        log.setStaDisconnTime(dto.getStaDisconnTime());
//                        log.setStaIpAddr(dto.getStaIpAddr());
//                        log.setHostName(dto.getHostName());
//                        log.setStaTxFrameCount(dto.getStaTxFrameCount());
//                        log.setStaRxFrame(dto.getStaRxFrame());
//                        log.setStaTxByte(dto.getStaTxByte());
//                        log.setStaRxByte(dto.getStaRxByte());
//                        log.setStaDeltaTxByte(dto.getStaDeltaTxByte());
//                        log.setStaDeltaRxByte(dto.getStaDeltaRxByte());
//                        log.setStaTxRatePerEcho(dto.getStaTxRatePerEcho());
//                        log.setStaRxRatePerEcho(dto.getStaRxRatePerEcho());
//                        log.setStaTxUCast(dto.getStaTxUCast());
//                        log.setStaTxFailures(dto.getStaTxFailures());
//                        log.setStaRxErrors(dto.getStaRxErrors());
//                        log.setStaRxMicErrors(dto.getStaRxMicErrors());
//                        log.setStaRxDecryptErrors(dto.getStaRxDecryptErrors());
//                        log.setStaRxrate(dto.getStaRxrate());
//                        log.setTxrate(dto.getTxrate());
//                        log.setStaChannel(dto.getStaChannel());
//                        log.setAuthSessionId(dto.getAuthSessionId());

                        //System.out.println(dto.getStaDeltaRxByte(), "---", dto.getStaDeltaRxByte());
                        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dto.getTimestamp()));
                        //System.out.println("--->Date:", result1);
                        set.add(dto.getStaMac());
                        if(dto.getStaStatus() == 4) authList.add(dto.getStaMac());
                        if(dto.getStaStatus() == 0) disConnectList.add(dto.getStaMac());
                        if(dto.getStaStatus() == 99) connectedList.add(dto.getStaMac());
                        sumTx += dto.getStaDeltaTxByte();
                        sumRx += dto.getStaDeltaRxByte();

                    }
                    //取下一页，scrollId在es服务器上可能会发生变化，需要用最新的。发起continueScroll请求会重新刷新快照保留时间
                    scroll = (ScrolledPage<WifiDeviceLog>) elasticsearchRestTemplate.continueScroll(scroll.getScrollId(), 3000, WifiDeviceLog.class, searchResultMapper);
                    scrollId = scroll.getScrollId();
                }

                //当日统计
                totalTx += sumTx / (1024.0*1024.0);
                System.out.println("sumTx -----------=" + String .format("%.2f", (sumTx / (1024.0*1024.0))));//+ new BigDecimal(sumTx / 1024.0*1024.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
                totalRx += sumRx / (1024.0*1024.0);
                System.out.println("sumRx -----------=" + String .format("%.2f", (sumRx / (1024.0*1024.0))));//new BigDecimal(sumRx / 1024.0*1024.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
                totalDevices += set.size();
                System.out.println("连接设备数：------= " + set.size());

                int connectTimes = connectedList.size();
                int disConnectTimes = disConnectList.size();

                totalConnected += connectTimes;
                System.out.println("连接次数：-------=" + connectTimes);

                totalDisConnected += disConnectTimes;
                System.out.println("断开连接次数：-------=" + disConnectTimes);

                totalAuth += authList.size();
                System.out.println("认证次数：-------=" + authList.size());

                //天累计

                //及时释放es服务器资源
                try {
                    elasticsearchRestTemplate.clearScroll(scroll.getScrollId());
                } catch(Exception e) {
                }

            }

            //月累计
            WifiDeviceLogMonth wifiDeviceLogMonth = new WifiDeviceLogMonth();
            wifiDeviceLogMonth.setApSerial(ApSerial);
            wifiDeviceLogMonth.setConnectionCount(totalDisConnected);
            wifiDeviceLogMonth.setAuthCount(totalAuth);
            wifiDeviceLogMonth.setTxRxByte(totalRx + totalTx);
            wifiDeviceLogMonth.setDeviceCount(totalDevices);
            wifiDeviceLogMonth.setStoreId(apRefStore.get(ApSerial));
            wifiDeviceLogMonth.setLogDate(new Date());
            wifiDeviceLogMonthService.saveWifiDevLogsMonth(wifiDeviceLogMonth);

            ResultVo resultVo = new ResultVo();
            resultVo.setTotalTx(totalTx);
            resultVo.setTotalRx(totalRx);
            resultVo.setTotalConnected(totalConnected);
            resultVo.setTotalDisConnected(totalDisConnected);
            resultVo.setTotalDevConnected(totalDevices);
            resultVo.setTotalAuth(totalAuth);
            resList.add(resultVo);

        }

        return resList;
    }

    private final SearchResultMapper searchResultMapper = new SearchResultMapper() {
        @Override
        public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
            List<WifiDeviceLog> result = new ArrayList<>();
            for (SearchHit hit : response.getHits()) {
                if (response.getHits().getHits().length <= 0) {
                    return new AggregatedPageImpl<T>(Collections.EMPTY_LIST, pageable, response.getHits().getTotalHits(), response.getScrollId());
                }
                //可以做更复杂的映射逻辑
                WifiDeviceLog log = new WifiDeviceLog();
                log.setApSerial((String) hit.getSourceAsMap().get("ApSerial"));
                log.setStaDeltaTxByte((Integer) hit.getSourceAsMap().get("StaDeltaTxByte"));
                log.setStaDeltaRxByte((Integer) hit.getSourceAsMap().get("StaDeltaRxByte"));
                log.setStaMac((String) hit.getSourceAsMap().get("StaMac"));
                log.setStaStatus((Integer) hit.getSourceAsMap().get("StaStatus"));
                log.setTimestamp((Long) hit.getSourceAsMap().get("Timestamp"));
                log.setStaConnTime((Long) hit.getSourceAsMap().get("StaConnTime"));
                result.add(log);
            }
            if (result.isEmpty()) {
                return new AggregatedPageImpl<T>(Collections.EMPTY_LIST, pageable, response.getHits().getTotalHits(), response.getScrollId());
            }
            return new AggregatedPageImpl<T>((List<T>) result, pageable, response.getHits().getTotalHits(), response.getScrollId());
        }

        @Override
        public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
            return null;
        }
    };

    @Data
    public class ResultVo {

        private double totalTx = 0.0;
        private double totalRx = 0.0;
        private int totalDevConnected = 0;
        private int totalConnected = 0;
        private int totalDisConnected = 0;
        private int totalAuth = 0;

    }

}
