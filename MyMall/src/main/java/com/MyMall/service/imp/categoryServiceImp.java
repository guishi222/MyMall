package com.MyMall.service.imp;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MyMall.common.ServerResponse;
import com.MyMall.dao.CategoryMapper;
import com.MyMall.pojo.Category;
import com.MyMall.service.IcategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Service("icategoryService")
public class categoryServiceImp implements IcategoryService{
	@Autowired
	private CategoryMapper categoryMapper;
	public ServerResponse addCategory(String categoryName,Integer parentId){
		if(parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.CreateByErrorMsg("参数错误");
        }
		Category category=new Category();
		category.setName(categoryName);
		category.setParentId(parentId);
		category.setStatus(true);
		int count=categoryMapper.insertSelective(category);
		if (count>0) {
			return ServerResponse.CreateBySuccessMsg("添加成功");
		}
		return ServerResponse.CreateByErrorMsg("添加失败");
	}
	public ServerResponse updateCategoryName(Integer id,String categoryName){
		if(id == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.CreateByErrorMsg("参数错误");
        }
		Category category=new Category();
		category.setId(id);
		category.setName(categoryName);
		int count=categoryMapper.updateByPrimaryKeySelective(category);
		if (count>0) {
			return ServerResponse.CreateBySuccessMsg("修改成功");
		}
		return ServerResponse.CreateByErrorMsg("修改失败");
	}
//	查询一级子分类
	public ServerResponse<List<Category>> selectOneChild(Integer categoryId) {
		if(categoryId==null){
            return ServerResponse.CreateByErrorMsg("参数错误");
        }
		List<Category> category=categoryMapper.selectChildOne(categoryId);
		if (CollectionUtils.isEmpty(category)) {
			return ServerResponse.CreateByErrorMsg("该分类下无子分类");
		}
		return ServerResponse.CreateBySuccessData(category);
	}
/**
 * 查询所有子分类
 * */
	public ServerResponse<List<Integer>> selectChildAll(Integer categoryId){
		if(categoryId==null){
            return ServerResponse.CreateByErrorMsg("参数错误");
        }
		Set<Category> categorySet=Sets.newHashSet();
		findChildCategory(categorySet, categoryId);
		List<Integer> categoryIdList=Lists.newArrayList();
		for (Category categoryItem : categorySet) {
			categoryIdList.add(categoryItem.getId());
		}
		return ServerResponse.CreateBySuccessData(categoryIdList);
	}
	
//	递归查询该分类下所有子分类
	private Set<Category> findChildCategory (Set<Category> categorySet,Integer categoryId){
		Category category=categoryMapper.selectByPrimaryKey(categoryId);
		if (category!=null) {
			categorySet.add(category);
		}
		List<Category> categoryList=categoryMapper.selectChildOne(categoryId);
		for (Category categoryItem : categoryList) {
			findChildCategory(categorySet, categoryItem.getId());
		}
		return categorySet;
	}
}
