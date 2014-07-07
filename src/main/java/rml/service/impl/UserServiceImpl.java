package rml.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rml.dao.BaseDaoI;
import rml.model.po.Tauth;
import rml.model.po.Trole;
import rml.model.po.Troletauth;
import rml.model.po.Tuser;
import rml.model.po.Tusertrole;
import rml.model.vo.DataGrid;
import rml.model.vo.RoleChart;
import rml.model.vo.User;
import rml.service.UserServiceI;
import rml.util.Encrypt;

@Service("userService")
public class UserServiceImpl implements UserServiceI {

	private BaseDaoI<Tuser> userDao;
	private BaseDaoI<Trole> roleDao;
	private BaseDaoI<Tusertrole> userroleDao;
	private BaseDaoI<RoleChart> roleChartDao;
	
	public BaseDaoI<Tuser> getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(BaseDaoI<Tuser> userDao) {
		this.userDao = userDao;
	}
	
	public BaseDaoI<Trole> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(BaseDaoI<Trole> roleDao) {
		this.roleDao = roleDao;
	}

	public BaseDaoI<Tusertrole> getUserroleDao() {
		return userroleDao;
	}

	@Autowired
	public void setUserroleDao(BaseDaoI<Tusertrole> userroleDao) {
		this.userroleDao = userroleDao;
	}

	public BaseDaoI<RoleChart> getRoleChartDao() {
		return roleChartDao;
	}

	@Autowired
	public void setRoleChartDao(BaseDaoI<RoleChart> roleChartDao) {
		this.roleChartDao = roleChartDao;
	}
	
	public User login(User user) {
		Tuser t = userDao.get("from Tuser t where t.cname = ? and t.cpwd = ?", new Object[] { user.getCname(), Encrypt.e(user.getCpwd()) });
		if (t != null) {
			BeanUtils.copyProperties(t, user, new String[] { "cpwd" });

			Map<String, String> authIdsMap = new HashMap<String, String>();
			String authIds = "";
			String authNames = "";
			Map<String, String> authUrlsMap = new HashMap<String, String>();
			String authUrls = "";
			String roleIds = "";
			String roleNames = "";
			boolean b1 = false;
			Set<Tusertrole> tusertroles = t.getTusertroles();
			if (tusertroles != null && tusertroles.size() > 0) {
				for (Tusertrole tusertrole : tusertroles) {
					Trole trole = tusertrole.getTrole();
					if (b1) {
						roleIds += ",";
						roleNames += ",";
					}
					roleIds += trole.getCid();
					roleNames += trole.getCname();
					b1 = true;

					Set<Troletauth> troletauths = trole.getTroletauths();
					if (troletauths != null && troletauths.size() > 0) {
						for (Troletauth troletauth : troletauths) {
							Tauth tauth = troletauth.getTauth();
							authIdsMap.put(tauth.getCid(), tauth.getCname());
							authUrlsMap.put(tauth.getCid(), tauth.getCurl());
						}
					}
				}
			}
			boolean b2 = false;
			for (String id : authIdsMap.keySet()) {
				if (b2) {
					authIds += ",";
					authNames += ",";
				}
				authIds += id;
				authNames += authIdsMap.get(id);
				b2 = true;
			}
			user.setAuthIds(authIds);
			user.setAuthNames(authNames);
			user.setRoleIds(roleIds);
			user.setRoleNames(roleNames);
			boolean b3 = false;
			for (String id : authUrlsMap.keySet()) {
				if (b3) {
					authUrls += ",";
				}
				authUrls += authUrlsMap.get(id);
				b3 = true;
			}
			user.setAuthUrls(authUrls);

			return user;
		}
		return null;
	}

	public void save(User user) {	
		Tuser u = new Tuser();
		BeanUtils.copyProperties(user, u, new String[] { "cpwd" });
		u.setCid(UUID.randomUUID().toString());
		u.setCpwd(Encrypt.e(user.getCpwd()));
		if (user.getCcreatedatetime() == null) {
			u.setCcreatedatetime(new Date());
		}
		if (user.getCmodifydatetime() == null) {
			u.setCmodifydatetime(new Date());
		}			
		userDao.save(u);
		
		this.saveUserRole(user, u);
	}

	/**
	 * keep relationship between user and role
	 * 
	 * @param user
	 * @param u
	 */
	private void saveUserRole(User user, Tuser u) {
		userroleDao.executeHql("delete Tusertrole t where t.tuser = ?", new Object[] { u });
		if (user.getRoleIds() != null) {
			for (String id : user.getRoleIds().split(",")) {
				Tusertrole tusertrole = new Tusertrole();
				tusertrole.setCid(UUID.randomUUID().toString());
				tusertrole.setTrole(roleDao.get(Trole.class, id.trim()));
				tusertrole.setTuser(u);
				userroleDao.save(tusertrole);
			}
		}
	}

