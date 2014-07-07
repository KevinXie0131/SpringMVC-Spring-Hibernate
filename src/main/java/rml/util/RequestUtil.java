package rml.util;

import javax.servlet.http.HttpServletRequest;

/**
 * request util
 */
public class RequestUtil {

	/**
	 * get path of request
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI();
		requestPath = requestPath.substring(request.getContextPath().length());// remove path of project
		return requestPath;
	}

}
