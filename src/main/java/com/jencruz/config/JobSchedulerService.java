package com.jencruz.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobSchedulerService {

    private final JobLauncher jobLauncher;

    @Qualifier("batchJob")
    private final Job batchJob;

    public JobSchedulerService(JobLauncher jobLauncher, Job batchJob) {
        this.jobLauncher = jobLauncher;
        this.batchJob = batchJob;
    }

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void scheduleJob() {
        Map<String, JobParameter<?>> params = new HashMap<>();
        params.put("currentTime", new JobParameter<>(LocalDateTime.now().toString(), String.class));
        JobParameters jobParameters = new JobParameters(params);

        try {
            jobLauncher.run(batchJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }
}
