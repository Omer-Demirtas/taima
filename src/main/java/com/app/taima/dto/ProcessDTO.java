package com.app.taima.dto;

import com.app.taima.enums.ProcessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProcessDTO {
    private Long id;

    private ProcessType processType;

    private String url;
}
