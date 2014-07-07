package rml.model.vo;

import java.util.List;
import java.util.Map;

/**
 * tree model for easyui
 *
 */
public class TreeNode implements java.io.Serializable {

	private String id;
	private String text;// name of tree node
	private String iconCls;// icon
	private Boolean checked = false;// is checked
	private Map<String, Object> attributes;// other parameters
	private List<TreeNode> children;// child node
	private String state = "open";// (open,closed)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

}
