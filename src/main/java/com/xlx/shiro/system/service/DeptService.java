package com.xlx.shiro.system.service;

import com.xlx.shiro.common.util.TreeUtil;
import com.xlx.shiro.system.dao.DeptMapper;
import com.xlx.shiro.system.dto.TreeDTO;
import com.xlx.shiro.system.entity.Dept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * dept
 *
 * @author xielx on 2019/9/8
 */
@Service
public class DeptService {
	
	private static final Logger log = LoggerFactory.getLogger(DeptService.class);
	
	@Resource
	private DeptMapper deptMapper;
	
	
	/**
	 * 查询所有部门
	 *
	 * @return 部门树
	 */
	public TreeDTO<Dept> listAllDepts() {
		List<TreeDTO<Dept>> treeDTOList = new ArrayList<>();
		List<Dept> deptList = this.deptMapper.selectAllDept();
		
		//封装deptList
		deptList.forEach(dept -> {
			TreeDTO<Dept> treeDTO = new TreeDTO<>();
			treeDTO.setId(dept.getDeptId().toString());
			treeDTO.setText(dept.getDeptName());
			treeDTO.setParentId(dept.getParentId().toString());
			treeDTOList.add(treeDTO);
		});
		
		return TreeUtil.build(treeDTOList);
	}
	
	
}
