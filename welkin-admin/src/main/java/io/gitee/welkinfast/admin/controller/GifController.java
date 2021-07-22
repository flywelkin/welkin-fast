package io.gitee.welkinfast.admin.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.gitee.welkinfast.common.response.CustomResponse;
import io.gitee.welkinfast.gif.config.MiniProgramProperties;
import io.gitee.welkinfast.gif.entity.GifEntity;
import io.gitee.welkinfast.gif.service.GifServie;
import io.gitee.welkinfast.gif.service.MiniProgramService;
import io.gitee.welkinfast.service.mapper.dao.MiniGifCount;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/16 11:06
 * @Version 1.0.0
 */

@Slf4j
@Api(tags = "GIF管理")
@RestController
public class GifController {

    @Autowired
    private GifServie gifServie;
    @Autowired
    private MiniProgramService miniProgramService;
    @Autowired
    private MiniProgramProperties miniProgramProperties;


    @PostMapping(value = "/welkin/vidwo/gif")
    public CustomResponse<String> video2Gif(GifEntity entity, @RequestParam("file") MultipartFile multipartFile) throws IOException {

        String primaryName = FileUtil.mainName(multipartFile.getOriginalFilename());
        String extension = FileUtil.extName(multipartFile.getOriginalFilename());
        String fileName = primaryName + "_" + System.currentTimeMillis() + "." + extension;

        String todaySaveFileName = getTodaySaveFileName();
        String filePath = miniProgramProperties.getGifFilePath() + File.separator + todaySaveFileName;

        String newFilePath = filePath + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            FileUtil.mkdir(filePath);
        }
        File newFile = new File(newFilePath);
        multipartFile.transferTo(newFile);
        entity.setPath(newFilePath);

        GifEntity gifEntity = gifServie.video2Gif(entity);

        String path = miniProgramService.uploadFile(todaySaveFileName, gifEntity.getOutPath());

        return CustomResponse.OK(path);
    }


    private String getTodaySaveFileName() {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }


    @GetMapping(value = "/welkin/gif/count")
    public CustomResponse<Integer> getGifCount(@RequestParam("openId") String openId) {
        Integer count = miniProgramService.getGifCount(openId);
        return CustomResponse.OK(count);
    }

    @PutMapping(value = "/welkin/gif/count")
    public CustomResponse<String> updateGifCount(@RequestParam("openId") String openId) {
        miniProgramService.updateGifCount(openId);
        return CustomResponse.OK("");
    }

    @GetMapping("mini/user")
    public CustomResponse<Page<MiniGifCount>> miniUserPage() {
        return CustomResponse.OK(null);
    }
}
