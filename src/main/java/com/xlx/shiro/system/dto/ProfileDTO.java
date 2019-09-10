package com.xlx.shiro.system.dto;

import lombok.Data;

/**
 * profile
 *
 * @author xielx on 2019/9/8
 */
@Data
public class ProfileDTO {
	
	private Long userId;
	private Long deptId;
	private String avatarName;
	private String avatarUrl;
	private String userName;
	private Integer gender;
	private String phone;
	private String mail;
	
	private String roleName;
	private String deptName;
	private String roleIds;
	private String description;
	
	
}
