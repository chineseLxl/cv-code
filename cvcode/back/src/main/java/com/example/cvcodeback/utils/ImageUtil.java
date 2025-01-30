package com.example.cvcodeback.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ImageUtil {
    public Map<String,String> UploadImg(String path, String imgName,MultipartFile file) throws IOException {
        File newFile = new File(path+imgName);
        // 如果路径不存在则创建
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        file.transferTo(newFile);
        Map<String,String> resMap = new HashMap<>();
        // 路由地址
        resMap.put("routePath","/api/img/direct/" + imgName);
        // 物理地址
        resMap.put("absolutePath",path + imgName);
        return resMap;
    }
}
