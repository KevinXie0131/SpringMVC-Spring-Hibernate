package rml.service;

import java.util.List;

import rml.model.vo.DataGrid;
import rml.model.vo.Role;

public interface RoleServiceI {

	public void add(Role role);

	public void edit(Role role);

	public void delete(String ids);
	
	public DataGrid datagrid(Role role);
	
	public List<Role> combobox();

}
