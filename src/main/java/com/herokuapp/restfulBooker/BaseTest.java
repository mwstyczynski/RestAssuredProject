package com.herokuapp.restfulBooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected RequestSpecification spec;

    @BeforeMethod
    public void setUp(){
        spec = new RequestSpecBuilder().
                setBaseUri("https://restful-booker.herokuapp.com").build();
    }


    protected Response createBooking(String firstname) {

//      Create json body: add JSON in Java dependency
        JSONObject body = new JSONObject();
//      Create piece by piece the elements of our jsonBody
        body.put("firstname", firstname);
        body.put("lastname", "Brown");
        body.put("totalprice", 666);
        body.put("depositpaid", false);

//      Every JSON object needs to be created separately
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2021-01-01");
        bookingDates.put("checkout", "2022-01-01");

//      We insert child object like we insert a value into the key/parameter
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "cocaine");

//      Create a RestAssured response instance of Response class
        Response response = RestAssured.given(spec).
//                used for requests that require authentication
//                auth().preemptive().basic("admin", "password123").
                contentType(ContentType.JSON).
                body(body.toString()).post("/booking");

        return response;
    }
}


        /*
  https://restful-booker.herokuapp.com/booking
  {                                         Complete body object
    "firstname" : "Jim",
    "lastname" : "Brown",
    "totalprice" : 111,
    "depositpaid" : true,
    "bookingdates" : {                      Separate bookingDates object
        "checkin" : "2018-01-01",
        "checkout" : "2019-01-01" },
    "additionalneeds" : "Breakfast"
  }
        */
