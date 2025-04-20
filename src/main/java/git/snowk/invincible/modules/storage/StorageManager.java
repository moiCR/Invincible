package git.snowk.invincible.modules.storage;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.storage.json.JsonStorage;
import lombok.Getter;


@Getter
public class StorageManager {

    private final IStorage storage;

    public StorageManager(){
        this.storage = new JsonStorage(Invincible.getInstance().getGson());
    }


    public void onLoad(){
        this.storage.onLoad();
    }

    public void onDisable(){
        this.storage.onDisable();
    }
}
