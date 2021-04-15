# welkin-im

### 介绍

&emsp;&emsp;支持分布式节点的实时通信服务器

### 包结构
```
welkin-im
├── distributed                     分布式节点管理
│   ├── client                      远程节点客户端
│   ├── entity                      节点信息实体
│   ├── LocalNodeClient.java        本地节点客户端
│   ├── LocalNodeProcesser.java     本地节点管理程序
│   └── RemoteNodeProcesser.java    远程节点客户端管理程序
├── server                          服务端
│   ├── codec                       编码/解码
│   ├── handler                     自定义channelHandler
│   ├── WsHandlerInitialzer.java    装配配channelHandler
│   └── WsServer.java               服务端启动类
├── session                         session管理
│   ├── cache                       缓存接口
│   │   ├── entity                  缓存实体
│   │   ├── impl                    缓存实现
│   ├── ChannelType.java            通道类型管理(用户/远程客户端)   
│   ├── LocalSession.java           本地session实现
│   ├── RemoteSession.java          远程session实现    
│   ├── ServerSession.java          session抽象接口    
│   └── SessionManager.java         session管理
```