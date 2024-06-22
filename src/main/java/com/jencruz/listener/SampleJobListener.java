package com.jencruz.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class SampleJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before Job: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Job Params: " + jobExecution.getJobParameters());
        System.out.println("Job Exec Context: " + jobExecution.getExecutionContext());
        System.out.println("Job Exec Id " + jobExecution.getId());

        jobExecution.getExecutionContext().put("Job Execution Params", "Executed");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("After Job: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Job Params: " + jobExecution.getJobParameters());
        System.out.println("Job Exec Context: " + jobExecution.getExecutionContext());
        System.out.println("Job Exec Id " + jobExecution.getId());
    }
}
