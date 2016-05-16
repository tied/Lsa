package com.itzabota.jira.plugins.utils.rest;

//import org.apache.http.auth.AuthenticationException;
//import org.apache.wink.client.RestClient;

//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientHandlerException;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;

public class RestClientUtils {
//    /**
//     * REST-запрос Get: запрос - ответ 
//     * @param строка ауторизации
//     * @param строка сетевого адреса
//     * @return строка-сетевой адрес подключения
//     * @throws AuthenticationException 
//     * @throws ClientHandlerException 
//     */  
//	public static String invokeGetMethod(String auth, String url) throws AuthenticationException, ClientHandlerException {
//        Client client = Client.create();
//        WebResource webResource = client.resource(url);
//        ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
//                .accept("application/json").get(ClientResponse.class);
//        int statusCode = response.getStatus();
//        if (statusCode == 401) {
//            throw new AuthenticationException("Имя пользователя или пароль ошибочны");
//        }
//        if (statusCode == 403) {
//            throw new AuthenticationException("Недостаточно прав для выполнения операции");
//        }    
//        if (statusCode == 404) {
//            throw new AuthenticationException("Данный ресурс не существует");
//        }             
//        return response.getEntity(String.class);
//    } 
//	
//	public static String invokeGetMethodNoAuthorize(String url) throws AuthenticationException, ClientHandlerException {
//        Client client = Client.create();
//        WebResource webResource = client.resource(url);
//        ClientResponse response = webResource.accept("application/json")
//                .get(ClientResponse.class);        
//        int statusCode = response.getStatus();
//        if (statusCode == 401) {
//            throw new AuthenticationException("Имя пользователя или пароль ошибочны");
//        }
//        if (statusCode == 403) {
//            throw new AuthenticationException("Недостаточно прав для выполнения операции");
//        }    
//        if (statusCode == 404) {
//            throw new AuthenticationException("Данный ресурс не существует");
//        }             
//        return response.getEntity(String.class);
//    } 	
//	
//	public static String invokePostMethodNoAuthorize(String url, String data) throws AuthenticationException, ClientHandlerException {
//        Client client = Client.create();
//        WebResource webResource = client.resource(url);
//        ClientResponse response = webResource.type("application/json")
//     		   .post(ClientResponse.class, data);        
//        int statusCode =  response.getStatus();
//        if (statusCode == 401) {
//            throw new AuthenticationException("Имя пользователя или пароль ошибочны");
//        }
//
//        return response.getEntity(String.class);
//    } 	
//	
//	public static String invokePutMethodNoAuthorize(String url, String data) throws AuthenticationException, ClientHandlerException {
//        Client client = Client.create();
//        WebResource webResource = client.resource(url);
//        ClientResponse response = webResource.type("application/json")
//     		   .put(ClientResponse.class, data);        
//        int statusCode =  response.getStatus();
//        if (statusCode == 401) {
//            throw new AuthenticationException("Имя пользователя или пароль ошибочны");
//        }
//        return response.getEntity(String.class);
//    }
//	
//
//	   /**
//     * REST-запрос Post: запрос - ответ 
//     * @param строка ауторизации
//     * @param строка сетевого адреса
//     * @param строка запроса
//     * @return строка-сетевой адрес подключения
//     * @throws AuthenticationException 
//     * @throws ClientHandlerException 
//     */  
//	public static String invokePostMethod(String auth, String url, String data) throws AuthenticationException, ClientHandlerException {
//        Client client = Client.create();
//        WebResource webResource = client.resource(url);
//        ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
//                .accept("application/json").post(ClientResponse.class, data);
//        int statusCode =  response.getStatus();
//        if (statusCode == 401) {
//            throw new AuthenticationException("Имя пользователя или пароль ошибочны");
//        }
//        return response.getEntity(String.class);
//    } 	
//	
//	public static String invokePutMethod(String auth, String url, String data) throws AuthenticationException, ClientHandlerException {
//        Client client = Client.create();
//        WebResource webResource = client.resource(url);
//        ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
//                .accept("application/json").put(ClientResponse.class, data);
//        int statusCode =  response.getStatus();
//        if (statusCode == 401) {
//            throw new AuthenticationException("Имя пользователя или пароль ошибочны");
//        }
//        return response.getEntity(String.class);
//    } 		
}
