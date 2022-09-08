package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.domain.ResponseResult;
import com.chen.domain.entity.Article;
import com.chen.domain.entity.Category;
import com.chen.domain.vo.CategoryVo;
import com.chen.enums.SystemConstants;
import com.chen.mapper.ArticleMapper;
import com.chen.mapper.CategoryMapper;
import com.chen.service.CategoryService;
import com.chen.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sg_category(分类表)】的数据库操作Service实现
 * @createDate 2022-09-08 11:17:53
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Resource
    private ArticleMapper articleMapper;


    @Override
    public ResponseResult getCategoryList() {
        //第一步查询文章状态为正常列表
        //LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //articleLambdaQueryWrapper.select(Article::getCategoryId);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct category_id").lambda().eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleMapper.selectList(queryWrapper);

        //把category id 收集起来
        List<Long> categoryIdList = articles.stream().map(Article::getCategoryId).collect(Collectors.toList());

        //将category id 收起来 进行查询
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.in(Category::getId, categoryIdList).eq(Category::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Category> list = list(categoryLambdaQueryWrapper);

        //List<Category> categories = listByIds(categoryIdList);
        //String s = String.valueOf(SystemConstants.ARTICLE_STATUS_NORMAL);
        //List<Category> list = categories.stream().filter(category -> s.equals(category.getStatus())).collect(Collectors.toList());

        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(list, CategoryVo.class));
    }
}




