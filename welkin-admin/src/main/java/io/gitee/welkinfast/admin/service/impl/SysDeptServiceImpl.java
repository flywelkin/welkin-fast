package io.gitee.welkinfast.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.admin.mapper.SysDeptMapper;
import io.gitee.welkinfast.admin.mapper.dao.SysDept;
import io.gitee.welkinfast.admin.service.SysDeptService;
import io.gitee.welkinfast.common.error.WelkinErrorType;
import io.gitee.welkinfast.common.error.WelkinExcption;
import io.gitee.welkinfast.common.id.WelkinIdGenerator;
import org.apache.commons.lang3.StringUtils;
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


    @Override
    public SysDept getDeptById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new WelkinExcption(WelkinErrorType.PARAMETER_VALIDATION_ERROR);
        }
        SysDept queryResult = this.getById(id);
        if (ObjectUtils.isEmpty(queryResult)) {
            throw new WelkinExcption(WelkinErrorType.DATA_NULL);
        }
        return queryResult;
    }


    @Override
    public void saveDept(SysDept sysDept) {
        sysDept.setId(WelkinIdGenerator.getId());
        this.save(sysDept);
    }


    @Override
    public void updateDept(SysDept sysDept) {
        boolean success = this.updateById(sysDept);
        if (!success) {
            throw new WelkinExcption(WelkinErrorType.DATA_UPDATE_FAIL);
        }
    }

    @Override
    public void deleteById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new WelkinExcption(WelkinErrorType.PARAMETER_VALIDATION_ERROR);
        }
        boolean success = this.removeById(id);
        if (!success) {
            throw new WelkinExcption(WelkinErrorType.DATA_DELETE_FAIL);
        }
    }


}

