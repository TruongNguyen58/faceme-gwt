/**
 * 
 */
package mgwt.faceme.server;

import java.util.List;

import mgwt.faceme.client.DatabaseService;
import mgwt.faceme.client.model.database.UserDB;
import mgwt.faceme.client.model.entities.User;

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
		return userDb.getOnlineUsers();
	}

	@Override
	public User getUserByEmail(String email) {
		return userDb.getUserByEmail(email);
	}

}
