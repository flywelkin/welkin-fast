package io.gitee.welkinfast.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.admin.mapper.dao.SysDept;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
public interface SysDeptService extends IService<SysDept> {


    SysDept getDeptById(String id);

    void saveDept(SysDept sysDept);

    void updateDept(SysDept sysDept);

    void deleteById(String id);

}

