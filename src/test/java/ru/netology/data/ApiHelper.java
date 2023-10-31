package ru.netology.data;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.data.DataHelper;
import static io.restassured.RestAssured.given;

public class ApiHelper {
    private static final Gson gson = new Gson();
    private static final RequestSpecification spec = new RequestSpecBuilder().setBaseUri("http://localhost").setPort(9999)
            .setAccept(ContentType.JSON).setContentType(ContentType.JSON).log(LogDetail.ALL).build();

    public static void getData(DataHelper.CardInfo cardInfo, String url, int statusCode) {
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post(url)
                .then().statusCode(statusCode);
    }
}
