package com.kdgc.flow.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 流程实体类
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "dc_c_flow")
@Setter
@Getter
@SuppressWarnings("serial")
public class DcwebFlows {

	/** flowId 流程ID */
	@Id
	private Long flowId;
	/** flowCode 流程编码 */
	private String flowCode;
	/** pFlowId 父流程编码 */
	private String pflowCode;
	/** 流程名称 */
	private String flowName;
	/** 流程名称 */
	private String flowContent;
	/** 流程的定时执行表达式 */
	private String cronExpression;
	/** 标签 */
	private String rmk;

	// add by yzou
	/** 数据标签 */
	private Long dataTag;

	// add by lcm
	/** 部门id */
	private Long deptId;

	/** 加密方式 */
	private String cryptoType;
	/** 密钥 */
	private String cryptoKey;

	/**流程图是否有更改**/
	private int modified=0;
	
	// 增加流程内局部变量，便于不同目标的相似流程的流程复制
	/** 流程参数 */
	private String taskContext;
	


}
