package com.tuling.mall.sentineldemo.demo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guanwu
 * @created on 2022-08-25 19:12:40
 **/
public class SystemGuardDemo {

    private static AtomicInteger pass = new AtomicInteger();
    private static AtomicInteger block = new AtomicInteger();
    private static AtomicInteger total = new AtomicInteger();

    private static volatile boolean stop = false;
    private static final int threadCount = 100;

    private static int seconds = 60 + 40;

    public static void main(String[] args) {

        initSystemGuardRule();

        startMonitoring();

        for(int i = 0; i < threadCount; i++) {
            new Thread(SystemGuardDemo::execute).start();
        }

    }

    private static void execute() {
        while(true) {
            Entry entry = null;
            try {
                entry = SphU.entry("methodA", EntryType.IN);
                pass.incrementAndGet();
            } catch (BlockException be) {
                block.incrementAndGet();
                try {
                    TimeUnit.MILLISECONDS.sleep(20L);
                } catch (Exception ex) {
                    //ignore
                }
            } catch (Exception ex) {

            } finally {
                total.incrementAndGet();
                if (null != entry)  {
                    entry.exit();
                }
            }
        }

    }

    private static void initSystemGuardRule() {
        List<SystemRule> rules = new ArrayList<SystemRule>();
        SystemRule rule = new SystemRule();
        rule.setHighestSystemLoad(3.0);
        rule.setHighestCpuUsage(0.6);
        rule.setAvgRt(10);
        rule.setQps(20);
        rule.setMaxThread(10);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }

    private static void startMonitoring() {
        new Thread(new MonitorTask()).start();
    }

    private static class MonitorTask implements Runnable {
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            System.out.println("begin to statistic!!!");

            long oldTotal = 0;
            long oldPass = 0;
            long oldBlock = 0;

            while (!stop) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                long globalTotal = total.get();
                long oneSecondTotal = globalTotal - oldTotal;
                oldTotal = globalTotal;

                long globalPass = pass.get();
                long oneSecondPass = globalPass - oldPass;
                oldPass = globalPass;

                long globalBlock = block.get();
                long oneSecondBlock = globalBlock - oldBlock;
                oldBlock = globalBlock;

                System.out.println(seconds + " total qps is: " + oneSecondTotal);
                System.out.println(TimeUtil.currentTimeMillis() + ", total:" + oneSecondTotal
                        + ", pass:" + oneSecondPass
                        + ", block:" + oneSecondBlock);
                if (seconds-- <= 0) {
                    stop = true;
                }

            }

            long cost = System.currentTimeMillis() - start;
            System.out.println("time cost: " + cost + " ms");
            System.out.println("total:" + total.get() + ", pass:" + pass.get()
                    + ", block:" + block.get());
            System.exit(0);
        }
    }
}
