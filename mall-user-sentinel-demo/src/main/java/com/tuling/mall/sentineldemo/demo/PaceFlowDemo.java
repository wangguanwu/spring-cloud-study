package com.tuling.mall.sentineldemo.demo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guanwu
 * @created on 2022-08-25 16:37:38
 * <p>
 * 流控demo: 匀速器模式（漏桶算法）
 **/
public class PaceFlowDemo {

    private final static String KEY = "abc";

    private static volatile CountDownLatch countDown;

    private static final Integer requestQps = 100;
    private static final Integer count = 10;
    private static final AtomicInteger done = new AtomicInteger();
    private static final AtomicInteger pass = new AtomicInteger();
    private static final AtomicInteger block = new AtomicInteger();

    static void resetCounter() {
        done.set(0);
        pass.set(0);
        block.set(0);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Pace behavior");
        countDown = new CountDownLatch(1);

        initPaceFlowRule();
        simulatePulseFlow();
        countDown.await();

        System.out.println("Done! ");
        System.out.println("Total pass:" + pass.get() + ", total block: " + block.get());

        TimeUnit.SECONDS.sleep(2);

        resetCounter();
        countDown = new CountDownLatch(1);
        initDefaultFlowRule();
        simulatePulseFlow();
        countDown.await();
        System.out.println("Done! ");
        System.out.println("Total pass:" + pass.get() + ", total block: " + block.get());
        System.exit(0);
    }

    private static void initPaceFlowRule() {
        List<FlowRule> rules = new ArrayList<>();

        FlowRule rule = new FlowRule();
        rule.setResource(KEY);
        rule.setCount(count);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        //CONTROL_BEHAVIOR_RATE_LIMITER means requests more than threshold will be queueing in the queue,
        // until the queueing time is more than {@link FlowRule#maxQueueingTimeMs}, the requests will be rejected.
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
        rule.setMaxQueueingTimeMs(20 * 1000);

        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    private static void initDefaultFlowRule() {
        List<FlowRule> rules = new ArrayList<>();

        FlowRule rule = new FlowRule();
        rule.setResource(KEY);
        rule.setCount(count);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        //CONTROL_BEHAVIOR_DEFAULT means requests more than threshold will be rejected immediately.
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        rule.setMaxQueueingTimeMs(20 * 1000);

        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    private static void simulatePulseFlow() {

        for (int i = 0; i < requestQps; i++) {
            new Thread(PaceFlowDemo::execute).start();
        }

    }


    private static void execute() {
        long startTime = TimeUtil.currentTimeMillis();

        Entry entry = null;
        try {
            entry = SphU.entry(KEY);
        } catch (BlockException ex) {
            System.err.println("Blocked...");
            block.incrementAndGet();
        } catch (Exception ex) {
            //do nothing
        } finally {
            if (null != entry) {
                entry.exit();
                pass.incrementAndGet();
                long cost = TimeUtil.currentTimeMillis() - startTime;
                System.out.println(TimeUtil.currentTimeMillis() + " one request pass," +
                        "cost " + cost + " ms");
            }

        }
        try {
            TimeUnit.MILLISECONDS.sleep(1L);
        } catch (InterruptedException e) {
            //ignore
        }

        if (done.incrementAndGet() >= requestQps) {
            countDown.countDown();
        }
    }


}
