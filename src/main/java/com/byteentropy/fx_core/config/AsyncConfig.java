package com.byteentropy.fx_core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {

    @Bean
    public AsyncTaskExecutor applicationTaskExecutor() {
        // Explicitly use Virtual Threads for @Async tasks
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }
}