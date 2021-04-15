package io.gitee.welkinfast.im.distributed.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 服务节点对象实体
 *
 * @Author yuanjg
 * @CreateTime 2021/03/21 11:40
 * @Version 1.0.0
 */
public class NodeEntity implements Serializable {

    private static final long serialVersionUID = -499010884211304846L;

    /**
     * 分布式节点的Id,zookeeper负责生成
     */
    private long id;

    /**
     * Netty 服务 IP
     */
    private String host;

    /**
     * Netty 服务 端口
     */
    private Integer port;

    public NodeEntity() {
    }

    public NodeEntity(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("id=").append(id);
        sb.append(", host='").append(host).append('\'');
        sb.append(", port=").append(port);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NodeEntity node = (NodeEntity) o;
        return Objects.equals(host, node.host) &&
                Objects.equals(port, node.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, host, port);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
