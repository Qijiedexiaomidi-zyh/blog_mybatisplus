package com.zyh.blog;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class BlogApplicationTests {

//    @Autowired
//    OSSClient ossClient;
//
//    //老方式
//    @Test
//    void contextLoads() {
//        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "oss-cn-beijing.aliyuncs.com";
//        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
//        String accessKeyId = "LTAI4FuY2o1znnqmLJp8f6oP";
//        String accessKeySecret = "XN3cPIfgRx7B7xKVUFuiM2i2y1EawU";
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//        // 创建PutObjectRequest对象。
//        PutObjectRequest putObjectRequest = new PutObjectRequest("gmall-01-zyh", "网易云.jpg", new File("C:\\Users\\Administrator\\Desktop\\11.png"));
//
//        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
//        // ObjectMetadata metadata = new ObjectMetadata();
//        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//        // metadata.setObjectAcl(CannedAccessControlList.Private);
//        // putObjectRequest.setMetadata(metadata);
//
//        // 上传文件。
//        ossClient.putObject(putObjectRequest);
//        // 关闭OSSClient。
//        ossClient.shutdown();
//        System.out.println("上传文件到oss成功");
//    }
//
//    //新的方式，更加简洁
//    @Test
//    public void testOss(){
//
//        // 创建PutObjectRequest对象。
//        PutObjectRequest putObjectRequest = new PutObjectRequest("gmall-01-zyh", "网易云2.jpg", new File("C:\\Users\\Administrator\\Desktop\\22.png"));
//
//        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
//        // ObjectMetadata metadata = new ObjectMetadata();
//        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//        // metadata.setObjectAcl(CannedAccessControlList.Private);
//        // putObjectRequest.setMetadata(metadata);
//
//        // 上传文件。
//        ossClient.putObject(putObjectRequest);
//        // 关闭OSSClient。
//        ossClient.shutdown();
//        System.out.println("上传文件到oss成功");
//    }

}
