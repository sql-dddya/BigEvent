package org.example.controller;

import org.example.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUplodaController {

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + originName.substring(originName.lastIndexOf("."));
        // 将文件存入本地磁盘
        file.transferTo(new File("/Users/shanqiuli/Desktop/file/" + fileName));
        return Result.success("图片URL 。。。。。");
    }

}
