package io.gitee.welkinfast.admin;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/17 18:56
 * @Version 1.0.0
 */
public class WeFileTest {
    public static final String CONTENT_TYPE_MULTIPART_PREFIX = "multipart/form-data; boundary=";
    public static final String BOUNDARY = "--------------------Hutool_" + RandomUtil.randomString(16);

    public static void main(String[] args) {
        String accessToken = "44_B1zzQkGcRtzf81AJWJGDhkdNPK_I93F2V552DlP80rEUxI-Ys1OJT9tdly2CNfzIxbXGrT7potIquTx7jERDCnsIHlznfIquItpHeSyhuq5qnzaa5ytiZDBPfucU9VbgOHYRl6j90vz3mLvcMVAbAIAPYD";
        String uploadPath = "https://api.weixin.qq.com/tcb/uploadfile?access_token=";
        JSONObject params = new JSONObject();
        params.put("env","welkin-mini-3ggtet5i19915ff2");
        params.put("path","2021-04-17/file3.gif");
        String post = HttpUtil.post(uploadPath + accessToken, params.toJSONString());
        System.out.println(post);
        JSONObject uploadResult = JSONObject.parseObject(post);

        File file = new File("E:\\2021-04-17\\2P5qm7pmiyAT5432b5d61c6ad5bcd90dd26fe2ffec32_1618589911684.gif");
        String url = uploadResult.getString("url");

        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("key", "2021-04-17/file3.gif");
        formParams.put("Signature", uploadResult.getString("authorization"));
        formParams.put("x-cos-security-token", uploadResult.getString("token"));
        formParams.put("x-cos-meta-fileid", uploadResult.getString("cos_file_id"));

        String body = HttpRequest.post(url)
                .header(Header.CONTENT_TYPE, ContentType.MULTIPART.getValue() , true)
                .form(formParams)
                .form("file",file)
                .timeout(120000)
                .execute().body();
        System.out.println(body);

    }
}
