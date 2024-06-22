package com.jencruz.batch_jobs;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
// @EnableBatchProcessing
@ComponentScan(basePackages = {
        "com.jencruz.config",
        "com.jencruz.service",
        "com.jencruz.listener",
        "com.jencruz.reader",
        "com.jencruz.writer",
        "com.jencruz.processor",
        "com.jencruz.controller"
})
@EnableAsync
@EnableScheduling
public class BatchJobsApplication {

    public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
//        ConfigurableApplicationContext context = new SpringApplicationBuilder(BatchJobsApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
//        Job job = context.getBean(Job.class);
//        jobLauncher.run(job, BatchJobsApplication.timeStamp());
        SpringApplication.run(BatchJobsApplication.class, args);
    }

//    public static JobParameters timeStamp() {
//        return new JobParametersBuilder()
//                .addString("timeStamp", LocalDateTime.now().toString())
//                .toJobParameters();
//    }
}

