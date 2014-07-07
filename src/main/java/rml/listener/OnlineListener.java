package rml.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import rml.model.vo.SessionInfo;
import rml.service.OnlineServiceI;
import rml.util.ResourceUtil;

/**
 * online user monitor
 *  
 */
public class OnlineListener implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener, ServletRequestListener, ServletRequestAttributeListener {

	private static final Logger logger = Logger.getLogger(OnlineListener.class);

	private static ApplicationContext ctx = null;

	public OnlineListener() {
	}

	public void requestDestroyed(ServletRequestEvent arg0) {
	}

	/**
	 * called when add attributes to session (called after successful login)
	 */
	public void attributeAdded(HttpSessionBindingEvent evt) {
		String name = evt.getName();
		logger.debug("the name of attribute saved in session: " + name);
		if (ResourceUtil.getSessionInfoName().equals(name)) {// the name of attribute saved in session is 'sessionInfo'
			HttpSession session = evt.getSession();
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(name);
			if (sessionInfo != null) {
				OnlineServiceI onlineService = (OnlineServiceI) ctx.getBean("onlineService");
				onlineService.saveOrUpdateTonlineByLoginNameAndIp(sessionInfo.getLoginName(), sessionInfo.getIp());
			}
		}
	}

	/**
	 * called when server initialized
	 */
	public void contextInitialized(ServletContextEvent evt) {
		logger.debug("server started");
		ctx = WebApplicationContextUtils.getWebApplicationContext(evt.getServletContext());
	}

	public void sessionDidActivate(HttpSessionEvent arg0) {
	}

	public void valueBound(HttpSessionBindingEvent arg0) {
	}

	public void attributeAdded(ServletContextAttributeEvent arg0) {
	}

	public void attributeRemoved(ServletContextAttributeEvent arg0) {
	}

	/**
	 * session destroyed(called after logout)
	 */
	public void sessionDestroyed(HttpSessionEvent evt) {
		HttpSession session = evt.getSession();
		if (session != null) {
			logger.debug("session destroyed: " + session.getId());
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ResourceUtil.getSessionInfoName());
			if (sessionInfo != null) {
				OnlineServiceI onlineService = (OnlineServiceI) ctx.getBean("onlineService");
				onlineService.deleteTonlineByLoginNameAndIp(sessionInfo.getLoginName(), sessionInfo.getIp());
			}
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
	}

	public void attributeAdded(ServletRequestAttributeEvent evt) {
	}

	public void valueUnbound(HttpSessionBindingEvent arg0) {
	}

	public void sessionWillPassivate(HttpSessionEvent arg0) {
	}

	public void sessionCreated(HttpSessionEvent arg0) {
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
	}

	public void attributeReplaced(ServletContextAttributeEvent arg0) {
	}

	public void attributeRemoved(ServletRequestAttributeEvent arg0) {
	}

	public void contextDestroyed(ServletContextEvent evt) {
		logger.debug("server closed");
	}

	public void attributeReplaced(ServletRequestAttributeEvent arg0) {
	}

	public void requestInitialized(ServletRequestEvent arg0) {
	}

}

