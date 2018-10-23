package weather;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.google.gson.*;



@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    String appId = "Pp0YmSEldf3bpBpTJcvj";
    String appCode = "3A5_nyHhOaOcSvGsb8Fk7A";

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			String lat = "41.8842";
            String lng = "-87.6388";
            String weatherData = getWeather(restTemplate,lat, lng);
            String locationName = getLocation(restTemplate,lat, lng);
            System.out.println(locationName);
		};
    }

    public String getLocation(RestTemplate restTemplate,String lat,String lng){
        String prox = lat + "," + lng + ",250";
        String LocationtoNameUrl = "https://reverse.geocoder.api.here.com/6.2/reversegeocode.json";
        String requestUrl=LocationtoNameUrl+"?app_id="+appId+"&app_code="+ appCode 
                        +"&prox="+prox+"&mode=retrieveAddresses&maxresults=1";
        System.out.println(requestUrl);
        return restTemplate.getForEntity(requestUrl,String.class).toString();
    }
    
    public String getWeather(RestTemplate restTemplate,String lat,String lng){
        String reqUrl = "https://api.darksky.net/forecast/0f9fe348e6d02681367462dc6646c813/"+lat+","+lng;
        ResponseEntity<String> response =  restTemplate.getForEntity(reqUrl,String.class);
        response.ok(new Gson().toJson(response.toString()));
    }
}
