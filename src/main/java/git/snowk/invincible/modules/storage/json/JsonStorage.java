package git.snowk.invincible.modules.storage.json;

import com.google.gson.Gson;
import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.storage.IStorage;
import git.snowk.invincible.utils.FileUtils;

import java.io.File;
import java.util.Map;


public class JsonStorage implements IStorage {

    private final Gson gson;
    private final File cratesDir;

    public JsonStorage(Gson gson) {
        this.gson = gson;

        this.cratesDir = new File(Invincible.getInstance().getDataFolder(), "crates");

        if (!this.cratesDir.exists()) {
            this.cratesDir.mkdirs();
        }
    }

    @Override
    public void onLoad() {
        this.loadCrates();
    }

    @Override
    public void onDisable() {
        this.saveCrates();
    }

    @Override
    public void loadCrates() {
        File[] files = this.cratesDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            String content = FileUtils.readWholeFile(file);
            if (content == null) continue;
            Map<String, Object> crateMap = this.gson.fromJson(content, Map.class);
            Crate crate = new Crate(crateMap);
            Invincible.getInstance().getCrateManager().addCrate(crate);
        }

    }

    @Override
    public void saveCrate(Crate crate) {
        File file = FileUtils.getOrCreateFile(this.cratesDir, crate.getCrateName().toLowerCase() + ".json");
        String crateToJson = this.gson.toJson(crate.serialize());
        FileUtils.writeString(file, crateToJson);
    }

    @Override
    public void saveCrates() {
        for (Crate crate : Invincible.getInstance().getCrateManager().getCrates().values()) {
            this.saveCrate(crate);
        }
    }

    @Override
    public void removeCrate(Crate crate) {
        File file = new File(this.cratesDir, crate.getCrateName().toLowerCase() + ".json");
        if (file.exists()) {
            file.delete();
        }
    }
}
