package org.datacenter.utils;

import java.util.ArrayList;
import java.util.List;

import org.datacenter.model.Resource;

public class TreeUtil {
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


	    /**
	     * @return
	     * @Author
	     * @Description //TODO 获取根节点的子节点
	     * @Date 2018/10/30 0030 下午 11:37
	     * @Param
	     */
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
}
