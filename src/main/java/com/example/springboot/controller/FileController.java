package com.example.springboot.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.example.springboot.common.Result;
import com.example.springboot.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "文件上传下载")
@RestController
@RequestMapping("/files")
public class FileController {

    //获取文件路径

    private static final String filePath =System.getProperty("user.dir")+"/files/";

    @Operation(summary = "文件上传")
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        //文件流形式 接受文件
        //loacl....8080/files/download/xxx.jpg
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new CustomException("500", "上传的文件为空，请重新选择文件");
        }
        String originalFilename = file.getOriginalFilename();//xx.png
        if(!FileUtil.isDirectory(filePath)){//判断文件夹是否存在
            FileUtil.mkdir(filePath);//创建一个files目录
        }

        String fileName = System.currentTimeMillis()+"_"+originalFilename;
        String realPath = filePath+fileName;
//        System.out.println("<------------------>");
//        System.out.println(originalFilename);// 为空?
//        System.out.println("<------------------>");
        try {
            FileUtil.writeBytes(file.getBytes(),realPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("500","文件上传失败");
        }
        String url = "http://localhost:8080/files/download/"+fileName;
        return Result.success(url);
    }


    //文件下载
    @Operation(summary = "文件下载", description = "通过文件名下载相应文件")
    @GetMapping("download/{fileName}")
    public void download(@PathVariable String fileName,  HttpServletResponse response){
        try {

            // 使用 Java 8 兼容的编码方式 
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment; " + encodedFileName);
            response.setContentType("application/octet-stream");

            OutputStream  os = response.getOutputStream();
            String realPath = filePath+fileName;
            byte [] bytes = FileUtil.readBytes(realPath);
            os.write(bytes);
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("500","文件下载失败");
        }

    }


    @Operation(summary = "文件上传失败", description = "???")
    @PostMapping("wang/upload")
    public Map<String, Object> wangEditorUpload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if(!FileUtil.isDirectory(filePath)){
            FileUtil.mkdir(filePath);
        }
        String fileName = System.currentTimeMillis()+"_"+originalFilename;
        String realPath = filePath+fileName;
        try {
            FileUtil.writeBytes(file.getBytes(),realPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("500","文件上传失败");
        }
        String url = "http://localhost:8080/files/download/"+fileName;
        Map<String, Object>resMap=new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> urlMap = new HashMap<>();
        urlMap.put("url",url);
        list.add(urlMap);
        resMap.put("errno",0);
        resMap.put("data",list);
        return resMap;
    }

}
