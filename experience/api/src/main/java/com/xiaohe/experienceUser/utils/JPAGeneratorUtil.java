package com.xiaohe.experienceUser.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.xiaohe.experienceUser.modules.generatecode.AutoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.*;

/**
 * 此处是自定义生成jpa格式的代码（不包含xml,impl层等） 如果不自定义，可以设置templateConfig（）里面的参数，默认生成
 *
 * @author admin
 */

public class JPAGeneratorUtil {
    private static Logger log = LoggerFactory.getLogger(JPAGeneratorUtil.class);
    private static final String SQL = "SELECT * FROM ";// 数据库操作

    public static void autoCode(Properties rb, Integer model, List<AutoVo> autoVos) {

//		Properties rb = new Properties();
//		/// code/src/main/resources/jpa.properties
//		FileInputStream in = new FileInputStream(new File("src/main/resources/application.properties"));
//		// FileInputStream in = new FileInputStream(args[0].toString());
//		rb.load(in);
//		log.info(rb.get("url").toString());
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        globalConfig(rb, mpg, model);
        // 数据源配置
        dataSourceConfig(rb, mpg);
        // 策略配置
        strategyConfig(rb, mpg);
        // 包配置
        packageConfig(rb, mpg);
        // 自定义输出配置
        injectionConfig(rb, mpg, model);
        //
        templateConfig(mpg);
        // 执行生成
        mpg.execute();
    }

    private static void injectionConfig(Properties rb, AutoGenerator mpg, Integer model) {
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】 ${cfg.abc}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList;
        if (model == 1) {
            focList = getMybatisConfigs(rb);
        } else {
            focList = getJpaConfigs(rb);
        }

        getVueList(rb, focList);

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
    }

