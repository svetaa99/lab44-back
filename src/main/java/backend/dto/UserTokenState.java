package backend.dto;

import java.util.List;

import backend.models.Role;

public class UserTokenState {
	
    private String accessToken;
    private Long expiresIn;
    private List<Role> roles;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
        this.roles = null;
    }

    public UserTokenState(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
    
    public UserTokenState(String accessToken, Long expiresIn, List<Role> role) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.roles = role;
	}

	public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
