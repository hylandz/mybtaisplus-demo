package com.xlx.ssmshiro.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xlx
 * @since 2019-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_resources")
public class Resources extends Model<Resources> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "resource_id", type = IdType.AUTO)
    private Long resourceId;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 图标
     */
    private String icon;
    /**
     * 类型,菜单?按钮
     */
    private String type;
    /**
     * 资源url
     */
    private String url;
    /**
     * 父节点id
     */
    private Long parentId;
    /**
     * 完整节点id
     */
    private String parentIds;
    /**
     * 资源权限
     */
    private String permission;
    /**
     * 是否可用,0:不可,默认;1:可用
     */
    private boolean available;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;


    @Override
    protected Serializable pkVal() {
        return this.resourceId;
    }

}
