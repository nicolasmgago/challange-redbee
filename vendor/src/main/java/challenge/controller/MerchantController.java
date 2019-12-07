package challenge.controller;

import challenge.common.MerchantRequest;
import challenge.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @RequestMapping("/")
    public String home() {
        return "Hello MERCHANT!";
    }

    @ResponseBody
    @RequestMapping(value = "/validate")
//                    method = RequestMethod.POST)
    public ResponseEntity<String> validate(@RequestBody MerchantRequest merchantRequest) {

        return merchantService.validate(merchantRequest.getId(), merchantRequest.getAmount());
    }
}