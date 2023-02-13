package com.newcen.newcen.common.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchSentMessageCondition {

    private String messageTitle;

    private String messageContent;

    private String messageReceiver;
}

