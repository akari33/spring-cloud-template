package com.temp.common.web.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "ids批量和状态参数，多用于批量修改状态")
public class IdsAndStatusParam {
    @Schema(description = "id")
    @NotNull
    private List<String> ids;

    @Schema(description = "状态")
    @NotNull
    private Integer status;
}
