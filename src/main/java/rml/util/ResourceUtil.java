package rml.util;

import java.util.ResourceBundle;

/**
 * Config parameter util
 * 
 */
public class ResourceUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	private void ResourceUtil() {
	}

	/**
	 * get name of sessionInfo
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

	/**
	 * get name of upload field name
	 * 
	 * @return
	 */
	public static final String getUploadFieldName() {
		return bundle.getString("uploadFieldName");
	}

	/**
	 * maximum size of uploaded file
	 * 
	 * @return
	 */
	public static final long getUploadFileMaxSize() {
		return Long.valueOf(bundle.getString("uploadFileMaxSize"));
	}

	/**
	 * extention name of uploaded file
	 * 
	 * @return
	 */
	public static final String getUploadFileExts() {
		return bundle.getString("uploadFileExts");
	}

	/**
	 * get the directory of uploaded file
	 * 
	 * @return
	 */
	public static final String getUploadDirectory() {
		return bundle.getString("uploadDirectory");
	}

}
