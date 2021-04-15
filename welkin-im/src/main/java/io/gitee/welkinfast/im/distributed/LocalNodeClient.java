package io.gitee.welkinfast.im.distributed;

import com.alibaba.fastjson.JSONObject;
import io.gitee.welkinfast.im.distributed.entity.NodeEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 本地节点客户端,管理和发布客户端信息
 *
 * @Author yuanjg
 * @CreateTime 2021/03/24 10:10
 * @Version 1.0.0
 */
@Slf4j
public class LocalNodeClient {

    /**
     * 工作节点的父路径
     */
    public static final String MANAGE_PATH = "/im/nodes";

    /**
     * 工作节点的路径前缀
     */
    public static final String PATH_PREFIX = MANAGE_PATH + "/seq-";

    private CuratorFramework curatorFramework = null;

    /**
     * 当前节点
     */
    private static NodeEntity localNodeEntity = null;

    /**
     * 当前Znode节点的路径
     */
    private String pathRegistered = null;

    /**
     * 本地节点客户端实例
     */
    private static LocalNodeClient singleInstance = null;

    private LocalNodeClient() {
    }

    public static LocalNodeClient getInstance() {
        if (null == singleInstance) {
            singleInstance = new LocalNodeClient();
            localNodeEntity = new NodeEntity();
        }
        return singleInstance;
    }

    public void setCuratorFramework(CuratorFramework curatorFramework) {
        singleInstance.curatorFramework = curatorFramework;
    }

    /**
     * 设置本地节点ip和端口
     *
     * @param ip
     * @param port
     */
    public void setNodeEntity(String ip, int port) {
        localNodeEntity.setHost(ip);
        localNodeEntity.setPort(port);
    }

    /**
     * 在zookeeper中创建临时节点,
     * 节点的 payload
     * 为当前 nodeEntity
     * 实例
     */
    public void publish() {
        createParentIfNeeded(MANAGE_PATH);
        try {
            byte[] payload = JSONObject.toJSONBytes(localNodeEntity);
            pathRegistered = curatorFramework.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(PATH_PREFIX, payload);
            // 为LocalNode设置id
            localNodeEntity.setId(getIdByPath(pathRegistered));
            log.debug("本地节点初始话完成：{}", JSONObject.toJSONString(localNodeEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建父节点
     *
     * @param managePath 父节点路径
     */
    private void createParentIfNeeded(String managePath) {
        try {
            Stat stat = curatorFramework.checkExists().forPath(managePath);
            if (null == stat) {
                curatorFramework.create()
                        .creatingParentsIfNeeded()
                        .withProtection()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(managePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 取得节点编号
     *
     * @param path 路径
     * @return 编号
     */
    public static long getIdByPath(String path) {
        String sid = null;
        if (null == path) {
            throw new RuntimeException("节点路径有误");
        }
        int index = path.lastIndexOf(PATH_PREFIX);
        if (index >= 0) {
            index += PATH_PREFIX.length();
            sid = index <= path.length() ? path.substring(index) : null;
        }
        if (null == sid) {
            throw new RuntimeException("节点ID获取失败");
        }
        return Long.parseLong(sid);
    }

    public NodeEntity get() {
        return localNodeEntity;
    }

}
