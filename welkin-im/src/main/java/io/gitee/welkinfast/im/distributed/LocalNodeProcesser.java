package io.gitee.welkinfast.im.distributed;

import io.gitee.welkinfast.im.utils.IOUtil;
import org.apache.curator.framework.CuratorFramework;

/**
 * 当前节点的协调客户端
 *
 * @Author yuanjg
 * @CreateTime 2021/03/21 12:07
 * @Version 1.0.0
 */
public class LocalNodeProcesser {

    private final CuratorFramework curatorFramework;

    public LocalNodeProcesser(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    public LocalNodeClient initLocalNode(Integer port) {
        LocalNodeClient instance = LocalNodeClient.getInstance();
        instance.setCuratorFramework(curatorFramework);
        String ip = IOUtil.getHostAddress();
        instance.setNodeEntity(ip, port);
        instance.publish();
        return instance;
    }

}