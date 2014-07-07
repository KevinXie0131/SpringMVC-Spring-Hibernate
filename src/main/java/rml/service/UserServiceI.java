package rml.service;

import java.util.List;

import rml.model.vo.DataGrid;
import rml.model.vo.RoleChart;
import rml.model.vo.User;

public interface UserServiceI {

	public User login(User user);

	public void save(User user);

	public void update(User user);

	public void delete(String ids);

	public void roleEdit(User user);

	public void editUserInfo(User user);
	
	public DataGrid datagrid(User user);

	public List<User> combobox(User user);
	
	public List<RoleChart> countRole();

}
