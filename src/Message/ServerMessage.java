package Message;

import Client_Server.Objects;
import Game.HunterMen;

public class ServerMessage {
	Objects objectType;
	HunterMen location;
	
	public ServerMessage(Objects objectType, HunterMen location) {
		this.objectType = objectType;
		this.location = location;
	}

}
