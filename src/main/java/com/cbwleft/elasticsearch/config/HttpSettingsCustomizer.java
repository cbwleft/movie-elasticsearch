package com.cbwleft.elasticsearch.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.searchbox.client.AbstractJestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.boot.autoconfigure.elasticsearch.jest.HttpClientConfigBuilderCustomizer;
import org.springframework.stereotype.Component;

import static io.searchbox.client.AbstractJestClient.ELASTIC_SEARCH_DATE_FORMAT;

@Component
public class HttpSettingsCustomizer implements HttpClientConfigBuilderCustomizer {

    /**
     * 解决Date成员无法正确序列化为ElasticSearch时间格式的bug，
     * 因为使用SpringBoot自动配置会覆盖默认JestClient的gson序列化配置，此处手动设置避免。
     * @see AbstractJestClient#gson
     * @see JestClientFactory#getObject
     * @param builder
     */
    @Override
    public void customize(HttpClientConfig.Builder builder) {
        Gson gson = new GsonBuilder()
                .setDateFormat(ELASTIC_SEARCH_DATE_FORMAT)
                .create();
        builder.gson(gson);
    }

}
