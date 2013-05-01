/**
 * 
 */
package mgwt.faceme.client.model.entities;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

/**
 * @author heroandtn3
 * 
 */
@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	@Ignore
	private static final long serialVersionUID = 1L;
	@Id
	private Long Id;
	@Index
	private String fullName;
	@Index
	private String email;
	private String password;
	@Index
	private boolean online;
	private boolean playing;

	/**
	 * 
	 */
	public User() {

	}

	public User(String fullName, String email, String password) {
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.online = true;
		this.playing = false;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
