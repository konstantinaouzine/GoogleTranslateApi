package google;

import google.objects.TranslatedObject;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static google.TextConstants.*;
import static google.Utils.getRapidApiHost;
import static org.testng.Assert.*;
import static google.Utils.prepareTranslationQueryParams;

public class Translate extends BaseClass{
    @Test
    public void getTest_checkStatusCode(){
        //Test validates proper response status code
        Response response = getGeneric(EndPointsRepo.getTranslation_someTest);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void getTranslation_fromFrenchLanguageSent(){
        //Test validates proper translation from French when language optional parameter sent and a translated word is 'Hello'
        //Response data is being compared by response body json deserialization to expected Class
        String language = FRENCH_LANGUAGE;
        String textToBeTranslated = HELLO_TEXT;
        String expectedTranslation = HELLO_FRENCH;
        var queryParams = prepareTranslationQueryParams(true, language, true, textToBeTranslated);
        Response response = getGeneric(EndPointsRepo.getTranslation_translate, queryParams);

        assertEquals(response.statusCode(), 200);
        String actualTranslation = (response.body().as(TranslatedObject.class)).getText();
        assertEquals(actualTranslation, expectedTranslation);
    }

    @Test
    public void getTranslation_fromFrenchLanguageNotSent(){
        //Test validates proper translation from French when language optional parameter NOT sent and a translated word is 'Hello'
        //specific data may be extracted from response json also by key
        String textToBeTranslated = HELLO_TEXT;
        String expectedTranslation = HELLO_FRENCH;
        var queryParams = prepareTranslationQueryParams(false, null, true, textToBeTranslated);
        Response response = getGeneric(EndPointsRepo.getTranslation_translate, queryParams);

        assertEquals(response.statusCode(), 200);
        String actualTranslation = response.jsonPath().getString("text");
        assertEquals(actualTranslation, expectedTranslation);
    }

    @Test
    public void getTranslation_fromRussian(){
        //Test validates proper translation from Russian when language optional parameter sent and a translated word is 'Hello'
        String language = RUSSIAN_LANGUAGE;
        String textToBeTranslated = HELLO_TEXT;
        String expectedTranslation = HELLO_RUSSIAN;
        var queryParams = prepareTranslationQueryParams(true, language, true, textToBeTranslated);
        Response response = getGeneric(EndPointsRepo.getTranslation_translate, queryParams);

        assertEquals(response.statusCode(), 200);
        String actualTranslation = (response.body().as(TranslatedObject.class)).getText();
        assertEquals(actualTranslation, expectedTranslation);
    }

    @Test
    public void getTranslation_fromHebrew(){
        //Test validates proper translation from Russian when language optional parameter sent and a translated word is 'Hello'
        String language = HEBREW_LANGUAGE;
        String textToBeTranslated = HELLO_TEXT;
        String expectedTranslation = HELLO_HEBREW;
        var queryParams = prepareTranslationQueryParams(true, language, true, textToBeTranslated);
        Response response = getGeneric(EndPointsRepo.getTranslation_translate, queryParams);

        assertEquals(response.statusCode(), 200);
        String actualTranslation = (response.body().as(TranslatedObject.class)).getText();
        assertEquals(actualTranslation, expectedTranslation);
    }

    @Test
    public void getTranslation_incorrectLanguageSent_Negative(){
        //Test validates proper response status code in case supplied language parameter is incorrect
        String incorrectLanguage = "INCORRECT";
        String textToBeTranslated = HELLO_TEXT;
        var queryParams = prepareTranslationQueryParams(true, incorrectLanguage, true, textToBeTranslated);
        Response response = getGeneric(EndPointsRepo.getTranslation_translate, queryParams);
        assertEquals(response.statusCode(), 400);
    }

    @Test
    public void getTranslation_KeyHeaderNotSent_Negative(){
        //Test validates proper response status code and message in case key header not supplied
        String language = FRENCH_LANGUAGE;
        String textToBeTranslated = HELLO_TEXT;
        String hostHeader = getRapidApiHost();
        String keyHeader = null; //Means that it will not be sent
        var queryParams = prepareTranslationQueryParams(true, language, true, textToBeTranslated);

        Response response = getGeneric(EndPointsRepo.getTranslation_translate, hostHeader, keyHeader, queryParams);
        assertEquals(response.statusCode(), 401);
        String responseMessage = response.jsonPath().get(MESSAGE);
        assertTrue(responseMessage.contains(INVALID_API_KEY));

    }

}
