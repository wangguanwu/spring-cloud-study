package com.tuling.mall.sentineldemo.demo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guanwu
 * @created on 2022-08-25 15:19:36
 **/
public class VegetableBirds {

    public static void main(String[] args) throws InterruptedException {
        initFlowRules();
        while(true) {
            try(Entry entry = SphU.entry("HelloWorld")) {
                System.out.println("hello world");
            } catch (BlockException ex) {

                System.out.println("blocked");
                Thread.sleep(1000l);
            }
        }
    }

    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
