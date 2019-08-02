package com.xiaohe.mapshow.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.google.gson.Gson;
import com.xiaohe.mapshow.modules.register.entity.TemplateSMS;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * 腾讯平台短信
 *
 * @author hzh
 * @version 1.0
 * @date 2019/1/10 18:15
 */
public class SmsUtil {
    private static Logger log = LoggerFactory.getLogger(SmsUtil.class);
    /**
     * 短信应用SDK AppID
     */
    private static int APP_ID = 1400179427;

    /**
     * 短信应用SDK AppKey
     */
    private static String APP_KEY = "d9191bfbb1cad521c465cc7621984f9e";

    /**
     * 短信模板ID，需要在短信应用中申请
     * templateId7839对应的内容是"您的验证码是: {1}"
     * NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
     */
    private static int TEMPLATE_ID = 264315;

    /**
     * 签名
     * NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
     */
    private static String SMS_SIGN = "宜达商城";


    /**
     * 云之讯配置
     *
     * @param phone
     * @param nonceStr
     * @return
     */
    private static String Y_SID = "be57178a9a099a0acd130365ff1f2f49";
    private static String Y_APP_ID = "4bf2b8dedf404e9c87c49c4253d6bab7";
    private static String Y_TOKEN = "54f4094ea3e1cda68898a95495cc3d6b";
    private static String Y_TEMPLATE_ID = "6514";
    private static String Y_VERSION = "2014-06-30";
    private static String Y_API_URL = "https://api.ucpaas.com";

    public static Result sendMsg(String phone, String nonceStr) {
        Result result = new Result();
        try {
            String[] params = {nonceStr, "30"};
            SmsSingleSender singleSender = new SmsSingleSender(APP_ID, APP_KEY);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult sendWithParam = singleSender.sendWithParam("86", phone, TEMPLATE_ID, params, null, "", "");
            log.info("腾讯短信下发结果:{}", sendWithParam.errMsg);
            result.error(sendWithParam.result, sendWithParam.errMsg);
        } catch (HTTPException | JSONException | IOException e) {
            // HTTP响应码错误
            log.info("腾讯短信下发失败");
        }
        return result;
    }

    public  static String sendSms(String phone,String authCode){
        String result ="";
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient httpClient = httpClientBuilder.build();
        try {
            // 时间戳
            String timestamp = DateUtil.dateToStr(new Date());
            String signature = getSignature(Y_SID, Y_TOKEN, timestamp);

            String url = new StringBuffer(Y_API_URL).append("/").append(Y_VERSION).append("/Accounts/").append(Y_SID).append("/Messages/templateSMS").append("?sig=").append(signature).toString();


            TemplateSMS templateSMS = new TemplateSMS();
            templateSMS.setAppId(Y_APP_ID);
            templateSMS.setTemplateId(Y_TEMPLATE_ID);
            templateSMS.setTo(phone);
            templateSMS.setParam(authCode);
            Gson gson = new Gson();
            String body = gson.toJson(templateSMS);
            body = "{\"templateSMS\":" + body + "}";


            HttpPost httppost = new HttpPost(url);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-Type", "application/json" + ";charset=utf-8");
            String src = Y_SID + ":" + timestamp;
            String auth = null;

            auth = EncryptUtil.base64Encoder(src);
            httppost.setHeader("Authorization", auth);
            BasicHttpEntity requestBody = new BasicHttpEntity();
            requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
            requestBody.setContentLength(body.getBytes("UTF-8").length);
            httppost.setEntity(requestBody);
            // 执行客户端请求
            HttpResponse response = httpClient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String nonceStr = RandomCode.getNonceStr();
        System.out.println(nonceStr);
        sendMsg("15515779364", nonceStr);
    }

    public static String getSignature(String accountSid, String authToken, String timestamp) throws Exception {
        String sig = accountSid + authToken + timestamp;
        String signature = EncryptUtil.md5Digest(sig);
        return signature;
    }


}
