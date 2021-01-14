package com.kdgc.flow.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 任务实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "dc_c_tsk")
@Setter
@Getter
@SuppressWarnings("serial")
public class DcwebTask {
	/**
	 * 任务ID
	 */
	@Id
	private Long tskId;
	/**
	 * 任务编码
	 */
	private String tskCode;
	/**
	 * flowCode 流程编码
	 */
	private String flowCode;
	/**
	 * 任务名称
	 */
	private String tskName;
	/**
	 * ordBy 排序
	 */
	private Long ordBy;

	private String pluginId;
	/**
	 * 插件参数
	 */
	private String pluginParm;
	/**
	 * 字段映射关系
	 */
	private String fldMapping;
	/**
	 * 节点机编码
	 */
	private String peerCode;
	/**
	 * 资源库编码
	 */
	private String rsrcCode;


	// add by yzou
	/** 数据标签 */
	private Long dataTag;

	private String dataName;

	@Transient
	private List<String> cols;
}
