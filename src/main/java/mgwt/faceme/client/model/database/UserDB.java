/**
 * 
 */
package mgwt.faceme.client.model.database;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import mgwt.faceme.client.model.entities.User;

import com.googlecode.objectify.ObjectifyService;

/**
 * @author heroandtn3
 * 
 */
public class UserDB {

	/**
	 * 
	 */
	public UserDB() {

	}

	public void insertUser(User user) {
		ObjectifyService.register(User.class);
		if (user != null) {
			ofy().save().entity(user);
		}
	}

	public void deleteUser(User user) {
		ObjectifyService.register(User.class);
		if (user != null) {
			ofy().delete().entity(user);
		}
	}
	
	public User getUserByEmail(String email) {
		ObjectifyService.register(User.class);
		return ofy().load().type(User.class).filter("email", email).first().get();
	}

	public List<User> getAllUsers() {
		ObjectifyService.register(User.class);
		List<User> users = new ArrayList<User>();
		List<User> q = ofy().load().type(User.class).list();
		if (q != null) {
			for (User user : q) {
				users.add(user);
			}
		}

		return users;
	}

	public List<User> getOnlineUsers() {
		ObjectifyService.register(User.class);
		List<User> users = new ArrayList<User>();
		List<User> q = ofy().load().type(User.class).filter("online", true)
				.list();
		if (q != null) {
			for (User user : q) {
				users.add(user);
			}
		}
		return users;
	}

}
