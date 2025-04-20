package dev.snowk.dante.util.menu.paginated;



import dev.snowk.dante.util.menu.Menu;
import dev.snowk.dante.util.menu.button.Button;
import dev.snowk.dante.util.menu.button.paginated.NextButton;
import dev.snowk.dante.util.menu.button.paginated.PreviousButton;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public abstract class MenuPaginated extends Menu {

    private int currentPage = 1;
    private Map<Integer, Button> navigateBar;

    public MenuPaginated(Player player, String title, int rows, boolean update) {
        super(player, title, rows, update);
        this.navigateBar = new HashMap<>();
    }

    public abstract Map<Integer, Button> getPaginatedButtons();

    @Override
    public Map<Integer, Button> getButtons() {
        return getButtonsInRange();
    }


    public void nextPage() {
        if (currentPage < getMaxPages()) {
            this.currentPage += 1;
            update();
        }
    }

    /**
     * Navigate to the previous menu page
     */
    public void previousPage() {
        if (this.currentPage == 1) return;
        this.currentPage -= 1;
        update();
    }


    public Map<Integer, Button> getButtonsInRange() {
        final Map<Integer, Button> returningButtons = new HashMap<>();

        final int size = this.getSize();
        final int page = this.currentPage;

        final int maxElements = size - 9;

        final int start = ((page - 1) * maxElements);
        final int end = (start + maxElements) - 1;

        for (Map.Entry<Integer, Button> entry : getPaginatedButtons().entrySet()) {
            if (entry.getValue() != null && entry.getKey() >= start && entry.getKey() <= end) {
                returningButtons.put(entry.getKey() - ((maxElements) * (page - 1)) + 9, entry.getValue());
            }
        }

        returningButtons.put(0, new PreviousButton(this));
        returningButtons.put(8, new NextButton(this));

        returningButtons.putAll(navigateBar);

        return returningButtons;
    }

    public int getMaxPages() {
        int maxElements = this.getSize() - 9;
        int totalElements = getPaginatedButtons().size();
        return (int) Math.ceil((double) totalElements / maxElements);
    }

}
