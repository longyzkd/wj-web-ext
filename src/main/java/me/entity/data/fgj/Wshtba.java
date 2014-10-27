package me.entity.data.fgj;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import me.entity.common.DataEntity;
import me.utils.excel.Uploadable;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 房管局网上合同备案表
 * @author wj
 *
 */
@Entity
@Table(name = "data_fgj_wshtba")
public class Wshtba extends DataEntity implements Uploadable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8419055117956389256L;
	/**
	 * 序号
	 */
	private Long xh;
	/**
	 * 企业名称
	 */
	private String qymc;
	
	/**
	 * 组织机构代码
	 */
	private String zzjgdm;
	
	/**
	 * 工商注册号
	 */
	private String gszch;
	
	
	/**
	 * 税号
	 */
	private String sh;
	
	/**
	 * 项目名称
	 */
	private String xmmc;
	
	/**
	 * 建筑面积
	 */
	private Double jzmj;
	
	/**
	 * 合同金额(元)
	 */
	private Double htje;
	
	/**
	 * 起始日期
	 */
	private Date startDate;
	
	/**
	 * 截止日期
	 */
	private Date endDate;
	
	/**
	 * 填报单位
	 */
	private String tbDw;
	
	/**
	 * 填报日期
	 */
	private Date tbDate;
	
	/**
	 * 文档名称
	 */
	private String wdMc;
	
	private Long uploadId;
	
	
	private String title;
	

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUploadId() {
		return uploadId;
	}

	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
	}

	@NotNull
	@Digits(fraction=1,integer=10000)
	public Long getXh() {
		return xh;
	}

	public void setXh(Long xh) {
		this.xh = xh;
	}
	@NotBlank
	public String getQymc() {
		return qymc;
	}

	public void setQymc(String qymc) {
		this.qymc = qymc;
	}
	@NotBlank
	public String getZzjgdm() {
		return zzjgdm;
	}

	public void setZzjgdm(String zzjgdm) {
		this.zzjgdm = zzjgdm;
	}
	@NotBlank
	public String getGszch() {
		return gszch;
	}

	public void setGszch(String gszch) {
		this.gszch = gszch;
	}
	@NotBlank
	public String getSh() {
		return sh;
	}
	public void setSh(String sh) {
		this.sh = sh;
	}
	@NotBlank
	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

//	@DecimalMin("1")
	public Double getJzmj() {
		return jzmj;
	}

	public void setJzmj(Double jzmj) {
		this.jzmj = jzmj;
	}
//	@DecimalMin("1")
	public Double getHtje() {
		return htje;
	}

	public void setHtje(Double htje) {
		this.htje = htje;
	}
	@JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+08:00")//set时候没用？
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+08:00")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTbDw() {
		return tbDw;
	}

	public void setTbDw(String tbDw) {
		this.tbDw = tbDw;
	}
	@JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+08:00")
	public Date getTbDate() {
		return tbDate;
	}

	public void setTbDate(Date tbDate) {
		this.tbDate = tbDate;
	}

	public String getWdMc() {
		return wdMc;
	}

	public void setWdMc(String wdMc) {
		this.wdMc = wdMc;
	}
	
	
}
