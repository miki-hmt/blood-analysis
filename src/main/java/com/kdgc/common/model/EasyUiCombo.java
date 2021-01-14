package com.kdgc.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author jczhou
 * @description
 * @date 2020/7/27
 **/
@Setter
@Getter
public class EasyUiCombo implements Serializable {

    private String value;

    private String text;

    private boolean selected;

    public EasyUiCombo(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public EasyUiCombo(String value, String text, boolean selected) {
        this.value = value;
        this.text = text;
        this.selected = selected;
    }
}
