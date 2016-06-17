package com.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author ilebedyuk
 */
public class Document {
    @JsonProperty("@timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date timestamp;

    @JsonProperty("node")
    private String node;

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private long value;

    public Document() {
    }

    public Document(Date timestamp, String name, long value) {
        this.timestamp = timestamp;
        this.node = System.getProperty("weblogic.Name", "UNKNOWN");
        this.name = name;
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Document{" +
                "timestamp=" + timestamp +
                ", node='" + node + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
