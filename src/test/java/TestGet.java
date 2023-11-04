import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class TestGet {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test(description = "Получение питомцев по статусу")
    public void gPetByStatusAvailable() {
        Specification.installSpec(Specification.requestSpec(), Specification.responseSpec200());
        List<Pet> pets = given()
                .queryParam("status", "available") // Замените "available" на желаемый статус
                .when()
                .get("pet/findByStatus")
                .then().log().all()
                .extract().body().jsonPath().getList(".", Pet.class);

        assertNotNull("Список питомцев не должен быть пустым", pets);
        for (Pet pet : pets) {
            assertNotNull("Статус питомца не должен быть null", pet.getStatus());
            assertEquals("Статус питомца должен быть 'available'", "available", pet.getStatus());
        }
    }

    @Test(description = "Получение питомцев по статусу")
    public void gPetByStatusPending() {
        Specification.installSpec(Specification.requestSpec(), Specification.responseSpec200());
        List<Pet> pets = given()
                .queryParam("status", "pending") // Замените "available" на желаемый статус
                .when()
                .get("pet/findByStatus")
                .then().log().all()
                .extract().body().jsonPath().getList(".", Pet.class);

        assertNotNull("Список питомцев не должен быть пустым", pets);
        for (Pet pet : pets) {
            assertNotNull("Статус питомца не должен быть null", pet.getStatus());
            assertEquals("Статус питомца должен быть 'pending'", "pending", pet.getStatus());
        }
    }

    @Test(description = "Получение питомцев по статусу")
    public void gPetByStatusSold() {
        Specification.installSpec(Specification.requestSpec(), Specification.responseSpec200());
        List<Pet> pets = given()
                .queryParam("status", "sold") // Замените "available" на желаемый статус
                .when()
                .get("pet/findByStatus")
                .then().log().all()
                .extract().body().jsonPath().getList(".", Pet.class);

        assertNotNull("Список питомцев не должен быть пустым", pets);
        for (Pet pet : pets) {
            assertNotNull("Статус питомца не должен быть null", pet.getStatus());
            assertEquals("Статус питомца должен быть 'sold'", "sold", pet.getStatus());
        }
    }
    @Test
    public void testMultipleStatuses() {
        Specification.installSpec(Specification.requestSpec(), Specification.responseSpec200());
        List<Pet> pets = given()
                .queryParam("status", "pending")
                .queryParam("status", "sold")
                .when()
                .get("/pet/findByStatus")
                .then()
                .assertThat()
                .body("status", everyItem(anyOf(equalTo("pending"), equalTo("sold"))))
                .extract()
                .jsonPath()
                .getList("", Pet.class);

        // Проверяем, что в списке нет питомцев с другими статусами
        for (Pet pet : pets) {
            Assert.assertTrue(pet.getStatus().equals("pending") || pet.getStatus().equals("sold"),
                    "Pet status is not pending or sold.");
        }
    }

    @Test
    public void testAllPets() {
        Specification.installSpec(Specification.requestSpec(), Specification.responseSpec200());
        List<Pet> petsAll = given()
                .queryParam("status", "available")
                .queryParam("status", "pending")
                .queryParam("status", "sold")
                .when()
                .get("/pet/findByStatus")
                .then()
                .assertThat()
                .body("status", everyItem(anyOf( equalTo("available"), equalTo("pending"), equalTo("sold"))))
                .extract()
                .jsonPath()
                .getList("", Pet.class);

        // Проверяем, что в списке нет питомцев с другими статусами
        for (Pet pet : petsAll) {
            Assert.assertTrue(pet.getStatus().equals("pending") || pet.getStatus().equals("sold") || pet.getStatus().equals("available") ,
                    "Pet status is not pending or sold.");
        }
    }

    @Test
    public void getPetByIdAvailable() {
        Specification.installSpec(Specification.requestSpec(), Specification.responseSpec200());
        long petId = 88; // ID животного, которое вы хотите найти
        Pet pet = given()
                .when()
                .get("/pet/" + petId)
                .then().log().all()
                .extract().body().as(Pet.class);

        // Проверки
        Assert.assertNotNull(pet, "The pet should not be null");
        Assert.assertEquals(pet.getId(), Long.valueOf(petId), "Pet ID doesn't match the requested ID");
    }
    @Test
    public void getPetByIdPending() {
        Specification.installSpec(Specification.requestSpec(), Specification.responseSpec200());
        long petId = 111; // ID животного, которое вы хотите найти
        Pet pet = given()
                .when()
                .get("/pet/" + petId)
                .then().log().all()
                .extract().body().as(Pet.class);

        // Проверки
        Assert.assertNotNull(pet, "The pet should not be null");
        Assert.assertEquals(pet.getId(), Long.valueOf(petId), "Pet ID doesn't match the requested ID");
    }
    @Test
    public void getPetByIdSold() {
        Specification.installSpec(Specification.requestSpec(), Specification.responseSpec200());
        long petId = 1234567; // ID животного, которое вы хотите найти
        Pet pet = given()
                .when()
                .get("/pet/" + petId)
                .then().log().all()
                .extract().body().as(Pet.class);

        // Проверки
        Assert.assertNotNull(pet, "The pet should not be null");
        Assert.assertEquals(pet.getId(), Long.valueOf(petId), "Pet ID doesn't match the requested ID");
    }

}