/**
 * 
 */
package mgwt.faceme.shared;

import java.util.List;

import mgwt.faceme.client.model.entities.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author heroandtn3
 * 
 */
public interface DatabaseServiceAsync {

	void deleteUser(User user, AsyncCallback<Void> callback);

	void getAllUsers(AsyncCallback<List<User>> callback);

	void getOnlineUsers(AsyncCallback<List<User>> callback);

	void getUserByEmail(String email, AsyncCallback<User> callback);

	void insertUser(User user, AsyncCallback<Void> callback);

	void updateUserEmail(Long userId, String email, AsyncCallback<Void> callback);

	void updateUserOnlineStatus(String email, boolean online,
			AsyncCallback<Void> callback);

	void updateUserPassword(Long userId, String password,
			AsyncCallback<Void> callback);

	void updateUserPlayStatus(Long userId, boolean playing,
			AsyncCallback<Void> callback);

}
