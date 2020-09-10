package io.gitee.welkinfast.admin.controller;

import io.gitee.welkinfast.admin.controller.vo.DeptVo;
import io.gitee.welkinfast.admin.mapper.dao.SysDept;
import io.gitee.welkinfast.admin.service.SysDeptService;
import io.gitee.welkinfast.common.page.PageRequest;
import io.gitee.welkinfast.common.page.PageResult;
import io.gitee.welkinfast.common.response.CustomResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 部门管理控制器
 * @Author yuanjg
 * @CreateTime 2020/08/14 19:14
 * @Version 1.0.0
 */
@Api(tags  = "部门管理")
@RestController
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @ApiOperation("根据ID获取部门信息")
    @PreAuthorize("hasAuthority('sys:dept:view')")
    @GetMapping("/dept/{id}")
    public CustomResponse getById(@PathVariable("id") String id) {
        SysDept sysDept = sysDeptService.getDeptById(id);
        DeptVo deptVo = dao2Vo(sysDept);
        return CustomResponse.OK(deptVo);
    }

    @ApiOperation("保存部门信息")
    @PreAuthorize("hasAuthority('sys:dept:add')")
    @PostMapping("/dept")
    public CustomResponse save(@Validated @RequestBody DeptVo deptVo) {
        SysDept sysDept = vo2Dao(deptVo);
        sysDeptService.saveDept(sysDept);
        return CustomResponse.OK("保存成功");
    }

    @ApiOperation("根据ID更新部门信息")
    @PreAuthorize("hasAuthority('sys:dept:update')")
    @DeleteMapping("/dept")
    public CustomResponse updateById(@Validated @RequestBody DeptVo deptVo) {
        SysDept sysDept = vo2Dao(deptVo);
        sysDeptService.updateDept(sysDept);
        return CustomResponse.OK("更新成功");
    }

    @ApiOperation("根据ID删除部门信息")
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    @PutMapping("/dept/{id}")
    public CustomResponse deleteById(@PathVariable("id") String id) {
        sysDeptService.deleteById(id);
        return CustomResponse.OK("删除成功");
    }

    @ApiOperation("分页获取用户信息列表")
    @PreAuthorize("hasAuthority('sys:dept:list')")
    @PostMapping("/dept/list")
    public CustomResponse<PageResult<SysDept>> getUserList(@RequestBody PageRequest<DeptVo> pageRequest) {
        PageResult<SysDept> result = sysDeptService.getUserList(pageRequest);
        return CustomResponse.OK(result);
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
