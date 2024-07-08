package com.example.cold_room.scanalert;

import com.example.cold_room.model.Alert;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AlertParser {

    private static final String coolingAlertRegex = "Cooling\\(id=(?<id>.*), idRoom=(?<idRoom>.*), isCooling=(?<isCooling>.*), temperature=(?<temperature>.*), consumption=(?<consumption>.*), messageDate=(?<timestamp>.*)\\)";
    Pattern coolingAlertPattern = Pattern.compile(coolingAlertRegex);
    public Alert parse(String str){
        Matcher coolingMatcher = coolingAlertPattern.matcher(str);
        coolingMatcher.matches();
        Alert alert = new Alert();
        alert.setTemperature(Double.parseDouble(
                coolingMatcher.group("temperature")));
        alert.setIdRoom(Integer.parseInt(
                coolingMatcher.group("idRoom")));
        alert.setConsumption(Double.parseDouble(
                coolingMatcher.group("consumption")));
        return alert;
    }
}
