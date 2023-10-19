package com.handson.chatbot.service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AmazonService {
    public static final Pattern PRODUCT_PATTERN = Pattern.compile("<span class=\\\"a-size-medium a-color-base a-text-normal\\\">([^<]+)<\\/span>.*<div class=\\\"a-row a-size-small\\\"><span aria-label=([^<]+)<span.*<span class=\\\"a-offscreen\\\">([^<]+)");

    public String searchProducts(String keyword) throws IOException {
        return parseProductHtml(getProductHtml(keyword));
    }

    private String parseProductHtml(String html) {
        String res = "";
        Matcher matcher = PRODUCT_PATTERN.matcher(html);
        while (matcher.find()) {
            res += matcher.group(1) + " - " + matcher.group(2) + ", price:" + matcher.group(3) + "<br>\n";
        }
        return res;
    }
    private String getProductHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://www.amazon.com/s?i=aps&k="+keyword+"&ref=nb_sb_noss&url=search-alias%3Daps")
                .method("GET",null)
                .addHeader("authority", "www.amazon.com")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "he-IL,he;q=0.9,en-US;q=0.8,en;q=0.7")
                .addHeader("cookie", "aws-target-data=%7B%22support%22%3A%221%22%7D; session-id=134-7744074-3310161; ubid-main=134-8403102-7381669; lc-main=en_US; session-id-time=2082787201l; AMCV_7742037254C95E840A4C98A6%40AdobeOrg=1585540135%7CMCIDTS%7C19603%7CMCMID%7C56946917420866402233582529957490836574%7CMCAAMLH-1694291192%7C6%7CMCAAMB-1694291192%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1693693592s%7CNONE%7CMCAID%7CNONE%7CvVersion%7C4.4.0; aws-target-visitor-id=1647453331099-594384.47_0; aws-ubid-main=411-4510313-8407125; remember-account=false; aws-account-alias=995553441267; regStatus=registered; aws-userInfo=%7B%22arn%22%3A%22arn%3Aaws%3Aiam%3A%3A995553441267%3Auser%2Fyarinh%22%2C%22alias%22%3A%22995553441267%22%2C%22username%22%3A%22yarinh%22%2C%22keybase%22%3A%22%22%2C%22issuer%22%3A%22http%3A%2F%2Fsignin.aws.amazon.com%2Fsignin%22%2C%22signinType%22%3A%22PUBLIC%22%7D; i18n-prefs=USD; sp-cdn=\"L5Z9:IL\"; skin=noskin; session-token=D961W7CsNIk9bCgZaFDEPbtrpAmcSc4sJfbTeqOIe69zFPLrWsI22EvXK+ZsHt8eaT/Nts3zrELZGGW/UEZsNzJokohXEFLzy41lZGsPBSMDCDAezZQkyvxIzo2SH6SeT5MawidPuGnII8K+5ZUJFnbZOPqS8vXax88ga/PDUVVjw+MlU0PS5CkSHLny8nj2qYAnUsR5pqxZlE+/+AOMFu+QWfLEAxJBlh6EohOq8vO6it9ZLM9DIdV94xNL/4u7IEC4nB4zaqyaALO76VWyyH545MD7bKUOxmXCbDSsygUCGXvZHzQxBTSvl4uEPrJeczylPPl52XUBCayrK2iXiMNmPuUCop0I; csm-hit=tb:JG1JE96585205N5WJ9Y3+s-J09YQK323W9KB8PJ3NQ4|1697366742437&t:1697366742437&adb:adblk_yes")
                .addHeader("device-memory", "8")
                .addHeader("downlink", "10")
                .addHeader("dpr", "1.5")
                .addHeader("ect", "4g")
                .addHeader("referer", "https://www.amazon.com/s?k=ipod&crid=3VBWJ14T9LCMN&sprefix=ip%2Caps%2C756&ref=nb_sb_noss_2")
                .addHeader("rtt", "50")
                .addHeader("sec-ch-device-memory", "8")
                .addHeader("sec-ch-dpr", "1.5")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"116\", \"Not)A;Brand\";v=\"24\", \"Google Chrome\";v=\"116\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-ch-ua-platform-version", "\"15.0.0\"")
                .addHeader("sec-ch-viewport-width", "1280")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36")
                .addHeader("viewport-width", "1280")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}

