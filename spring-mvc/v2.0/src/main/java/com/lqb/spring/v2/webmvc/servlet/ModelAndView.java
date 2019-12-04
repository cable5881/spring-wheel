package com.lqb.spring.v2.webmvc.servlet;

import java.util.Map;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/3 16:51
 **/
public class ModelAndView {

    private String viewName;
    private Map<String, ?> model;

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public ModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

}
