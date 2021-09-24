package com.koligrum.test;

//import org.junit.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UserTest {
    final static String url = "http://localhost/";
    final static int port = 1234;

    @Test
    public void getUserByNameTest(){
        Response response = given()
                .baseUri(url)
                .port(port)
                .header("accept", "application/json")
                .queryParam("name", "falah")
                .basePath("v1")
                .log().all()
                .when()
                .get("users");

        response.getBody().prettyPrint();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200,statusCode);

        //assert with json path
        String firstName = response.jsonPath().getJsonObject("data[0].firstName");
        System.out.println(firstName);
        Assert.assertEquals("Falah",firstName);
    }

    //post user
    @Test
    public void createUserTest(){
        String firstName = "Falah";
        String lastName = "Yusnika";
        int age = 23;
        String occupation = "IT";
        String nationality = "Indonesia";
        String hobby = "reading";
        String gender = "FEMALE";

        String body = "{\n"
                + "\n"
                + "  \"firstName\": \"" + firstName + "\",\n"
                + "  \"lastName\": \"" + lastName + "\",\n"
                + "  \"age\": " + age + ",\n"
                + "  \"occupation\": \"" + occupation + "\",\n"
                + "  \"nationality\": \"" + nationality + "\",\n"
                + "  \"hobbies\": [\n"
                + "    \""+ hobby +"\"\n"
                + "  ],\n"
                + "  \"gender\": \""+ gender +"\"\n"
                + "}";

        Response responsePost = given()
                .baseUri(url)
                .port(port)
                .header("accept", "application/json")
                .queryParam("name","falah")
                .contentType(ContentType.JSON)
                .basePath("v1")
                .body(body).log().all()
                .when()
                .post("users");

        responsePost.getBody().prettyPrint();

        //get id user
        String id =responsePost.path("id");

        //validasi create user
        Response responseGetByID = given()
                .baseUri(url)
                .port(port)
                .header("accept", "application/json")
                .pathParam("id",id)
                .basePath("v1")
                .log().all()
                .when()
                .get("users/{id}");

        responseGetByID.getBody().prettyPrint();
        Assert.assertEquals(200, responseGetByID.statusCode());
        Assert.assertEquals(firstName,responseGetByID.path("firstName"));
//        Assert.assertEquals(lastName,responseGetByID.path("lastName"));
//        Assert.assertEquals(gender,responseGetByID.path("gender"));
////        Assert.assertEquals(age,responseGetByID.path("age"));
//        Assert.assertEquals(occupation,responseGetByID.path("occupation"));
//        Assert.assertEquals(nationality,responseGetByID.path("nationality"));
////        Assert.assertEquals(hobby,responseGetByID.path("hobby"));


    }

    //update user
    @Test
    public void updateUserTest(){
        String id = "28f82304-d32d-41ed-bfa0-fa0572b6c47d";
        String firstName = "Update User Falah";
        String lastName = "Yusnika";
        int age = 23;
        String occupation = "IT";
        String nationality = "Indonesia";
        String hobby = "reading";
        String gender = "FEMALE";

        String body = "{\n"
                + "\n"
                + "  \"id\": \"" + id + "\", \n"
                + "  \"firstName\": \"" + firstName + "\",\n"
                + "  \"lastName\": \"" + lastName + "\",\n"
                + "  \"age\": " + age + ",\n"
                + "  \"occupation\": \"" + occupation + "\",\n"
                + "  \"nationality\": \"" + nationality + "\",\n"
                + "  \"hobbies\": [\n"
                + "    \""+ hobby +"\"\n"
                + "  ],\n"
                + "  \"gender\": \""+ gender +"\"\n"
                + "}";

        Response responsePut = given()
                .baseUri(url)
                .port(port)
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .basePath("v1")
                .body(body).log().all()
                .when()
                .put("users");

        responsePut.getBody().prettyPrint();

        //get id user
        String idd = responsePut.path("id");

        //validasi create user by id
        Response responseGetByID = given()
                .baseUri(url)
                .port(port)
                .header("accept", "application/json")
                .pathParam("id",idd)
                .basePath("v1")
                .log().all()
                .when()
                .get("users/{id}");

        responseGetByID.getBody().prettyPrint();
        Assert.assertEquals(200, responseGetByID.statusCode());
        Assert.assertEquals(firstName,responseGetByID.path("firstName"));
//        Assert.assertEquals(lastName,responseGetByID.path("lastName"));
//        Assert.assertEquals(gender,responseGetByID.path("gender"));
////        Assert.assertEquals(age,responseGetByID.path("age"));
//        Assert.assertEquals(occupation,responseGetByID.path("occupation"));
//        Assert.assertEquals(nationality,responseGetByID.path("nationality"));
////        Assert.assertEquals(hobby,responseGetByID.path("hobby"));
    }

    //delete user
    @Test
    public void deleteUserTest(){

        Response responseDelete = given()
                .baseUri(url)
                .port(port)
                .header("accept", "application/json")
                .pathParam("id", "65507bae-76f4-4a22-b0b6-a34167eada42")
                .basePath("v1")
                .log().all()
                .when()
                .delete("users/{id}");

        responseDelete.getBody().prettyPrint();
        int statusCode = responseDelete.getStatusCode();

    }
}
