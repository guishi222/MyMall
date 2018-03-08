package com.MyMall.service.imp;

import com.MyMall.common.ServerResponse;
import com.MyMall.dao.CategoryMapper;
import com.MyMall.dao.ProductMapper;
import com.MyMall.pojo.Product;
import com.MyMall.service.IcategoryService;
import com.MyMall.service.IproductService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/3/8.
 */
@Service("iproductSrtvice")
public class productServiceImp implements IproductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private IcategoryService iCategoryService;
    public ServerResponse saveOrUpdateProduct(Product product){
        if(product != null)
        {
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length > 0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            if(product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKey(product);
                if(rowCount > 0){
                    return ServerResponse.CreateBySuccessMsg("更新成功");
                }
                return ServerResponse.CreateByErrorMsg("更新失败");
            }else{
                int rowCount = productMapper.insert(product);
                if(rowCount > 0){
                    return ServerResponse.CreateBySuccessMsg("新增产品成功");
                }
                return ServerResponse.CreateByErrorMsg("新增产品失败");
            }
        }
        return ServerResponse.CreateByErrorMsg("新增或更新产品参数不正确");
    }


    public ServerResponse<String> setSaleStatus(Integer productId,Integer status){
        if(productId == null || status == null){
            return ServerResponse.CreateByErrorMsg("参数错误");
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount > 0){
            return ServerResponse.CreateBySuccessMsg("修改产品销售状态成功");
        }
        return ServerResponse.CreateByErrorMsg("修改产品销售状态失败");
    }
}