	public void update(User user) {	
		Tuser u = userDao.get(Tuser.class, user.getCid());
		BeanUtils.copyProperties(user, u, new String[] { "cid", "cpwd" });
		if (user.getCpwd() != null && !user.getCpwd().trim().equals("")) {
			u.setCpwd(Encrypt.e(user.getCpwd()));
		}
		u.setCmodifydatetime(new Date());
		this.saveUserRole(user, u);
	}

	public DataGrid datagrid(User user) {
		DataGrid j = new DataGrid();
		j.setRows(this.changeModel(this.find(user)));
		j.setTotal(this.total(user));
		return j;
	}

	private List<User> changeModel(List<Tuser> tusers) {
		List<User> users = new ArrayList<User>();
		if (tusers != null && tusers.size() > 0) {
			for (Tuser tu : tusers) {
				User u = new User();
				BeanUtils.copyProperties(tu, u);

				Set<Tusertrole> tusertroles = tu.getTusertroles();
				String roleIds = "";
				String roleNames = "";
				boolean b = false;
				if (tusertroles != null && tusertroles.size() > 0) {
					for (Tusertrole tusertrole : tusertroles) {
						if (tusertrole.getTrole() != null) {
							if (b) {
								roleIds += ",";
								roleNames += ",";
							}
							roleIds += tusertrole.getTrole().getCid();
							roleNames += tusertrole.getTrole().getCname();
							b = true;
						}
					}
				}
				u.setRoleIds(roleIds);
				u.setRoleNames(roleNames);

				users.add(u);
			}
		}
		return users;
	}

	private List<Tuser> find(User user) {
		String hql = "from Tuser t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(user, hql, values);

		if (user.getSort() != null && user.getOrder() != null) {
			hql += " order by " + user.getSort() + " " + user.getOrder();
		}
		return userDao.find(hql, values, user.getPage(), user.getRows());
	}

	private Long total(User user) {
		String hql = "select count(*) from Tuser t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(user, hql, values);
		return userDao.count(hql, values);
	}

	private String addWhere(User user, String hql, List<Object> values) {
		if (user.getCname() != null && !user.getCname().trim().equals("")) {
			hql += " and t.cname like ? ";
			values.add("%%" + user.getCname().trim() + "%%");
		}
/*		if (user.getCcreatedatetimeStart() != null) {
			hql += " and t.ccreatedatetime>=? ";
			values.add(user.getCcreatedatetimeStart());
		}
		if (user.getCcreatedatetimeEnd() != null) {
			hql += " and t.ccreatedatetime<=? ";
			values.add(user.getCcreatedatetimeEnd());
		}
		if (user.getCmodifydatetimeStart() != null) {
			hql += " and t.cmodifydatetime>=? ";
			values.add(user.getCmodifydatetimeStart());
		}
		if (user.getCmodifydatetimeEnd() != null) {
			hql += " and t.cmodifydatetime<=? ";
			values.add(user.getCmodifydatetimeEnd());
		}
*/
		return hql;
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				Tuser u = userDao.get(Tuser.class, id.trim());
				if (u != null) {
					userroleDao.executeHql("delete Tusertrole t where t.tuser = ?", new Object[] { u });
					userDao.delete(u);
				}
			}
		}
	}

	public void roleEdit(User user) {
		if (user.getIds() != null) {
			for (String id : user.getIds().split(",")) {
				Tuser u = userDao.get(Tuser.class, id);
				this.saveUserRole(user, u);
			}
		}
	}

	public void editUserInfo(User user) {
		if (user.getCpwd() != null && !user.getCpwd().trim().equals("")) {
			Tuser t = userDao.get(Tuser.class, user.getCid());
			t.setCpwd(Encrypt.e(user.getCpwd()));
		}
	}

	public List<User> combobox(User user) {
		String q = "";
		if (user != null && user.getQ() != null) {
			q = user.getQ().trim();
		}
		return changeModel(userDao.find("from Tuser t where t.cname like ?", new Object[] { "%%" + q.trim() + "%%" }, 1, 10));
	}

	@Override
	public List<RoleChart> countRole() {
		
		List<RoleChart> datalist = new ArrayList<RoleChart>();
		
		String hql = "select r.cname as name,count(u.cid) as count from Qrole r left join Qusertrole u on r.cid=u.croleid group by r.cname";
		List list = roleChartDao.findSQL(hql);	
		for(int i=0;i<list.size();i++){
			Object[] object = (Object[]) list.get(i);
			RoleChart rc = new RoleChart();
			rc.setName(object[0].toString());
			rc.setCount(Integer.valueOf((object[1]).toString()));
			datalist.add(rc);
		}
		return datalist;
	}

}
