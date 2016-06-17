package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * @author ilebedyuk
 */

@Component
public class AppConf {

    private ObjectMapper mapper = new ObjectMapper();
    private DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy.MM.dd");
    private static final String INDEX_PREFIX = "test";

    public void insert(String name, long value) {
        try {
            Date timestamp = new Date();
            DateTime dateTime = new DateTime(timestamp);
            byte[] json = mapper.writeValueAsBytes(new Document(timestamp, name, value));
            ElasticInserter.getBulkProcessor().add(new IndexRequest(INDEX_PREFIX + dateFormatter.print(dateTime), "own", null).source(json));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void generateDocuments(){
        Random random = new Random();
        for (long i = 0L; i < 10L; i++) {
            insert("UPDATE", random.nextInt(1000));
        }

        try {
            ElasticInserter.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
