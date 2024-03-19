package com.temp.common.web.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "ids批量参数，多用于批量删除等操作")
public class IdsParam {

    @Schema(description = "id")
    @NotNull
    private List<String> ids;
}
