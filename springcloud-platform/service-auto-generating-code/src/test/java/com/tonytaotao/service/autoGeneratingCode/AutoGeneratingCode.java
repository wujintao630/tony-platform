package com.tonytaotao.service.autoGeneratingCode;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class AutoGeneratingCode {

    @Test
    public void genetateCode() {

        // 注意修改src/main/resources/templates/controller.java.vm 中的 GlobalResult, QueryPage 包路径，因为这两个类可能放在其他jar包或者其他路径下
        //  import com.tonytaotao.service.common.base.GlobalResult;
        //  import com.tonytaotao.service.common.base.QueryPage;

        // 生成文件输出路径
        String outputDir = "E:\\WorkSpace\\IDEAworkspace\\tony-platform\\springcloud-platform\\service-producer\\src\\main\\java";
        // 作者
        String authorName = "tonytaotao";
        // 包名全路径
        String packageNameAllPath = "com.tonytaotao.service.producer";
        // 模块名
        String moduleName = "user";
        // 接口是否以I开头
        boolean serviceNameStartWithI = false;

        generateByTables(outputDir, authorName, packageNameAllPath, moduleName, serviceNameStartWithI, "user_info");
    }

     private void generateByTables(String outputDir, String authorName, String packageNameAllPath, String moduleName, boolean serviceNameStartWithI, String... tableNames) {

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(packageNameAllPath)
                     .setModuleName(moduleName)
                     .setController("controller")
                     .setService("service")
                     .setServiceImpl("service.impl")
                     .setMapper("mapper")
                     .setXml("mapper.xml")
                     .setEntity("entity");

        DbType dbType = DbType.MYSQL;
        String dbUrl = "jdbc:mysql://localhost:3306/springcloud_service_producer?useSSL=false&useUnicode=true&characterEncoding=UTF8&allowPublicKeyRetrieval=true&serverTimezone=GMT";
        String dbUserName = "root";
        String dbPassword = "123456";
        String dbDriver = "com.mysql.jdbc.Driver";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(dbType)
                        .setUrl(dbUrl)
                        .setUsername(dbUserName)
                        .setPassword(dbPassword)
                        .setDriverName(dbDriver);

         StrategyConfig strategyConfig = new StrategyConfig();
         strategyConfig.setCapitalMode(true)
                       .setRestControllerStyle(true) //controller开启restful
                       .setEntityLombokModel(true)  //支持lombok
                       .setDbColumnUnderline(true)
                        .setInclude(tableNames) // 数据表
                       .setNaming(NamingStrategy.underline_to_camel);

         GlobalConfig globalConfig = new GlobalConfig();

         globalConfig.setActiveRecord(true)
                     .setAuthor(authorName)
                     .setOutputDir(outputDir)
                     .setIdType(IdType.AUTO)
                     .setFileOverride(true) // 覆盖文件
                     .setOpen(false); // 不打开生成的文件夹

         if (! serviceNameStartWithI) {
             globalConfig.setServiceName("%sService");
         }

         AutoGenerator autoGenerator = new AutoGenerator();
         autoGenerator.setGlobalConfig(globalConfig)
                      .setDataSource(dataSourceConfig)
                      .setStrategy(strategyConfig)
                      .setPackageInfo(packageConfig);

         autoGenerator.execute();

     }

}