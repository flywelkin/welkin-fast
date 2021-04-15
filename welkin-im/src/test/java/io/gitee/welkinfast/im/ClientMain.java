package io.gitee.welkinfast.im;

import io.gitee.welkinfast.common.id.CustomIdGenerator;
import io.gitee.welkinfast.im.distributed.entity.NodeEntity;
import io.gitee.welkinfast.im.distributed.client.WebSocketClient;
import io.gitee.welkinfast.im.proto.ImEntity;

import java.util.Scanner;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/03/21 19:13
 * @Version 1.0.0
 */
public class ClientMain {


    public static void main(String[] args) throws Exception {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setHost("127.0.0.1");
        nodeEntity.setPort(8888);
        WebSocketClient webSocketClient = new WebSocketClient(nodeEntity);
        webSocketClient.start();
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (true) {
                String s = scanner.next();
                //发送链接成功的通知
                ImEntity.Message.Builder mb =
                        ImEntity.Message
                                .newBuilder()
                                .setType(ImEntity.HeadType.MESSAGE_NOTIFICATION)
                                .setSessionId("unknown")
                                .setId(CustomIdGenerator.getNext());
                ImEntity.Message message = mb.buildPartial();
                ImEntity.MessageNotification.Builder rb =
                        ImEntity.MessageNotification.newBuilder()
                                .setMsgType(2)
                                .setTimestamp(String.valueOf(System.currentTimeMillis()))
                                .setJson("{\"message\":\"" + s + "\"}");
                ImEntity.Message pkg = message.toBuilder().setNotification(rb).build();
                webSocketClient.writeAndFlush(pkg);
            }
        }).start();
        webSocketClient.close();
        System.out.println("");
    }
}
