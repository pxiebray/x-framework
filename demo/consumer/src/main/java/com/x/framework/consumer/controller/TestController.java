package com.x.framework.consumer.controller;

import com.x.framework.provider.entity.Product;
import com.x.framework.provider.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String test() {
//        System.out.println(productService.selectAll(1, 5, 0L));
//        System.out.println(productService.selectById(1L));

        ProductService.Page page = new ProductService.Page();
        page.setOffset(100);
        page.setLimit(5);

        Product filter = new Product();
        filter.setCategoryId(2L);
        page.setFilter(filter);

        page.setSort("-name,description");

        productService.selectAllGet(page);
//        productService.delete(1L);
//        productService.selectById(1L);
        return "time " + System.currentTimeMillis();
    }
}
