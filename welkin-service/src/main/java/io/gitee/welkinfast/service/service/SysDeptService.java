package io.gitee.welkinfast.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.common.page.PageResult;
import io.gitee.welkinfast.service.mapper.dao.SysDept;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
public interface SysDeptService extends IService<SysDept> {


    SysDept getDeptById(String id);

    void saveDept(SysDept sysDept);

    void updateDept(SysDept sysDept);

    void deleteById(String id);

    PageResult<SysDept> getUserList(SysDept sysDept, Integer current, Integer size);
}

