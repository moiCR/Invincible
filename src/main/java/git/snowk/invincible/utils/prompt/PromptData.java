package git.snowk.invincible.utils.prompt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PromptData {

    private final Prompt prompt;
    private final long startMillis;
    private final long timeout;
}
