package unithon8th.somethingnew.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import unithon8th.somethingnew.dto.user.NaverRequestDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Log4j2//커밋용주석
@RequiredArgsConstructor
public class NaverService {
    public NaverRequestDto getUserInfo(String access_token){
        NaverRequestDto naverRequestDto=new NaverRequestDto();

        try {
            URL url=new URL("https://openapi.naver.com/v1/nid/me");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Authorization", "Bearer "+access_token);
            conn.setRequestMethod("POST");

            int responseCode = conn.getResponseCode();
            log.info("200 is good={}",responseCode);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line="",result="";

            while ((line=bufferedReader.readLine())!=null){
                result+=line;
            }
            System.out.println("result = " + result);

            JsonParser jsonParser=new JsonParser();
            JsonElement element = jsonParser.parse(result);

            String naverId = element.getAsJsonObject().get("id").getAsString();
            String age = element.getAsJsonObject().get("age").getAsString();
            String email = element.getAsJsonObject().get("email").getAsString();
            String username = element.getAsJsonObject().get("name").getAsString();
            String profileImage = element.getAsJsonObject().get("profile_image").getAsString();

            naverRequestDto.setSocialId(naverId);
            naverRequestDto.setAge(age);
            naverRequestDto.setEmail(email);
            naverRequestDto.setUsername(username);
            naverRequestDto.setProfileImage(profileImage);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return naverRequestDto;
    }
}