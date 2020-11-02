package com.zyh.blog.controller.admin;

import com.aliyun.oss.OSS;
import com.zyh.blog.dto.FileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author ZYH
 * @Date 2020/9/14 15:19
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Controller
@RequestMapping("/admin")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private static final String UPLOADED_FOLDER = "D:\\idea\\SpringBoot\\blog_mybatisplus\\src\\main\\resources\\static\\images\\upload";

    @Autowired
    private OSS ossClient;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessId;

    @Value("${spring.cloud.alicloud.bucketName}")
    private String bucketName;

    /**
     * Markdown编辑器：editormd上传图片到阿里云oss对象存储
     * Markdown编辑器：editormd上传图片必须标明：editormd-image-file格式的文件，并且要返回三个参数（success、URL、message）
     */
    @RequestMapping("/OssUpload")
    @ResponseBody
    public FileDTO OssUpload(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file){
        FileDTO fileDTO = new FileDTO();
        try {
            String fileName = file.getOriginalFilename();
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(file.getBytes()));
            //上传到阿里云上的图片地址为：https://bucketname.endpoint/filename
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            fileDTO.setSuccess(1);
            fileDTO.setUrl(url);
            fileDTO.setMessage("上传图片成功");
            logger.info("上传图片成功");
        } catch (IOException e) {
            logger.info("上传图片失败：{}",e.getMessage());
        } finally {
            //关闭client
            ossClient.shutdown();
        }
        return fileDTO;
    }

    /**
     * 可以上传到项目中，但是博客中还是无法显示图片，因为Markdown编辑器只能显示带有URL路径的图片
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public Map<String, Object> demo(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        System.out.println(request.getContextPath());
        String realPath = UPLOADED_FOLDER;
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
/*        File targetFile = new File(realPath, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }*/
        //保存
        try {
            /*            file.transferTo(targetFile);*/
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + "_" + file.getOriginalFilename());
            Files.write(path, bytes);
            resultMap.put("success", 1);
            resultMap.put("message", "上传成功！");
            resultMap.put("url", UPLOADED_FOLDER + fileName);
        } catch (Exception e) {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败！");
            e.printStackTrace();
        }
        System.out.println(resultMap.get("success"));
        return resultMap;
    }
}
