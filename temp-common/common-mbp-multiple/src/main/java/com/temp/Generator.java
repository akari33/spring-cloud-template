package com.temp;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.temp.common.base.BaseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

public class Generator {

    private static final String URL = "url";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String PACKAGE_NAME = "com.temp";


    public static void main(String[] args) {
        //获取指定输出目录../com.temp
        String outputDir =
                System.getProperty("user.dir") + "/temp-common/common-mbp-multiple/src/main/java";
        //获取xml存放目录
        String xmlOutputDir =
                System.getProperty("user.dir") + "/temp-common/common-mbp-multiple/src/main/resources/com/temp/mapper";
        //获取配置文件
        Properties properties = getProperties();
        FastAutoGenerator
                .create(properties.getProperty(URL), properties.getProperty(USERNAME), properties.getProperty(PASSWORD))
                .globalConfig(builder -> {
                    builder.author("author") // 设置作者
                            .enableSpringdoc()// 开启 swagger 模式
                            .outputDir(outputDir); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> typeRegistry.getColumnType(metaInfo)))
                .packageConfig(builder -> {
                    builder.parent(PACKAGE_NAME) // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .entity("model")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, xmlOutputDir)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            .enableLombok() // 开启 lombok 模式
                            .disableSerialVersionUID() // 不生成 serialVersionUID
//                            .enableFileOverride() // 覆盖已生成文件
                            .superClass(BaseEntity.class)// 设置实体父类
                            .addSuperEntityColumns("id", "create_time", "update_time", "is_deleted"); // 自定义实体，公共字段
                    builder
//                            .addInclude("", "") // 设置需要生成的表名
                            .addTablePrefix("t_"); // 设置过滤表前缀
                })
                .templateConfig(builder -> {
                    builder.disable(TemplateType.CONTROLLER); // 禁用默认模板, 这里不生成controller
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    /**
     * 生成模板配置文件
     */


    /**
     * 读取properties文件
     */
    public static Properties getProperties() {
        //读取resources下的generator.properties文件
        Properties props = new Properties();
        try {
            InputStream in = Generator.class.getClassLoader().getResourceAsStream("generator.properties");
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
