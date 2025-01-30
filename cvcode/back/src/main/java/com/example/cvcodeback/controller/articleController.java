package com.example.cvcodeback.controller;

import com.example.cvcodeback.entity.Article;
import com.example.cvcodeback.entity.ResArticle;
import com.example.cvcodeback.entity.ResArticleInfo;
import com.example.cvcodeback.service.ArticleService;
import com.example.cvcodeback.utils.ImageUtil;
import com.example.cvcodeback.utils.JsonResult;
import com.example.cvcodeback.utils.PasswordMd5;
import com.example.cvcodeback.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class articleController {
    @Value("${img.path}")
    private String imgPath;
    final TokenUtil tokenUtil;
    final ArticleService articleService;
    final PasswordMd5 passwordMd5;
    final ImageUtil imageUtil;

    public articleController(TokenUtil tokenUtil, ArticleService articleService, PasswordMd5 passwordMd5, ImageUtil imageUtil) {
        this.tokenUtil = tokenUtil;
        this.articleService = articleService;
        this.passwordMd5 = passwordMd5;
        this.imageUtil = imageUtil;
    }
    // 发布博客
    @PostMapping("uploadArticle")
    public JsonResult uploadArticle(HttpServletRequest request, @RequestBody Article article) {
        Map<String,String> tokenMap = tokenUtil.parseToken(request.getHeader("token"));
        try {
            article.setUid(Integer.parseInt(tokenMap.get("userId")));
            article.setNickName(tokenMap.get("nickName"));
            article.setStatus("1");
            article.setUp(0);
            article.setDown(0);
            article.setOpenId(article.getUid() + "-" + article.getOpenId());
            if (articleService.existArticleByOpenId(article.getOpenId())) {
                return new JsonResult<>("请勿重复提交同一份文章", "-1");
            } else {
                articleService.UploadArticle(article);
                return new JsonResult<>("提交审核成功", "1");
            }
        } catch (Exception e) {
            return new JsonResult<>("服务器错误", "-1");
        }
    }

    // 获取已审核通过的所有文章
    @PostMapping("getArticleAll")
    public JsonResult getArticleAll() {
        List<ResArticle> resArticleList = articleService.getArticleByStatus("1");
        return new JsonResult<>(resArticleList);
    }

    // 获取已审核通过的所有文章
    @RequestMapping("getArticlePage")
    public JsonResult getArticlePage(@RequestParam("page") Integer page, @RequestParam("mid") Integer mid) {
        if (mid == null) {
            mid = 0;
        }
        List<ResArticle> resArticleList = articleService.getArticlePage(page, mid, 5);
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("articleList", resArticleList);
        if (resArticleList.size() != 0) {
            jsonMap.put("mid", resArticleList.get(resArticleList.size() - 1).getId());
        } else {
            jsonMap.put("mid", mid);
        }
        return new JsonResult<>(jsonMap);
    }
    // 获取文章可见范围列表
    @PostMapping("getArticleRange")
    public JsonResult getArticleRange() {
        return new JsonResult<>(articleService.getRangeAll());
    }
    // 获取文章类型列表
    @PostMapping("getArticleType")
    public JsonResult getArticleType() {
        return new JsonResult<>(articleService.getTypeAll());
    }
    // 获取文章标签目录
    @RequestMapping(value = "getArticleMenu", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody JsonResult getArticleMenu() {
        return new JsonResult(articleService.getArticleMenu());
    }
    // 上传封面
    @PostMapping("uploadDirect")
    public String uploadImg(@RequestParam("file") MultipartFile file,HttpServletRequest request,
                            @RequestParam("openId") String openId) throws IOException {
        if (!file.isEmpty() && !request.getHeader("token").isEmpty()) {
            Map<String,String> tokenMap = tokenUtil.parseToken(request.getHeader("token"));
            // 将图片名MD5加密 ------ 因图片名中含有用户id
            String imgName =  passwordMd5.encode(tokenMap.get("userId")+"-"+openId) + ".jpg";
            String path = imgPath + "/direct/";
            return imageUtil.UploadImg(path,imgName,file).get("routePath");
        }
        return "";
    }
    // 获取文章详细内容
    @PostMapping("getArticleInfo")
    public JsonResult getArticleInfo(@RequestBody Map<String,String> reqMap) {
        return new JsonResult<>(articleService.getArticleInfoById(Integer.parseInt(reqMap.get("id"))));
    }


}
