package com.sanbang.bean;

import java.io.Serializable;

public class ezs_user_role  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6549187649134542792L;

	
	private Long user_id;

    private Long role_id;
    
    private ezs_role role;
    
    

    public ezs_role getRole() {
		return role;
	}
	public void setRole(ezs_role role) {
		this.role = role;
	}

	public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }
}