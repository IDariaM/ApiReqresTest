import io.qameta.allure.Description;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class ApiTests {

    @Test
    @Description ("Checking if pictures are correct")
    public void checkPics() {
        Specification.installSpec(Specification.requestSpec(),Specification.responseSpec());
        Map<String,String> expectedResults = new HashMap<>() {{
            put("7", "https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg");
            put("8", "https://s3.amazonaws.com/uifaces/faces/twitter/araa3185/128.jpg");
            put("9", "https://s3.amazonaws.com/uifaces/faces/twitter/vivekprvr/128.jpg");
            put("10", "https://s3.amazonaws.com/uifaces/faces/twitter/russoedu/128.jpg");
            put("11", "https://s3.amazonaws.com/uifaces/faces/twitter/mrmoiree/128.jpg");
            put("12", "https://s3.amazonaws.com/uifaces/faces/twitter/hebertialmeida/128.jpg");
        }};

        ApiSteps.checkPics(expectedResults);
        Specification.clearSpec();

    }

    @Test
    @Description("Registration check")
    public void registrationCheck (){
        Map <String,String> data = new HashMap<>();
        data.put("email","eve.holt@reqres.in");
        data.put("password","pistol");
        Specification.installSpec(Specification.requestSpec(),Specification.responseSpec());
        ApiSteps.userRegistration(data);
        Specification.clearSpec();
    }

    @Test
    @Description("Login successful check")
    public void successfulLogin (){
        Map <String,String> data = new HashMap<>();
        data.put("email","eve.holt@reqres.in");
        data.put("password","cityslicka");
        Specification.installSpec(Specification.requestSpec(),Specification.responseSpec());
        ApiSteps.successfulLogin(data);
        Specification.clearSpec();
    }

    @Test
    @Description("Login unsuccessful check")
    public void unsuccessfulLogin (){
        Map <String,String> data = new HashMap<>();
        data.put("email","eve.holt@reqres.in");
        data.put("password","");
        Specification.installSpec(Specification.requestSpec());
        ApiSteps.unsuccessfulLogin(data);
        Specification.clearSpec();
    }

    @Test
    @Description("Check that data sorted by year")
    public void checkDataSorting () {
        Specification.installSpec(Specification.requestSpec(), Specification.responseSpec());
        ApiSteps.checkDataSorting();
        Specification.clearSpec();
    }

    }
