package io.gitee.welkinfast.admin.controller;

import io.gitee.welkinfast.admin.controller.vo.DeptVo;
import io.gitee.welkinfast.admin.mapper.dao.SysDept;
import io.gitee.welkinfast.admin.service.SysDeptService;
import io.gitee.welkinfast.common.response.WelkinResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 部门管理控制器
 * @Author yuanjg
 * @CreateTime 2020/08/14 19:14
 * @Version 1.0.0
 */
@Api(description = "部门管理")
@RestController
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @ApiOperation("根据ID获取部门信息")
    @GetMapping("/dept/{id}")
    public WelkinResult getById(@PathVariable("id") String id) {
        SysDept sysDept = sysDeptService.getDeptById(id);
        DeptVo deptVo = dao2Vo(sysDept);
        return WelkinResult.OK(deptVo);
    }

    @ApiOperation("保存部门信息")
    @PostMapping("/dept")
    public WelkinResult save(@Validated @RequestBody DeptVo deptVo) {
        SysDept sysDept = vo2Dao(deptVo);
        sysDeptService.saveDept(sysDept);
        return WelkinResult.OK("保存成功");
    }

    @ApiOperation("根据ID更新部门信息")
    @DeleteMapping("/dept")
    public WelkinResult updateById(@Validated @RequestBody DeptVo deptVo) {
        SysDept sysDept = vo2Dao(deptVo);
        sysDeptService.updateDept(sysDept);
        return WelkinResult.OK("更新成功");
    }

    @ApiOperation("根据ID删除部门信息")
    @PutMapping("/dept/{id}")
    public WelkinResult deleteById(@PathVariable("id") String id) {
        sysDeptService.deleteById(id);
        return WelkinResult.OK("删除成功");
    }

    private DeptVo dao2Vo(SysDept sysDept) {
        DeptVo deptVo = new DeptVo();
        BeanUtils.copyProperties(sysDept, deptVo);
        return deptVo;
    }

    private SysDept vo2Dao(DeptVo deptVo) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(deptVo, sysDept);
        return sysDept;
    }
}
