package io.gitee.welkinfast.gif.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import io.gitee.welkinfast.gif.config.MiniProgramProperties;
import io.gitee.welkinfast.gif.service.MiniProgramService;
import io.gitee.welkinfast.service.mapper.MiniGifCountMapper;
import io.gitee.welkinfast.service.mapper.dao.MiniGifCount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/19 14:27
 * @Version 1.0.0
 */
@Slf4j
@Service
public class MiniProgramServiceImpl implements MiniProgramService {

    private static final String ACCESS_TOKEN_KAY = "access_token";


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MiniProgramProperties miniProgramProperties;
    @Autowired
    private MiniGifCountMapper miniGifCountMapper;

    @Override
    public String getAccessToken() {
        Object o = redisTemplate.opsForValue().get(ACCESS_TOKEN_KAY);
        if (o != null && o instanceof String) {
            return (String) o;
        } else {
            throw new CustomExcption(CustomErrorType.MINI_ERROR, "token为空");
        }
    }

    @Override
    public String uploadFile(String todaySaveFileName, String filePath) {
        String accessToken = getAccessToken();

        String uploadfileUrl = "https://api.weixin.qq.com/tcb/uploadfile?access_token=";
        JSONObject params = new JSONObject();
        params.put("env", miniProgramProperties.getCloudEnv());
        params.put("path", todaySaveFileName + "_" + FileUtil.getName(filePath));
        String post = HttpUtil.post(uploadfileUrl + accessToken, params.toJSONString());
        System.out.println(post);
        JSONObject uploadResult = JSONObject.parseObject(post);

        if (uploadResult.getInteger("errcode") != 0) {
            throw new CustomExcption(CustomErrorType.CONVERSION_ERROR);
        }

        File file = new File(filePath);
        String url = uploadResult.getString("url");

        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("key", todaySaveFileName + "_" + FileUtil.getName(filePath));
        formParams.put("Signature", uploadResult.getString("authorization"));
        formParams.put("x-cos-security-token", uploadResult.getString("token"));
        formParams.put("x-cos-meta-fileid", uploadResult.getString("cos_file_id"));

        HttpRequest.post(url)
                .header(Header.CONTENT_TYPE, ContentType.MULTIPART.getValue(), true)
                .form(formParams)
                .form("file", file)
                .timeout(120000)
                .execute().body();
        log.info("上传到云存储: {}", uploadResult.getString("file_id"));
        return uploadResult.getString("file_id");
    }

    @Override
    public void  getAndSaveAccessToken() {
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        accessTokenUrl = String.format(accessTokenUrl, miniProgramProperties.getAppId(), miniProgramProperties.getSecret());
        String accessTokenBody = HttpRequest.get(accessTokenUrl).execute().body();
        JSONObject accessTokenJson = JSONObject.parseObject(accessTokenBody);
        log.info("获取 access token， {}", accessTokenBody);
        Integer errcode = accessTokenJson.getInteger("errcode");
        if (errcode == null) {
            // 成功获取access_token
            String accessToken = accessTokenJson.getString("access_token");
            //expires_in	number	凭证有效时间，单位：秒。目前是7200秒之内的值。
            redisTemplate.opsForValue().set(ACCESS_TOKEN_KAY, accessToken, 7000, TimeUnit.SECONDS);
        } else if (errcode == -1) {
            // 系统繁忙，稍候再试
            getAccessToken();
        } else {
            throw new CustomExcption(CustomErrorType.MINI_ERROR);
        }
    }

    @Override
    public Integer getGifCount(String openId) {
        MiniGifCount miniGifCount = miniGifCountMapper.selectById(openId);
        if(ObjectUtils.isEmpty(miniGifCount)){
            miniGifCount = new MiniGifCount();
            miniGifCount.setOpenId(openId);
            miniGifCount.setCount(10);
            miniGifCountMapper.insert(miniGifCount);
        }
        return miniGifCount.getCount();
    }

    @Override
    public void updateGifCount(String openId) {
        MiniGifCount miniGifCount = miniGifCountMapper.selectById(openId);
        if (miniGifCount.getCount() > 0) {
            miniGifCount.setCount(miniGifCount.getCount() - 1);
        }
        miniGifCountMapper.updateById(miniGifCount);
    }

    @Override
    public void resetGifCountJob() {
        LambdaQueryWrapper<MiniGifCount> queryWrapper = Wrappers.<MiniGifCount>lambdaQuery();
        MiniGifCount miniGifCount = new MiniGifCount();
        miniGifCount.setCount(10);
        miniGifCountMapper.update(miniGifCount,queryWrapper);
    }
}
