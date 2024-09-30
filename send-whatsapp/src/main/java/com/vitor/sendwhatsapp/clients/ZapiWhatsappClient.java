package com.vitor.sendwhatsapp.clients;

import com.vitor.sendwhatsapp.dto.request.SendMessageTextRequest;
import com.vitor.sendwhatsapp.dto.response.SendMessageTextResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zapi", url = "https://api.z-api.io/instances/")
public interface ZapiWhatsappClient {

    @PostMapping("{instance}/token/{token}/send-text")
    SendMessageTextResponse sendText(@PathVariable("instance") String instance, @PathVariable("token") String token, @RequestBody SendMessageTextRequest request);

}
