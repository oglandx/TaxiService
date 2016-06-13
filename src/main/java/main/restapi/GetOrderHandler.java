package main.restapi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.facade.ApplicationError;
import main.facade.PassengerFacade;
import main.logic.AuthData;
import main.logic.Order;
import main.logic.OrderStatus;
import main.logic.Passenger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oglandx on 6/13/16.
 */
public class GetOrderHandler implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            Map<String, String> queries = splitQuery(httpExchange.getRequestURI());
            String role = queries.get("role");
            String response;
            if (role != null && role.equals("passenger")) {
                response = getPassengerOrders(queries);
            } else {
                response = "Unknown role";
            }
            httpExchange.getResponseHeaders().set("Content-Type", "text/html;charset=cp1251");
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            final byte[] responseBytes = response.getBytes("cp1251");
            os.write(responseBytes);
            os.close();
        }
        finally {
            httpExchange.close();
        }
    }

    private Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {
        Map<String, String> result = new HashMap<>();
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int ix = pair.indexOf("=");
            if (ix != -1) {
                result.put(URLDecoder.decode(pair.substring(0, ix), "UTF-8"),
                        URLDecoder.decode(pair.substring(ix + 1), "UTF-8"));
            }
            else {
                result.put(URLDecoder.decode(pair, "UTF-8"), null);
            }
        }
        return result;
    }

    private String getPassengerOrders(Map<String, String> q) {
        String result;
        try {
            PassengerFacade passengerFacade = PassengerFacade.getInstance();
            Passenger passenger = passengerFacade.auth(new AuthData(q.get("email"), q.get("password")));
            if (passenger == null) {
                result = "Wrong auth data";
            }
            else {
                OrderStatus status = q.get("order_status") == null ? null : OrderStatus.valueOf(q.get("order_status"));
                List<Order> passengerList = passengerFacade.getOrderList(passenger, status);
                try {
                    result = getOrdersJson(passengerList);
                }
                catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                    result = "Suddenly, bad encoding. So sorry.";
                }
            }
            passengerFacade.closeConnection();
        }
        catch (ApplicationError e) {
            result = "An error occurred";
        }
        return result;
    }

    private String getOrdersJson(List<Order> orders) throws UnsupportedEncodingException {
        JSONArray array = new JSONArray();
        for (Order order: orders) {
            array.put(new JSONObject(order));
        }
        return array.toString();
    }
}
