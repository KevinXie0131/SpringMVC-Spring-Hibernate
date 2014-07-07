package rml.model.vo;

import java.util.List;

/**
 * datagrid model for easyui
 * 
 */
public class DataGrid implements java.io.Serializable {

	private Long total;// total number of records
	private List rows;// each records
	private List footer;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public List getFooter() {
		return footer;
	}

	public void setFooter(List footer) {
		this.footer = footer;
	}

}
