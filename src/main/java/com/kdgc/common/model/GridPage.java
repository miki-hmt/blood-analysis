package com.kdgc.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class GridPage implements Serializable {
    private long total;
    private transient List<?> rows = new ArrayList();
    private transient List footer = new ArrayList();
    private boolean showApprove;
    public static GridPage of(Page<?> page){
        GridPage gridPage=new GridPage();
        gridPage.setTotal(page.getTotalElements());
        gridPage.setRows(page.getContent());
        return gridPage;
    }
}
