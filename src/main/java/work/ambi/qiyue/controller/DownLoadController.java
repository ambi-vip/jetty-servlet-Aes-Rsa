package work.ambi.qiyue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import work.ambi.qiyue.service.DownLoadServe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/2 14:40
 */
@Controller
public class DownLoadController {

    @Autowired
    private DownLoadServe downLoadServe;

    @ResponseBody
    @GetMapping("/download")
    public String logDownload(@RequestParam String Uid,@RequestParam String filetype) throws Exception {
        return downLoadServe.logDownload(Uid,filetype);
    }
}
