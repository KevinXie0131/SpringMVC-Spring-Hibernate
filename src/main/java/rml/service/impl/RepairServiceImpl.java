package rml.service.impl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rml.dao.BaseDaoI;
import rml.model.po.Tauth;
import rml.model.po.Tdoc;
import rml.model.po.Tequip;
import rml.model.po.Tlog;
import rml.model.po.Tmenu;
import rml.model.po.Tonline;
import rml.model.po.Trole;
import rml.model.po.Troletauth;
import rml.model.po.Tuser;
import rml.model.po.Tusertrole;
import rml.service.RepairServiceI;
import rml.util.Encrypt;

@Service("repairService")
public class RepairServiceImpl implements RepairServiceI {

	private BaseDaoI<Tuser> userDao;
	private BaseDaoI<Tmenu> menuDao;
	private BaseDaoI<Tonline> onlineDao;
	private BaseDaoI<Tauth> authDao;
	private BaseDaoI<Trole> roleDao;
	private BaseDaoI<Tusertrole> userroleDao;
	private BaseDaoI<Troletauth> roleauthDao;
	private BaseDaoI<Tequip> equipDao;
	private BaseDaoI<Tdoc> docDao;
	private BaseDaoI<Tlog> logDao;

	public BaseDaoI<Troletauth> getRoleauthDao() {
		return roleauthDao;
	}

	@Autowired
	public void setRoleauthDao(BaseDaoI<Troletauth> roleauthDao) {
		this.roleauthDao = roleauthDao;
	}

	public BaseDaoI<Tusertrole> getUserroleDao() {
		return userroleDao;
	}

	@Autowired
	public void setUserroleDao(BaseDaoI<Tusertrole> userroleDao) {
		this.userroleDao = userroleDao;
	}

	public BaseDaoI<Trole> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(BaseDaoI<Trole> roleDao) {
		this.roleDao = roleDao;
	}

	public BaseDaoI<Tauth> getAuthDao() {
		return authDao;
	}

	@Autowired
	public void setAuthDao(BaseDaoI<Tauth> authDao) {
		this.authDao = authDao;
	}

	public BaseDaoI<Tonline> getOnlineDao() {
		return onlineDao;
	}

	@Autowired
	public void setOnlineDao(BaseDaoI<Tonline> onlineDao) {
		this.onlineDao = onlineDao;
	}

	public BaseDaoI<Tmenu> getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(BaseDaoI<Tmenu> menuDao) {
		this.menuDao = menuDao;
	}

	public BaseDaoI<Tuser> getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(BaseDaoI<Tuser> userDao) {
		this.userDao = userDao;
	}
	
	public BaseDaoI<Tequip> getEquipDao() {
		return equipDao;
	}

	@Autowired
	public void setEquipDao(BaseDaoI<Tequip> equipDao) {
		this.equipDao = equipDao;
	}
	
	public BaseDaoI<Tdoc> getDocDao() {
		return docDao;
	}

	@Autowired
	public void setDocDao(BaseDaoI<Tdoc> docDao) {
		this.docDao = docDao;
	}
	
	public BaseDaoI<Tlog> getLogDao() {
		return logDao;
	}

	@Autowired
	public void setLogDao(BaseDaoI<Tlog> logDao) {
		this.logDao = logDao;
	}
	
	synchronized public void deleteAndRepair() {
		onlineDao.executeHql("delete Tonline");
		menuDao.executeHql("update Tmenu t set t.tmenu = null");
		menuDao.executeHql("delete Tmenu");
		roleauthDao.executeHql("delete Troletauth");
		userroleDao.executeHql("delete Tusertrole");
		authDao.executeHql("update Tauth t set t.tauth = null");
		authDao.executeHql("delete Tauth");
		roleDao.executeHql("delete Trole");
		userDao.executeHql("delete Tuser");
		repair();
	}
	
	synchronized public void repair() {
		repairMenu(); 
		repairAuth();  
		repairRole();  
		repairUser();
		repairRoleAuth(); 
		repairUserRole();
		repairEquipment();
		repairDocument();
		repairLog();
	}

