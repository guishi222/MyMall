package com.MyMall.service;

import com.MyMall.common.ServerResponse;
import com.MyMall.pojo.Product;

/**
 * Created by Administrator on 2018/3/8.
 */
public interface IproductService {
    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId,Integer status);
}
