package io.gitee.welkinfast.im.web;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/11 18:08
 * @Version 1.0.0
 */
@Slf4j
@SpringBootTest
public class ImWebApplicationTest {

    @Autowired
    MongoTemplate mongoTemplate;


    @Test
    public void saveTest(){
        MongoTest mongoTest = new MongoTest();
        mongoTest.setId(UUID.randomUUID().toString());
        mongoTest.setName("菜鸟教程");
        mongoTemplate.save(mongoTest);
        MongoTest mongoTest1 = new MongoTest();
        mongoTest1.setId(UUID.randomUUID().toString());
        mongoTest1.setName("菜鸟教程CSDN");
        mongoTemplate.save(mongoTest1);
    }

    @Test
    public void queryTest(){
        Query query=new Query(Criteria.where("name").is("菜鸟教程"));
        List<MongoTest> test = mongoTemplate.find(query, MongoTest.class);
        log.info("");
    }
}

@Data
@Document(collection = "welkin_test")
class MongoTest implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;
    String id;
    String name;
}
