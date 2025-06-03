package org.example;

import data.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class AddPlace {
@Test
public void post(){
    RestAssured.baseURI = "https://rahulshettyacademy.com";

    //Add place and assert directly
    given().log().all().queryParam("key", "qaclick123").header("Content-type", "application/json")
            .body(Payload.ReqBody()).when().post("/maps/api/place/add/json")
            .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"));
    String postResponse = given()
            .queryParam("key", "qaclick123")
            .header("Contenttype", "application/json")
            .body(Payload.ReqBody())
            .when().post("/maps/api/place/add/json")
            .then().extract().response().asString();
    JsonPath res= new JsonPath(postResponse);
    String placeId= res.getString("place_id");
    System.out.println(placeId);
}
    public static void main(String[] args) {
        String paramValue="qaclick123";
        String header="application/json";
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Add place and assert directly
        given().log().all().queryParam("key", "qaclick123").header("Content-type", "application/json")
                .body(Payload.ReqBody()).when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"));

        //Add  place and extract the response
        String response = given()
                .queryParam("key", "qaclick123")
                .header("Contenttype", "application/json")
                .body(Payload.ReqBody())
                .when().post("/maps/api/place/add/json")
                .then().extract().response().asString();
           JsonPath res= new JsonPath(response);
           String placeId= res.getString("place_id");
        System.out.println(placeId);
        //Update the place retrieved from the previous request
            given().queryParam("key",paramValue).queryParam("place_id",placeId).header("Content-type",header)
                    .body("{\n" +
                            "\"place_id\":\""+placeId+"\",\n" +
                            "\"address\":\"70 winter walk, USA\",\n" +
                            "\"key\":\"qaclick123\"\n" +
                            "}\n")
                    .when().put("/maps/api/place/update/json")
                    .then().log().all();//.body("msg",equalTo("Address successfully updated")).assertThat().statusCode(200);

   //Get the place after update
        given().log().all().queryParam("key",paramValue).queryParam("place_id",placeId)
                .when().get("/maps/api/place/get/json")
                .then().log().all().assertThat().body("address",equalTo("70 winter walk, USA"));


    }




}