	private void repairMenu() {
		menuDao.executeHql("update Tmenu m set m.tmenu = null");

		Tmenu root = new Tmenu();
		root.setCid("0");
		root.setCname("Home");
		root.setCseq(BigDecimal.valueOf(1));
		root.setCurl("");
		root.setTmenu(null);
		root.setCiconcls("icon-save");
		menuDao.saveOrUpdate(root);
		//
		Tmenu menu1 = new Tmenu();
		menu1.setCid("menu1");
		menu1.setCname("Management");
		menu1.setCseq(BigDecimal.valueOf(1));
		menu1.setCurl("");
		menu1.setTmenu(root);
		menu1.setCiconcls("icon-sum");
		menuDao.saveOrUpdate(menu1);

		Tmenu menu1_1 = new Tmenu();
		menu1_1.setCid("menu1_1");
		menu1_1.setCname("Equipment");
		menu1_1.setCseq(BigDecimal.valueOf(1));
		menu1_1.setCurl("/equipController/equip");
		menu1_1.setTmenu(menu1);
		menuDao.saveOrUpdate(menu1_1);
		
		Tmenu menu1_2 = new Tmenu();
		menu1_2.setCid("menu1_2");
		menu1_2.setCname("Document");
		menu1_2.setCseq(BigDecimal.valueOf(2));
		menu1_2.setCurl("/docController/doc");
		menu1_2.setTmenu(menu1);
		menuDao.saveOrUpdate(menu1_2);
		//
		Tmenu menu2 = new Tmenu();
		menu2.setCid("menu2");
		menu2.setCname("System");
		menu2.setCseq(BigDecimal.valueOf(2));
		menu2.setCurl("");
		menu2.setTmenu(root);
		menu2.setCiconcls("icon-sum");
		menuDao.saveOrUpdate(menu2);

		Tmenu menu2_1 = new Tmenu();
		menu2_1.setCid("menu2_1");
		menu2_1.setCname("User");
		menu2_1.setCseq(BigDecimal.valueOf(1));
		menu2_1.setCurl("/userController/user");
		menu2_1.setTmenu(menu2);
		menuDao.saveOrUpdate(menu2_1);

		Tmenu menu2_2 = new Tmenu();
		menu2_2.setCid("menu2_2");
		menu2_2.setCname("Role");
		menu2_2.setCseq(BigDecimal.valueOf(2));
		menu2_2.setCurl("/roleController/role");
		menu2_2.setTmenu(menu2);
		menuDao.saveOrUpdate(menu2_2);

		Tmenu menu2_3 = new Tmenu();
		menu2_3.setCid("menu2_3");
		menu2_3.setCname("Authority");
		menu2_3.setCseq(BigDecimal.valueOf(3));
		menu2_3.setCurl("/authController/auth");
		menu2_3.setTmenu(menu2);
		menuDao.saveOrUpdate(menu2_3);
		//
		Tmenu menu3 = new Tmenu();
		menu3.setCid("menu3");
		menu3.setCname("Other");
		menu3.setCseq(BigDecimal.valueOf(3));
		menu3.setCurl("");
		menu3.setTmenu(root);
		menu3.setCiconcls("icon-sum");
		menuDao.saveOrUpdate(menu3);

		Tmenu menu3_1 = new Tmenu();
		menu3_1.setCid("menu3_1");
		menu3_1.setCname("Userlog");
		menu3_1.setCseq(BigDecimal.valueOf(1));
		menu3_1.setCurl("/logController/log");
		menu3_1.setTmenu(menu3);
		menuDao.saveOrUpdate(menu3_1);

		Tmenu menu3_2 = new Tmenu();
		menu3_2.setCid("menu3_2");
		menu3_2.setCname("Chart");
		menu3_2.setCseq(BigDecimal.valueOf(2));
		menu3_2.setCurl("/userController/chart");
		menu3_2.setTmenu(menu3);
		menuDao.saveOrUpdate(menu3_2);

	}
	
