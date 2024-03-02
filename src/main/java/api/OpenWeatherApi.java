package api;

import DTO.LocationDTO;
import DTO.WeatherDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import module.Locations;
import module.Users;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
@Slf4j
public class OpenWeatherApi {
    private final String key = "60c619fd77e20ba571275e52ca7e0641";

    public LocationDTO getLocationByCityName(String cityName){

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" +
                cityName +
                "&appid=" +
                key;
        HttpRequest request = null;
        try{
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(Duration.of(10, ChronoUnit.SECONDS))
                    .build();
        }catch (Exception e){
            log.error("OpenWeatherApi request exception " + e);
        }
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse<String> response = null;
        LocationDTO dto = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            log.error("OpenWeatherApi response exception " + e);
        }
        try {
            dto = objectMapper.readValue(response.body(), LocationDTO.class);
        }catch (Exception e){
            log.error("OpenWeatherApi mapper exception " + e);
        }

        return dto;
    }
    public WeatherDto getWeatherByCoordinates(Double latitude,Double longitude){
        String url = "https://api.openweathermap.org/data/2.5/weather?" +
                "lat=" + latitude +
                "&" +
                "lon="+longitude +
                "&" +
                "appid=" + key;
        HttpRequest request = null;

        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.of(10,ChronoUnit.SECONDS))
                    .GET()
                    .build();
        }catch (Exception e){
            log.error("OpenWeatherApi request exception " + e);
        }
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            log.error("OpenWeatherApi response exception " + e);
        }
        WeatherDto dto = null;
        try {
            dto = objectMapper.readValue(response.body(), WeatherDto.class);
        }catch (Exception e){
            log.error("OpenWeatherApi mapper exception " + e);
        }
        return dto;
    }

}
