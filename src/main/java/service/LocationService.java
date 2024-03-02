package service;

import DTO.ForecastDTO;
import DTO.LocationDTO;
import DTO.WeatherDto;
import api.OpenWeatherApi;
import lombok.extern.slf4j.Slf4j;
import module.Locations;
import module.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LocationService extends Config {
    private OpenWeatherApi api = new OpenWeatherApi();

    public LocationDTO getLocationByName(String cityName){
        LocationDTO locationDTO = api.getLocationByCityName(cityName);
        return locationDTO;
    }
    public WeatherDto getWeatherForecast(Double latitude,Double longitude){
        WeatherDto weatherDto = api.getWeatherByCoordinates(latitude,longitude);
        return weatherDto;
    }

    public void saveLocation(Users user,LocationDTO dto){
        Locations location = Locations
                .builder()
                .user(user)
                .name(dto.getName())
                .latitude(dto.getCoord().get("latitude"))
                .longitude(dto.getCoord().get("longitude"))
                .build();
        Configuration configuration = getConfiguration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(location);
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("Location save " + e);
        }finally {
            session.close();
        }
    }
    public ForecastDTO forecast(WeatherDto dto){
        ForecastDTO result = ForecastDTO
                .builder()
                .name(dto.getName())
                .temperature(dto.getMain().get("temp"))
                .feels_like(dto.getMain().get("feels_like"))
                .windSpeed(dto.getWind().get("speed"))
                .windDegree(dto.getWind().get("deg"))
                .build();
        return result;
    }
    public void deleteLocation(){

    }
    public List<Locations> getAllLocationsByUser(Users user){
        Configuration configuration = getConfiguration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Locations> locationsList = null;
        try {
            session.beginTransaction();
            String hql = "select l from Locations l where l.user=:user";
            Query query = session.createQuery(hql, Locations.class);
            query.setParameter("user", user);
            locationsList =  query.getResultList();
        }catch (Exception e){
            log.error("getAllLocByUser " + e);
        }finally {
            session.close();
        }
        return locationsList;
    }
    public List<ForecastDTO> getForecastsForAllSavedLocations(List<Locations> locationsList){
        List<ForecastDTO> result = new ArrayList<>();
        for(Locations location:locationsList){
            WeatherDto weatherDto = getWeatherForecast(location.getLatitude(),location.getLongitude());
            ForecastDTO forecastDTO = forecast(weatherDto);
            result.add(forecastDTO);
        }
        return result;
    }

}