	private void repairAuth() {
		authDao.executeHql("update Tauth a set a.tauth = null");

		Tauth auth0 = new Tauth();
		auth0.setCid("0");
		auth0.setTauth(null);
		auth0.setCname("Home");
		auth0.setCurl("");
		auth0.setCseq(BigDecimal.valueOf(1));
		auth0.setCdesc("");
		authDao.saveOrUpdate(auth0);
		//
		Tauth auth1 = new Tauth();
		auth1.setCid("auth1");
		auth1.setTauth(auth0);
		auth1.setCname("Management");
		auth1.setCurl("");
		auth1.setCseq(BigDecimal.valueOf(1));
		auth1.setCdesc("Equipment and document management");
		authDao.saveOrUpdate(auth1);

		Tauth auth1_1 = new Tauth();
		auth1_1.setCid("auth1_1");
		auth1_1.setTauth(auth1);
		auth1_1.setCname("Equipment");
		auth1_1.setCurl("/equipController/datagrid");
		auth1_1.setCseq(BigDecimal.valueOf(1));
		auth1_1.setCdesc("Equipment management");
		authDao.saveOrUpdate(auth1_1);
		
		Tauth auth1_2 = new Tauth();
		auth1_2.setCid("auth1_2");
		auth1_2.setTauth(auth1);
		auth1_2.setCname("Equipment");
		auth1_2.setCurl("/equipController/equip");
		auth1_2.setCseq(BigDecimal.valueOf(2));
		auth1_2.setCdesc("Equipment management");
		authDao.saveOrUpdate(auth1_2);
		
		Tauth auth1_3 = new Tauth();
		auth1_3.setCid("auth1_3");
		auth1_3.setTauth(auth1);
		auth1_3.setCname("Document");
		auth1_3.setCurl("/docController/datagrid");
		auth1_3.setCseq(BigDecimal.valueOf(3));
		auth1_3.setCdesc("Document management");
		authDao.saveOrUpdate(auth1_3);
		
		Tauth auth1_4 = new Tauth();
		auth1_4.setCid("auth1_4");
		auth1_4.setTauth(auth1);
		auth1_4.setCname("Document");
		auth1_4.setCurl("/docController/doc");
		auth1_4.setCseq(BigDecimal.valueOf(4));
		auth1_4.setCdesc("Document management");
		authDao.saveOrUpdate(auth1_4);
		//
		Tauth auth2 = new Tauth();
		auth2.setCid("auth2");
		auth2.setTauth(auth0);
		auth2.setCname("User");
		auth2.setCurl("");
		auth2.setCseq(BigDecimal.valueOf(2));
		auth2.setCdesc("User management");
		authDao.saveOrUpdate(auth2);

		Tauth auth2_1 = new Tauth();
		auth2_1.setCid("auth2_1");
		auth2_1.setTauth(auth2);
		auth2_1.setCname("Query user");
		auth2_1.setCurl("/userController/user");
		auth2_1.setCseq(BigDecimal.valueOf(1));
		auth2_1.setCdesc("");
		authDao.saveOrUpdate(auth2_1);

		Tauth auth2_2 = new Tauth();
		auth2_2.setCid("auth2_2");
		auth2_2.setTauth(auth2);
		auth2_2.setCname("Query user");
		auth2_2.setCurl("/userController/datagrid");
		auth2_2.setCseq(BigDecimal.valueOf(2));
		auth2_2.setCdesc("");
		authDao.saveOrUpdate(auth2_2);

		Tauth auth2_3 = new Tauth();
		auth2_3.setCid("auth2_3");
		auth2_3.setTauth(auth2);
		auth2_3.setCname("Add user");
		auth2_3.setCurl("/userController/userAdd");
		auth2_3.setCseq(BigDecimal.valueOf(3));
		auth2_3.setCdesc("");
		authDao.saveOrUpdate(auth2_3);

		Tauth auth2_4 = new Tauth();
		auth2_4.setCid("auth2_4");
		auth2_4.setTauth(auth2);
		auth2_4.setCname("Add user");
		auth2_4.setCurl("/userController/add");
		auth2_4.setCseq(BigDecimal.valueOf(4));
		auth2_4.setCdesc("");
		authDao.saveOrUpdate(auth2_4);

		Tauth auth2_5 = new Tauth();
		auth2_5.setCid("auth2_5");
		auth2_5.setTauth(auth2);
		auth2_5.setCname("Edit user");
		auth2_5.setCurl("/userController/userEdit");
		auth2_5.setCseq(BigDecimal.valueOf(5));
		auth2_5.setCdesc("");
		authDao.saveOrUpdate(auth2_5);

		Tauth auth2_6 = new Tauth();
		auth2_6.setCid("auth2_6");
		auth2_6.setTauth(auth2);
		auth2_6.setCname("Edit user");
		auth2_6.setCurl("/userController/edit");
		auth2_6.setCseq(BigDecimal.valueOf(6));
		auth2_6.setCdesc("");
		authDao.saveOrUpdate(auth2_6);

		Tauth auth2_7 = new Tauth();
		auth2_7.setCid("auth2_7");
		auth2_7.setTauth(auth2);
		auth2_7.setCname("Delete user");
		auth2_7.setCurl("/userController/delete");
		auth2_7.setCseq(BigDecimal.valueOf(7));
		auth2_7.setCdesc("");
		authDao.saveOrUpdate(auth2_7);

		Tauth auth2_8 = new Tauth();
		auth2_8.setCid("auth2_8");
		auth2_8.setTauth(auth2);
		auth2_8.setCname("Edit role");
		auth2_8.setCurl("/userController/userRoleEdit");
		auth2_8.setCseq(BigDecimal.valueOf(8));
		auth2_8.setCdesc("Batch edit role");
		authDao.saveOrUpdate(auth2_8);

		Tauth auth2_9 = new Tauth();
		auth2_9.setCid("auth2_9");
		auth2_9.setTauth(auth2);
		auth2_9.setCname("Edit role");
		auth2_9.setCurl("/userController/roleEdit");
		auth2_9.setCseq(BigDecimal.valueOf(9));
		auth2_9.setCdesc("Batch edit role");
		authDao.saveOrUpdate(auth2_9);
		//
		Tauth auth3 = new Tauth();
		auth3.setCid("auth3");
		auth3.setTauth(auth0);
		auth3.setCname("Role");
		auth3.setCurl("");
		auth3.setCseq(BigDecimal.valueOf(3));
		auth3.setCdesc("Role management");
		authDao.saveOrUpdate(auth3);

		Tauth auth3_1 = new Tauth();
		auth3_1.setCid("auth3_1");
		auth3_1.setTauth(auth3);
		auth3_1.setCname("Query role");
		auth3_1.setCurl("/roleController/role");
		auth3_1.setCseq(BigDecimal.valueOf(1));
		auth3_1.setCdesc("");
		authDao.saveOrUpdate(auth3_1);

		Tauth auth3_2 = new Tauth();
		auth3_2.setCid("auth3_2");
		auth3_2.setTauth(auth3);
		auth3_2.setCname("Query role");
		auth3_2.setCurl("/roleController/datagrid");
		auth3_2.setCseq(BigDecimal.valueOf(2));
		auth3_2.setCdesc("");
		authDao.saveOrUpdate(auth3_2);

		Tauth auth3_3 = new Tauth();
		auth3_3.setCid("auth3_3");
		auth3_3.setTauth(auth3);
		auth3_3.setCname("Add role");
		auth3_3.setCurl("/roleController/roleAdd");
		auth3_3.setCseq(BigDecimal.valueOf(3));
		auth3_3.setCdesc("");
		authDao.saveOrUpdate(auth3_3);

		Tauth auth3_4 = new Tauth();
		auth3_4.setCid("auth3_4");
		auth3_4.setTauth(auth3);
		auth3_4.setCname("Add role");
		auth3_4.setCurl("/roleController/add");
		auth3_4.setCseq(BigDecimal.valueOf(4));
		auth3_4.setCdesc("");
		authDao.saveOrUpdate(auth3_4);

		Tauth auth3_5 = new Tauth();
		auth3_5.setCid("auth3_5");
		auth3_5.setTauth(auth3);
		auth3_5.setCname("Edit role");
		auth3_5.setCurl("/roleController/roleEdit");
		auth3_5.setCseq(BigDecimal.valueOf(5));
		auth3_5.setCdesc("");
		authDao.saveOrUpdate(auth3_5);

		Tauth auth3_6 = new Tauth();
		auth3_6.setCid("auth3_6");
		auth3_6.setTauth(auth3);
		auth3_6.setCname("Edit role");
		auth3_6.setCurl("/roleController/edit");
		auth3_6.setCseq(BigDecimal.valueOf(6));
		auth3_6.setCdesc("");
		authDao.saveOrUpdate(auth3_6);

		Tauth auth3_7 = new Tauth();
		auth3_7.setCid("auth3_7");
		auth3_7.setTauth(auth3);
		auth3_7.setCname("Delete role");
		auth3_7.setCurl("/roleController/delete");
		auth3_7.setCseq(BigDecimal.valueOf(7));
		auth3_7.setCdesc("");
		authDao.saveOrUpdate(auth3_7);
		//
		Tauth auth4 = new Tauth();
		auth4.setCid("auth4");
		auth4.setTauth(auth0);
		auth4.setCname("Authority");
		auth4.setCurl("");
		auth4.setCseq(BigDecimal.valueOf(4));
		auth4.setCdesc("Authority management");
		authDao.saveOrUpdate(auth4);

		Tauth auth4_1 = new Tauth();
		auth4_1.setCid("auth4_1");
		auth4_1.setTauth(auth4);
		auth4_1.setCname("Query auth");
		auth4_1.setCurl("/authController/auth");
		auth4_1.setCseq(BigDecimal.valueOf(1));
		auth4_1.setCdesc("");
		authDao.saveOrUpdate(auth4_1);

		Tauth auth4_2 = new Tauth();
		auth4_2.setCid("auth4_2");
		auth4_2.setTauth(auth4);
		auth4_2.setCname("Query auth");
		auth4_2.setCurl("/authController/treegrid");
		auth4_2.setCseq(BigDecimal.valueOf(2));
		auth4_2.setCdesc("");
		authDao.saveOrUpdate(auth4_2);

		Tauth auth4_3 = new Tauth();
		auth4_3.setCid("auth4_3");
		auth4_3.setTauth(auth4);
		auth4_3.setCname("Add role");
		auth4_3.setCurl("/authController/authAdd");
		auth4_3.setCseq(BigDecimal.valueOf(3));
		auth4_3.setCdesc("");
		authDao.saveOrUpdate(auth4_3);

		Tauth auth4_4 = new Tauth();
		auth4_4.setCid("auth4_4");
		auth4_4.setTauth(auth4);
		auth4_4.setCname("Add auth");
		auth4_4.setCurl("/authController/add");
		auth4_4.setCseq(BigDecimal.valueOf(4));
		auth4_4.setCdesc("");
		authDao.saveOrUpdate(auth4_4);

		Tauth auth4_5 = new Tauth();
		auth4_5.setCid("auth4_5");
		auth4_5.setTauth(auth4);
		auth4_5.setCname("Edit auth");
		auth4_5.setCurl("/authController/authEdit");
		auth4_5.setCseq(BigDecimal.valueOf(5));
		auth4_5.setCdesc("");
		authDao.saveOrUpdate(auth4_5);

		Tauth auth4_6 = new Tauth();
		auth4_6.setCid("auth4_6");
		auth4_6.setTauth(auth4);
		auth4_6.setCname("Edit auth");
		auth4_6.setCurl("/authController/edit");
		auth4_6.setCseq(BigDecimal.valueOf(6));
		auth4_6.setCdesc("");
		authDao.saveOrUpdate(auth4_6);

		Tauth auth4_7 = new Tauth();
		auth4_7.setCid("auth4_7");
		auth4_7.setTauth(auth4);
		auth4_7.setCname("Delete auth");
		auth4_7.setCurl("/authController/delete");
		auth4_7.setCseq(BigDecimal.valueOf(7));
		auth4_7.setCdesc("");
		authDao.saveOrUpdate(auth4_7);

	}

