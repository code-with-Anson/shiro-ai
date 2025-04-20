package com.alice.shiroai.config;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    @Value("${ai.openai.api-key}")
    private String API_KEY;
    @Value("${ai.openai.base-url}")
    private String BASE_URL;
    @Value("${ai.openai.default-deepseek-model}")
    private String DEFAULT_DEEPSEEK_MODEL;

    @Bean
    public OpenAiApi chatCompletionApi() {
        return OpenAiApi.builder()
                .apiKey(API_KEY) // 替换为你的API密钥
                .baseUrl(BASE_URL) // 替换为你的API基础URL
                .build();
    }

    @Bean
    public OpenAiChatModel openAiClient(OpenAiApi openAiApi) {
        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder().model(DEFAULT_DEEPSEEK_MODEL).build())
                .build();
    }
}
