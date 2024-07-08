package com.example.cold_room.processor;

import com.example.cold_room.model.Cooling;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CoolingParser {

    private static final String coolingRegex = "temperature:(?<temperature>.*)," +
            "idRoom:(?<idRoom>\\d*)," +
            "isCooling:(?<isCooling>.*)," +
            "consumption:(?<consumption>.*)";
    Pattern coolingPattern = Pattern.compile(coolingRegex);
    public Cooling parse(String str){
        Matcher coolingMatcher = coolingPattern.matcher(str);
        coolingMatcher.matches();
        Cooling cooling = new Cooling();
        cooling.setTemperature(Double.parseDouble(
                coolingMatcher.group("temperature")));
        cooling.setIdRoom(Integer.parseInt(
                coolingMatcher.group("idRoom")));
        cooling.setIsCooling(Boolean.parseBoolean(
                coolingMatcher.group("isCooling")));
        cooling.setConsumption(Double.parseDouble(
                coolingMatcher.group("consumption")));
        return cooling;
    }
}
