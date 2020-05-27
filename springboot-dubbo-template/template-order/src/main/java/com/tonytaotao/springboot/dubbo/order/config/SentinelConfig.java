package com.tonytaotao.springboot.dubbo.order.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * sentinel限流熔断配置
 * @author tonytaotao
 */
@Configuration
public class SentinelConfig {

    /**
     * 启动@SentinelResource注解
     * @return
     */
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    /**
     * 手动添加sentinel规则配置，本地生效；
     * 可以选择在sentinel控制台配置，自动推送到客户端
     */
    //@PostConstruct
    private void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();

        FlowRule rule = new FlowRule();
        rule.setResource("sayHello");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(2);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);

        //降级规则，可以多个degradeRule rule
        //DegradeRuleManager.getRules()可以获取到已经设置的降级规则
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        //设置资源名称，sentinel降级都是以资源为单位进行
        degradeRule.setResource("circuitBreaker");
        //使用异常统计降级,分钟统计,滑动时间窗口
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        //异常数达到的数量阈值
        degradeRule.setCount(2);
        //秒级时间窗口,该值必须有且必须大于零，否则降级将无法生效
        degradeRule.setTimeWindow(1);
        degradeRules.add(degradeRule);
        //重新加载限流规则，此处将覆盖原有的限流，所以如果想要不覆盖
        //请使用DegradeRuleManager.getRules()获取到的加入到rules中
        DegradeRuleManager.loadRules(degradeRules);
    }
}
