package com.chen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.domain.ResponseResult;
import com.chen.domain.entity.Article;

/**
 * @author Administrator
 * @description 针对表【sg_article(文章表)】的数据库操作Service
 * @createDate 2022-09-08 09:34:15
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);
}