    private static List<FileOutConfig> getMybatisConfigs(Properties rb) {
        // 自定义 xxListIndex.html 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/mybatis/Dao.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirMybatis").toString(), rb.get("parent").toString(), rb.get("dao").toString())
                        + tableInfo.getEntityName() + "Dao.java";
            }
        });
        focList.add(new FileOutConfig("/templates/mybatis/Dao.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirMybatis").toString(), rb.get("parent").toString(), rb.get("xml").toString())
                        + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        focList.add(new FileOutConfig("/templates/mybatis/Service.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirMybatis").toString(), rb.get("parent").toString(), rb.get("service").toString())
                        + tableInfo.getEntityName() + "Service.java";
            }
        });
        focList.add(new FileOutConfig("/templates/mybatis/ServiceImpl.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirMybatis").toString(), rb.get("parent").toString(), rb.get("impl").toString())
                        + tableInfo.getEntityName() + "ServiceImpl.java";
            }
        });
        focList.add(new FileOutConfig("/templates/mybatis/Controller.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirMybatis").toString(), rb.get("parent").toString(),
                        rb.get("controller").toString()) + tableInfo.getEntityName() + "Controller.java";
            }

        });
        focList.add(new FileOutConfig("/templates/mybatis/Entity.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirMybatis").toString(), rb.get("parent").toString(), rb.get("entity").toString())
                        + tableInfo.getEntityName() + ".java";
            }
        });
        focList.add(new FileOutConfig("/templates/mybatis/menu.sql.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirMybatis").toString(), rb.get("parent").toString(),
                        rb.get("menu").toString()) + "menu.sql";
            }

        });
        return focList;
    }

    private static List<FileOutConfig> getJpaConfigs(Properties rb) {
        // 自定义 xxListIndex.html 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/jpa/jpa.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirJpa").toString(), rb.get("parent").toString(), rb.get("jpa").toString())
                        + tableInfo.getEntityName() + "Repository.java";
            }
        });

        //根据springBoot版本动态生成代码
        if ("1.5.9".equals(rb.get("boot.version").toString())) {
            focList.add(new FileOutConfig("/templates/jpa/service_1.5.9.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return getUrl(rb.get("outputDirJpa").toString(), rb.get("parent").toString(), rb.get("service").toString())
                            + tableInfo.getEntityName() + "Service.java";
                }
            });
            focList.add(new FileOutConfig("/templates/jpa/serviceImpl_1.5.9.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return getUrl(rb.get("outputDirJpa").toString(), rb.get("parent").toString(), rb.get("impl").toString())
                            + tableInfo.getEntityName() + "ServiceImpl.java";
                }
            });
            focList.add(new FileOutConfig("/templates/jpa/controller_1.5.9.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return getUrl(rb.get("outputDirJpa").toString(), rb.get("parent").toString(),
                            rb.get("controller").toString()) + tableInfo.getEntityName() + "Controller.java";
                }

            });
        } else {
            focList.add(new FileOutConfig("/templates/jpa/service_2.0.4.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return getUrl(rb.get("outputDirJpa").toString(), rb.get("parent").toString(), rb.get("service").toString())
                            + tableInfo.getEntityName() + "Service.java";
                }
            });
            focList.add(new FileOutConfig("/templates/jpa/serviceImpl_2.0.4.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return getUrl(rb.get("outputDirJpa").toString(), rb.get("parent").toString(), rb.get("impl").toString())
                            + tableInfo.getEntityName() + "ServiceImpl.java";
                }
            });
            focList.add(new FileOutConfig("/templates/jpa/controller_2.0.4.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return getUrl(rb.get("outputDirJpa").toString(), rb.get("parent").toString(),
                            rb.get("controller").toString()) + tableInfo.getEntityName() + "Controller.java";
                }

            });
        }
        focList.add(new FileOutConfig("/templates/jpa/entity.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirJpa").toString(), rb.get("parent").toString(), rb.get("entity").toString())
                        + tableInfo.getEntityName() + ".java";
            }
        });
        //分页对象
        focList.add(new FileOutConfig("/templates/jpa/query.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirJpa").toString(), rb.get("parent").toString(), rb.get("entity").toString())
                        + "Query" + tableInfo.getEntityName() + ".java";
            }
        });


        focList.add(new FileOutConfig("/templates/jpa/menu.sql.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirJpa").toString(), rb.get("parent").toString(),
                        rb.get("menu").toString()) + "menu.sql";
            }

        });
        return focList;
    }

    private static void getVueList(Properties rb, List<FileOutConfig> focList) {

        focList.add(new FileOutConfig("/templates/vue/data.js.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirVue").toString(), null,
                        rb.get("js").toString()) + "data.js";
            }

        });
        focList.add(new FileOutConfig("/templates/vue/api.js.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirVue").toString(), null,
                        rb.get("js").toString()) + "api.js";
            }

        });
        focList.add(new FileOutConfig("/templates/vue/index.scss.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirVue").toString(), null,
                        rb.get("js").toString()) + "index.scss";
            }

        });
        focList.add(new FileOutConfig("/templates/vue/index.vue.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getUrl(rb.get("outputDirVue").toString(), null,
                        rb.get("js").toString()) + StringUtil.toLowerCaseFirstOne(tableInfo.getEntityName()) + ".vue";
            }

        });
    }

    private static String getUrl(String outputDir, String parent, String real) {
        String strTemp;
        if (parent != null) {
            strTemp = outputDir + parent.replace(".", "/") + "/" + real + "/";
        } else {
            strTemp = outputDir + "/" + real + "/";
        }
        return strTemp.replace("/", "//");

    }

    private static void templateConfig(AutoGenerator mpg) {
        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
        tc.setController(null);
        tc.setService(null);
        tc.setEntity(null);
        tc.setMapper(null);
        tc.setXml(null);
        tc.setServiceImpl(null);
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。全部为空这不使用默认的模板
        mpg.setTemplate(tc);
    }

    private static void packageConfig(Properties rb, AutoGenerator mpg) {
        PackageConfig pc = new PackageConfig();
        pc.setParent(rb.get("parent").toString());
        pc.setController(rb.get("controller").toString());
        pc.setEntity(rb.get("entity").toString());
        pc.setService(rb.get("service").toString());
        pc.setMapper(rb.get("dao").toString());
        mpg.setPackageInfo(pc);
    }

    private static void globalConfig(Properties rb, AutoGenerator mpg, Integer model) {
        GlobalConfig gc = new GlobalConfig();
        if (model == 1) {
            gc.setOutputDir(rb.get("outputDirMybatis").toString());
        } else {
            gc.setOutputDir(rb.get("outputDirJpa").toString());
        }
        gc.setOpen(true);
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(false);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor(rb.get("author").toString());
        mpg.setGlobalConfig(gc);
    }

    private static void strategyConfig(Properties rb, AutoGenerator mpg) {
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        if (!StringUtils.isEmpty(rb.get("tablePrefix"))) {
            String[] arr = rb.get("tablePrefix").toString().split(";");
            strategy.setTablePrefix(arr);// 此处可以修改为您的表前缀
        }
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        if (!StringUtils.isEmpty(rb.get("include"))) {
            strategy.setInclude(rb.get("include").toString().split(";")); // 需要生成的表
        } else {
            strategy.setInclude(getTableName(rb)); // 需要生成的表
        }
        if (!StringUtils.isEmpty(rb.get("exclude"))) {
            strategy.setExclude(rb.get("exclude").toString().split(";"));
        }
        strategy.setEntityColumnConstant(true);
        mpg.setStrategy(strategy);
    }

    private static void dataSourceConfig(Properties rb, AutoGenerator mpg) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                log.info("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName(rb.get("spring.datasource.driver-class-name").toString());
        dsc.setUsername(rb.get("spring.datasource.username").toString());
        dsc.setPassword(rb.get("spring.datasource.password").toString());
        dsc.setUrl(rb.get("spring.datasource.url").toString());
        mpg.setDataSource(dsc);
    }

    /**
     * 得到所有的表名称
     *
     * @return
     */
    public static String[] getTableName(Properties rb) {
        List<String> list = getTableList(rb);
        return list.toArray(new String[list.size()]);
    }

    /**
     * 获取表名
     *
     * @param rb
     * @return
     */
    public static List<String> getTableList(Properties rb) {
        List<String> list = new ArrayList<String>();
        try {
            Connection conn = getConnection(rb);
            Statement stmt = (Statement) conn.createStatement();
            ResultSet rs = stmt.executeQuery("show tables; ");
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 得到数据库连接
     *
     * @param rb
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    private static Connection getConnection(Properties rb) throws ClassNotFoundException, SQLException {
        Class.forName(rb.get("spring.datasource.driver-class-name").toString());
        return (Connection) DriverManager
                .getConnection(rb.get("spring.datasource.url") + "&user=" + rb.get("spring.datasource.username") + "&password=" + rb.get("spring.datasource.password"));
    }

    /**
     * 获取表中所有字段名称
     *
     * @param tableName 表名
     * @return
     */
    public static List<String> getColumnNames(String tableName, Properties rb) {
        List<String> columnNames = new ArrayList<>();
        //与数据库的连接
        Connection conn = null;
        try {
            conn = getConnection(rb);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            log.error("getColumnNames failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    log.error("getColumnNames close pstem and connection failure", e);
                }
            }
        }
        return columnNames;
    }

    /**
     * 获取表中字段的所有注释
     *
     * @param tableName
     * @return
     */
    public static List<String> getColumnComments(String tableName, Properties rb) {
        List<String> columnTypes = new ArrayList<>();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>();//列名注释集合
        Connection conn = null;
        ResultSet rs = null;
        try { //与数据库的连接
            conn = getConnection(rb);
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    log.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return columnComments;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("close connection failure", e);
            }
        }
    }
}