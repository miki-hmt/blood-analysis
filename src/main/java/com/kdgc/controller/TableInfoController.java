package com.kdgc.controller;

import com.kdgc.common.constant.SysCons;
import com.kdgc.common.model.EasyUiCombo;
import com.kdgc.common.model.GridPage;
import com.kdgc.common.model.GridPageRequest;
import com.kdgc.common.model.JsonResult;
import com.kdgc.entity.TableInfo;
import com.kdgc.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jczhou
 * @description
 * @date 2020/7/20
 **/
@Controller
@RequestMapping(value = "/tab")
public class TableInfoController {

    @Autowired
    private TableInfoService tableInfoService;


    @CrossOrigin
    @RequestMapping(value = "/findById")
    @ResponseBody
    public TableInfo findById(Long id) {
        return tableInfoService.findById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/getChildTableLink")
    @ResponseBody
    public JsonResult getChildTableLink(String url, String tableName) {
        return JsonResult.success(tableInfoService.getChildTableLink(url, tableName));
    }

    @CrossOrigin
    @RequestMapping(value = "/getParentTableLink")
    @ResponseBody
    public JsonResult getParentTableLink(String url, String tableName) {
        return JsonResult.success(tableInfoService.getParentTableLink(url, tableName));
    }


    @CrossOrigin
    @RequestMapping(value = "/getAllTableRelByTableName")
    @ResponseBody
    public JsonResult getAllTableRelByTableName(String tableName, String url) {
        if (StringUtils.isEmpty(tableName)) {
            return JsonResult.error(SysCons.nullParam("tableName"));
        }
        if (StringUtils.isEmpty(url)) {
            return JsonResult.error(SysCons.nullParam("url"));
        }
        return JsonResult.success(tableInfoService.getAllTableRelByTableName(tableName, url));
    }

    @CrossOrigin
    @RequestMapping(value = "/getTableModel")
    @ResponseBody
    public JsonResult getTableModel(String tableName, String url) {
        if (StringUtils.isEmpty(tableName)) {
            return JsonResult.error(SysCons.nullParam("tableName"));
        }
        if (StringUtils.isEmpty(url)) {
            return JsonResult.error(SysCons.nullParam("url"));
        }
        return JsonResult.success(tableInfoService.getTableModel(tableName, url));
    }

    @CrossOrigin
    @RequestMapping(value = "/getGoJsGraphModel")
    @ResponseBody
    public JsonResult getGoJsGraphModel(String tableName, String url) {
        if (StringUtils.isEmpty(tableName)) {
            return JsonResult.error(SysCons.nullParam("tableName"));
        }
        if (StringUtils.isEmpty(url)) {
            return JsonResult.error(SysCons.nullParam("url"));
        }
        return JsonResult.success(tableInfoService.getGoJsGraphModel(tableName, url));
    }


    @RequestMapping(value = "/saveTable")
    @ResponseBody
    public JsonResult save(TableInfo tableInfo) {
        tableInfoService.save(tableInfo);
        return JsonResult.success();
    }

    @RequestMapping(value = "/delTable")
    @ResponseBody
    public JsonResult del(TableInfo tableInfo) {
        tableInfoService.del(tableInfo);
        return JsonResult.success();
    }

    @RequestMapping(value = "/delTableRel")
    @ResponseBody
    public JsonResult delTableRel(TableInfo tableInfo) {
        tableInfoService.del(tableInfo);
        return JsonResult.success();
    }

    @RequestMapping(value = "/getDbSource")
    @ResponseBody
    public List<EasyUiCombo> getDbSource() {
        //TODO 资源库暂时设置测试数据
        List<EasyUiCombo> easyUiCombo = new ArrayList<>();
        easyUiCombo.add(new EasyUiCombo("RSRC_14863586492416000", "192.168.62.44测试资源库"));
        easyUiCombo.add(new EasyUiCombo("RSRC_14865608093466624", "192.168.62.45测试资源库"));
        return easyUiCombo;
    }

    @RequestMapping(value = "/pageQuery")
    @ResponseBody
    public GridPage pageQuery(GridPageRequest gridPageRequest) {
        return tableInfoService.pageQuery(gridPageRequest.pageable());
    }

}
