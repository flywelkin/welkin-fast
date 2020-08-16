package io.gitee.welkinfast.admin.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value = "io-gitee-welkinfast-admin-controller-vo-DeptVo")
@Data
public class DeptVo implements Serializable {
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String id;

    /**
     * 机构名称
     */
    @ApiModelProperty(value = "机构名称")
    private String name;

    /**
     * 上级机构ID，一级机构为0
     */
    @ApiModelProperty(value = "上级机构ID，一级机构为0")
    private Long parentId;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String lastUpdateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date lastUpdateTime;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    @ApiModelProperty(value = "是否删除  -1：已删除  0：正常")
    private Byte delFlag;

    private static final long serialVersionUID = 1L;
}
