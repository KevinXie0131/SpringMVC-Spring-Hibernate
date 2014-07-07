package rml.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rml.comparator.MenuComparator;
import rml.dao.BaseDaoI;
import rml.model.po.Tmenu;
import rml.model.vo.Menu;
import rml.model.vo.TreeNode;
import rml.service.MenuServiceI;

@Service("menuService")
public class MenuServiceImpl implements MenuServiceI {

	private BaseDaoI<Tmenu> menuDao;

	public BaseDaoI<Tmenu> getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(BaseDaoI<Tmenu> menuDao) {
		this.menuDao = menuDao;
	}

	public List<TreeNode> tree(Menu menu, Boolean b) {
		List<Object> param = new ArrayList<Object>();
		String hql = "from Tmenu t where t.tmenu is null order by t.cseq";
		if (menu != null && menu.getId() != null && !menu.getId().trim().equals("")) {
			hql = "from Tmenu t where t.tmenu.cid = ? order by t.cseq";
			param.add(menu.getId());
		}
		List<Tmenu> l = menuDao.find(hql, param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (Tmenu t : l) {
			tree.add(this.tree(t, b));
		}
		return tree;
	}

	private TreeNode tree(Tmenu t, boolean recursive) {
		TreeNode node = new TreeNode();
		node.setId(t.getCid());
		node.setText(t.getCname());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("url", t.getCurl());
		node.setAttributes(attributes);
		if (t.getCiconcls() != null) {
			node.setIconCls(t.getCiconcls());
		} else {
			node.setIconCls("");
		}
		if (t.getTmenus() != null && t.getTmenus().size() > 0) {
			node.setState("closed");
			if (recursive) {// query child nodes recursively
				List<Tmenu> l = new ArrayList<Tmenu>(t.getTmenus());
				Collections.sort(l, new MenuComparator());// sort
				List<TreeNode> children = new ArrayList<TreeNode>();
				for (Tmenu r : l) {
					TreeNode tn = tree(r, true);
					children.add(tn);
				}
				node.setChildren(children);
				node.setState("open");
			}
		}
		return node;
	}

	public List<Menu> treegrid(Menu menu) {
		List<Tmenu> l;
		if (menu != null && menu.getId() != null) {
			l = menuDao.find("from Tmenu t where t.tmenu.cid = ? order by t.cseq", new Object[] { menu.getId() });
		} else {
			l = menuDao.find("from Tmenu t where t.tmenu is null order by t.cseq");
		}
		return changeModel(l);
	}

	private List<Menu> changeModel(List<Tmenu> Tmenus) {
		List<Menu> l = new ArrayList<Menu>();
		if (Tmenus != null && Tmenus.size() > 0) {
			for (Tmenu t : Tmenus) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				if (t.getTmenu() != null) {
					m.setPid(t.getTmenu().getCid());
					m.setPname(t.getTmenu().getCname());
				}
				if (countChildren(t.getCid()) > 0) {
					m.setState("closed");
				}
				if (t.getCiconcls() == null) {
					m.setIconCls("");
				} else {
					m.setIconCls(t.getCiconcls());
				}
				l.add(m);
			}
		}
		return l;
	}

	/**
	 * count child nodes
	 */
	private Long countChildren(String pid) {
		return menuDao.count("select count(*) from Tmenu t where t.tmenu.cid = ?", new Object[] { pid });
	}

}
