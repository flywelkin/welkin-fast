package io.gitee.welkinfast.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.admin.controller.vo.DeptVo;
import io.gitee.welkinfast.admin.mapper.dao.SysDept;
import io.gitee.welkinfast.common.page.PageRequest;
import io.gitee.welkinfast.common.page.PageResult;

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

    PageResult<SysDept> getUserList(PageRequest<DeptVo> pageRequest);
}

