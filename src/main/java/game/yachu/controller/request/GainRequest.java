package game.yachu.controller.request;

import game.yachu.domain.Genealogy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GainRequest {
    private Genealogy category;

    private int gained;
}