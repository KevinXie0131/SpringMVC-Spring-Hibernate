package rml.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import rml.model.vo.DataGrid;
import rml.model.vo.Log;
import rml.model.vo.User;

public interface LogServiceI {

	public void beforeLog(JoinPoint point);

	public void afterLog(JoinPoint point);

	public Object aroundLog(ProceedingJoinPoint point);
	
	public DataGrid datagrid(Log log);

}
