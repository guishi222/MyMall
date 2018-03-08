package com.MyMall.service;

import com.MyMall.common.ServerResponse;
import com.MyMall.pojo.Category;

import java.util.List;

public interface IcategoryService {
	ServerResponse<String> addCategory(String categoryName, Integer parentId);
	
	ServerResponse<String> updateCategoryName(Integer id, String categoryName);
	
	ServerResponse<List<Category>> selectOneChild(Integer categoryId);

	ServerResponse<List<Integer>> selectChildAll(Integer categoryId);
	
}
