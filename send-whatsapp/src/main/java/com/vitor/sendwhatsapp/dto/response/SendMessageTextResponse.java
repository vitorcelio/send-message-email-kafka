package com.vitor.sendwhatsapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendMessageTextResponse {

    private String zaapId;
    private String messageId;
    private String id;

}
