/**
 * 
 */
package mgwt.faceme.client.model.database;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import mgwt.faceme.client.model.entities.User;

import com.googlecode.objectify.Key;
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
			System.out.println("Insert user: " + user.getFullName());
			ofy().save().entity(user).now();
		} else {
			throw new NullPointerException();
		}

	}

	public void deleteUser(User user) {
		ObjectifyService.register(User.class);
		if (user != null) {
			ofy().delete().entity(user).now();
		}
	}

	public User getUserById(Long userId) {
		ObjectifyService.register(User.class);
		// ofy().clear();
		// User user = ofy().load().type(User.class).id(userId).get();
		User user = ofy().load().key(Key.create(User.class, userId)).get();
		if (user == null) {
			System.out.println("User from db is null!!!");
		}
		return user;
	}

	public User getUserByEmail(String email) {
		ObjectifyService.register(User.class);
		ofy().clear();
		return ofy().load().type(User.class).filter("email", email).first()
				.get();
	}

	public List<User> getAllUsers() {
		ObjectifyService.register(User.class);
		ofy().clear();
		List<User> users = new ArrayList<User>();
		List<User> q = ofy().load().type(User.class).list();
		if (q != null) {
			for (User user : q) {
				users.add(user);
			}
		}

		return users;
	}

	public List<User> getAllUsers(boolean online) {
		ObjectifyService.register(User.class);
		ofy().clear();
		List<User> users = new ArrayList<User>();
		List<User> q = ofy().load().type(User.class).filter("online", online)
				.list();
		if (q != null) {
			for (User user : q) {
				users.add(user);
			}
		}
		return users;
	}

}
