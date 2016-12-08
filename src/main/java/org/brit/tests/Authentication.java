package org.brit.tests;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * Created by sbryt on 9/1/2016.
 */
public class Authentication {

    public static String Login(final String userName, final String password) {
        HashMap<String, Object> map =
                new HashMap<String, Object>() {{
                    put("scope", "email");
                    put("client_id", "someclientid");
                    put("accept", "Allow");
                    put("request_id", "");
                    put("username", userName);
                    put("password", password);
                    put("login", "");
                }};

        return given()
                .formParams(map)
                .post("http://petstore.swagger.io/oauth/login?redirect_uri=http://petstore.swagger.io/o2c.html")
                .getHeader("Location")
                .split("#")[1].split("=")[1];
    }
}
