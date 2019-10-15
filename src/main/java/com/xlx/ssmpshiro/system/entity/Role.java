package com.xlx.ssmpshiro.system.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("sys_roles")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;
    /**
     * 角色字符
     */
    private String roleKey;
    /**
     * 角色名称
     */
    private String roleName;
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
        return this.roleId;
    }

}
