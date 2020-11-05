package server.telran.net.server;

import common.telran.net.*;

public interface Protocol {
	Response getResponse(Request request);
}
