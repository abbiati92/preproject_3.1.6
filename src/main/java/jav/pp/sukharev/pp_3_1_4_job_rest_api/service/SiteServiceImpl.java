package jav.pp.sukharev.pp_3_1_4_job_rest_api.service;

import jav.pp.sukharev.pp_3_1_4_job_rest_api.model.SiteInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class SiteServiceImpl implements SiteService{

    private final RestTemplate restTemplate;
    private final String serverUrl;
    private String sessionId;

    public SiteServiceImpl(RestTemplate restTemplate,
                           @Value("${application.server.url}") String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    @Override
    public List<SiteInfo> findAllUser() {
        ResponseEntity<List<SiteInfo>> response = restTemplate.exchange(
                serverUrl + "/api/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SiteInfo>>() {
                }
        );

        HttpHeaders headers = response.getHeaders();

        List<String> cookies = headers.get(HttpHeaders.SET_COOKIE);
        if (cookies != null) {
            for (String cookie: cookies) {
                if (cookie.startsWith("JSESSIONID")) {
                    int endIndex = cookie.indexOf(';');
                    sessionId = endIndex != -1 ? cookie.substring(0, endIndex) : cookie;
                    System.out.println("Полученный на первом этапе:" + sessionId);
                    break;
                }
            }
        }
        return response.getBody();
    }

    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void saveUser() {
        SiteInfo user = new SiteInfo();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 23);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", getSessionId());


        HttpEntity<SiteInfo> request = new HttpEntity<>(user,headers);
        ResponseEntity<String> response = restTemplate.exchange(
                serverUrl + "/api/users",
                HttpMethod.POST,
                request,
                String.class
        );
        System.out.println(response.getBody());
    }

    @Override
    public void updateUser() {
        SiteInfo updateUser = new SiteInfo();
        updateUser.setId(3L);
        updateUser.setName("Thomas");
        updateUser.setLastName("Shelby");
        updateUser.setAge((byte) 23);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", getSessionId());

        HttpEntity<SiteInfo> request = new HttpEntity<>(updateUser, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                serverUrl + "/api/users",
                HttpMethod.PUT,
                request,
                String.class
        );
        System.out.println(response.getBody());
    }

    public void deleteUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", getSessionId());

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                serverUrl + "/api/users/3",
                HttpMethod.DELETE,
                request,
                String.class
        );
        System.out.println(response.getBody());
    }
}
