package com.kdgc.controller;

import com.kdgc.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jczhou
 * @description
 * @date 2020/7/22
 **/
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private TableInfoService tableInfoService;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        return view;
    }

    @RequestMapping(value = "/index0")
    public ModelAndView index0() {
        ModelAndView view = new ModelAndView();
        view.setViewName("gojs");
        return view;
    }


    @RequestMapping(value = "/nodeDetails")
    public ModelAndView nodeDetails(Long nodeId) {
        ModelAndView view = new ModelAndView();
        view.addObject("node", tableInfoService.findById(nodeId));
        view.setViewName("nodeDetails");
        return view;
    }

    @RequestMapping(value = "/rel")
    public ModelAndView rel() {
        ModelAndView view = new ModelAndView();
        view.setViewName("create-rel");
        return view;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView edit(String srType) {
        ModelAndView view = new ModelAndView();
        view.addObject("nodeType", srType);
        view.setViewName("edit");
        return view;
    }


    @RequestMapping(value = "/test")
    public ModelAndView test() {
        ModelAndView view = new ModelAndView();
        view.setViewName("test");
        return view;
    }


}
