package com.upwork.shortenerapi.config;

import com.upwork.shortenerapi.domain.CounterRange;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.util.Objects;

/**
 * ShortenerCounterConfiguration
 * Configures counter from zookeeper
 */
@Configuration
@Slf4j
public class ShortenerCounterConfiguration {

    private final String shortenerPath;

    private final String rangeSize;

    public ShortenerCounterConfiguration(@Value("${shortener.zookeeper.path}") String shortenerPath,
                                         @Value("${shortener.counter.rangeSize}") String rangeSize) {
        this.shortenerPath = shortenerPath;
        this.rangeSize = rangeSize;
    }

    @Bean
    public CounterRange counterRange(ZooKeeper zooKeeper) {

        if (!exists(zooKeeper, shortenerPath)) {
            createCounter(zooKeeper);
        }

        BigInteger zookeeperCounter = getCounter(zooKeeper);

        log.info("ShortenerCounterConfiguration#counterRange - Zookeeper counter obtained. zookeeperCounter={}", zookeeperCounter);
        BigInteger rangeSizeNumber = new BigInteger(rangeSize);
        BigInteger start = zookeeperCounter.add(rangeSizeNumber);
        BigInteger end = zookeeperCounter.add(rangeSizeNumber.multiply(BigInteger.TWO));

        updateCounter(zooKeeper, start);

        log.info("ShortenerCounterConfiguration#counterRange - Counter Range configured. start={} end={}",
                start,
                end);

        return CounterRange.builder()
                .start(start)
                .current(start)
                .end(end)
                .build();
    }

    private void createCounter(ZooKeeper zooKeeper) {
        String counter = "0";
        try {
            String path = zooKeeper.create(shortenerPath, counter.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            log.info("ShortenerCounterConfiguration#createCounter - Shortener counter has been created. path={}", path);
        } catch (KeeperException | InterruptedException ke) {
            log.error("");
            throw new RuntimeException(ke);
        }
    }

    private boolean exists(ZooKeeper zooKeeper, String path) {
        try {
            Stat stat = zooKeeper.exists(path, true);
            return Objects.nonNull(stat);
        } catch (InterruptedException | KeeperException e) {
            log.error("");
            throw new RuntimeException(e);
        }
    }

    private BigInteger getCounter(ZooKeeper zooKeeper) {
        try {
            Stat counterStat =  new Stat();
            byte[] data = zooKeeper.getData(shortenerPath, true, counterStat);
            String counterStr = new String(data);
            return new BigInteger(counterStr);
        } catch (InterruptedException | KeeperException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateCounter(ZooKeeper zooKeeper, BigInteger counter) {
        Stat counterStat = null;
        try {
            counterStat = zooKeeper.exists(shortenerPath, true);
            if (Objects.nonNull(counterStat)) {
                zooKeeper.setData(shortenerPath, counter.toString().getBytes(), counterStat.getVersion());
            }
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
