/**
 * 
 */
package mgwt.faceme.shared;

import java.util.List;

import mgwt.faceme.client.model.entities.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author heroandtn3
 * 
 */
@RemoteServiceRelativePath("DatabaseService")
public interface DatabaseService extends RemoteService {
	public void insertUser(User user);

	public void deleteUser(User user);

	public void updateUserEmail(Long userId, String email);

	public void updateUserPassword(Long userId, String password);

	public void updateUserPlayStatus(Long userId, boolean playing);

	public void updateUserOnlineStatus(String email, boolean online);

	public User getUserByEmail(String email);

	public List<User> getAllUsers();

	public List<User> getOnlineUsers();

}
