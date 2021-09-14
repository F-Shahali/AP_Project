package Message;

public class ClientMessage{
	ClientMessageType messageType;
	Object info;
	
	ClientMessage(ClientMessageType messageType, Object info){
		this.messageType = messageType;
		this.info = info;
	}
}
