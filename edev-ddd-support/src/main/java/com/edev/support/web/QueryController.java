package com.edev.support.web;

import com.edev.support.entity.ResultSet;
import com.edev.support.query.QueryService;
import com.edev.support.utils.SpringHelper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class QueryController extends AbstractController {
    @Autowired
    private SpringHelper springHelper;

    @RequestMapping(value = "query/{bean}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultSet query(@PathVariable("bean") String beanName,
                           @RequestBody(required = false)Map<String, Object> map,
                           HttpServletRequest request) {
        QueryService service = (QueryService) springHelper.getService(beanName);
        Map<String, Object> json = mergeDataToJson(map, request);
        return service.query(json);
    }

    /**
     * do query by post method
     * @param beanName the name of the bean in the spring factory
     * @param params the parameters the query need
     * @return the result set after query
     */
    public ResultSet queryByPost(@NonNull String beanName, Map<String, Object> params) {
        return query(beanName, params, null);
    }

    /**
     * do query by get method
     * @param beanName the name of the bean in the spring factory
     * @param request the http servlet request
     * @return the result set after query
     */
    public ResultSet queryByGet(@NonNull String beanName, @NonNull HttpServletRequest request) {
        return query(beanName, null, request);
    }

}
