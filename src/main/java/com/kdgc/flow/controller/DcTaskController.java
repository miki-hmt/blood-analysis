package com.kdgc.flow.controller;

import com.kdgc.flow.entity.DcwebTask;
import com.kdgc.flow.service.DcTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/31
 **/
@Controller
@RequestMapping(value = "/task")
public class DcTaskController {

    @Autowired
    private DcTaskService dcTaskService;


    @RequestMapping(value = "/findByFlowCode")
    @ResponseBody
    public List<DcwebTask> findByFlowCode(String flowCode) {
        return dcTaskService.findByFlowCode(flowCode);
    }
}
