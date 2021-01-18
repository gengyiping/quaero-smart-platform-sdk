package com.quaero.quaerosmartplatform.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;


public class CodeGenerator {
    /**
     * 测试 run 执行
     * <p>
     * </p>
     */
    @Test
    public void generateCode() {
        String packageName = "com.quaero.quaerosmartplatform";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        generateByTables(serviceNameStartWithI, packageName, "POR1","OWOR");
    }
    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:sqlserver://192.168.123.11:1433;DatabaseName=Test0513";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        dataSourceConfig.setTypeConvert(new MySqlTypeConvert(){
            @Override
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                String t = fieldType.toLowerCase();
                //如果是datetime类型，转换成Date字段类型
                if(t.contains("datetime")){
                    return DbColumnType.DATE;
                }
                return (DbColumnType) super.processTypeConvert(globalConfig,fieldType);
            }
        });
        dataSourceConfig.setDbType(DbType.SQL_SERVER)
                .setUrl(dbUrl)
                .setUsername("sa")
                .setPassword("JXquaero2018")
                .setDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组

        config.setActiveRecord(false)
                .setAuthor("wuhanzhang@")
                .setOutputDir("d:\\TestCodeGen")
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("domain.entity")
                                .setMapper("domain.mapper")
                ).execute();
    }
    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }
}
