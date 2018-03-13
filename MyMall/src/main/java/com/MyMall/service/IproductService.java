package com.MyMall.service;

import com.MyMall.common.ServerResponse;
import com.MyMall.pojo.Product;
import com.MyMall.vo.ProductDetailVo;

public interface IproductService {
    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ProductDetailVo assembleProductDetailVo(Product product);
}
