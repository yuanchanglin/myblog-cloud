package com.yuanchanglin.articleservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文章管理接口
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final Map<Long, Map<String, Object>> articleDb = new ConcurrentHashMap<>();
    private Long idCounter = 1L;

    @PostMapping("/create")
    public Map<String, Object> createArticle(@RequestBody Map<String, Object> params) {
        Map<String, Object> article = new HashMap<>();
        article.put("id", idCounter++);
        article.put("title", params.get("title"));
        article.put("content", params.get("content"));
        article.put("authorId", params.get("authorId"));
        article.put("createTime", new Date());
        article.put("updateTime", new Date());
        articleDb.put((Long) article.get("id"), article);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "创建成功");
        result.put("data", article);
        return result;
    }

    @GetMapping("/list")
    public Map<String, Object> getArticleList(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;

        List<Map<String, Object>> allArticles = new ArrayList<>(articleDb.values());
        allArticles.sort((a, b) -> ((Date) b.get("createTime")).compareTo((Date) a.get("createTime")));

        int start = (page - 1) * size;
        int end = Math.min(start + size, allArticles.size());
        List<Map<String, Object>> pageData = start < allArticles.size()
            ? allArticles.subList(start, end)
            : Collections.emptyList();

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "查询成功");
        result.put("data", pageData);
        result.put("total", allArticles.size());
        result.put("page", page);
        result.put("size", size);
        return result;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getArticle(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> article = articleDb.get(id);
        if (article != null) {
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", article);
        } else {
            result.put("code", 404);
            result.put("message", "文章不存在");
        }
        return result;
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateArticle(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> article = articleDb.get(id);
        if (article != null) {
            if (params.containsKey("title")) {
                article.put("title", params.get("title"));
            }
            if (params.containsKey("content")) {
                article.put("content", params.get("content"));
            }
            article.put("updateTime", new Date());
            result.put("code", 200);
            result.put("message", "更新成功");
            result.put("data", article);
        } else {
            result.put("code", 404);
            result.put("message", "文章不存在");
        }
        return result;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteArticle(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        if (articleDb.remove(id) != null) {
            result.put("code", 200);
            result.put("message", "删除成功");
        } else {
            result.put("code", 404);
            result.put("message", "文章不存在");
        }
        return result;
    }
}
