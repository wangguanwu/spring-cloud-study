package com.tuling.mall.sentineldemo.demo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;

import java.util.Collections;

/**
 * @author guanwu
 * @created on 2022-08-25 15:59:19
 **/
public class AuthorityDemo {

    private final static String RESOURCE_NAME = "testABC";

    public static void main(String[] args) {

        System.out.println("============Test for black list============");
        initBlackListRules();
        testReqApi("appA", RESOURCE_NAME);
        testReqApi("appB", RESOURCE_NAME);
        testReqApi("appC", RESOURCE_NAME);
        testReqApi("appD", RESOURCE_NAME);
        testReqApi("appE", RESOURCE_NAME);

        System.out.println("============Test for while list============");

        initWhiteListRules();
        testReqApi("appA", RESOURCE_NAME);
        testReqApi("appB", RESOURCE_NAME);
        testReqApi("appC", RESOURCE_NAME);
        testReqApi("appD", RESOURCE_NAME);
        testReqApi("appE", RESOURCE_NAME);
    }

    private static void testReqApi(String appName, String resource) {
        ContextUtil.enter(resource, appName);
        Entry entry = null;
        try  {
            entry = SphU.entry(resource);
            System.out.println(String.format("Passed for resource %s, app:%s", resource, appName));
        } catch(BlockException ex) {
            System.err.println(String.format("Blocked for resource %s, app:%s", resource, appName));

        } finally {
            if (entry != null) {
                entry.exit();
            }
            ContextUtil.exit();
        }
    }

    private static void initWhiteListRules() {
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(RESOURCE_NAME);
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rule.setLimitApp("appE,appA");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }

    private static void initBlackListRules() {
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(RESOURCE_NAME);
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule.setLimitApp("appA,appB");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }
}
