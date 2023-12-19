package jav.pp.sukharev.pp_3_1_4_job_rest_api.service;

import jav.pp.sukharev.pp_3_1_4_job_rest_api.model.SiteInfo;
import java.util.List;

public interface SiteService {
    List<SiteInfo> findAllUser();
    void saveUser();
    void updateUser();
    void deleteUser();

}
