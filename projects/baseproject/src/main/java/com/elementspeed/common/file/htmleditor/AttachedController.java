package com.elementspeed.common.file.htmleditor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elementspeed.common.env.EnvironmentVars;
import com.elementspeed.framework.base.controller.BaseCtrl;

@Controller
@RequestMapping("/usr/srm/attachments/files/")
public class AttachedController extends BaseCtrl{
	
	
	@RequestMapping("/{yymm}/{modules}/{fileName}.{suffix}")  
    public void attached(HttpServletRequest request, HttpServletResponse response,   
            @PathVariable String yymm,  
            @PathVariable String modules,
            @PathVariable String suffix,  
            @PathVariable String fileName) {  
        //根据suffix设置响应ContentType  
        //response.setContentType("text/html; charset=UTF-8");  
          
        InputStream is = null;  
        OutputStream os = null;  
        try {  
            File file = new File(EnvironmentVars.getUpPath() + yymm + "/" + modules + "/" + fileName + "." + suffix);  
            is = new FileInputStream(file);  
            byte[] buffer = new byte[is.available()];  
            is.read(buffer);  
              
            os = new BufferedOutputStream(response.getOutputStream());  
            os.write(buffer);  
            os.flush();  
        } catch (Exception e) {  
            //判断suffix  
        } finally {  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                }  
                  
                if (os != null) {  
                    try {  
                        os.close();  
                    } catch (IOException e) {  
                    }  
                }  
            }  
        }  
          
    }  
}
