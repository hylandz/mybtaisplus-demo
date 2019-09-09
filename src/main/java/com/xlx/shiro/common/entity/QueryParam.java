package com.xlx.shiro.common.entity;

import com.google.common.base.MoreObjects;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询参数
 *
 * @author xielx on 2019/9/6
 */
@Data
public class QueryParam implements Serializable {
	
	
	//页数
	private Integer pageNum = 1;
	//笔数
	private Integer pageSize = 10;
	
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
						       .add("pageNum", pageNum)
						       .add("pageSize", pageSize)
						       .toString();
	}
}
