package git.snowk.invincible.modules.storage;

import git.snowk.invincible.modules.crate.Crate;


public interface IStorage {

    void onLoad();

    void onDisable();

    void loadCrates();

    void saveCrate(Crate crate);

    void saveCrates();

    void removeCrate(Crate crate);

    //Airdrop
}
