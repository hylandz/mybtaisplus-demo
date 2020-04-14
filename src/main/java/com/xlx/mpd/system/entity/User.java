package com.xlx.mpd.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author xlx
 * @since 2020-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "sys_user",autoResultMap = true)
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    /**
     * 主键,自增
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 部门id
     */
    @TableField(value = "dept_id",jdbcType = JdbcType.BIGINT)
    private Long deptId;

    /**
     * 昵称
     */
    @TableField(value = "nick_name",jdbcType = JdbcType.VARCHAR)
    private String nickName;

    /**
     * 头像url
     */
    @TableField(value = "avatar_url",jdbcType = JdbcType.VARCHAR)
    private String avatarUrl;

    /**
     * 用户名
     */
    @TableField(value = "user_name",jdbcType = JdbcType.VARCHAR)
    private String userName;

    /**
     * 真名
     */
    @TableField(value = "real_name",jdbcType = JdbcType.VARCHAR)
    private String realName;

    /**
     * 密码
     */
    @TableField(value = "password",jdbcType = JdbcType.VARCHAR)
    private String password;

    /**
     * 盐值
     */
    @TableField(value = "salt",jdbcType = JdbcType.VARCHAR)
    @NotNull
    private String salt;

    /**
     * cookie使用
     */
    @TableField(value = "token",jdbcType = JdbcType.VARCHAR)
    @NotNull
    private String token;

    /**
     * 性别,1:男;2:女;0:保密,默认
     */
    @TableField(value = "gender",jdbcType = JdbcType.INTEGER)
    private Integer gender;

    /**
     * 出生日期,yyyy-MM-dd
     */
    @TableField(value = "birth",jdbcType = JdbcType.DATE)
    private LocalDate birth;

    /**
     * 邮箱
     */
    @TableField(value = "email",jdbcType = JdbcType.VARCHAR)
    @Email
    private String email;

    /**
     * 联系方式
     */
    @TableField(value = "phone",jdbcType = JdbcType.VARCHAR)
    private String phone;

    /**
     * 乐观锁
     */
    @TableField(value = "version",jdbcType = JdbcType.INTEGER)
    @Version
    private Integer version;

    /**
     * 逻辑删除,1:已删除;0:未删除
     */
    @TableField(value = "deleted",jdbcType = JdbcType.INTEGER)
    private Integer deleted;

    /**
     * 用户状态,枚举,1:正常,2:锁定等
     */
    @TableField(value = "status",jdbcType = JdbcType.INTEGER)
    private Integer status;
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create",jdbcType = JdbcType.BIGINT,fill = FieldFill.INSERT)
    private Long gmtCreate;


    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified",jdbcType = JdbcType.BIGINT,fill = FieldFill.UPDATE)
    private Long gmtModified;
    
    
    /**
     * 年龄,非数据库字段
     */
    @TableField(exist = false)
    private Integer age;
    
    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
