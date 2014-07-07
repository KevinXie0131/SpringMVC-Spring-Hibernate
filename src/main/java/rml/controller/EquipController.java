package rml.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rml.controller.BaseController;
import rml.model.vo.DataGrid;
import rml.model.vo.Equip;
import rml.model.vo.Json;
import rml.service.EquipServiceI;
import rml.util.ExceptionUtil;

@Controller
@RequestMapping("/equipController")
public class EquipController extends BaseController{

	private static final Logger logger = Logger.getLogger(EquipController.class);
	
	@Autowired
	private EquipServiceI equipService;
	
	@RequestMapping("/equip")
	public String equip() {
		return "/admin/equip";
	}

	@RequestMapping("/equipAdd")
	public String equipAdd() {
		return "/admin/equipAdd";
	}
	
	@RequestMapping("/equipEdit")
	public String equipEdit() {
		return "/admin/equipEdit";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Json add(Equip equip) {
		Json j = new Json();
		try {
			equipService.save(equip);
			j.setSuccess(true);
			j.setMsg("Add successfully");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("Add unsuccessfully");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(Equip equip) {
		Json j = new Json();
		try {
			equipService.update(equip);
			j.setSuccess(true);
			j.setMsg("Edit successfully");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("Edit unsuccessfully");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(Equip equip) {
		Json j = new Json();
		try {
			equipService.delete(equip.getIds());
			j.setSuccess(true);
			j.setMsg("Delete successfully");
		}catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("Delete unsuccessfully");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping("/datagrid")
	public DataGrid datagrid(Equip equip) {
		return equipService.datagrid(equip);
	}
	
	@ResponseBody
	@RequestMapping("/exportToExcel")
	public String exportToExcel(HttpServletResponse response){	
		equipService.exportToExcelFile(response);
		return "/admin/equip";
	}
}
