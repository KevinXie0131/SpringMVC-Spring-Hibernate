package rml.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

/**
 * Exception util
 * 
 */
public class ExceptionUtil {

	private static final Logger logger = Logger.getLogger(ExceptionUtil.class);

	/**
	 * return error message
	 * 
	 * @param ex	Exception
	 *            
	 * @return Error Message
	 */
	public static String getExceptionMessage(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String errorMessage = sw.toString();
		pw.close();
		try {
			sw.close();
		} catch (IOException e) {
			logger.error(e);
		}
		return errorMessage;
	}

}
