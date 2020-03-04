package work.ambi.qiyue.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import work.ambi.qiyue.entity.MyFile;
import work.ambi.qiyue.until.RandomStringUtils;
import work.ambi.qiyue.until.ReadConfigFile;
import work.ambi.qiyue.until.RsaUtil;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/2/29 11:51
 */
@Controller
@RequestMapping("/files")
public class fileController {

    @Value("${privateKey}")
    private String privateKey ;

    @ApiOperation("文件上传界面")
    @GetMapping("/")
    @RequestMapping
    public String file(Model model){
        String SID= RandomStringUtils.getRandomString(6);
        model.addAttribute("SID",SID);
        String encode = RsaUtil.enWithRSAPrivateKey(SID, privateKey);
        model.addAttribute("Signature",encode);
        return "file";
    }

    @ApiOperation("文件列表界面")
    @GetMapping("/fileList")
    public String fileList(Model model){
        String SID= RandomStringUtils.getRandomString(6);
        model.addAttribute("SID",SID);
        String encode = RsaUtil.enWithRSAPrivateKey(SID, privateKey);
        model.addAttribute("Signature",encode);
        return "fileList";
    }


}
