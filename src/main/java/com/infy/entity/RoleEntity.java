package com.infy.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infy.model.Role;
import com.infy.model.RoleMapping;

@Entity
@Table(name="role")
public class RoleEntity {
	@Id
	private int rId;
	
	@Enumerated(EnumType.STRING)
	private RoleMapping rName;

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public RoleMapping getrName() {
		return rName;
	}

	public void setrName(RoleMapping rName) {
		this.rName = rName;		
	}
	public Role roleEntityToModel()
	{
		Role r = new Role();
		r.setrId(this.rId);
		r.setrName(this.rName);	
		return r;
	}

}
