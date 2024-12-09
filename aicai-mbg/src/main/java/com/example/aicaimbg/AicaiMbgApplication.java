package com.example.aicaimbg;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.example.aicaiframework.demos.entity.base.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import java.util.Scanner;

@Slf4j
public class AicaiMbgApplication {


    /**
     * 默认代码保存路径
     */
    private static final String DEFAULT_SRC_MAIN_JAVA = "/src/main/java";
    /**
     * 默认Mapper.xml生成路径
     */
    private static final String DEFAULT_MAPPER_XML = "/src/main/resources/mapper";
    /**
     * 数据源JDBC驱动配置项名称
     */
    private static final String DRIVER_NAME_KEY = "code.jdbc.driver-class";
    /**
     * 数据源连接URL配置项名称
     */
    private static final String JDBC_URL_KEY = "code.jdbc.url";
    /**
     * 数据库username配置项名称
     */
    private static final String USER_NAME_KEY = "code.jdbc.username";
    /**
     * 数据源password配置项名称
     */
    private static final String PASSWORD_KEY = "code.jdbc.password";
    /**
     * 配置属性
     */
    private static Properties configProperties;
    /**
     * 当前工程根目录
     */
    private static String projectRootPath = System.getProperty("user.dir");

    static {
        //加载配置
        configProperties = loadConfig();
    }

    /**
     * 禁止外部实例化
     */
    private AicaiMbgApplication() {

    }


    public static void main(String[] args) {
        //输入需要生成代码的模块名称t_report_detail
        String moduleName = scanner("模块(module)名称");
        //输入模块统一的父级包名
        String parentPackage = scanner("父级包名");
        //输入当前模块的子级包名
        String subPackage = scanner("子级包名(无子级输入#)").replaceAll("#","");
        //输入作者
        String author = scanner("作者");
        //输入表名,多个表名以英文逗号隔开
        String tables = scanner("表名(多个以','隔开)");
        //输入表前缀
        String prefixes = scanner("表前缀(无前缀输入#)").replaceAll("#","");
        //是否生成resultMap
        String yesNo = scanner("是否生成ResultMap,输入(Y/N)");
        boolean hashResultMap = yesNo.equalsIgnoreCase("Y");
        //执行代码生成
        doGenerator(moduleName,parentPackage,subPackage,author,tables,prefixes,hashResultMap);
    }

    /**
     * 执行代码生成
     * @param moduleName 模块名称
     * @param parentPackage 父级包名
     * @param subPackage 子级包名
     * @param author 作者
     */
    private static void doGenerator(String moduleName,String parentPackage,String subPackage,String author,String tables,String prefixes,boolean hashResultMap) {
        AutoGenerator generator = new AutoGenerator(getDataSourceConfig());
        generator.global(getGlobalConfig(moduleName,author));
        generator.packageInfo(getPackageConfig(parentPackage,subPackage,moduleName));
        generator.strategy(getStrategyConfig(tables,prefixes,hashResultMap));
        generator.execute();
    }

    /**
     * 全局配置
     * @param moduleName 模块名称
     * @param author 作者
     * @return
     */
    private static GlobalConfig getGlobalConfig(String moduleName, String author) {
        String outPutDir = getOutPath(moduleName,DEFAULT_SRC_MAIN_JAVA);
        log.info("代码输出路径: {}",outPutDir);
        return new GlobalConfig.Builder()
                .outputDir(outPutDir)
                .author(author)
                .dateType(DateType.ONLY_DATE)
                .disableOpenDir()
                .enableSwagger()
                .commentDate("yyyy-MM-dd")
                .fileOverride()
                .build();
    }

    /**
     * 数据库配置
     * @return
     */
    private static DataSourceConfig getDataSourceConfig() {
        String url = configProperties.getProperty(JDBC_URL_KEY);
        String username = configProperties.getProperty(USER_NAME_KEY);
        String password = configProperties.getProperty(PASSWORD_KEY);
        return new DataSourceConfig.Builder(url,username,password)
                .dbQuery(new MySqlQuery())
                .schema("mybatis-plus")
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler())
                .build();
    }

    /**
     * 包配置
     * @param parent
     * @param subName
     * @param moduleName
     * @return
     */
    private static PackageConfig getPackageConfig(String parent, String subName, String moduleName) {
        String xmlPath = getOutPath(moduleName,DEFAULT_MAPPER_XML);
        return new PackageConfig.Builder()
                .parent(parent)
                .moduleName(subName)
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml,xmlPath))
                .build();
    }

    /**
     * 策略配置
     * @return
     */
    private static StrategyConfig getStrategyConfig(String tables, String prefixes, boolean hasResultMap) {
        StrategyConfig.Builder builder = new StrategyConfig.Builder();
        //表策略
        builder.enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                .addInclude(tables.split(","))
                .addTablePrefix(prefixes.split(","));

        //实体策略
        builder.entityBuilder()
                .superClass(BaseEntity.class)
                .disableSerialVersionUID()
                .enableLombok()
                .enableTableFieldAnnotation()
                .enableActiveRecord()
                .idType(IdType.AUTO)
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .logicDeleteColumnName("is_active")
                .logicDeletePropertyName("isActive")
                .addSuperEntityColumns("id","is_active","create_time","create_by","create_by_name","update_time","update_by","update_by_name")
                .addTableFills(new Column("create_time", FieldFill.INSERT))
                .addTableFills(new Property("updateTime",FieldFill.UPDATE))
                .formatFileName("%sEntity");

        //Service策略
        builder.serviceBuilder()
                .superServiceClass(BaseService.class)
                .superServiceImplClass(BaseServiceImpl.class)
                .formatServiceFileName("I%sService")
                .formatServiceImplFileName("%sServiceImpl");

        //Mapper规则
        Mapper.Builder mapperBuilder = builder.mapperBuilder()
                .superClass(BaseMapper.class)
                .enableMapperAnnotation();
        if (hasResultMap) {
            mapperBuilder.enableBaseResultMap();
        }
        mapperBuilder.enableBaseColumnList()
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sMapper");

        //Controller策略
        builder.controllerBuilder()
                .superClass(BaseController.class)
                .enableRestStyle()
                .formatFileName("%sController");

        return builder.build();
    }

    /**
     * 组合输出路径
     * @param targetPath
     * @return
     */
    private static String getOutPath(String moduleName,String targetPath) {
        String outPutDir = projectRootPath + File.separator + moduleName + targetPath;
        //if (PlatformUtil.isWindows()) {
        //    outPutDir = outPutDir.replaceAll("/+|\\\\+","\\\\");
        //} else {
        //}
        outPutDir = outPutDir.replaceAll("/+|\\\\+","/");
        return outPutDir;
    }

    /**
     * 加载配置
     * @return
     */
    private static Properties loadConfig() {
        try(InputStream in = AicaiMbgApplication.class.getClassLoader().getResourceAsStream("config.properties")){
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取用户输入
     * @param tip
     * @return
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入").append(tip).append(":");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String result = scanner.nextLine();
            if (!StringUtils.isBlank(result)) {
                return result;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "! ");
    }

}
