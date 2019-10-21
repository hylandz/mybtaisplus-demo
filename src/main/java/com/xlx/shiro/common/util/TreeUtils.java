package com.xlx.shiro.common.util;

import com.xlx.shiro.system.dto.TreeDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形菜单工具类
 *
 * @author xielx on 2019/9/5
 */
public class TreeUtils {
	
	protected TreeUtils(){}
	
	/**
	 * 节点对象排序
	 * 思路:
	 *   1.遍历节点集合里的每个对象
	 *   2.取出该对象1的父节点
	 *   3.如果父节点==null||==0,说明是一个topNode,return?
	 *   4.不等,又遍历一次,取出该节点对象2的id
	 *   5.如果节点2的id==节点1的父节点id
	 *   6.说明节点2是爸爸,节点1是儿子,return?
	 *
	 *   7.创建根节点root,返回它
	 * @param nodes 菜单
	 * @param <T> 泛型
	 * @return tree
	 */
	public static <T> TreeDTO<T> build(List<TreeDTO<T>> nodes){
		if (nodes == null || nodes.size() == 0){
			return null;
		}
		
		List<TreeDTO<T>> topNodes = new ArrayList<>();
		//最高级节点
		nodes.forEach(child -> {
			//该节点的父id
			String pid = child.getParentId();//假设child的爸爸id=1
			//父节点id=0说明就是一个独立的顶层节点
			if (pid == null || "0".equals(pid)){
				topNodes.add(child);
				return;
			}
			
			//不是最高级节点
			for (TreeDTO<T> node2 : nodes){
				//当前节点的id
				String id = node2.getId();//假设node2的id=1
				if (id != null && id.equals(pid)){
					//node2的id和child爸爸的id一样:爸爸=node2,儿子=child
					node2.getChildren().add(child);
					child.setHasParent(true);
					node2.setHasChildren(true);
					return;
				}
			}
		});
		
		//
		TreeDTO<T> root = new TreeDTO<>();
		root.setId("0");
		root.setParentId("");
		root.setHasParent(false);
		root.setHasChildren(true);
		root.setChecked(true);
		root.setChildren(topNodes);
		root.setText("根节点");
		Map<String,Object> state = new HashMap<>(16);
		state.put("opened",true);
		root.setState(state);
		return root;
		
	}
	
	/**
	 * 1.循环遍历该节点集合里的每个节点对象1
	 * 2.取出节点对象1的父节点pid
	 * 3.如果pid==null||==id参数
	 * 4.说明是topNode,return?
	 * 5.不等
	 * 6.又遍历一次,取出节点对象2的id
	 * 7.如果id==id参数(这个如何能判断对象1,2的关系?)
	 * 8.说明对象2是爸爸,对象1是儿子?
	 * 9.返回排序号的节点集合
	 * @param nodes 节点集合
	 * @param idParam id参数
	 * @param <T> 泛型
	 * @return 集合
	 */
	public static <T> List<TreeDTO<T>> buildList(List<TreeDTO<T>> nodes,String idParam){
		if (nodes == null || nodes.size() == 0){
			return new ArrayList<>();
		}
		
		//最高级节点
		List<TreeDTO<T>> topNode = new ArrayList<>();
		nodes.forEach( child -> {
			//该节点的父id
			String pId = child.getParentId();
			if (pId == null || idParam.equals(pId)){
				topNode.add(child);
				return;
			}
			
			//child父亲的不为空/不于idParam相等
			nodes.forEach(node -> {
				String id = node.getId();
				if (id != null && id.equals(idParam)){//node的id和idParam相等
					//说明node是父亲,child是儿子??????
					node.getChildren().add(child);
					child.setHasParent(true);
					node.setHasChildren(true);
				}
			});
			
		});
		return topNode;
	}
}
