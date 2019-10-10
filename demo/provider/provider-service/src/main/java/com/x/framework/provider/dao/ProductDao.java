package com.x.framework.provider.dao;

import com.x.framework.provider.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDao {
    List<Product> selectAll();

    Product selectById(Long id);

    int insert(Product product);

    int delete(Long id);

    int update(Product product);
}
