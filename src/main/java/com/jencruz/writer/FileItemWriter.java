package com.jencruz.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

// long is from the itemproccesor
@Component
public class FileItemWriter implements ItemWriter<Long> {

    @Override
    public void write(Chunk<? extends Long> chunk) throws Exception {
        System.out.println("Items finally processed: ");
        chunk.getItems().forEach(System.out::println);
    }
}
