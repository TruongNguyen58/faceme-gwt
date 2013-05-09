/**
 * 
 */
package mgwt.faceme.client.model.entities;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author heroandtn3
 * 
 */
public class Message implements Serializable, IsSerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String fromClientId;
	private String content;

	public Message() {
	}

	public Message(String type, String fromClientId, String content) {
		this.type = type;
		this.fromClientId = fromClientId;
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFromClientId() {
		return fromClientId;
	}

	public void setFromClientId(String fromClientId) {
		this.fromClientId = fromClientId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
