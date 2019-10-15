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
@TableName("sys_depts")
public class Dept extends Model<Dept> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,id
     */
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 完整路径
     */
    private String parentIds;
    /**
     * 可用,0:不可用默认,1:可用
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
        return this.deptId;
    }

}
