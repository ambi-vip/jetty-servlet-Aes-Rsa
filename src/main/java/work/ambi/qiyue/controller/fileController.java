package work.ambi.qiyue.controller;

import io.swagger.annotations.ApiOperation;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import work.ambi.qiyue.entity.MyFile;
import work.ambi.qiyue.provider.JsonResult;
import work.ambi.qiyue.provider.Message;
import work.ambi.qiyue.service.MyFileService;
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

    @Autowired
    private MyFileService myFileService;

    @Value("${privateKey}")
    private String privateKey ;

    @ApiOperation("文件上传界面")
    @GetMapping("/")
    @RequestMapping
    public String file(Model model){
        String SID= RandomStringUtils.getRandomString(6);
        model.addAttribute("SID",SID);
        String encode = RsaUtil.enWithRSAPrivateKey(SID, privateKey);
        System.out.println(privateKey);
        model.addAttribute("Signature",encode);
        return "file";
    }

    @ApiOperation("文件列表界面")
    @GetMapping("/fileList")
    public String fileList(){
        return "fileList";
    }


    @ResponseBody
    @ApiOperation("返回全部文件")
    @GetMapping("/allFiles")
    public JsonResult Allfiles(){
        List<MyFile> myFiles = myFileService.AllFiles();
        return JsonResult.ok(myFiles);
    }

    @ResponseBody
    @ApiOperation("下载文件")
    @GetMapping("/downloadFile")
    public JsonResult downloadFile(){
        return JsonResult.ok("ss");
    }

    @ResponseBody
    @ApiOperation("测试接口")
    @GetMapping("/test")
    public JsonResult test(){
        return JsonResult.ok("ss");
    }

    @ApiOperation("上传文件")
    @PostMapping("/fileUpload")
    @ResponseBody
    public JsonResult fillUpload(MultipartFile file){
        if (file.isEmpty()){
            return JsonResult.errorMsg("文件为空");
        }
        MyFile myFile = new MyFile();

        //获取文件名。
        String fileName = file.getOriginalFilename();
        myFile.setOldname(fileName);

        int size = (int)file.getSize();
        myFile.setFileSize(String.valueOf(size));

        String path = "f:/t";
        String uuid = UUID.randomUUID().toString();
        myFile.setUid(uuid);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        //获取当前日期
        String currentDate = dateFormat.format( new Date() );
        //获取文件的后缀名
        String suffix = "."+ fileName.substring(fileName.lastIndexOf(".") + 1);
        myFile.setFiletype(suffix);

        //存储到数据库的路径
        String dbpath = "/"+currentDate+"/"+uuid+suffix;
        myFile.setPath(dbpath);

        File dest = new File(path+dbpath);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try{
            file.transferTo(dest);
            myFileService.save(myFile);
            return JsonResult.ok(dbpath);
        }catch (IllegalStateException | IOException e){
            e.printStackTrace();
            return JsonResult.errorException("文件上传异常");
        }
    }

    /**
     * 实现多文件上传
     * */
    @ApiOperation("多文件上传")
    @PostMapping("/multifileUpload")
    public @ResponseBody String multifileUpload(HttpServletRequest request){

        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileName");

        if(files.isEmpty()){
            System.out.println("空");
            return "false";
        }

        String path = "F:/t" ;

        for(MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            if(file.isEmpty()){
                return "false";
            }else{
                File dest = new File(path + "/" + fileName);
                if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                }catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "false";
                }
            }
        }
        return "true";
    }

}
