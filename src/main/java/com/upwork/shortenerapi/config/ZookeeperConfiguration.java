package com.upwork.shortenerapi.config;


import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Configuration
public class ZookeeperConfiguration {

    private final String zookeeperHost;

    CountDownLatch connectionLatch = new CountDownLatch(1);

    public ZookeeperConfiguration(@Value("${zookeeper.host}") String zookeeperHost) {
        this.zookeeperHost = zookeeperHost;
    }

    @Bean
    @Scope("singleton")
    public ZooKeeper zooKeeper() throws IOException, InterruptedException {
        ZooKeeper zoo = new ZooKeeper(zookeeperHost, 2000, we -> {
            if (we.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connectionLatch.countDown();
            }
        });

        connectionLatch.await();
        return zoo;
    }



}
