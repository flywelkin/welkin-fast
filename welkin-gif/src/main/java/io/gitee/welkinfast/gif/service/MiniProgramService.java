package io.gitee.welkinfast.gif.service;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/19 14:25
 * @Version 1.0.0
 */
public interface MiniProgramService {

    String getAccessToken();

    String uploadFile(String todaySaveFileName, String filePath);

    void getAndSaveAccessToken();

    Integer getGifCount(String openId);

    void updateGifCount(String openId);

    void resetGifCountJob();
}

