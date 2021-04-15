package io.gitee.welkinfast.im.distributed;

import com.alibaba.fastjson.JSONObject;
import io.gitee.welkinfast.im.distributed.client.WebSocketClient;
import io.gitee.welkinfast.im.distributed.entity.NodeEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 分布式节点的协调客户端，
 * 通过zk同步分布式节点数据并为远程节点创建客户端用于消息转发
 *
 * @Author yuanjg
 * @CreateTime 2021/03/21 12:18
 * @Version 1.0.0
 */
@Slf4j
public class RemoteNodeProcesser {

    public static final ConcurrentHashMap<Long, WebSocketClient> WORKER_ROUTER = new ConcurrentHashMap<>();

    private final CuratorFramework curatorFramework;

    public RemoteNodeProcesser(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    /**
     * 初始化工作路由节点管理器
     */
    public void initWorkerRouter() {
        try {
            PathChildrenCacheListener childrenCacheListener = new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    log.debug("[RemoteNode] 开始监听其他的子节点:");
                    ChildData data = event.getData();
                    switch (event.getType()) {
                        case CHILD_ADDED:
                            log.debug("[RemoteNode] NODE_ADDED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            processNodeAdded(data);
                            break;
                        case CHILD_REMOVED:
                            log.debug("[RemoteNode] NODE_REMOVED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            processNodeRemoved(data);
                            break;
                        case CHILD_UPDATED:
                            log.debug("[RemoteNode] NODE_UPDATED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            break;
                        default:
                            log.debug("[RemoteNode] 节点数据为空, path={}", data == null ? "null" : data.getPath());
                            break;
                    }

                }

            };
            PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, LocalNodeClient.MANAGE_PATH, true);
            childrenCache.getListenable().addListener(childrenCacheListener);
            log.debug("[RemoteNode] 成功向Zookeeper注册监听节点事件!");
            childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 节点增加的处理,如果是本地节点或者重复节点则忽略，
     * 如果不的是重复节点则创建远程客户端
     *
     * @param data 新节点
     */
    private void processNodeAdded(ChildData data) {
        byte[] payload = data.getData();
        NodeEntity n = JSONObject.parseObject(payload, NodeEntity.class);
        long id = LocalNodeClient.getIdByPath(data.getPath());
        n.setId(id);
        if (n.equals(LocalNodeClient.getInstance().get())) {
            log.debug("[RemoteNode] 本地节点, path={}, data={}", data.getPath(), new String(data.getData()));
            return;
        }
        WebSocketClient client = WORKER_ROUTER.get(n.getId());
        if (null != client && client.getNode().equals(n)) {
            log.debug("[RemoteNode] 节点重复增加, path={}, data={}", data.getPath(), new String(data.getData()));
            return;
        }
        if (null != client) {
            log.debug("[RemoteNode] 节点更新端口, path={}, data={}", data.getPath(), new String(data.getData()));
            client.stopConnecting();
        } else {
            log.debug("[RemoteNode] 节点新增, path={}, data={}", data.getPath(), new String(data.getData()));
        }
        client = new WebSocketClient(n);
        client.start();
        WORKER_ROUTER.put(n.getId(), client);
    }

    private void processNodeRemoved(ChildData data) {
        byte[] payload = data.getData();
        NodeEntity n = JSONObject.parseObject(payload, NodeEntity.class);
        long id = LocalNodeClient.getIdByPath(data.getPath());
        n.setId(id);
        log.debug("[RemoteNode] 节点删除, path={}, data={}", data.getPath(), new String(data.getData()));
        WebSocketClient client = WORKER_ROUTER.get(n.getId());
        if (null != client) {
            client.stopConnecting();
            WORKER_ROUTER.remove(n.getId());
        }
    }


    public void printMap() {
        StringBuilder builder = new StringBuilder("======================================\n");
        RemoteNodeProcesser.WORKER_ROUTER.entrySet().stream().forEach(item -> {
            builder.append(item.getKey())
                    .append("==>")
                    .append(JSONObject.toJSONString(item.getValue()))
                    .append("\n");
        });
        builder.append("======================================\n");
        log.debug(builder.toString());
    }
}
