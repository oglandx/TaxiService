package main.restapi;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * Created by oglandx on 6/13/16.
 */
public class Server {
    public Server(int port) throws IOException {
        InetSocketAddress address = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(address, port);
        server.createContext("/taxi/order", new GetOrderHandler());
        server.setExecutor(null);
        server.start();
    }
}
