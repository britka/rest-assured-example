package org.brit.tests;

import org.brit.tests.actions.pets.PetsActions;
import org.brit.tests.classes.Pet;
import org.brit.tests.classes.StatusEnum;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by sbryt on 9/9/2016.
 */
public class PetTestsUsingLittleLibrary {
    PetsActions petsActions;

    @BeforeClass
    public void beforeClass() {
        petsActions = new PetsActions();
    }

    @Test
    public void getPetsByStatus() {
        List<Pet> pets = petsActions.getPetsByStatus(StatusEnum.available);
        for (Pet pet : pets) {
            Assert.assertEquals(pet.getStatus(), StatusEnum.available);
        }
    }

    @Test
    public void addNewPet() {
        Pet petRequest = new Pet();
        Pet petResponse = petsActions.addNewPet(petRequest);
        Assert.assertEquals(petRequest, petResponse);
    }


    @Test
    public void deletePetItem() {
        Pet petRequest = new Pet();
        Pet response = petsActions.addNewPet(petRequest);
        petsActions.deletePet(response);
        Assert.assertTrue(!petsActions.isPetExists(response));
    }
}
