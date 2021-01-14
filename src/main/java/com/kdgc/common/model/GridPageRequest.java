package com.kdgc.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;


@Getter
@Setter
public class GridPageRequest implements Serializable {
    private Integer page;
    private Integer rows;

    public Pageable pageable(){
        if(page == null||rows == null){
            return Pageable.unpaged();
        }else{
            return PageRequest.of(this.page-1,this.rows);
        }
    }
}
