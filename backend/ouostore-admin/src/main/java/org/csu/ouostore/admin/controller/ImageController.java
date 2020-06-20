package org.csu.ouostore.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.admin.util.AliyunOSSClientUtil;
import org.csu.ouostore.common.api.CommonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 发货地址地址管理Controller
 *
 * @author zack
 */
@RestController
@Api(tags = "上传图片")
@RequestMapping("/api/v1/images")
public class ImageController {

    @ApiOperation("上传图片")
    @PostMapping(value = "")
    public CommonResult<String> list(MultipartFile file) {
        if (file.getSize() == 0) {
            return CommonResult.failed("文件大小为0");
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
            return CommonResult.failed("只允许传输图片");
        }
        System.out.println(file.getContentType());
        String url = AliyunOSSClientUtil.uploadObject2OSS(file);
        return CommonResult.OK(url);
    }
}
