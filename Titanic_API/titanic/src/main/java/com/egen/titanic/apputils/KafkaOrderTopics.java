package com.egen.titanic.apputils;

public enum KafkaOrderTopics {

    CREATE("CREATE"),
    MODIFY("MODIFY"),
    CANCEL("CANCEL");

    private final String topic;

    KafkaOrderTopics(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return this.topic;
    }
}
