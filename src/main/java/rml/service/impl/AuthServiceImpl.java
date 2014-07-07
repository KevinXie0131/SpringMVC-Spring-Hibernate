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

import rml.comparator.AuthComparator;
import rml.dao.BaseDaoI;
import rml.model.po.Tauth;
import rml.model.po.Troletauth;
import rml.model.vo.Auth;
import rml.model.vo.TreeNode;
import rml.service.AuthServiceI;

@Service("authService")
public class AuthServiceImpl implements AuthServiceI {

	private BaseDaoI<Tauth> authDao;
	private BaseDaoI<Troletauth> roleauthDao;

	public BaseDaoI<Troletauth> getRoleauthDao() {
		return roleauthDao;
	}

	@Autowired
	public void setRoleauthDao(BaseDaoI<Troletauth> roleauthDao) {
		this.roleauthDao = roleauthDao;
	}

	public BaseDaoI<Tauth> getAuthDao() {
		return authDao;
	}

	@Autowired
	public void setAuthDao(BaseDaoI<Tauth> authDao) {
		this.authDao = authDao;
	}

	public void add(Auth auth) {
		Tauth t = new Tauth();
		BeanUtils.copyProperties(auth, t);
		t.setCid(UUID.randomUUID().toString());
		if (auth.getPid() != null && !auth.getPid().equals(auth.getCid())) {
			t.setTauth(authDao.get(Tauth.class, auth.getPid()));
		}
		authDao.save(t);
	}

	public void edit(Auth auth) {
		Tauth t = authDao.get(Tauth.class, auth.getCid());// authority to be edit
		BeanUtils.copyProperties(auth, t);
		if (auth.getPid() != null && !auth.getPid().equals(auth.getCid())) {
			Tauth pAuth = authDao.get(Tauth.class, auth.getPid());// parent authority
			if (pAuth != null) {
				if (isDown(t, pAuth)) {
					Set<Tauth> tauths = t.getTauths();// all child auth of current auth
					if (tauths != null && tauths.size() > 0) {
						for (Tauth tauth : tauths) {
							if (tauth != null) {
								tauth.setTauth(null);
							}
						}
					}
				}
				t.setTauth(pAuth);
			}
		}
	}

	private boolean isDown(Tauth t, Tauth pt) {
		if (pt.getTauth() != null) {
			if (pt.getTauth().getCid().equals(t.getCid())) {
				return true;
			} else {
				return isDown(t, pt.getTauth());
			}
		}
		return false;
	}
	
	public void delete(Auth auth) {
		del(auth.getCid());
	}

	private void del(String cid) {
		Tauth t = authDao.get(Tauth.class, cid);
		if (t != null) {
			roleauthDao.executeHql("delete Troletauth t where t.tauth = ?", new Object[] { t });
			Set<Tauth> auths = t.getTauths();
			if (auths != null && !auths.isEmpty()) {
				// there exists child auth under current auth
				for (Tauth tauth : auths) {
					del(tauth.getCid());
				}
			}
			authDao.delete(t);
		}
	}
	
	public List<TreeNode> tree(Auth auth, boolean b) {
		List<Object> param = new ArrayList<Object>();
		String hql = "from Tauth t where t.tauth is null order by t.cseq";
		if (auth != null && auth.getId() != null && !auth.getId().trim().equals("")) {
			hql = "from Tauth t where t.tauth.cid = ? order by t.cseq";
			param.add(auth.getId());
		}
		List<Tauth> l = authDao.find(hql, param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (Tauth t : l) {
			tree.add(this.tree(t, b));
		}
		return tree;
	}
	
	public List<Auth> treegrid(Auth auth) {
		List<Tauth> l;
		if (auth != null && auth.getId() != null) {
			l = authDao.find("from Tauth t where t.tauth.cid = ? order by t.cseq", new Object[] { auth.getId() });
		} else {
			l = authDao.find("from Tauth t where t.tauth is null order by t.cseq");
		}
		return changeModel(l);
	}

	private List<Auth> changeModel(List<Tauth> tauths) {
		List<Auth> l = new ArrayList<Auth>();
		if (tauths != null && tauths.size() > 0) {
			for (Tauth t : tauths) {
				Auth a = new Auth();
				BeanUtils.copyProperties(t, a);
				if (t.getTauth() != null) {
					a.setPid(t.getTauth().getCid());
					a.setPname(t.getTauth().getCname());
				}
				if (countChildren(t.getCid()) > 0) {
					a.setState("closed");
				}
				l.add(a);
			}
		}
		return l;
	}

	/**
	 * count the number of child nodes
	 */
	private Long countChildren(String pid) {
		return authDao.count("select count(*) from Tauth t where t.tauth.cid = ?", new Object[] { pid });
	}
	
	private TreeNode tree(Tauth t, boolean recursive) {
		TreeNode node = new TreeNode();
		node.setId(t.getCid());
		node.setText(t.getCname());
		Map<String, Object> attributes = new HashMap<String, Object>();
		node.setAttributes(attributes);
		if (t.getTauths() != null && t.getTauths().size() > 0) {
			node.setState("closed");
			if (recursive) {// query child nodes recursively
				List<Tauth> l = new ArrayList<Tauth>(t.getTauths());
				Collections.sort(l, new AuthComparator());// sort
				List<TreeNode> children = new ArrayList<TreeNode>();
				for (Tauth r : l) {
					TreeNode tn = tree(r, true);
					children.add(tn);
				}
				node.setChildren(children);
				node.setState("open");
			}
		}
		return node;
	}

}
