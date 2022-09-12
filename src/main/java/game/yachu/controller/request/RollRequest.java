package game.yachu.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class RollRequest {
    @NotNull
    private List<Boolean> fixStates;

    public RollRequest(List<Boolean> fixStates) {
        this.fixStates = fixStates;
    }
}
