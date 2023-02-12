package frc.robot;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONManager {
    public JSONParser jsonParser;
    public JSONObject jsonObject;
    private String FILEPATH = "/home/robotConfig.json";

    public JSONManager() throws FileNotFoundException, IOException, ParseException {
        jsonParser = new JSONParser();
        jsonObject = (JSONObject) jsonParser.parse(new FileReader(FILEPATH));
    }

    public void writeFile() {
        JSONObject a = new JSONObject();
        a.put("moduleRotation0", 0);
        a.put("moduleRotation1", 0);
        a.put("moduleRotation2", 0);
        a.put("moduleRotation3", 0);
        try( FileWriter file = new FileWriter("/home/robotConfig.json") ) {
            file.write(jsonObject.toJSONString());
        } catch( IOException e) {
            e.printStackTrace();
        }
    }

    public Object getModuleDegrees(int moduleNumber) {
        return jsonObject.get("moduleRotation" + moduleNumber);
    }

    public void setModuleRadians(int moduleNumber, double degrees) {
        jsonObject.put("moduleRotation" + moduleNumber, degrees);

        try( FileWriter file = new FileWriter(FILEPATH) ) {
            file.write(jsonObject.toJSONString());
        } catch( IOException e) {
            e.printStackTrace();
        }
    }
}
