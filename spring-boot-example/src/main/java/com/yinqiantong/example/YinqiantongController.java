package com.yinqiantong.example;

import com.yinqiantong.Yinqiantong;
import com.yinqiantong.example.cros.Cros;
import com.yinqiantong.model.NotifyRes;
import com.yinqiantong.model.Options;
import com.yinqiantong.model.Order;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class YinqiantongController {

    private static final String APP_ID = "00000000";
    private static final String APP_KEY = "1234567890123456";
    private static final String APP_SECRET = "12345678901234567890123456789012";

    @Cros
    @RequestMapping("/order/create")
    public Order createOrder(String channel, String platform, String code, String extra) throws Exception {
        // 根据您的逻辑，填好Options的参数
        int money = 1;
        String clientIp = "183.48.246.68";
        return Yinqiantong.create(APP_ID, APP_KEY, APP_SECRET)
                .createOrder(
                        Options.newInstance()
                                .setChannel(channel)
                                .setPlatform(platform)
                                .setCode(code)
                                .setMoney(money)
                                .setClientIp(clientIp)
                                .setNotifyUrl("https://yinqiantong.com/test")
                );
    }

    @Cros
    @RequestMapping("/order/query")
    public Order queryOrder(String outTradeNo) throws Exception {
        // 1，判断您当前数据库的订单信息，如果订单已完成，直接返回
        // 2，如果订单未完成，调用 Yinqiantong.create(APP_ID, APP_KEY, APP_SECRET).getOrder(outTradeNo) 获取订单信息
        return Yinqiantong.create(APP_ID, APP_KEY, APP_SECRET).getOrder(outTradeNo);
    }

    @RequestMapping("/notify")
    public NotifyRes orderNotify(@RequestHeader String appid,
                           @RequestHeader String appkey,
                           @RequestHeader long ts,
                           @RequestHeader String sign,
                           @RequestBody Map<String, Object> data) {
        boolean isSignOk = Yinqiantong.create(APP_ID, APP_KEY, APP_SECRET).checkSign(data, ts, sign);
        if (!isSignOk) {
            // 签名不正确，有可能是 appid appsecret 跟银钱通发过来的不一致导致，请确认 APP_ID, APP_KEY, APP_SECRET 是否正确
            return NotifyRes.fail();
        }
        // 处理回调的 data 内容
        // ...
        //
        return NotifyRes.success();
    }

}
