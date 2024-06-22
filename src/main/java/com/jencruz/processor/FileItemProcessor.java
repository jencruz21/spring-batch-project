package com.jencruz.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

// inputs integer and outputs long
// integer is from the itemreader
// long will is from the itemwriter
@Component
public class FileItemProcessor implements ItemProcessor<Integer, Long> {
    @Override
    public Long process(Integer item) throws Exception {
        System.out.println("Item Processor");
        return Long.valueOf(item * 2L);
    }
}
