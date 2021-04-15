package io.gitee.welkinfast.im;

import io.gitee.welkinfast.im.distributed.LocalNodeProcesser;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author yuanjg
 * @CreateTime 2021/03/21 09:19
 * @Version 1.0.0
 */
@SpringBootTest
public class ImAppTest {

    @Autowired
    CuratorFramework curatorFramework;
    @Autowired
    private LocalNodeProcesser localNodeProcesser;

    @Test
    public void test1() throws Exception {
        curatorFramework
                .create()
                .creatingParentsIfNeeded()
                .withProtection()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/managePath");

    }
}
