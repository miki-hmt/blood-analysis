package com.kdgc.controller;

import com.kdgc.common.constant.SysCons;
import com.kdgc.common.model.JsonResult;
import com.kdgc.service.ColumnInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jczhou
 * @description
 * @date 2020/7/20
 **/
@Controller
@RequestMapping(value = "/col")
public class ColumnInfoController {

    @Autowired
    private ColumnInfoService columnInfoService;


    @CrossOrigin
    @RequestMapping(value = "/colMap")
    @ResponseBody
    public JsonResult getColMap(Long nodeId) {
        if (StringUtils.isEmpty(nodeId)) {
            return JsonResult.error(SysCons.nullParam("nodeId"));
        }
        return JsonResult.success(columnInfoService.getColMap(nodeId));
    }
}
