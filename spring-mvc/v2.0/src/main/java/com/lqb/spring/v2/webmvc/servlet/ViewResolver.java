package com.lqb.spring.v2.webmvc.servlet;

import java.io.File;
import java.util.Locale;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/3 16:55
 **/
public class ViewResolver {
    private final String DEFAULT_TEMPLATE_SUFFX = ".html";

    private File templateRootDir;
//    private String viewName;

    public ViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    public View resolveViewName(String viewName, Locale locale) throws Exception{
        if(null == viewName || "".equals(viewName.trim())){return null;}
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+","/"));
        return new View(templateFile);
    }
}
