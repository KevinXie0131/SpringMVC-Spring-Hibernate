package rml.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rml.dao.BaseDaoI;
import rml.model.po.Tdoc;
import rml.model.vo.DataGrid;
import rml.model.vo.Doc;
import rml.service.DocServiceI;

@Service("docService")
public class DocServiceImpl implements DocServiceI{

	private BaseDaoI<Tdoc> docDao;
	
	public BaseDaoI<Tdoc> getDocDao() {
		return docDao;
	}

	@Autowired
	public void setDocDao(BaseDaoI<Tdoc> docDao) {
		this.docDao = docDao;
	}
	
	@Override
	public void save(Doc doc) {
		Tdoc d = new Tdoc();
		BeanUtils.copyProperties(doc, d, new String[]{"cmanual"});
		d.setCid(UUID.randomUUID().toString());
		docDao.save(d);
	}

	@Override
	public void update(Doc doc) {
		Tdoc d = docDao.get(Tdoc.class, doc.getCid());
		BeanUtils.copyProperties(doc, d, new String[]{"cmanual"});
	}

	@Override
	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {	
				Tdoc d = docDao.get(Tdoc.class, id.trim());
				if (d != null) {
					docDao.delete(d);
				}
			}
		}	
	}

	@Override
	public void upload(Doc doc, String fileName) {
		
		Tdoc d = docDao.get(Tdoc.class, doc.getCid());
		String manualUrl = "<a href='upload/" + fileName + "'>" + fileName + "</a>";
		d.setCmanual(manualUrl);
	}	
	
	@Override
	public DataGrid datagrid(Doc doc) {
		DataGrid j = new DataGrid();
		j.setRows(this.changeModel(this.find(doc)));
		j.setTotal(this.total(doc));
		return j;
	}

	private List<Doc> changeModel(List<Tdoc> tdocs) {
		List<Doc> docs = new ArrayList<Doc>();
		if (tdocs != null && tdocs.size() > 0) {
			for (Tdoc td : tdocs) {
				Doc d = new Doc();
				BeanUtils.copyProperties(td,d);
				docs.add(d);
			}
		}
		return docs;
	}
	
	private List<Tdoc> find(Doc doc) {
		String hql = "from Tdoc t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(doc, hql, values);

		if (doc.getSort() != null && doc.getOrder() != null) {
			hql += " order by " + doc.getSort() + " " + doc.getOrder();
		}
		return docDao.find(hql, values, doc.getPage(), doc.getRows());
	}

	private Long total(Doc doc) {
		String hql = "select count(*) from Tdoc t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(doc, hql, values);
		return docDao.count(hql, values);
	}

	private String addWhere(Doc doc, String hql, List<Object> values) {
		if (doc.getCname() != null && !doc.getCname().trim().equals("")) {
			hql += " and t.cname like ? ";
			values.add("%%" + doc.getCname().trim() + "%%");
		}
		return hql;
	}
	
}
