package main.common;

import main.logic.Entity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by oglandx on 6/5/16.
 */
public class Query {
    private final JSONObject query;

    /**
        Example:
        {
            "x": 1,
            "y__gte": 2,
            "OR": {
                "z__exact": 3,
                "y": 0
            }
        }
        means: x == 1 && y >= 2 && (z == 3 || y == 0)
    **/
    public Query(JSONObject query){
        this.query = query;
    }

    public Query(String query) {
        JSONObject _query;
        try {
            _query = new JSONObject(query);
        }
        catch(JSONException e){
            _query = null;
        }
        this.query = _query;
    }

    public boolean is_invalid(){
        return query == null;
    }

    public boolean check(Entity entry){
        return checkEntry(entry, query, "AND");
    }

    private boolean checkEntry(Entity entry, JSONObject query, String operation) {
        if(query == null || operation == null){
            return false;
        }

        String subOperation;
        JSONObject sub;
        try {
            subOperation = "AND";
            sub = query.getJSONObject("AND");
        }
        catch(JSONException e){
            try {
                subOperation = "OR";
                sub = query.getJSONObject("OR");
            }
            catch(JSONException ex){
                sub = null;
                subOperation = null;
            }
        }

        JSONArray names = query.names();

        boolean result = operation.equals("AND");
        try {
            check:
            for (int i = 0; i < names.length(); i++) {

                final String[] parsedQuery = names.getString(i).split("__");
                final String name = parsedQuery[0];

                if(name.equals("AND") || name.equals("OR")){
                    continue;
                }

                String func = "";
                if(parsedQuery.length > 1){
                    func = parsedQuery[1];
                }

                final String methodName = "get" +
                        name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

                Method method = entry
                        .getClass()
                        .getMethod(methodName);

                final String value = String.valueOf(method.invoke(entry));
                final String qValue = query.getString(names.getString(i));

                switch (func){
                    case "":
                    case "exact":
                        if(operation.equals("OR") && value.equals(qValue) ||
                                operation.equals("AND") && !value.equals(qValue)){
                            result = !result;
                            break check;
                        }
                        break;
                    case "gte":
                        boolean gte = Double.valueOf(value) >= Double.valueOf(qValue);
                        if(operation.equals("OR") && gte || operation.equals("AND") && !gte){
                            result = !result;
                            break check;
                        }
                        break;
                    case "gt":
                        boolean gt = Double.valueOf(value) > Double.valueOf(qValue);
                        if(operation.equals("OR") && gt || operation.equals("AND") && !gt){
                            result = !result;
                            break check;
                        }
                        break;
                    case "lte":
                        boolean lte = Double.valueOf(value) <= Double.valueOf(qValue);
                        if(operation.equals("OR") && lte || operation.equals("AND") && !lte){
                            result = !result;
                            break check;
                        }
                        break;
                    case "lt":
                        boolean lt = Double.valueOf(value) <= Double.valueOf(qValue);
                        if(operation.equals("OR") && lt || operation.equals("AND") && !lt){
                            result = !result;
                            break check;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        catch (JSONException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
            return false;
        }

        return sub != null && (
                    operation.equals("AND") && result && this.checkEntry(entry, sub, subOperation) ||
                    operation.equals("OR") && (result || this.checkEntry(entry, sub, subOperation))) ||
                sub == null && result;
    }

    public String sql(){
        return makeSql(query, "AND", null, null);
    }

    public String sql(Map<String, String> dispatcher, String table){
        // dispatcher: field -> table
        return makeSql(query, "AND", dispatcher, table);
    }

    private String makeSql(JSONObject query, String operation, Map<String, String> dispatcher, String table){
        if(query == null || operation == null){
            return "FALSE";
        }

        String subOperation;
        JSONObject sub;
        try {
            sub = query.getJSONObject("AND");
            subOperation = "AND";
        }
        catch(JSONException e){
            try {
                sub = query.getJSONObject("OR");
                subOperation = "OR";
            }
            catch(JSONException ex){
                subOperation = null;
                sub = null;
            }
        }

        JSONArray names = query.names();
        ArrayList<String> sqlQueries = new ArrayList<>();

        try {
            for (int i = 0; i < names.length(); i++) {

                final String[] parsedQuery = names.getString(i).split("__");
                final String name = parsedQuery[0];

                if(name.equals("AND") || name.equals("OR")){
                    continue;
                }

                if(dispatcher != null && (
                        table == null && dispatcher.get(name) != null ||
                        table != null && (
                                dispatcher.get(name) == null ||
                                !dispatcher.get(name).equals(table)))) {
                    continue;
                }

                String func = "";
                if(parsedQuery.length > 1){
                    func = parsedQuery[1];
                }

                String qValue = "\'" + query.getString(names.getString(i)) + "\'";

                switch (func){
                    case "":
                    case "exact":
                        func = " = ";
                        break;
                    case "gte":
                        func = " >= ";
                        break;
                    case "gt":
                        func = " > ";
                        break;
                    case "lte":
                        func = " <= ";
                        break;
                    case "lt":
                        func = " < ";
                        break;
                    case "in":
                        func = " IN ";
                        CharSequence[] vals = qValue.split(",");
                        qValue = "(" + String.join("\',\'", vals) + ")";
                    default:
                        break;
                }

                final String q = name + func + qValue;
                sqlQueries.add(q);
            }
        }
        catch (JSONException e){
            return "FALSE";
        }

        if(sub != null) {
            sqlQueries.add(makeSql(sub, subOperation, dispatcher, table));
        }
        String statement = sqlQueries.size() == 0 ? "TRUE" : String.join(" " + operation + " ", sqlQueries);
        return "(" + statement + ")";
    }
}
