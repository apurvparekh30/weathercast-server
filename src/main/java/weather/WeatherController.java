package weather;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.servlet.http.HttpServletRequest;

@RestController
class WeatherController{

    RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/mylocation")
    public ResponseEntity<JsonNode> getCurrentPosition(HttpServletRequest request) throws Exception{
        String ipAddress="";
        ipAddress = request.getHeader("X-Forwarded-For");
        //System.out.println(ipAddress);
        if(ipAddress == null || ipAddress.isEmpty() || ipAddress.equals(""))
            ipAddress = request.getRemoteAddr();
        String key = "7af6f2d92eeed85c576c533ad90cdfa1";
        String reqUrl = "http://api.ipstack.com/"+ipAddress+"?access_key="+key;
        ResponseEntity<String> response = restTemplate.getForEntity(reqUrl,String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        return ResponseEntity.status(response.getStatusCode()).headers(headers).body(root);
    }

    @RequestMapping("/weather")
    public ResponseEntity<JsonNode> getWeather(@RequestParam(value="lat")String lat,@RequestParam(value="lng")String lng) throws Exception{
        String reqUrl = "https://api.darksky.net/forecast/0f9fe348e6d02681367462dc6646c813/"+lat+","+lng;
        ResponseEntity<String> response = restTemplate.getForEntity(reqUrl,String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        return ResponseEntity.status(response.getStatusCode()).headers(headers).body(root);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/coordinates")
    public ResponseEntity<JsonNode> getCoordinates(@RequestParam(value="text")String text) throws Exception{
        String appId = "Pp0YmSEldf3bpBpTJcvj";
        String appCode = "3A5_nyHhOaOcSvGsb8Fk7A";
        String reqUrl = "https://geocoder.api.here.com/6.2/geocode.json";
        reqUrl+="?app_id="+appId+"&app_code="+appCode+"&searchtext="+text;
        ResponseEntity<String> response = restTemplate.getForEntity(reqUrl,String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        return ResponseEntity.status(response.getStatusCode()).headers(headers).body(root);
    }

    @RequestMapping("/location")
    public ResponseEntity<JsonNode> getLocation(@RequestParam(value="lat")String lat,@RequestParam(value="lng")String lng) throws Exception{
        String appId = "Pp0YmSEldf3bpBpTJcvj";
        String appCode = "3A5_nyHhOaOcSvGsb8Fk7A";
        String prox = lat + "," + lng + ",250";
        String reqUrl = "https://reverse.geocoder.api.here.com/6.2/reversegeocode.json";
        reqUrl+="?app_id="+appId+"&app_code="+appCode+"&prox="+prox+"&mode=retrieveAddresses&maxresults=1";
        ResponseEntity<String> response = restTemplate.getForEntity(reqUrl,String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        return ResponseEntity.status(response.getStatusCode()).headers(headers).body(root);
    }

}