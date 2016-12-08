package org.brit.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.brit.tests.classes.Pet;
import org.brit.tests.classes.StatusEnum;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.brit.tests.Constants.BASE_URL;
import static org.brit.tests.actions.pets.PetsActions.PET_ENDPOINT;

/**
 * Created by sbryt on 9/1/2016.
 */
public class PetTestSerializeDeserialize {

    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .addHeader("api_key", Authentication.Login("britks", "password"))
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL).build();

    @Test
    public void getPetsByStatus() {
        List<Pet> pets = given(requestSpecification)
                .queryParam("status", StatusEnum.available.toString())
                .get(PET_ENDPOINT + "/findByStatus")
                .then().log().all()
                .extract().body()
                .jsonPath().getList("", Pet.class);
        for (Pet pet : pets) {
            Assert.assertEquals(pet.getStatus(), StatusEnum.available);
        }
    }

    @Test
    public void addNewPet() {
        Pet petRequest = new Pet()
                .id("8888885")
                .name("MyLittlePet")
                .status(StatusEnum.available);

        Pet petResponse = given(requestSpecification)
                .body(petRequest)
                .post(PET_ENDPOINT).as(Pet.class);

        Assert.assertEquals(petRequest, petResponse);
    }

    @Test
    public void addNewPet1() {
        Pet petResponse = given(requestSpecification)
                .body(new Pet()
                        .id("88188805")
                        .name("MyLittlePet2")
                        .status(StatusEnum.available))
                .post(PET_ENDPOINT).as(Pet.class);

        List<Pet> pets = given(requestSpecification)
                .queryParam("status", StatusEnum.available.toString())
                .get(PET_ENDPOINT + "/findByStatus")
                .then().log().all()
                .extract().body()
                .jsonPath().getList("", Pet.class);

        Assert.assertTrue(pets.contains(petResponse));
    }

    @Test
    public void deletePetItem() {
        Pet petResponse = given(requestSpecification)
                .body(new Pet()
                        .id("88188805")
                        .name("MyLittlePet2")
                        .status(StatusEnum.available))
                .post(PET_ENDPOINT).as(Pet.class);


        List<Pet> pets = given(requestSpecification)
                .queryParam("status", StatusEnum.available.toString())
                .get(PET_ENDPOINT + "/findByStatus")
                .then().log().all()
                .extract().body()
                .jsonPath().getList("", Pet.class);

        Assert.assertTrue(pets.contains(petResponse));

        given(requestSpecification)
                .pathParam("petId", petResponse.getId())
                .delete(PET_ENDPOINT + "/{petId}");

        pets = given(requestSpecification)
                .queryParam("status", StatusEnum.available.toString())
                .get(PET_ENDPOINT + "/findByStatus")
                .then().log().all()
                .extract().body()
                .jsonPath().getList("", Pet.class);

        Assert.assertTrue(!pets.contains(petResponse));
    }
}
