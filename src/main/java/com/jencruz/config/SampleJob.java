package com.jencruz.config;

import com.jencruz.listener.FirstStepListener;
import com.jencruz.listener.SampleJobListener;
import com.jencruz.processor.FileItemProcessor;
import com.jencruz.reader.FileItemReader;
import com.jencruz.writer.FileItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SampleJob {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final FirstTasklet firstTasklet;
    private final SecondTasklet secondTasklet;
    private final SampleJobListener sampleJobListener;
    private final FirstStepListener firstStepListener;
    private final FileItemReader fileItemReader;
    private final FileItemProcessor fileItemProcessor;
    private final FileItemWriter fileItemWriter;

    public SampleJob(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            FirstTasklet firstTasklet,
            SecondTasklet secondTasklet,
            SampleJobListener sampleJobListener,
            FirstStepListener firstStepListener,
            FileItemReader fileItemReader,
            FileItemProcessor fileItemProcessor,
            FileItemWriter fileItemWriter
    ) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.firstTasklet = firstTasklet;
        this.secondTasklet = secondTasklet;
        this.sampleJobListener = sampleJobListener;
        this.firstStepListener = firstStepListener;
        this.fileItemReader = fileItemReader;
        this.fileItemProcessor = fileItemProcessor;
        this.fileItemWriter = fileItemWriter;
    }

    @Bean
    public Job taskletJob() {
        return new JobBuilder("tasklet_job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(sampleJobListener)
                .start(firstStep())
                .build();
    }

    @Bean
    public Job batchJob() {
        return new JobBuilder("item_job", jobRepository)
                .listener(sampleJobListener)
                .incrementer(new RunIdIncrementer())
                .start(secondStep())
                .next(firstStep())
                .build();
    }

    public Step firstStep() {
        return new StepBuilder("first_step", jobRepository)
                .listener(firstStepListener)
                .tasklet(firstTasklet, platformTransactionManager)
                .build();
    }

    public Step secondStep() {
        return new StepBuilder("batch_step", jobRepository)
                .<Integer, Long>chunk(2, platformTransactionManager)
                .reader(fileItemReader)
                .processor(fileItemProcessor)
                .writer(fileItemWriter)
                .build();
    }

}
