/**
 * 
 */
package mgwt.faceme.client;

import java.util.List;

import mgwt.faceme.client.model.entities.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author heroandtn3
 *
 */
public interface DatabaseServiceAsync {

	void insertUser(User user, AsyncCallback<Void> callback);

	void deleteUser(User user, AsyncCallback<Void> callback);

	void getAllUsers(AsyncCallback<List<User>> callback);

	void getOnlineUsers(AsyncCallback<List<User>> callback);

	void getUserByEmail(String email, AsyncCallback<User> callback);

}
