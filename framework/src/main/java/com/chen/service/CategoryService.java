package com.chen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.domain.ResponseResult;
import com.chen.domain.entity.Category;

/**
 * @author Administrator
 * @description 针对表【sg_category(分类表)】的数据库操作Service
 * @createDate 2022-09-08 11:17:53
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
