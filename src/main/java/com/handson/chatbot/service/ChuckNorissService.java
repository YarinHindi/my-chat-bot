package com.handson.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;

import java.io.IOException;

@Service
public class ChuckNorissService{
    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    @Autowired
    ObjectMapper om;
    public Integer getTotalRes() throws IOException {


        Request request = new Request.Builder()
                .url("https://api.chucknorris.io/jokes/search?query=big")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        ChuckNorissResults res = om.readValue(response.body().string(), ChuckNorissResults.class);
        return res.getTotal();

    }
    static class ChuckNorissResults{
        public Integer getTotal() {
            return total;
        }

        Integer total;
    }


}
