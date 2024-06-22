package com.jencruz.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

// Inputs integer
@Component
public class FileItemReader implements ItemReader<Integer> {

    private final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    private Integer i = 0;

    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("File Item Reader");
        Integer item;
        if (i < list.size()) {
            item = list.get(i);
            i++;
            return item;
        }
        return null;
    }
}
