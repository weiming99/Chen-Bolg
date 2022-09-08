package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.domain.ResponseResult;
import com.chen.domain.entity.Article;
import com.chen.domain.vo.HotArticleVo;
import com.chen.enums.SystemConstants;
import com.chen.mapper.ArticleMapper;
import com.chen.service.ArticleService;
import com.chen.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【sg_article(文章表)】的数据库操作Service实现
 * @createDate 2022-09-08 09:34:15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {


    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL).orderByDesc(Article::getViewCount);

        Page<Article> page = page(new Page<Article>(1, 10), articleLambdaQueryWrapper);

        List<Article> articles = page.getRecords();

        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(articles, HotArticleVo.class));
    }
}




