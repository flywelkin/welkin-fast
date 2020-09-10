package io.gitee.welkinfast.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.admin.controller.vo.DeptVo;
import io.gitee.welkinfast.admin.mapper.SysDeptMapper;
import io.gitee.welkinfast.admin.mapper.dao.SysDept;
import io.gitee.welkinfast.admin.service.SysDeptService;
import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import io.gitee.welkinfast.common.page.PageRequest;
import io.gitee.welkinfast.common.page.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public SysDept getDeptById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        SysDept queryResult = this.getById(id);
        if (ObjectUtils.isEmpty(queryResult)) {
            throw new CustomExcption(CustomErrorType.DATA_NULL);
        }
        return queryResult;
    }


    @Override
    public void saveDept(SysDept sysDept) {
        this.save(sysDept);
    }


    @Override
    public void updateDept(SysDept sysDept) {
        boolean success = this.updateById(sysDept);
        if (!success) {
            throw new CustomExcption(CustomErrorType.DATA_UPDATE_FAIL);
        }
    }

    @Override
    public void deleteById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        boolean success = this.removeById(id);
        if (!success) {
            throw new CustomExcption(CustomErrorType.DATA_DELETE_FAIL);
        }
    }

    @Override
    public PageResult<SysDept> getUserList(PageRequest<DeptVo> pageRequest) {
        Page<SysDept> iPage = new Page(pageRequest.getCurrent(), pageRequest.getSize());
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc(SysDept.COL_ORDER_NUM);
        if (pageRequest.paramsIsNotEmpty() && pageRequest.getParams().getParentId() != null) {
            queryWrapper.eq(SysDept.COL_PARENT_ID, pageRequest.getParams().getParentId());
        } else {
            queryWrapper.eq(SysDept.COL_PARENT_ID, 0);
        }
        Page<SysDept> sysUserPage = sysDeptMapper.selectPage(iPage, queryWrapper);
        PageResult<SysDept> result = new PageResult();
        result.setCurrent(sysUserPage.getCurrent());
        result.setSize(sysUserPage.getSize());
        result.setTotal(sysUserPage.getTotal());
        result.setRecords(sysUserPage.getRecords());
        return result;
    }


}

