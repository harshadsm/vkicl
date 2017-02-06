package vkicl.report.bean;

import java.util.Date;

@SuppressWarnings("serial")
public class DeliveryNoteBean extends BaseReportBean {
	private Integer id;
	private Integer ppoLineitemNo = null;
	private Date created;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPpoLineitemNo() {
		return ppoLineitemNo;
	}

	public void setPpoLineitemNo(Integer ppoLineitemNo) {
		this.ppoLineitemNo = ppoLineitemNo;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
