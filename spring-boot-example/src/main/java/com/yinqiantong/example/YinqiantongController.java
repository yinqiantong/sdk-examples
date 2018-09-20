package com.yinqiantong.example;

import com.yinqiantong.Yinqiantong;
import com.yinqiantong.model.Options;
import com.yinqiantong.model.Order;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class YinqiantongController {

    private static final String APP_ID = "00000000";
    private static final String APP_KEY = "1234567890123456";
    private static final String APP_SECRET = "12345678901234567890123456789012";

    @RequestMapping("/order/create")
    public Order createOrder(HttpServletRequest request) throws Exception {
        // 根据您的逻辑，填好Options的参数
        return Yinqiantong.create(APP_ID, APP_KEY, APP_SECRET)
                .createOrder(
                        Options.newInstance()
                                .setChannel("alipay")
                                .setPlatform("h5")
                                .setMoney(1)
                                .setClientIp(request)
                                .setNotifyUrl("https://yinqiantong.com/test")
                );
    }

    @RequestMapping("/order/query")
    public Order queryOrder(String outTradeNo) throws Exception {
        // 1，判断您当前数据库的订单信息，如果订单已完成，直接返回
        // 2，如果订单未完成，调用 Yinqiantong.create(APP_ID, APP_KEY, APP_SECRET).getOrder(outTradeNo) 获取订单信息
        return Yinqiantong.create(APP_ID, APP_KEY, APP_SECRET).getOrder(outTradeNo);
    }

    @RequestMapping("/notify")
    public void orderNotify(@RequestHeader String appid,
                            @RequestHeader String appkey,
                            @RequestHeader long ts,
                            @RequestHeader String sign,
                            @RequestBody Map<String, Object> data) {
    }

}
