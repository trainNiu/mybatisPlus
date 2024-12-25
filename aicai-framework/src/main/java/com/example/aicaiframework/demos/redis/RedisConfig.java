package com.example.aicaiframework.demos.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private final static Logger logger = LoggerFactory.getLogger(RedisConfig.class);


    @Bean(name = "redisTemplateAdapter")
    public RedisTemplateAdapter<String> redisTemplate(RedisConnectionFactory factory) {
        logger.info("开始初始化redis连接");
        RedisTemplateAdapter<String> template = new RedisTemplateAdapter();
        template.setConnectionFactory(factory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(stringRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        logger.info("初始化redis连接成功");
        return template;
    }


//    /**
//     * 连接池配置信息
//     */
//    @Bean
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        //最大连接数
//        jedisPoolConfig.setMaxTotal(100);
//        //最小空闲连接数
//        jedisPoolConfig.setMinIdle(20);
//        //当池内没有可用连接时，最大等待时间
//        jedisPoolConfig.setMaxWaitMillis(10000);
//        //其他属性可以自行添加
//        return jedisPoolConfig;
//    }


//    @Bean(name = "redisTemplate")
//    //@SuppressWarnings("all")
//    public <V> RedisTemplateAdapter<String, V> redisTemplate(
//                                                      @Value("${spring.redis.host}") String redisHost,
//                                                      @Value("${spring.redis.port}") Integer redisPort,
//                                                      @Value("${spring.redis.password}") String redisPwd,
//                                                      @Value("${spring.redis.database}") Integer redisDB) {
//        logger.info("开始初始化redis连接");
//        RedisTemplateAdapter<String, V> template = new RedisTemplateAdapter();
//        template.setConnectionFactory(this.redisConnectionFactory(redisHost, redisPort, redisPwd, redisDB, this.jedisPoolConfig()));
////        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
////        ObjectMapper om = new ObjectMapper();
////        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
////        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
////        jackson2JsonRedisSerializer.setObjectMapper(om);
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        // key采用String的序列化方式
//        template.setKeySerializer(stringRedisSerializer);
//        // hash的key也采用String的序列化方式
//        template.setHashKeySerializer(stringRedisSerializer);
//        // value序列化方式采用jackson
//        template.setValueSerializer(stringRedisSerializer);
//        // hash的value序列化方式采用jackson
//        template.setHashValueSerializer(stringRedisSerializer);
//        template.afterPropertiesSet();
//        logger.info("初始化redis连接成功");
//        return template;
//    }
//
//
//    /**
//     * 配置
//     *
//     * @param host
//     * @param port
//     * @param pwd
//     * @param dataBase
//     * @param jedisPoolConfig
//     * @return
//     */
//    protected RedisConnectionFactory redisConnectionFactory(String host, int port, String pwd, int dataBase, JedisPoolConfig jedisPoolConfig) {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        //设置redis服务器的host或者ip地址
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setPassword(pwd);
//        redisStandaloneConfiguration.setDatabase(dataBase);
//
//        //获得默认的连接池构造
//        //这里需要注意的是，edisConnectionFactoryJ对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，对此
//        //我们用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)
//                JedisClientConfiguration.builder();
//        //修改我们的连接池配置
//        jpcf.poolConfig(jedisPoolConfig);
//        //通过构造器来构造jedis客户端配置
//        JedisClientConfiguration jedisClientConfiguration = jpcf.build();
//
//        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
//    }
//
//


}
