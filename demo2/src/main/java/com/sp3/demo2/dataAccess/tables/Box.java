package com.sp3.demo2.dataAccess.tables;

import java.util.Date;

import com.jfcore.orm.Column;
import com.jfcore.orm.IdAuto;
import com.jfcore.orm.Table;

import lombok.Getter;
import lombok.Setter;

/*  箱子表   */

@Getter @Setter
@IdAuto
@Table (name="w_box",key="id",uniqueKey = "boxCode")
public class Box {
	
	/*   id      */
	@Column(name = "id",lable = "")
	private Integer id;
	
	/*   id      */
	@Column(name = "boxCode",lable = "")
	private String boxCode;
	
	/*   A:占板，B纸箱，C料箱      */
	@Column(name = "type",lable = "A:占板，B纸箱，C料箱")
	private String type;
	
	/*   库位编码      */
	@Column(name = "locationCode",lable = "库位编码")
	private String locationCode;
	
	/*   执行状态，A：在库，B：在途，C：入站，D：释放      */
	@Column(name = "execStatus",lable = "执行状态，A：在库，B：在途，C：入站，D：释放")
	private String execStatus;
	
	/*   最后一次更新时间      */
	@Column(name = "updateTime",lable = "最后一次更新时间")
	private Date updateTime;
	
	/*   剩余容积      */
	@Column(name = "remainVolume",lable = "剩余容积")
	private Double remainVolume;

	/*   创建人      */
	@Column(name = "createBy",lable = "创建人")
	private String createBy;

	/*   创建时间      */
	@Column(name = "createTime",lable = "创建时间")
	private Date createTime;

	/*   warehouseId      */
	@Column(name = "warehouseId",lable = "仓库Id")
	private Integer warehouseId;
}
