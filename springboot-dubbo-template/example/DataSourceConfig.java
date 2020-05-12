package com.tonytaotao.springboot.dubbo.common.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@NacosPropertySource(dataId = "init-config", groupId = "template-account", autoRefreshed = false)
public class DataSourceConfig {

    @NacosValue(value = "${mysql.jdbc.driver}")
    private String driverClassName;
    @NacosValue(value = "${mysql.jdbc.url}")
    private String url;
    @NacosValue(value = "${mysql.jdbc.username}")
    private String username;
    @NacosValue(value = "${mysql.jdbc.password}")
    private String password;
    @NacosValue(value = "${mysql.jdbc.publicKey}")
    private String publicKey;

    @NacosValue(value = "${mysql.jdbc.pool.initialSize}")
    private Integer initialSize;
    @NacosValue(value = "${mysql.jdbc.pool.maxActive}")
    private Integer maxActive;
    @NacosValue(value = "${mysql.jdbc.pool.minIdle}")
    private Integer minIdle;
    @NacosValue(value = "${mysql.jdbc.pool.maxWait}")
    private Integer maxWait;

    @NacosValue(value = "${mysql.jdbc.manager.loginName}")
    private String loginName;
    @NacosValue(value = "${mysql.jdbc.manager.loginPass}")
    private String loginPass;
    @NacosValue(value = "${mysql.jdbc.manager.loginAllowIp}")
    private String loginAllowIp;

    @Bean
    public DataSource druidDataSource() throws Exception{

        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);

        // 初始化创建的连接数
        druidDataSource.setInitialSize(initialSize);
        // 最大连接数
        druidDataSource.setMaxActive(maxActive);
        // 最小连接数
        druidDataSource.setMinIdle(minIdle);
        // 获取连接时最大等待时间（毫秒）
        druidDataSource.setMaxWait(maxWait);

        // 对支持游标的数据库性能提升巨大，例如oracle,但是使用MySQL时建议关闭
        druidDataSource.setPoolPreparedStatements(false);
        // 启用pool-prepared-statements时才会设值大于0
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(-1);
        // 检测连接是否有效的sql
        druidDataSource.setValidationQuery("SELECT 1");
        // 检测连接是否有效的超时时间（秒）
        druidDataSource.setValidationQueryTimeout(1);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(30000);

        // 数据库密码加密规则
        // 1、命令行输入 java -cp druid-1.1.20.jar com.alibaba.druid.filter.config.ConfigTools xxxxxx
        // 2、新建一个类，在main方法中执行 ConfigTools.main(new String[]{"xxxxxx"});
        // 结果显示为      privateKey:xxxxxx
        //                publicKey:xxxxxx
        //                password:xxxxxx
        Properties connectproperties = new Properties();
        connectproperties.setProperty("config.decrypt", "true");
        // 配置公钥
        connectproperties.setProperty("config.decrypt.key", publicKey);
        druidDataSource.setConnectProperties(connectproperties);

        // 开启数据库密码加密、sql监控、防火墙、过滤器、控制台
        druidDataSource.setFilters("config,stat,wall,web-stat-filter,stat-view-servlet");

        return druidDataSource;

    }

    @Bean
    public Advice advice() {
        return new DruidStatInterceptor();
    }
    @Bean
    public Advisor advisor() {
        return new RegexpMethodPointcutAdvisor("com.tonytaotao.springboot.dubbo.*.*.service.impl.*", advice());
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public StatFilter statFilter() {

        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setSlowSqlMillis(1000);
        statFilter.setMergeSql(true);
        statFilter.setDbType("mysql");

        return statFilter;
    }

    @Bean
    public WallFilter wallFilter() {

        WallConfig wallConfig = new WallConfig();
        wallConfig.setAlterTableAllow(false);
        wallConfig.setTruncateAllow(false);
        wallConfig.setDropTableAllow(false);
        wallConfig.setNoneBaseStatementAllow(false);
        wallConfig.setUpdateWhereNoneCheck(true);
        wallConfig.setSelectIntoOutfileAllow(false);

        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType("mysql");
        wallFilter.setConfig(wallConfig);
        // 被认为是攻击的sql进行LOG.error输出
        wallFilter.setLogViolation(true);
        // 被认为是攻击的sql抛出SQLException
        wallFilter.setThrowException(true);

        return wallFilter;

    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        // 配置过滤器
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        // 过滤所有
        filterRegistrationBean.addUrlPatterns("/*");

        // 免过滤
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        // 知道当前的cookie用户是谁
        filterRegistrationBean.addInitParameter("principalCookieName", "admin");
        // 知道当前的session用户是谁
        filterRegistrationBean.addInitParameter("principalSessionName", "admin");
        // 监控单个url调用的sql列表
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        // session的统计功能
        filterRegistrationBean.addInitParameter("sessionStatEnable", "true");
        // 最大session数
        filterRegistrationBean.addInitParameter("sessionStatMaxCount", "100000");

        return filterRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {

        // 配置一个拦截器
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet());

        // 只拦截druid管理页面的请求
        servletRegistrationBean.addUrlMappings("/druid/*");

        // 后台登陆用户
        servletRegistrationBean.addInitParameter("loginUsername", loginName);
        // 后台登陆密码
        servletRegistrationBean.addInitParameter("loginPassword", loginPass);
        // 是否可以重置数据源，禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "true");
        // 允许ip
        servletRegistrationBean.addInitParameter("allow", loginAllowIp == null ? "127.0.0.1" : loginAllowIp);

        return servletRegistrationBean;
    }

}
