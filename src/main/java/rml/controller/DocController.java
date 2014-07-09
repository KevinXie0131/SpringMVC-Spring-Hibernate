package rml.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import rml.model.vo.DataGrid;
import rml.model.vo.Doc;
import rml.model.vo.Json;
import rml.service.DocServiceI;
import rml.util.ExceptionUtil;

@Controller
@RequestMapping("/docController")
public class DocController extends BaseController implements ServletContextAware{

	private static final Logger logger = Logger.getLogger(DocController.class);
	
	@Autowired
	private DocServiceI docService;
	
	private ServletContext servletContext;
	
	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext  = context;
	}
	
	@RequestMapping("/doc")
	public String doc() {
		return "/admin/doc";
	}

	@RequestMapping("/docAdd")
	public String docAdd() {
		return "/admin/docAdd";
	}

	@RequestMapping("/docEdit")
	public String docEdit() {
		return "/admin/docEdit";
	}
	
	@RequestMapping("/docUpload")
	public String docUpload() {
		return "/admin/docUpload";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(Doc doc) {
		Json j = new Json();
		try {
			docService.save(doc);
			j.setSuccess(true);
			j.setMsg("Add successfully");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("Add unsuccessfully");
		}
		return j;
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Doc doc) {
		Json j = new Json();
		try {
			docService.update(doc);
			j.setSuccess(true);
			j.setMsg("Edit successfully");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("Edit unsuccessfully");
		}
		return j;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Doc doc) {
		Json j = new Json();
		try {
			docService.delete(doc.getIds());
			j.setSuccess(true);
			j.setMsg("Delete successfully");
		}catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("Delete unsuccessfully");
		}
		return j;
	}

	@RequestMapping("/datagrid")
	@ResponseBody
	public DataGrid datagrid(Doc doc) {
		return docService.datagrid(doc);
	}
		
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	@ResponseBody
	public Json upload(Doc doc, @RequestParam("uploadFile") CommonsMultipartFile file) {
		Json j = new Json();
		
		try {
			String realpath = this.servletContext.getRealPath("/upload");			
			String uploadFileFileName = file.getOriginalFilename();			
			String uploadFileFileNameWithoutSpace = uploadFileFileName.replaceAll(" ", "");		
			String fileType = uploadFileFileNameWithoutSpace.substring(uploadFileFileNameWithoutSpace.lastIndexOf("."));
			
			File targetFile = new File(realpath+File.separator, uploadFileFileNameWithoutSpace);
			if (targetFile.exists()) {
				targetFile.delete();
			}
			file.getFileItem().write(targetFile);		
			docService.upload(doc,uploadFileFileNameWithoutSpace);
			
			j.setSuccess(true);
			j.setMsg("Upload manual successfully");
			
		}catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("Upload manual unsuccessfully");
		}
		
		return j;
	}
}