	private void repairRole() {
		Trole admin = new Trole();
		admin.setCid("0");
		admin.setCname("Administrator");
		admin.setCdesc("Full authority");
		roleDao.saveOrUpdate(admin);

		Trole guest = new Trole();
		guest.setCid("1");
		guest.setCname("Guest");
		guest.setCdesc("Minimum authority");
		roleDao.saveOrUpdate(guest);
		
		Trole user = new Trole();
		user.setCid("2");
		user.setCname("User");
		user.setCdesc("Some authority");
		roleDao.saveOrUpdate(user);
	}

	private void repairUser() {
		// if cid!='0' and cname='admin', repair is needed
		List<Tuser> t = userDao.find("from Tuser t where t.cname = ? and t.cid != ?", new Object[] { "admin", "0" });
		if (t != null && t.size() > 0) {
			for (Tuser u : t) {
				u.setCname(u.getCname() + UUID.randomUUID().toString());
			}
		}

		Tuser admin = new Tuser();
		admin.setCid("0");
		admin.setCname("admin");
		admin.setCpwd(Encrypt.e("admin"));
		userDao.saveOrUpdate(admin);
	}

	private void repairRoleAuth() {
		roleauthDao.executeHql("delete Troletauth t where t.trole.cid = '0'");

		Trole role = roleDao.get(Trole.class, "0");

		List<Tauth> auths = authDao.find("from Tauth");
		if (auths != null && auths.size() > 0) {
			for (Tauth auth : auths) {
				Troletauth roleauth = new Troletauth();
				roleauth.setCid(UUID.randomUUID().toString());
				roleauth.setTrole(role);
				roleauth.setTauth(auth);
				roleauthDao.save(roleauth);
			}
		}
	}
	
