package com.mohism.pudding.system.manager.util;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.google.gson.Gson;
import com.mohism.pudding.system.manager.core.constants.SettingConstant;
import com.mohism.pudding.system.manager.vo.OssSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

/**
 * <p>
 *  阿里云OSS工具
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Component
@Slf4j
public class AliOssUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public OssSetting getOssSetting(){

        String v = redisTemplate.opsForValue().get(SettingConstant.ALI_OSS);
        if(StrUtil.isBlank(v)){
            throw new ServiceException("您还未配置阿里云OSS");
        }
        return new Gson().fromJson(v, OssSetting.class);
    }

    /**
     * 文件路径上传
     * @param filePath
     * @param key
     * @return
     */
    public String aliUpload(String filePath, String key) {

        OssSetting os = getOssSetting();
        OSSClient ossClient = new OSSClient(os.getHttp() + os.getEndpoint(), new DefaultCredentialProvider(os.getAccessKey(), os.getSecretKey()), null);
        ossClient.putObject(os.getBucket(), key, new File(filePath));
        ossClient.shutdown();
        return os.getHttp() + os.getBucket() + "." + os.getEndpoint() + "/" + key;
    }

    /**
     * 文件流上传
     * @param inputStream
     * @param key
     * @return
     */
    public String aliInputStreamUpload(InputStream inputStream, String key) {

        OssSetting os = getOssSetting();
        OSSClient ossClient = new OSSClient(os.getHttp() + os.getEndpoint(), new DefaultCredentialProvider(os.getAccessKey(), os.getSecretKey()), null);
        ossClient.putObject(os.getBucket(), key, inputStream);
        ossClient.shutdown();
        return os.getHttp() + os.getBucket() + "." + os.getEndpoint() + "/" + key;
    }

    /**
     * 重命名
     * @param fromKey
     * @param toKey
     */
    public String renameFile(String fromKey, String toKey){

        OssSetting os = getOssSetting();
        copyFile(fromKey, toKey);
        deleteFile(fromKey);
        return os.getHttp() + os.getBucket() + "." + os.getEndpoint() + "/" + toKey;
    }

    /**
     * 复制文件
     * @param fromKey
     */
    public String copyFile(String fromKey, String toKey){

        OssSetting os = getOssSetting();
        OSSClient ossClient = new OSSClient(os.getHttp() + os.getEndpoint(), new DefaultCredentialProvider(os.getAccessKey(), os.getSecretKey()), null);
        ossClient.copyObject(os.getBucket(), fromKey, os.getBucket(), toKey);
        ossClient.shutdown();
        return os.getHttp() + os.getBucket() + "." + os.getEndpoint() + "/" + toKey;
    }

    /**
     * 删除文件
     * @param key
     */
    public void deleteFile(String key){

        OssSetting os = getOssSetting();
        OSSClient ossClient = new OSSClient(os.getHttp() + os.getEndpoint(), new DefaultCredentialProvider(os.getAccessKey(), os.getSecretKey()), null);
        ossClient.deleteObject(os.getBucket(), key);
        ossClient.shutdown();
    }
}
