package jav.pp.sukharev.pp_3_1_4_job_rest_api;

import jav.pp.sukharev.pp_3_1_4_job_rest_api.service.SiteService;
import jav.pp.sukharev.pp_3_1_4_job_rest_api.service.SiteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Pp314JobRestApiApplication {
    @Autowired
    private SiteService siteService;

    public static void main(String[] args) {

        var context = SpringApplication.run(Pp314JobRestApiApplication.class, args);
        Pp314JobRestApiApplication app = context.getBean(Pp314JobRestApiApplication.class);
        app.siteService.findAllUser().forEach(site -> System.out.println(site.toString()));
        app.siteService.saveUser();
        app.siteService.updateUser();
        app.siteService.deleteUser();
    }


}
