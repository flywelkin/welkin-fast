package io.gitee.welkinfast.admin.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.gitee.welkinfast.security.entity.CustomUserDetails;
import io.gitee.welkinfast.security.CustomUserUtils;
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

    private static final String CREATE_BY = "createBy";
    private static final String LAST_UPDATE_BY = "lastUpdateBy";
    private static final String CREATE_TIME = "createTime";
    private static final String LAST_UPDATE_TIME = "lastUpdateTime";
    private static final String DEL_FLAG = "delFlag";

    @Override
    public void insertFill(MetaObject metaObject) {
        CustomUserDetails currentUserInfo = CustomUserUtils.getCurrentUserInfo();
        if (currentUserInfo != null) {
            if (metaObject.hasGetter(CREATE_BY)) {
                metaObject.setValue(CREATE_BY, null);
                this.strictInsertFill(metaObject, CREATE_BY, String.class, currentUserInfo.getUsername());
            }

            if (metaObject.hasGetter(LAST_UPDATE_BY)) {
                metaObject.setValue(LAST_UPDATE_BY, null);
                this.strictInsertFill(metaObject, LAST_UPDATE_BY, String.class, currentUserInfo.getUsername());
            }
        }

        if (metaObject.hasGetter(CREATE_TIME)) {
            metaObject.setValue(CREATE_TIME, null);
            this.strictInsertFill(metaObject, CREATE_TIME, Date.class, new Date());
        }

        if (metaObject.hasGetter(LAST_UPDATE_TIME)) {
            metaObject.setValue(LAST_UPDATE_TIME, null);
            this.strictInsertFill(metaObject, LAST_UPDATE_TIME, Date.class, new Date());
        }

        if (metaObject.hasGetter(DEL_FLAG)) {
            metaObject.setValue(DEL_FLAG, null);
            this.strictInsertFill(metaObject, DEL_FLAG, Integer.class, 0);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        CustomUserDetails currentUserInfo = CustomUserUtils.getCurrentUserInfo();
        if (currentUserInfo != null) {
            if (metaObject.hasGetter(LAST_UPDATE_BY)) {
                metaObject.setValue(LAST_UPDATE_BY, null);
                this.strictInsertFill(metaObject, LAST_UPDATE_BY, String.class, currentUserInfo.getUsername());
            }
        }
        if (metaObject.hasGetter(LAST_UPDATE_TIME)) {
            metaObject.setValue(LAST_UPDATE_TIME, null);
            this.strictInsertFill(metaObject, LAST_UPDATE_TIME, Date.class, new Date());
        }
    }
}
