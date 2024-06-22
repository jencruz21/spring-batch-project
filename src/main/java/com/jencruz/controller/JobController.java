package com.jencruz.controller;

import com.jencruz.request.JobParamRequest;
import com.jencruz.service.JobService;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Qualifier(value = "job_controller")
@RestController
@RequestMapping(value = "/api/job")
public class JobController {

    private final JobService jobService;
    private final JobOperator jobOperator;

    public JobController(JobService jobService, JobOperator jobOperator) {
        this.jobService = jobService;
        this.jobOperator = jobOperator;
    }

    @GetMapping(value = "/start/{jobName}")
    public String startJob(@PathVariable String jobName, ArrayList<JobParamRequest> jobParamRequests) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobService.startJob(jobName, jobParamRequests);
        return "Job Started: " + jobName;
    }

    @GetMapping(value = "/stop/{jobExecutionId}")
    public String stopJob(@PathVariable Long jobExecutionId) {
        try {
            jobOperator.stop(jobExecutionId);
        } catch (NoSuchJobExecutionException |JobExecutionNotRunningException  e) {
            throw new RuntimeException(e);
        }
        return "job stopped: " + jobExecutionId;
    }
}
