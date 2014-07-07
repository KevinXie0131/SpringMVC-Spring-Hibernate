package rml.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import rml.dao.BaseDaoI;
import rml.model.po.Tequip;
import rml.model.vo.DataGrid;
import rml.model.vo.Equip;
import rml.service.EquipServiceI;
import rml.util.ExcelExport;

@Service("equipService")
public class EquipServiceImpl implements EquipServiceI{

	private BaseDaoI<Tequip> equipDao;
	
	public BaseDaoI<Tequip> getEquipDao() {
		return equipDao;
	}

	@Autowired
	public void setEquipDao(BaseDaoI<Tequip> equipDao) {
		this.equipDao = equipDao;
	}
	
	@Override
	public void save(Equip equip) {
		Tequip e = new Tequip();
		BeanUtils.copyProperties(equip, e);
		e.setCid(UUID.randomUUID().toString());
		equipDao.save(e);
	}

	@Override
	public void update(Equip equip) {
		Tequip e = equipDao.get(Tequip.class, equip.getCid());
		BeanUtils.copyProperties(equip, e);
	}

	@Override
	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {	
				Tequip e = equipDao.get(Tequip.class, id.trim());
				if (e != null) {
					equipDao.delete(e);
				}
			}
		}	
	}

	@Override
	public DataGrid datagrid(Equip equip) {
		DataGrid j = new DataGrid();
		j.setRows(this.changeModel(this.find(equip)));
		j.setTotal(this.total(equip));
		return j;
	}

	private List<Equip> changeModel(List<Tequip> tequips) {
		List<Equip> equips = new ArrayList<Equip>();
		if (tequips != null && tequips.size() > 0) {
			for (Tequip te : tequips) {
				Equip e = new Equip();
				BeanUtils.copyProperties(te, e);
				equips.add(e);
			}
		}
		return equips;
	}
	
	private List<Tequip> find(Equip equip) {
		String hql = "from Tequip t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(equip, hql, values);

		if (equip.getSort() != null && equip.getOrder() != null) {
			hql += " order by " + equip.getSort() + " " + equip.getOrder();
		}
		return equipDao.find(hql, values, equip.getPage(), equip.getRows());
	}

	private Long total(Equip equip) {
		String hql = "select count(*) from Tequip t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(equip, hql, values);
		return equipDao.count(hql, values);
	}

	private String addWhere(Equip equip, String hql, List<Object> values) {
		if (equip.getCname() != null && !equip.getCname().trim().equals("")) {
			hql += " and t.cname like ? ";
			values.add("%%" + equip.getCname().trim() + "%%");
		}
		return hql;
	}

	@Override
	public void exportToExcelFile(HttpServletResponse response) {
		
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String formatDate = format.format(new Date());
		int random = new Random().nextInt(100);
		String xlsName = new StringBuffer("EquipmentList_").append(formatDate).append("_").append(random).append(".xls").toString();
		
	//	HttpServletResponse response = ServletActionContext.getResponse();
	
	//	HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
      
	//	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	//	ServletWebRequest servletWebRequest=new ServletWebRequest(request);
	//	HttpServletResponse response=servletWebRequest.getResponse();
		
		
		response.setContentType("octets/stream");
        response.addHeader("Content-Disposition","attachment;filename="+xlsName);
        
        ExcelExport<Tequip> export = new ExcelExport<Tequip>();
        String[] headers = {"Id","Model","Name","Producer","Number","Description"};
        
        List<Tequip> tequips = equipDao.find("from Tequip t where 1=1");      
        List<Tequip> dataset = new ArrayList<Tequip>();
        for(int i =0;i<tequips.size();i++){
    	   dataset.add(tequips.get(i));
        }
        
        OutputStream out = null;
        try {
        	out = response.getOutputStream();
            export.exportExcel("Equipment", headers, dataset, out, "yyyy-MM-dd");
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	if(out != null){
        		try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}

	// based on ExcelFileGenerator
/*	@Override
	public void exportToExcelFile() {
		
		ArrayList filedName = new ArrayList();
		String [] titles = {"Id","Model","Name","Producer","Number","Description"};
		for(int i=0;i<titles.length;i++){
			String title = titles[i];
			filedName.add(title);
		}
		
		ArrayList filedData = new ArrayList();		
		ArrayList dataList = new ArrayList();
		String hql = "from Tequip t where 1=1 ";	
		ArrayList tlist = (ArrayList) equipDao.find(hql);
		
		for(int i=0; i<tlist.size(); i++){
			Tequip t = (Tequip) tlist.get(i);
			dataList.add(t.getCid());
			dataList.add(t.getCmodel());
			dataList.add(t.getCname());
			dataList.add(t.getCproducer());
			dataList.add(t.getCno().toString());
			dataList.add(t.getCdesc());
			filedData.add(dataList);
		}
		
		try {
			
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String formatDate = format.format(new Date());
			int random = new Random().nextInt(10);
			String xlsName = new StringBuffer().append(formatDate).append("_").append(random).append(".xls").toString();
			
			HttpServletResponse response = ServletActionContext.getResponse();
			OutputStream out = response.getOutputStream();
			response.reset();
			response.setHeader("Content-Disposition", "attachment;filename="+xlsName);
			response.setContentType("application/vnd.ms-excel");
			ExcelFileGenerator generator = new ExcelFileGenerator(filedName,filedData);
			generator.expordExcel(out);
			System.setOut(new PrintStream(out));
			out.flush();
			if(out!=null){
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			
		}
		
	}
*/
	
}
