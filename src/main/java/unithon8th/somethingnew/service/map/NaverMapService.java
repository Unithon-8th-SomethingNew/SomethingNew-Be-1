package unithon8th.somethingnew.service.map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class NaverMapService {

    public String getUserLocation(String street){
        String privateKey="78c13b3cb1635c3eaff2a2f6bc2d9f54";
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        String jsonString = null;

        try {
            street = URLEncoder.encode(street, "UTF-8");

            String addr = apiUrl + "?query=" + street;

            URL url = new URL(addr);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Authorization", "KakaoAK " + privateKey);

            BufferedReader rd = null;
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer docJson = new StringBuffer();

            String line;
            while ((line=rd.readLine()) != null) {
                docJson.append(line);
            }
            jsonString = docJson.toString();
            JsonParser parser=new JsonParser();
            JsonElement element = parser.parse(docJson.toString());
            log.info("This is Element={}",element);
            rd.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
