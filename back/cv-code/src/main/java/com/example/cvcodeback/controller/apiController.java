package com.example.cvcodeback.controller;

import com.example.cvcodeback.entity.Article;
import com.example.cvcodeback.service.ArticleService;
import com.example.cvcodeback.utils.JsonResult;
import com.example.cvcodeback.utils.PasswordMd5;
import com.example.cvcodeback.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class apiController {
    @Value("${img.path}")
    private String imgPath;
    final TokenUtil tokenUtil;
    final ArticleService articleService;
    final PasswordMd5 passwordMd5;

    public apiController(TokenUtil tokenUtil, ArticleService articleService, PasswordMd5 passwordMd5) {
        this.tokenUtil = tokenUtil;
        this.articleService = articleService;
        this.passwordMd5 = passwordMd5;
    }

    @GetMapping("/test-{name}")
    public String test(@PathVariable("name") String name) {
        return name;
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
        return new JsonResult<>(articleService.getArticleByStatus("1"));
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
    // 上传封面
    @PostMapping("uploadDirect")
    public String uploadImg(@RequestParam("file") MultipartFile file,HttpServletRequest request,
                            @RequestParam("openId") String openId) throws IOException {
        if (!file.isEmpty() && !request.getHeader("token").isEmpty()) {
            Map<String,String> tokenMap = tokenUtil.parseToken(request.getHeader("token"));
            // 将图片名MD5加密 ------ 因图片名中含有用户id
            String imgName =  passwordMd5.encode(tokenMap.get("userId")+"-"+openId) + ".jpg";
            String path = imgPath + "/direct/" + imgName;
            File newFile = new File(path);
            // 如果路径不存在则创建
//            if (!newFile.exists()) {
//                newFile.mkdirs();
//            }
            file.transferTo(newFile);
            return "/api/img/direct/" + imgName;
        }
        return "";
    }

    @RequestMapping(value = "getArticleMenu", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody JsonResult getArticleMenu() {
        return new JsonResult(articleService.getArticleMenu());
    }

}
