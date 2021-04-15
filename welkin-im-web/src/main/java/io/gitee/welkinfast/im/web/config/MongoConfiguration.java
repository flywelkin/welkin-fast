package io.gitee.welkinfast.im.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoDatabaseFactorySupport;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/11 18:39
 * @Version 1.0.0
 */
@Configuration
public class MongoConfiguration {

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactorySupport mongoDatabaseFactory,
                                       MappingMongoConverter mappingMongoConverter) {
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory, mappingMongoConverter);
        return mongoTemplate;
    }
}

