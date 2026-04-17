package timetracker.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UpdateTaskStatusRequest {
    @NotNull
    @Pattern(regexp = "NEW|IN_PROGRESS|DONE", message = "Status has to be NEW, IN_PROGRESS or DONE")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
