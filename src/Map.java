import java.io.Serial;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.Serializable;

    public class Map implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        public static String[][] gameField;

        // Метод генерации местности игрового поля
        public static String[][] making() {
            String[][] gameField = new String[10][10]; // карта местности

            // Заполняем поле с учетом симметрии

            Random rand = new Random();
            for (int i = 0; i < 10; i++) {
                for (int j = i; j < 10; j++) {
                    int type = rand.nextInt(3);
                    switch (type) {
                        case 0:
                            gameField[i][j] = "Равнина";
                            gameField[j][i] = "Равнина";
                            break;
                        case 1:
                            gameField[i][j] = "Болото";
                            gameField[j][i] = "Болото";
                            break;
                        case 2:
                            gameField[i][j] = "Холм";
                            gameField[j][i] = "Холм";
                            break;
                    }
                }

            }
            return gameField;
        }

        // Метод возврата карты
        public static String[][] getMap() {
            return gameField;
        }

        public static void showing(String[][] gameField) {
            System.out.println("ИГРОВОЕ ПОЛЕ");
            System.out.println("\n___________________________________________________");
            for (int i = 0; i < 10; i++) {
                System.out.print("|");
                for (int j = 0; j < 10; j++) {
                    if (Unit.gameFieldUnit[i][j].getID() == 1) {
                        if (Building.gameFieldBuilding[i][j].getID() == 1) {
                            switch (gameField[i][j]) {
                                case "Равнина" -> System.out.print("____");
                                case "Болото" -> System.out.print("....");
                                case "Холм" -> System.out.print("_/\\_");
                            }
                        } else {
                            System.out.print(Building.gameFieldBuilding[i][j].getSymbol());
                        }
                    } else {
                        System.out.print(Unit.gameFieldUnit[i][j].getSymbol());
                    }
                    System.out.print("|");
                }
                System.out.print("\n");
            }
        }

        public static String[][] userMaking() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("""
                    Создание игрового поля
                    Выберите формат:
                    1 - Использовать готовый шаблон
                    2 - Создать карту вручную
                    3 - Загрузить карту
                    0 - Отмена
                    Ваш выбор:  """);
            String[][] gameField = new String[10][10];
            switch (scanner.nextInt()) {
                case 0 -> {
                    return making();
                }
                case 1 -> {
                    System.out.print("\nГотовые шаблоны:\n1 - Только равнина\n2 - Только болото\n3 - Творческая\nВаш выбор:");
                    switch (scanner.nextInt()) {
                        case 1 -> {
                            for (int i = 0; i < 10; i++) {
                                for (int j = i; j < 10; j++) {
                                    gameField[i][j] = "Равнина";
                                    gameField[j][i] = "Равнина";
                                }
                            }
                        }
                        case 2 -> {
                            for (int i = 0; i < 10; i++) {
                                for (int j = i; j < 10; j++) {
                                    gameField[i][j] = "Болото";
                                    gameField[j][i] = "Болото";

                                }
                            }
                        }
                        case 3 -> {
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 10; j++) {
                                    if (Math.abs(i - j) <= 1) {
                                        gameField[i][j] = "Холм";
                                        gameField[j][i] = "Холм";
                                    } else if (Math.abs(i - j) == 2) {
                                        gameField[i][j] = "Болото";
                                        gameField[j][i] = "Болото";
                                    } else {
                                        gameField[j][i] = "Равнина";
                                        gameField[j][i] = "Равнина";
                                    }
                                }
                            }
                        }
                    }
                }
                case 2 -> {
                    for (int i = 0; i < 10; i++) {
                        for (int j = i; j < 10; j++) {
                            if (i == j) {
                                System.out.println(STR."Заполнение клетки [\{i}] [\{j}]");
                            } else {
                                System.out.println(STR."Заполнение клеток [\{i}] [\{j}] и [\{j}] [\{i}]");
                            }
                            System.out.print("1 - Равнина, 2 - Болото, 3 - Холм\nВаш выбор:");
                            int userType = scanner.nextInt();
                            switch (userType) {
                                case 1:
                                    gameField[i][j] = "Равнина";
                                    gameField[j][i] = "Равнина";
                                    break;
                                case 2:
                                    gameField[i][j] = "Болото";
                                    gameField[j][i] = "Болото";
                                    break;
                                case 3:
                                    gameField[i][j] = "Холм";
                                    gameField[j][i] = "Холм";
                                    break;
                            }
                        }
                    }
                }
                case 3 -> {
                    loadSavedMap();
                }
            }
            System.out.println("Игровое поле создано!");
            System.out.println("\n___________________________________________________");
            for (int i = 0; i < 10; i++) {
                System.out.print("|");
                for (int j = 0; j < 10; j++) {
                            switch (gameField[i][j]) {
                                case "Равнина" -> System.out.print("____");
                                case "Болото" -> System.out.print("....");
                                case "Холм" -> System.out.print("_/\\_");
                            }
                    System.out.print("|");
                }
                System.out.print("\n");
            }
            System.out.print("\n1 - Играть на выбранном шаблоне\n 2 - Сохранить карту\n3 - Играть на случайной карте\nОтвет: ");
            switch (scanner.nextInt()) {
                case 1 -> {
                    return gameField;
                }
                case 2 -> {
                    GameSaveLoad.saveMap(gameField);
                    System.exit(0);
                }
                case 3 -> {
                    return making();
                }
            }
            return making();
        }

        public static void loadSavedMap() {
            List<String> savedMaps = GameSaveLoad.getSavedMaps();
            if (savedMaps.isEmpty()) {
                System.out.println("Нет сохраненных карт.");
                return;
            }

            // Выводим список сохраненных игр
            System.out.println("Выберите сохраненную карту для загрузки:");
            for (int i = 0; i < savedMaps.size(); i++) {
                System.out.println(STR."\{i + 1}. \{savedMaps.get(i)}");
            }

            // Пользователь выбирает шаблон
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            if (choice > 0 && choice <= savedMaps.size()) {
                String selectedSave = STR."maps/\{savedMaps.get(choice - 1)}";
                Game loadedMap = GameSaveLoad.loadGame(selectedSave);
                if (loadedMap != null) {
                    System.out.println("Карта загружена!");
                    loadedMap.startingGame(gameField);  // Возвращаемся в процесс игры
                }
            } else {
                System.out.println("Некорректный выбор.");
            }
        }
    }
