package io.gitee.welkinfast.admin.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.gitee.welkinfast.security.entity.DefaultUserDetails;
import io.gitee.welkinfast.security.util.WelkinUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * insert语句添加 创建人，创建时间，更新人更，更新新时间 字段
 * update语句添加 更新人更，更新新时间 字段
 *
 * @Description mybatis plus 字段自动填充配置，
 * @Author yuanjg
 * @CreateTime 2020/08/16 16:45
 * @Version 1.0.0
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        DefaultUserDetails currentUserInfo = WelkinUserUtils.getCurrentUserInfo();
        if (currentUserInfo != null) {
            this.strictInsertFill(metaObject, "createBy", String.class, currentUserInfo.getId());
            this.strictInsertFill(metaObject, "lastUpdateBy", String.class, currentUserInfo.getId());
        }
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "lastUpdateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "delFlag", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        DefaultUserDetails currentUserInfo = WelkinUserUtils.getCurrentUserInfo();
        if (currentUserInfo != null) {
            this.strictInsertFill(metaObject, "lastUpdateBy", String.class, currentUserInfo.getId());
        }
        this.strictInsertFill(metaObject, "lastUpdateTime", Date.class, new Date());
    }
}
