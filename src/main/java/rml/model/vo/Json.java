package rml.model.vo;

/**
 * JSON model
 * 
 */
public class Json implements java.io.Serializable {

	private boolean success = false;// is successful
	private String msg = "";// message
	private Object obj = null;// other info

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
