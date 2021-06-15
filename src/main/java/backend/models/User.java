package backend.models;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "users")
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1145944638748587516L;
	@Id
	protected Long id;

	@Column(name="name", unique=false, nullable=false)
	protected String name;
	
	@Column(name="surname", unique=false, nullable=false)
	protected String surname;
	
	@Column(name="email", unique=false, nullable=false)
	protected String email;
	
	@Column(name="password", unique=false, nullable=false)
	protected String password;
	
	@Column(name="address", unique=false, nullable=true)
	protected Long addressId;
	
	@Column(name="phone_num", unique=false, nullable=true)
	protected String phoneNum;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    protected List<Role> roles;
	
	public User() {
		
	}
	
	public User(String name, String surname, String email, String password, Long addressId, String phoneNum) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.addressId = addressId;
		this.phoneNum = phoneNum;
	}

	public User(Long id, String name, String surname, String email, String password, Long addressId, String phoneNum) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.addressId = addressId;
		this.phoneNum = phoneNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getAddress() {
		return addressId;
	}
	public void setAddress(Long addressId) {
		this.addressId = addressId;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", email=" + email + ", password=" + password
				+ ", addressId=" + addressId + ", phoneNum=" + phoneNum + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
