package com.example;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author ilebedyuk
 */
public class ElasticInserter {

    private static Client client;
    private static BulkProcessor bulkProcessor;

    public static Client get() throws Exception {

        if (client == null) {
            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", "elasticsearch-cluster")
                    .put("client.transport.sniff", true).build();
            TransportClient transportClient = TransportClient.builder()
                    .settings(settings)
                    .build();
            transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 8080));
            transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 8080));
            client = transportClient;
        }

        return client;
    }

    public static BulkProcessor getBulkProcessor() throws Exception {
        if (client == null) {
            get();
        }
        if (bulkProcessor == null) {
            bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
                @Override
                public void beforeBulk(long executionId,
                                       BulkRequest request) {
                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      BulkResponse response) {
                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      Throwable failure) {
                }
            })
                    .setBulkActions(1000)
                    .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                    .setFlushInterval(TimeValue.timeValueSeconds(5))
                    .setConcurrentRequests(1)
                    .build();
        }
        return bulkProcessor;
    }

    public static void shutdown() throws Exception {
        if (bulkProcessor != null) {
            bulkProcessor.awaitClose(10, TimeUnit.SECONDS);
            bulkProcessor = null;
        }
        if (client != null) {
            client.close();
            client = null;
        }
    }
}
