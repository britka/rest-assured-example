package org.brit.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.brit.tests.classes.StatusEnum;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.brit.tests.Constants.*;
import static org.brit.tests.actions.pets.PetsActions.PET_ENDPOINT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by sbryt on 8/26/2016.
 */
public class PetClassWithRequestSpec {
    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL).build();

    @Test
    public void getPetsByStatus() {
        given(requestSpecification)
                .queryParam("status", StatusEnum.available.toString())
                .get(PET_ENDPOINT + "/findByStatus")
                .then().extract().body().jsonPath().prettyPrint();
    }

    @Test
    public void getPetById() {
        given(requestSpecification)
                .pathParam("petId", "499278344")
                .get(PET_ENDPOINT + "/{petId}")
                .then().extract().body().jsonPath().prettyPrint();
    }

    @Test
    public void getPetByIdAndDoCheck() {
        given(requestSpecification)
                .pathParam("petId", "499278344")
                .get(PET_ENDPOINT + "/{petId}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Fido6"),
                        "id", is(499278344),
                        "status", equalTo("available"));
    }

    @Test
    public void updateExistingPet() {
        given(requestSpecification)
                .body("{\n" +
                        "  \"id\": 499278344,\n" +
                        "  \"name\": \"Fido6\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .put(PET_ENDPOINT)
                .then().extract().body().jsonPath().prettyPrint();

        given(requestSpecification)
                .pathParam("petId", "499278344")
                .get(PET_ENDPOINT + "/{petId}")
                .then()
                .body("name", equalTo("Fido6"))
                .extract().body().jsonPath()
                .prettyPrint();
    }

    @Test
    public void addNewPet() {
        given(requestSpecification)
                .body("{\n" +
                        "  \"id\": 89898888,\n" +
                        "  \"name\": \"MyLittlePet\",\n" +
                        "  \"photoUrls\": [],\n" +
                        "  \"tags\": [],\n" +
                        "  \"status\": \"" + StatusEnum.pending.toString() + "\"\n" +
                        "}")
                .post(PET_ENDPOINT);

        given(requestSpecification)
                .pathParam("petId", "89898888")
                .get(PET_ENDPOINT + "/{petId}")
                .then()
                .body("name", equalTo("MyLittlePet"))
                .extract().body().jsonPath()
                .prettyPrint();
    }

}
