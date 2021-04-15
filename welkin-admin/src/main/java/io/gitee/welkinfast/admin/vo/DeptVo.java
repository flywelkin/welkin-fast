package io.gitee.welkinfast.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "部门信息")
@Data
public class DeptVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "机构名称")
    private String name;
    @ApiModelProperty(value = "上级机构ID，一级机构为0")
    private String parentId;
    @ApiModelProperty(value = "排序")
    private Integer orderNum;
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新人")
    private String lastUpdateBy;
    @ApiModelProperty(value = "更新时间")
    private Date lastUpdateTime;

}
