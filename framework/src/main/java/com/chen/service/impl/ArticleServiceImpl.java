package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.domain.ResponseResult;
import com.chen.domain.entity.Article;
import com.chen.domain.entity.Category;
import com.chen.domain.vo.ArticleListVo;
import com.chen.domain.vo.HotArticleVo;
import com.chen.domain.vo.PageVo;
import com.chen.enums.SystemConstants;
import com.chen.mapper.ArticleMapper;
import com.chen.mapper.CategoryMapper;
import com.chen.service.ArticleService;
import com.chen.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sg_article(文章表)】的数据库操作Service实现
 * @createDate 2022-09-08 09:34:15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL).orderByDesc(Article::getViewCount);

        Page<Article> page = page(new Page<>(1, 10), articleLambdaQueryWrapper);

        List<Article> articles = page.getRecords();

        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(articles, HotArticleVo.class));
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        //如果有categoryid就要查询和传入相同
        //状态是正式发布的   对置顶的进行降序
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(!Objects.isNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId)
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(Article::getIsTop, Article::getViewCount);

        Map<Long, String> collect = categoryMapper.selectList(null).stream().collect(Collectors.toMap(Category::getId, Category::getName));

        Page<Article> page = page(new Page<>(pageNum, pageSize), articleLambdaQueryWrapper);

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        articleListVos.forEach(u -> {
            u.setCategoryName(collect.get(u.getCategoryId()));
        });

        //分页查询
        return ResponseResult.okResult(new PageVo(articleListVos, page.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);


        if (!Objects.isNull(categoryMapper.selectById(article.getCategoryId()))) {

        }

        return null;
    }
}




