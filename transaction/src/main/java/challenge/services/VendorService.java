package challenge.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import challenge.common.MerchantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VendorService implements IVendorService {

    private final String vendorURL;

    RestTemplateBuilder restTemplateBuilder;
    RestTemplate restTemplate;

    @Autowired
    public VendorService(RestTemplateBuilder restTemplateBuilder , @Value("${url.vendor}") String vendorURL) {
        restTemplate = restTemplateBuilder.build();
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverters.add(converter);
//        restTemplate.setMessageConverters(messageConverters);
        this.vendorURL = vendorURL;
    }

//    @HystrixCommand(fallbackMethod = "redirectValidation")
//    @Override
    public ResponseEntity<String> validate(Integer id, double amount) {

        ResponseEntity<String> response;
        MerchantRequest vr = new MerchantRequest();
        vr.setId(id);
        vr.setAmount(amount);

        try {
            // Validate if merchant exists and his limits
            response = restTemplate.postForEntity(this.vendorURL, vr, String.class);

            return response;

        } catch (HttpStatusCodeException exception) {

            return new ResponseEntity<String>(exception.getResponseBodyAsString(), exception.getStatusCode());
        }
    }

    public ResponseEntity<String> redirectValidation(Integer id, double amount) {
        return new ResponseEntity<String>("There was an error with the service", HttpStatus.OK);
    }
}
