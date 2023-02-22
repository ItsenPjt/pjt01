package com.newcen.newcen.common.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchReceivedMessageCondition {

    private String messageTitle;

    private String messageContent;

    private String messageSender;
}
