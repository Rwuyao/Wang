package org.datacenter.utils;

import java.util.ArrayList;
import java.util.List;

import org.datacenter.model.Resource;

public class TreeUtil {
	//返回树形数据结构
	 public static List<Resource> data(List<Resource> nodes) {
	        ArrayList<Resource> rootNode = new ArrayList<>();
	             for(Resource node:nodes){
	                  if(node.getParentid().equals(0)){
	                        rootNode.add(node);
	                  }
	             }
	             for(Resource node:rootNode){
	                 List<Resource> child = getChild(node.getId(), nodes);
	                 node.setChildren(child);
	             }
	             return rootNode;
	    }
	 public static List<Resource> getChild(Integer id, List<Resource> allNode) {
	        //存放子菜单的集合
	        ArrayList<Resource> listChild = new ArrayList<>();
	        for (Resource node : allNode) {
	            if (node.getParentid().equals(id)) {
	                listChild.add(node);
	            }
	        }
	        //递归：
	        for (Resource node : listChild) {
	            node.setChildren(getChild(node.getId(), allNode));
	        }
	        if (listChild.size() == 0) {
	            return null;
	        }
	        return listChild;
	    }
	 
	 //返回对应的父节点及其子孙节点的list数据
	 public static List<Resource> data(List<Resource> nodes,Integer Parentid) {
	        ArrayList<Resource> result = new ArrayList<>();
	             for(Resource node:nodes){
	                  if(node.getId().equals(Parentid)){
	                	   result.add(node);
	                	   //遍历
	                	   getChild(node.getId(), nodes,result);
	                       break;
	                  }
	             }
	             return result;
	    }
	 public static void getChild(Integer id, List<Resource> allNode,ArrayList<Resource> result) {
	        //存放子菜单的集合
	        ArrayList<Resource> listChild = new ArrayList<>();
	        for (Resource node : allNode) {
	            if (node.getParentid().equals(id)) {
	                listChild.add(node);
	            }
	        }
	        //递归：
	        for (Resource node : listChild) {
	            getChild(node.getId(), allNode,result);
	        }
	        if (listChild.size() > 0) {
		        result.addAll(listChild);
	        }
	    }
	    
}
