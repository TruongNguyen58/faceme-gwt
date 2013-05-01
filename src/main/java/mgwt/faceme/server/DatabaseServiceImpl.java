/**
 * 
 */
package mgwt.faceme.server;

import java.util.List;

import mgwt.faceme.client.model.database.UserDB;
import mgwt.faceme.client.model.entities.User;
import mgwt.faceme.shared.DatabaseService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author heroandtn3
 * 
 */
public class DatabaseServiceImpl extends RemoteServiceServlet implements
		DatabaseService {

	private UserDB userDb;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseServiceImpl() {
		userDb = new UserDB();
	}

	@Override
	public void insertUser(User user) {
		userDb.insertUser(user);
	}

	@Override
	public void deleteUser(User user) {
		userDb.deleteUser(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userDb.getAllUsers();
	}

	@Override
	public List<User> getOnlineUsers() {
		return userDb.getAllUsers(true);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDb.getUserByEmail(email);
	}

	@Override
	public void updateUserEmail(Long userId, String email) {
		User user = userDb.getUserById(userId);
		user.setEmail(email);
		userDb.insertUser(user);
	}

	@Override
	public void updateUserPassword(Long userId, String password) {
		User user = userDb.getUserById(userId);
		user.setPassword(password);
		userDb.insertUser(user);

	}

	@Override
	public void updateUserOnlineStatus(String email, boolean online) {
		User user = userDb.getUserByEmail(email);
		if (user != null) {
			user.setOnline(online);
			userDb.insertUser(user);
		} else {
			System.out.println("User is null");
		}

	}

	@Override
	public void updateUserPlayStatus(Long userId, boolean playing) {
		User user = userDb.getUserById(userId);
		user.setPlaying(playing);
		userDb.insertUser(user);

	}

}
