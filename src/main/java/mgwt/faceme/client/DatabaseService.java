/**
 * 
 */
package mgwt.faceme.client;

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
	
	public User getUserByEmail(String email);

	public List<User> getAllUsers();

	public List<User> getOnlineUsers();

}
