package io.gitee.welkinfast.im.service.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/11 18:45
 * @Version 1.0.0
 */

@Data
@Document(collection = "welkin_im")
public class MessageEntity {

    String userId;
    String seqId;
    String from;
    String to;
    String content;

}
