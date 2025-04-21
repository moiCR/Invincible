package git.snowk.invincible.utils.menu.decoration;

import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.Menu;
import git.snowk.invincible.utils.menu.button.Button;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public enum DecorationType {
    NONE,
    FILL,
    BORDER,
    CORNER;


    private Menu menu;

    public void decorate(Menu menu) {
        this.menu = menu;
        switch (this) {
            case FILL:
                fillDecoration();
                break;
            case BORDER:
                borderDecoration();
                break;
            case CORNER:
                cornerDecoration();
                break;
            case NONE:
                break;
        }

        menu.getDecorationButtons().forEach((key, button) -> {
            menu.getInventory().setItem(key, button.icon());
        });
    }

    private void fillDecoration() {
        for (int i = 0; i < menu.getSize(); i++) {
            setDecorationButton(i);
        }
    }

    private void borderDecoration() {
        int size = menu.getSize();
        int rows = menu.getRows();

        for (int i = 0; i < 9; i++) {
            setDecorationButton(i);
            setDecorationButton(size - 9 + 1);

        }

        for (int i = 0; i < rows - 1; i++) {
            setDecorationButton(i + 9);
            setDecorationButton(i * 9 + 8);
        }
    }

    private void cornerDecoration() {
        int size = menu.getSize();

        setDecorationButton(0);
        setDecorationButton(1);
        setDecorationButton(9);

        setDecorationButton(7);
        setDecorationButton(8);
        setDecorationButton(17);

        setDecorationButton(size - 18);
        setDecorationButton(size - 9);
        setDecorationButton(size - 8);

        setDecorationButton(size - 10);
        setDecorationButton(size - 1);
        setDecorationButton(size - 2);
    }

    private void setDecorationButton(int slot) {
        menu.getDecorationButtons().put(slot, new Button() {
            @Override
            public ItemStack icon() {
                return ItemMaker.of(menu.getDecorationItem().clone()).setAmount(1).setDisplayName(" ").build();
            }

            @Override
            public void setAction(InventoryClickEvent event) {

            }

            @Override
            public boolean isInteractable() {
                return false;
            }
        });
    }
}
