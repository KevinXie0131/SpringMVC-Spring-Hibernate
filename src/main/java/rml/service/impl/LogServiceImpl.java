package rml.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import rml.dao.BaseDaoI;
import rml.model.po.Tlog;
import rml.model.po.Tuser;
import rml.model.po.Tusertrole;
import rml.model.vo.DataGrid;
import rml.model.vo.Log;
import rml.model.vo.User;
import rml.service.LogServiceI;
import rml.util.Encrypt;
import rml.util.IpUtil;

@Aspect
@Service("logService")
public class LogServiceImpl implements LogServiceI {
	
	@Pointcut("execution(* rml.service.impl.UserServiceImpl.*(..))")
	public void pointcut(){}  
	
	private static final Logger logger = Logger.getLogger(LogServiceImpl.class);

	private BaseDaoI<Tlog> logDao;
	
	public BaseDaoI<Tlog> getLogDao() {
		return logDao;
	}
	
	@Autowired
	public void setLogDao(BaseDaoI<Tlog> logDao) {
		this.logDao = logDao;
	}

//	@Before("pointcut()")
	public void beforeLog(JoinPoint point) {
		logger.info(new Date() + " before enter");
		logger.info(point.getSignature().getName());
		Object[] args = point.getArgs();
		if (args != null && args.length > 0) {
			for (Object obj : args) {
				logger.info(obj);
			}
		}
	}

//	@After("pointcut()")
	public void afterLog(JoinPoint point) {
		logger.info(new Date() + " after enter");
		logger.info(point.getSignature().getName());
		Object[] args = point.getArgs();
		if (args != null && args.length > 0) {
			for (Object obj : args) {
				logger.info(obj);
			}
		}
	}

	@Around("pointcut()")
	public Object aroundLog(ProceedingJoinPoint point) {

		logger.info(new Date() + " around");
		logger.info(point.getSignature().getName());
		
		Object o = null;
		String methodName = point.getSignature().getName();//get method name		
		User user = null;

		Object[] args = point.getArgs();
		if (args != null && args.length > 0) {
			for (Object obj : args) {
				if (obj instanceof User) {
					user = (User) obj;// get arguments

				}
			}
		}

		if ("login".equals(methodName)) {
			Tlog t = new Tlog();	
			t.setCid(UUID.randomUUID().toString());
			t.setCname(user.getCname());
		//	t.setCip(IpUtil.getIpAddr(ServletActionContext.getRequest()));
			t.setCip(IpUtil.getIpAddr(((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest()));
			t.setCdatetime(new Date());
			try {
				o = point.proceed();//continue
				t.setCmsg("Log: Login successfully");
			} catch (Throwable e) {
				t.setCmsg("Log: Login unsuccessfully");
			}
			logDao.save(t);
		}
		else if ("save".equals(methodName)) {
			Tlog t = new Tlog();
			t.setCid(UUID.randomUUID().toString());
			t.setCname(user.getCname());
	//		t.setCip(IpUtil.getIpAddr(ServletActionContext.getRequest()));
			t.setCip(IpUtil.getIpAddr(((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest()));
			t.setCdatetime(new Date());
			try {
				o = point.proceed();//continue
				t.setCmsg("Log: Register successfully");
			} catch (Throwable e) {
				t.setCmsg("Log: Register unsuccessfully");
			}
			logDao.save(t);
		}
		else{
			try {
				o = point.proceed();//continue
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return o;
	}

	@Override
	public DataGrid datagrid(Log log) {
		DataGrid j = new DataGrid();
		j.setRows(this.changeModel(this.find(log)));
		j.setTotal(this.total(log));
		return j;
	}
	
	private List<Log> changeModel(List<Tlog> tlogs) {
		List<Log> logs = new ArrayList<Log>();
		if (tlogs != null && tlogs.size() > 0) {
			for (Tlog tl : tlogs) {
				Log l = new Log();
				BeanUtils.copyProperties(tl, l);
				logs.add(l);
			}
		}
		return logs;
	}

	private List<Tlog> find(Log log) {
		String hql = "from Tlog t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(log, hql, values);

		if (log.getSort() != null && log.getOrder() != null) {
			hql += " order by " + log.getSort() + " " + log.getOrder();
		}
		return logDao.find(hql, values, log.getPage(), log.getRows());
	}

	private Long total(Log log) {
		String hql = "select count(*) from Tlog t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(log, hql, values);
		return logDao.count(hql, values);
	}
	
	private String addWhere(Log log, String hql, List<Object> values) {
		if (log.getCname() != null && !log.getCname().trim().equals("")) {
			hql += " and t.cname like ? ";
			values.add("%%" + log.getCname().trim() + "%%");
		}
		return hql;
	}

}