	private void repairUserRole() {
		userroleDao.executeHql("delete Tusertrole t where t.tuser.cid in ( '0' )");

		Tusertrole userrole = new Tusertrole();
		userrole.setCid(UUID.randomUUID().toString());
		userrole.setTrole(roleDao.get(Trole.class, "0"));
		userrole.setTuser(userDao.get(Tuser.class, "0"));
		userroleDao.save(userrole);
	}
	
	
	private void repairEquipment() {
		
		equipDao.executeHql("delete Tequip");
		Tequip equip = new Tequip();
		
		equip.setCid(UUID.randomUUID().toString());
		equip.setCmodel("DPO70404C");
		equip.setCname("Oscilloscope");
		equip.setCproducer("Tektronix");
		equip.setCdesc("4 GHz Bandwidth, 25 GS/s Sample Rate, 4 Channels");
		equip.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip);
		
		Tequip equip1 = new Tequip();
		equip1.setCid(UUID.randomUUID().toString());
		equip1.setCmodel("DPO70604C");
		equip1.setCname("Oscilloscope");
		equip1.setCproducer("Tektronix");
		equip1.setCdesc("6 GHz Bandwidth, 25 GS/s Sample Rate, 4 Channels");
		equip1.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip1);
		
		Tequip equip2 = new Tequip();
		equip2.setCid(UUID.randomUUID().toString());
		equip2.setCmodel("DPO71254C");
		equip2.setCname("Oscilloscope");
		equip2.setCproducer("Tektronix");
		equip2.setCdesc("12.5 GHz Bandwidth, 50 - 100 GS/s Sample Rate, 4 Channels");
		equip2.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip2);
	
		Tequip equip3 = new Tequip();
		equip3.setCid(UUID.randomUUID().toString());
		equip3.setCmodel("MSO72004C");
		equip3.setCname("Oscilloscope");
		equip3.setCproducer("Tektronix");
		equip3.setCdesc("20 GHz Bandwidth, 50 - 100 GS/s Sample Rate, 4 Channels");
		equip3.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip3);
		
		Tequip equip4 = new Tequip();
		equip4.setCid(UUID.randomUUID().toString());
		equip4.setCmodel("DPO73304DX");
		equip4.setCname("Oscilloscope");
		equip4.setCproducer("Tektronix");
		equip4.setCdesc("33 GHz Bandwidth, 50 - 100 GS/s Sample Rate, 4 Channels");
		equip4.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip4);
		
		Tequip equip5 = new Tequip();
		equip5.setCid(UUID.randomUUID().toString());
		equip5.setCmodel("DSOX4022A");
		equip5.setCname("Oscilloscope");
		equip5.setCproducer("Agilent");
		equip5.setCdesc("200 MHz Bandwidth, 5 GS/s Sample Rate, 2 Channels");
		equip5.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip5);
		
		Tequip equip6 = new Tequip();
		equip6.setCid(UUID.randomUUID().toString());
		equip6.setCmodel("DSOX4052A");
		equip6.setCname("Oscilloscope");
		equip6.setCproducer("Agilent");
		equip6.setCdesc("500 MHz Bandwidth, 5 GS/s Sample Rate, 4 Channels");
		equip6.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip6);
		
		Tequip equip7 = new Tequip();
		equip7.setCid(UUID.randomUUID().toString());
		equip7.setCmodel("MSOX4154A");
		equip7.setCname("Oscilloscope");
		equip7.setCproducer("Agilent");
		equip7.setCdesc("1.5 GHz Bandwidth, 5 GS/s Sample Rate, 4 Channels");
		equip7.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip7);
		
		Tequip equip8 = new Tequip();
		equip8.setCid(UUID.randomUUID().toString());
		equip8.setCmodel("AFG2000");
		equip8.setCname("Function Generator");
		equip8.setCproducer("Tektronix");
		equip8.setCdesc("20 MHz Bandwidth, 250 MS/s Sample Rate, 14 bit Resolution");
		equip8.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip8);
		
		Tequip equip9 = new Tequip();
		equip9.setCid(UUID.randomUUID().toString());
		equip9.setCmodel("AFG3101C");
		equip9.setCname("Function Generator");
		equip9.setCproducer("Tektronix");
		equip9.setCdesc("100 MHz Bandwidth, 1 GS/s Sample Rate, 14 bit Resolution");
		equip9.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip9);
		
		Tequip equip10 = new Tequip();
		equip10.setCid(UUID.randomUUID().toString());
		equip10.setCmodel("AFG3252C");
		equip10.setCname("Function Generator");
		equip10.setCproducer("Tektronix");
		equip10.setCdesc("240 MHz Bandwidth, 1 GS/s Sample Rate, 14 bit Resolution");
		equip10.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip10);

		Tequip equip11 = new Tequip();
		equip11.setCid(UUID.randomUUID().toString());
		equip11.setCmodel("16801A");
		equip11.setCname("Logic Analyzer");
		equip11.setCproducer("Agilent");
		equip11.setCdesc("34 Channels, 1.0 GHz / 500 MHz Timing Speed");
		equip11.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip11);
		
		Tequip equip12 = new Tequip();
		equip12.setCid(UUID.randomUUID().toString());
		equip12.setCmodel("16802A");
		equip12.setCname("Logic Analyzer");
		equip12.setCproducer("Agilent");
		equip12.setCdesc("68 Channels, 1.0 GHz / 500 MHz Timing Speed");
		equip12.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip12);
		
		Tequip equip13 = new Tequip();
		equip13.setCid(UUID.randomUUID().toString());
		equip13.setCmodel("16803A");
		equip13.setCname("Logic Analyzer");
		equip13.setCproducer("Agilent");
		equip13.setCdesc("102 Channels, 1.0 GHz / 500 MHz Timing Speed");
		equip13.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip13);
		
		Tequip equip14 = new Tequip();
		equip14.setCid(UUID.randomUUID().toString());
		equip14.setCmodel("DMM4020");
		equip14.setCname("Digital Multimeter");
		equip14.setCproducer("Tektronix");
		equip14.setCdesc("5.5 Digit Resolution, ±0.015% Reading + 0.004% Range Accuracy");
		equip14.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip14);
		
		Tequip equip15 = new Tequip();
		equip15.setCid(UUID.randomUUID().toString());
		equip15.setCmodel("DMM4040");
		equip15.setCname("Digital Multimeter");
		equip15.setCproducer("Tektronix");
		equip15.setCdesc("6.5 Digit Resolution, 0.0035% Reading + 0.0005% Range Accuracy");
		equip15.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip15);
		
		Tequip equip16 = new Tequip();
		equip16.setCid(UUID.randomUUID().toString());
		equip16.setCmodel("DMM4050");
		equip16.setCname("Digital Multimeter");
		equip16.setCproducer("Tektronix");
		equip16.setCdesc("6.5 Digit Resolution, 0.0024% Reading + 0.0005% Range Accuracy");
		equip16.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip16);		
		
		Tequip equip17 = new Tequip();
		equip17.setCid(UUID.randomUUID().toString());
		equip17.setCmodel("N6763A");
		equip17.setCname("Precision DC Power Module");
		equip17.setCproducer("Agilent");
		equip17.setCdesc("20V, 50A, 300W");
		equip17.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip17);
		
		Tequip equip18 = new Tequip();
		equip18.setCid(UUID.randomUUID().toString());
		equip18.setCmodel("N6764A");
		equip18.setCname("Precision DC Power Module");
		equip18.setCproducer("Agilent");
		equip18.setCdesc("60V, 20A, 300W");
		equip18.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip18);
		
		Tequip equip19 = new Tequip();
		equip19.setCid(UUID.randomUUID().toString());
		equip19.setCmodel("N6766A");
		equip19.setCname("Precision DC Power Module");
		equip19.setCproducer("Agilent");
		equip19.setCdesc("60V, 17A, 500W");
		equip19.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip19);
		
		Tequip equip20 = new Tequip();
		equip20.setCid(UUID.randomUUID().toString());
		equip20.setCmodel("PWS2721");
		equip20.setCname("DC Power Supply");
		equip20.setCproducer("Tektronix ");
		equip20.setCdesc("1.5A, 72V");
		equip20.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip20);
		
		Tequip equip21 = new Tequip();
		equip21.setCid(UUID.randomUUID().toString());
		equip21.setCmodel("PWS2323");
		equip21.setCname("DC Power Supply");
		equip21.setCproducer("Tektronix ");
		equip21.setCdesc("3A, 32V");
		equip21.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip21);
		
		Tequip equip22 = new Tequip();
		equip22.setCid(UUID.randomUUID().toString());
		equip22.setCmodel("PWS2185");
		equip22.setCname("DC Power Supply");
		equip22.setCproducer("Tektronix ");
		equip22.setCdesc("5A, 18V");
		equip22.setCno(Integer.valueOf(10));
		equipDao.saveOrUpdate(equip22);
	}
	
	private void repairDocument() {
		
		docDao.executeHql("delete Tdoc");
	
		Tdoc doc = new Tdoc();
		doc.setCid(UUID.randomUUID().toString());
		doc.setCmodel("TMS320C6678");
		doc.setCname("Multicore Digital Signal Processor");
		doc.setCproducer("Texas Instruments");
		doc.setCno(Integer.valueOf(10));
		doc.setCmanual("<a href='../upload/tms320c6678.pdf'>tms320c6678.pdf</a>");
		docDao.saveOrUpdate(doc);
		
		Tdoc doc1 = new Tdoc();
		doc1.setCid(UUID.randomUUID().toString());
		doc1.setCmodel("TMS320C6474");
		doc1.setCname("Multicore Digital Signal Processor");
		doc1.setCproducer("Texas Instruments");
		doc1.setCno(Integer.valueOf(10));
		doc1.setCmanual("<a href='../upload/tms320c6474.pdf'>tms320c6474.pdf</a>");
		docDao.saveOrUpdate(doc1);
	
		Tdoc doc2 = new Tdoc();
		doc2.setCid(UUID.randomUUID().toString());
		doc2.setCmodel("TMS320VC5510A");
		doc2.setCname("Fixed-Point Digital Signal Processors");
		doc2.setCproducer("Texas Instruments");
		doc2.setCno(Integer.valueOf(10));
		doc2.setCmanual("<a href='../upload/tms320vc5510a.pdf'>tms320vc5510a.pdf</a>");
		docDao.saveOrUpdate(doc2);
		
		Tdoc doc3 = new Tdoc();
		doc3.setCid(UUID.randomUUID().toString());
		doc3.setCmodel("TMS320DM648");
		doc3.setCname("Digital Media Processor");
		doc3.setCproducer("Texas Instruments");
		doc3.setCno(Integer.valueOf(10));
		doc3.setCmanual("<a href='../upload/tms320dm648.pdf'>tms320dm648.pdf</a>");
		docDao.saveOrUpdate(doc3);
		
		Tdoc doc4 = new Tdoc();
		doc4.setCid(UUID.randomUUID().toString());
		doc4.setCmodel("LM7372");
		doc4.setCname("High Speed, Dual Operational Amplifier");
		doc4.setCproducer("Texas Instruments");
		doc4.setCno(Integer.valueOf(10));
		doc4.setCmanual("<a href='../upload/lm7372.pdf'>lm7372.pdf</a>");
		docDao.saveOrUpdate(doc4);
		
		Tdoc doc5 = new Tdoc();
		doc5.setCid(UUID.randomUUID().toString());
		doc5.setCmodel("TLC1079");
		doc5.setCname("Low-Voltage Operational Amplifier");
		doc5.setCproducer("Texas Instruments");
		doc5.setCno(Integer.valueOf(10));
		doc5.setCmanual("<a href='../upload/tlc1079.pdf'>tlc1079.pdf</a>");
		docDao.saveOrUpdate(doc5);
		
		Tdoc doc6 = new Tdoc();
		doc6.setCid(UUID.randomUUID().toString());
		doc6.setCmodel("STA540");
		doc6.setCname("Dual/quad power amplifier");
		doc6.setCproducer("STMIcroelectronics");
		doc6.setCno(Integer.valueOf(10));
		doc6.setCmanual("<a href='../upload/STA540.pdf'>STA540.pdf</a>");
		docDao.saveOrUpdate(doc6);
		
		Tdoc doc7 = new Tdoc();
		doc7.setCid(UUID.randomUUID().toString());
		doc7.setCmodel("TDA7491P");
		doc7.setCname("Dual BTL class-D audio amplifier");
		doc7.setCproducer("STMIcroelectronics");
		doc7.setCno(Integer.valueOf(10));
		doc7.setCmanual("<a href='../upload/TDA7491P.pdf'>TDA7491P.pdf</a>");
		docDao.saveOrUpdate(doc7);
		
		Tdoc doc8 = new Tdoc();
		doc8.setCid(UUID.randomUUID().toString());
		doc8.setCmodel("SPC560B40L332");
		doc8.setCname("32-bit Power Architecture MCU");
		doc8.setCproducer("STMIcroelectronics");
		doc8.setCno(Integer.valueOf(10));
		doc8.setCmanual("<a href='../upload/SPC560B40L3.pdf'>SPC560B40L3.pdf</a>");
		docDao.saveOrUpdate(doc8);
		
		Tdoc doc9 = new Tdoc();
		doc9.setCid(UUID.randomUUID().toString());
		doc9.setCmodel("SPC564B74L8");
		doc9.setCname("32-bit Power Architecture MCU");
		doc9.setCproducer("STMIcroelectronics");
		doc9.setCno(Integer.valueOf(10));
		doc9.setCmanual("<a href='../upload/SPC564B74L8.pdf'>SPC564B74L8.pdf</a>");
		docDao.saveOrUpdate(doc9);
		
		Tdoc doc10 = new Tdoc();
		doc10.setCid(UUID.randomUUID().toString());
		doc10.setCmodel("ST10F269");
		doc10.setCname("16Bit MCU");
		doc10.setCproducer("STMIcroelectronics");
		doc10.setCno(Integer.valueOf(10));
		doc10.setCmanual("<a href='../upload/ST10F269.pdf'>ST10F269.pdf</a>");
		docDao.saveOrUpdate(doc10);
		
		Tdoc doc11 = new Tdoc();
		doc11.setCid(UUID.randomUUID().toString());
		doc11.setCmodel("PIC10F200");
		doc11.setCname("8-Bit Flash Microcontroller");
		doc11.setCproducer("Microchip");
		doc11.setCno(Integer.valueOf(10));
		doc11.setCmanual("<a href='../upload/PIC10F200.pdf'>PIC10F200.pdf</a>");
		docDao.saveOrUpdate(doc11);
		
		Tdoc doc12 = new Tdoc();
		doc12.setCid(UUID.randomUUID().toString());
		doc12.setCmodel("MCP3901");
		doc12.setCname("Two Channel Analog Front End");
		doc12.setCproducer("Microchip");
		doc12.setCno(Integer.valueOf(10));
		doc12.setCmanual("<a href='../upload/MCP3901.pdf'>MCP3901.pdf</a>");
		docDao.saveOrUpdate(doc12);
		
		Tdoc doc13 = new Tdoc();
		doc13.setCid(UUID.randomUUID().toString());
		doc13.setCmodel("MCP3423");
		doc13.setCname("Two channel A/D converter");
		doc13.setCproducer("Microchip");
		doc13.setCno(Integer.valueOf(10));
		doc13.setCmanual("<a href='../upload/MCP3423.pdf'>MCP3423.pdf</a>");
		docDao.saveOrUpdate(doc13);
		
		Tdoc doc14 = new Tdoc();
		doc14.setCid(UUID.randomUUID().toString());
		doc14.setCmodel("SST39VF401C");
		doc14.setCname("6 CMOS Multi-Purpose Flash");
		doc14.setCproducer("Microchip");
		doc14.setCno(Integer.valueOf(10));
		doc14.setCmanual("<a href='../upload/SST39VF401C.pdf'>SST39VF401C.pdf</a>");
		docDao.saveOrUpdate(doc14);	
		
		Tdoc doc15 = new Tdoc();
		doc15.setCid(UUID.randomUUID().toString());
		doc15.setCmodel("MCP6N11");
		doc15.setCname("Instrumentation Amplifier");
		doc15.setCproducer("Microchip");
		doc15.setCno(Integer.valueOf(10));
		doc15.setCmanual("<a href='../upload/MCP6N11.pdf'>MCP6N11.pdf</a>");
		docDao.saveOrUpdate(doc15);
	}
	
	private void repairLog() {
		logDao.executeHql("delete Tlog");
		
		Tlog log = new Tlog();
		log.setCid(UUID.randomUUID().toString());
		log.setCname("admin");
		log.setCip("127.0.0.1");
		log.setCdatetime(new Date());
		log.setCmsg("create");
		logDao.saveOrUpdate(log);
	
	}
}
