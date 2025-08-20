package com.nicholas.app; 
import java.util.function.Consumer;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.nicholas.app.frontEnd.ErrorHolder;
import com.nicholas.app.frontEnd.NotesListPanel;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader; 
import com.google.gson.Gson;
public class HttpRequestUtility{
    private static Gson gson = new Gson();
    private static final Type errorType = new TypeToken<Map<String,String>>(){}.getType();

    public static <T> void HttpPutRequest(String stringUrl,String token,T requestBody){
        try{
            URL url = new URL(stringUrl); 
            var conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type","application/json; utf-8");
            conn.setDoOutput(true);           
            String json = gson.toJson(requestBody);
            try(OutputStream os = conn.getOutputStream()){
                os.write(json.getBytes());
            }
            int responseCode = conn.getResponseCode();
            String responseBody = readResponse(conn,responseCode);
            if (responseCode == 200){
                System.out.println(responseBody);
            } else {
                System.out.println(parseError(responseBody));
            }
            conn.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void HttpDeleteRequest(String StringUrl,String token){
        try{
            URL url = new URL(StringUrl);
            var conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);
            if (responseCode >= 399){
                InputStream is = conn.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String response = reader.lines().collect(Collectors.joining("\n"));
                reader.close();
                Type type = new TypeToken<Map<String,String>>(){}.getType();
                Map<String,String> errorMap = gson.fromJson(response,type);
                System.out.println(errorMap.get("error"));
            } else {
                System.out.println("Note Deleted");
            }
            conn.disconnect();
                

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static <T> Optional<T> httpGetRequest(String StringUrl, Type responseType, String token){
        try{
            URL url = new URL(StringUrl);
            var conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer " + token);
            int responseCode = conn.getResponseCode();
            String response = readResponse(conn,responseCode);
            return Optional.of(gson.fromJson(response,responseType));
            
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }

    }

    
    public static <T,R extends ErrorHolder> Optional<R> httpPostRequest(String StringUrl,T requestBody,Class<R> responseType,Optional<String> optToken){
        try{
            URL url = new URL(StringUrl);
            var conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            if (!optToken.isEmpty()){
                String token = optToken.get();
                conn.setRequestProperty("Authorization","Bearer " + token);
            }
            conn.setRequestProperty("Content-Type","application/json; utf-8");
            conn.setDoOutput(true);
            String json = gson.toJson(requestBody);

            try(OutputStream os = conn.getOutputStream()){
                os.write(json.getBytes());
            }
            int responseCode = conn.getResponseCode();
            String response = readResponse(conn,responseCode);
            conn.disconnect();

            if(responseCode == 200){
                return Optional.of(gson.fromJson(response,responseType));
            } else {
                R errorMessage = responseType.getDeclaredConstructor().newInstance();
                errorMessage.setErrorMessage(parseError(response));
                return Optional.of(errorMessage);
            }
            
        }catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static String parseError(String requestResponse){
        Map<String,String> errorMap = gson.fromJson(requestResponse,errorType);
        return errorMap.get("error");
    }
    
    private static String readResponse(HttpURLConnection conn, int responseCode){
        try{
            InputStream is = responseCode == 200 ? conn.getInputStream():conn.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            if (is == null) return "No content in response";
            String response = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
            return response;
        } catch(Exception e){
            e.printStackTrace();
            return "Unkown error occured";
        }
    }
}