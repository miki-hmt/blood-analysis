package com.kdgc.flow.controller;

import com.kdgc.common.model.JsonResult;
import com.kdgc.flow.service.DcFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jczhou
 * @description
 * @date 2020/7/30
 **/
@Controller
@RequestMapping(value = "/flow")
public class DcFlowController {

    private final static Logger logger = LoggerFactory.getLogger(DcFlowController.class);

    @Autowired
    private DcFlowService dcFlowService;


    @RequestMapping(value = "/flowToRel")
    @ResponseBody
    public Object flowToRel(String flowCode) {
        long startTime = System.currentTimeMillis();
        dcFlowService.flowToRel(flowCode);
        long endTime = System.currentTimeMillis();
        logger.info(String.format("接口调用总耗时：%s毫秒", endTime - startTime));
        return JsonResult.success();
    }
}
