package com.xlx.shiro.system.service;

import com.xlx.shiro.common.util.TreeUtils;
import com.xlx.shiro.system.dao.DeptMapper;
import com.xlx.shiro.system.dto.TreeDTO;
import com.xlx.shiro.system.entity.Dept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		List<Dept> deptList = this.deptMapper.selectDeptTree();
		
		//封装deptList
		deptList.forEach(dept -> {
			TreeDTO<Dept> treeDTO = new TreeDTO<>();
			treeDTO.setId(dept.getDeptId().toString());
			treeDTO.setText(dept.getDeptName());
			treeDTO.setParentId(dept.getParentId().toString());
			treeDTOList.add(treeDTO);
		});
		
		return TreeUtils.build(treeDTOList);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<Dept> findAllDepts(String deptName){
	  return deptMapper.selectAllDept(deptName);
	}
	
}
