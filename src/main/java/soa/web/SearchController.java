package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;

@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
			HashMap<String, Object> options = new HashMap<String, Object>();
			String[] queryParts = q.split(" max:");
			if (queryParts.length == 2) {
				options.put("CamelTwitterKeywords", queryParts[0]);
				options.put("CamelTwitterCount", queryParts[1]);
			} else {
				options.put("CamelTwitterKeywords", queryParts[0]);
			}
			return producerTemplate.requestBodyAndHeaders("direct:search", "", options);
			//return producerTemplate.requestBodyAndHeader("direct:search", "", "CamelTwitterKeywords", q);
		}
}
