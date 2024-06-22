package com.jencruz.service;

import com.jencruz.request.JobParamRequest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class JobService {

    private final JobLauncher jobLauncher;

    @Qualifier(value = "batchJob")
    private final Job batchJob;

    @Qualifier(value = "taskletJob")
    private final Job taskletJob;

    public JobService(JobLauncher jobLauncher, Job batchJob, Job taskletJob) {
        this.jobLauncher = jobLauncher;
        this.batchJob = batchJob;
        this.taskletJob = taskletJob;
    }

    @Async
    public void startJob(String jobName, ArrayList<JobParamRequest> jobParamRequests) {
        Map<String, JobParameter<?>> params = new HashMap<>();
        params.put("currentTime", new JobParameter<>(LocalDateTime.now().toString(), String.class));

        jobParamRequests.stream().forEach(jobParamRequest -> {
            params.put(jobParamRequest.getParamKey(), new JobParameter<>(jobParamRequest.getParamValue(), String.class));
        });

        JobParameters jobParameters = new JobParameters(params);

        try {
            if (jobName.equals("tasklet_job")) {
                jobLauncher.run(taskletJob, jobParameters);
            } else if (jobName.equals("item_job")) {
                jobLauncher.run(batchJob, jobParameters);
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }
}
