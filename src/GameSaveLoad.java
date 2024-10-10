import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameSaveLoad {

    // Метод для сохранения игры
    public static void saveGame(Game game) {
        String saveDir = "saves";
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String savePath = saveDir + "/" + timestamp;

        try {
            // Создаем директорию для сохранений, если она не существует
            Files.createDirectories(Paths.get(saveDir));

            // Сохраняем игру в файл с именем по текущей дате и времени
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(savePath))) {
                out.writeObject(game);
                System.out.println(STR."Игра сохранена: \{savePath}");
            }

        } catch (IOException e) {
            System.err.println(STR."Ошибка при сохранении игры: \{e.getMessage()}");
        }
    }

    // Метод для загрузки сохраненной игры
    public static Game loadGame(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Game) in.readObject();  // Десериализуем объект Game
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(STR."Ошибка при загрузке игры: \{e.getMessage()}");
            return null;
        }
    }

    // Метод для получения списка сохраненных игр
    public static List<String> getSavedGames() {
        List<String> saveFiles = new ArrayList<>();
        File saveDir = new File("saves");

        if (saveDir.exists() && saveDir.isDirectory()) {
            for (File file : saveDir.listFiles()) {
                saveFiles.add(file.getName());
            }
        }
        return saveFiles;
    }

    public static List<String> getSavedMaps() {
        List<String> saveMaps = new ArrayList<>();
        File saveDir = new File("maps");

        if (saveDir.exists() && saveDir.isDirectory()) {
            for (File file : saveDir.listFiles()) {
                saveMaps.add(file.getName());
            }
        }
        return saveMaps;
    }

    public static void saveMap(String[][] map) {
        String saveDir = "maps";
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String savePath = saveDir + "/" + timestamp;

        try {
            // Создаем директорию для сохранений, если она не существует
            Files.createDirectories(Paths.get(saveDir));

            // Сохраняем игру в файл с именем по текущей дате и времени
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(savePath))) {
                out.writeObject(map);
                System.out.println(STR."Карта сохранена: \{savePath}");
            }

        } catch (IOException e) {
            System.err.println(STR."Ошибка при сохранении карты: \{e.getMessage()}");
        }
    }
}