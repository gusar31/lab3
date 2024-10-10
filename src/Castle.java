import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;
import java.util.ArrayList;

public class Castle implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static int myGold = 100;
    public static int oppGold = 100;
    public static int myWood = 0;
    public static int oppWood = 0;
    public static int myStone = 0;
    public static int oppStone = 0;
    public static ArrayList<Unit> allUnits;
    public static ArrayList<Building> allBuildings;
    public Castle() {
        allUnits = new ArrayList<>();

        Unit villagerPitchfork = new Unit(1, "Крестьянин с вилами", "---E", 20, 20, 5, 1, 1, 10);
        allUnits.add(villagerPitchfork);

        Unit swordsman = new Unit(2, "Мечник", "-+--", 50, 50, 20, 1, 2, 30);
        allUnits.add(swordsman);

        Unit spearman = new Unit(3, "Копейщик", "->--", 40, 40, 15, 2, 2, 30);
        allUnits.add(spearman);

        Unit archer = new Unit(4, "Лучник", "(|->", 20, 20, 15, 3, 1, 20);
        allUnits.add(archer);

        Unit crossbowmen = new Unit(5, "Арбалетчик", "-&->", 30, 30, 20, 4, 1, 30);
        allUnits.add(crossbowmen);

        Unit horseman = new Unit(6, "Всадник", "(-)\"", 40, 40, 15, 1, 3, 30);
        allUnits.add(horseman);

        Unit cavalier = new Unit(7, "Кавалерист", "(=)\"", 50, 50, 20, 2, 4, 40);
        allUnits.add(cavalier);

        Unit gigachad = new Unit(420, "Гигачад", "(:0)", 500, 500, 500, 50, 50, 10);
        allUnits.add(gigachad);
        // инициализация "эталонных" юнитов

        allBuildings = new ArrayList<>();

        Building sawmill = new Building(1, "Лесопилка", "/-7\\", 100, 100, 0, 0, 50, 0, 0);
        allBuildings.add(sawmill);

        Building quarry = new Building(2, "Каменоломня", "/-}\\", 100, 100, 0, 0, 50, 0, 0);
        allBuildings.add(quarry);

        Building workshop = new Building(3, "Ремесленная мастерская", "/$+\\", 50, 50, 10, 4, 30, 10, 5);
        allBuildings.add(workshop);

        Building healingHouse = new Building(4, "Дом лекаря", "/++\\", 100, 100, 0, 0, 50, 10, 0);
        allBuildings.add(healingHouse);

        Building tavern = new Building(5, "Теверна", "/^^\\", 100, 100, 0, 0, 30, 10, 10);
        allBuildings.add(tavern);

        Building blacksmith = new Building(6, "Кузница", "/+-\\", 100, 100, 0, 0, 30, 10, 10);
        allBuildings.add(blacksmith);

        Building armory = new Building(7, "Арсенал", "/\\", 100, 100, 0, 0, 30, 10, 10);
        allBuildings.add(armory);

        Building academy = new Building(8, "Академия", "/+-\\", 100, 100, 0, 0, 30, 10, 10);
        allBuildings.add(academy);

        Building market = new Building(9, "Рынок", "/$$\\", 50, 50, 0, 0, 30, 0, 0);
        allBuildings.add(market);

        Building tower = new Building(10, "Башня", "/(|\\", 150, 150, 10, 4, 30, 20, 10);
        allBuildings.add(tower);

        Building ballista = new Building(11, "Баллиста", "/M-\\", 150, 150, 30, 4, 30, 20, 10);
        allBuildings.add(ballista);

    }


    public static void addGold(int team, int gold) {
        switch (team) {
            case 1 -> myGold += gold;
            case 2 -> oppGold += gold;
        }
    }

    public static void setGold(int team, int gold) {
        switch (team) {
            case 1 -> myGold = gold;
            case 2 -> oppGold = gold;
        }
    }

    public static int getGold(int team) {
        if (team == 1) {
                return myGold;
        } else {
                return oppGold;
        }
    }

    public static void addWood(int team, int wood) {
        switch (team) {
            case 1 -> myWood += wood;
            case 2 -> oppWood += wood;
        }
    }

    public static void setWood(int team, int wood) {
        switch (team) {
            case 1 -> myWood = wood;
            case 2 -> oppWood = wood;
        }
    }

    public static int getWood(int team) {
        if (team == 1) {
            return myWood;
        } else {
            return oppWood;
        }
    }

    public static void addStone(int team, int stone) {
        switch (team) {
            case 1 -> myStone += stone;
            case 2 -> oppStone += stone;
        }
    }

    public static void setStone(int team, int stone) {
        switch (team) {
            case 1 -> myStone = stone;
            case 2 -> oppStone = stone;
        }
    }

    public static int getStone(int team) {
        if (team == 1) {
            return myStone;
        } else {
            return oppStone;
        }
    }

    public void enterCastle(String[][] gameField) {
        while (true) {
            System.out.println(STR."Вы в замке, количество ваших ресурсов: \n\{myGold} ед. золота;\n\{myWood} ед. древесины;\n\{myStone} ед. камня;");
            System.out.println("Выберите действие:");
            Main.waiting(100);
            System.out.println("1 - Купить юнита");
            Main.waiting(100);
            System.out.println("2 - Возвести строение");
            Main.waiting(100);
            System.out.println("0 - Выйти из замка");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            if (choice == 1) {
                buyUnit(gameField);
            } else if (choice == 2) {
                buyBuilding(gameField);
            } else if (choice == 0) {
                Main.waiting(100);
                System.out.println("Вы покидаете замок.");
                break;
            } else {
                System.out.println("Неверный выбор.");
                Main.waiting(100);
            }
        }
    }

    private void buyUnit(String[][] gameField) {

        while (true) {
            Scanner scanner = new Scanner(System.in);
//            System.out.println("Доступные юниты для покупки:");
//            Main.waiting(100);
//            for (int i = 0; i < allUnits.size(); i++) {
//                Main.waiting(100);
//                System.out.println((i + 1) + ". " + allUnits.get(i).getName()
//                        + " (Цена: " + allUnits.get(i).getCost() + " золотых)");
//            }
            System.out.print("Выберите класс юнита: 1 - пехота, 2 - стрелки, 3 - наездники");
            if (allUnits.size() > 8) {
                System.out.print("4 - улучшенные юниты ");
            }
            System.out.print("(0 - отмена): ");
            int unitTypeChoice = scanner.nextInt();
            if (unitTypeChoice >= 0 && unitTypeChoice <= 4) {
                if (unitTypeChoice == 0) {
                    break;
                } else {
                    System.out.println("Доступные юниты для покупки:");
                    switch (unitTypeChoice) {
                        case 1 -> {
                            for (int i = 0; i <= 2; i++) {
                                System.out.println(STR."Код юнита: \{i + 1}. \{allUnits.get(i).
                                        getName()} (Цена: \{allUnits.get(i).getCost()} золотых)");
                            }
                        }
                        case 2 -> {
                            for (int i = 3; i <= 4; i++) {
                                    System.out.println(STR."Код юнита: \{i + 1}. \{allUnits.get(i).
                                            getName()} (Цена: \{allUnits.get(i).getCost()} золотых)");
                            }
                        }
                        case 3 -> {
                            for (int i = 5; i <= 6; i++) {
                                    System.out.println(STR."Код юнита: \{i + 1}. \{allUnits.get(i).
                                            getName()} (Цена: \{allUnits.get(i).getCost()} золотых)");
                            }
                        }
                        case 4 -> {
                            for (int i = 7; i <= allUnits.size() - 1; i++) {
                                System.out.println(STR."Код юнита: \{i + 1}. \{allUnits.get(i).
                                        getName()} (Цена: \{allUnits.get(i).getCost()} золотых)");
                            }
                        }
                    }
                }
            } else {
                System.out.println("Неверный выбор.");
                Main.waiting(100);
                break;
            }

            System.out.println("Введите код юнита (0 - отмена): ");
            int unitChoice = scanner.nextInt();
            if (unitChoice >= 0 && unitChoice <= allUnits.size()) {
                if (unitChoice == 0) {
                    break;
                }
                unitChoice -= 1;
                System.out.println(STR."Код юнита: \{unitChoice + 1}. \{allUnits.get(unitChoice).getName()}");
                System.out.println(STR."Здоровье: \{allUnits.get(unitChoice).getHealth()} HP.");
                if (allUnits.get(unitChoice).getMoveRange() != 1) {
                    System.out.println(STR."Дальность перемещения: \{allUnits.get(unitChoice).getMoveRange()} клетки.");
                } else {
                    System.out.println(STR."Дальность перемещения: \{allUnits.get(unitChoice).getMoveRange()} клетка.");
                }
                System.out.println(STR."Урон: \{allUnits.get(unitChoice).getDamage()} HP.");
                if (allUnits.get(unitChoice).getDamageRange() != 1) {
                    System.out.println(STR."Дальность нанесения урона: \{allUnits.get(unitChoice).getDamageRange()} клетки.");
                } else {
                    System.out.println(STR."Дальность нанесения урона: \{allUnits.get(unitChoice).getDamageRange()} клетка.");
                }
                System.out.println(STR."Цена: \{allUnits.get(unitChoice).getCost()} золотых.");
                Unit selectedUnit = allUnits.get(unitChoice);
                System.out.println("1. Купить юнита, 0. Отмена");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    if (selectedUnit.getCost() <= myGold) {
                        myGold -= selectedUnit.getCost();
                        // здесь нужно вашей логики для добавления выбранного юнита в игру
                        System.out.println(STR."Юнит \{allUnits.get(unitChoice).getName()} приобретен.");
                        MyUnit.spawnUnit(allUnits.get(unitChoice), 1);
                        Map.showing(gameField);
                        break;
                    } else {
                        System.out.println("Недостаточно золота для покупки этого юнита.");
                    }
                } else if (choice == 0) {
                    break;
                }
            } else {
                System.out.println("Неверный выбор.");
                Main.waiting(100);
            }
        }
    }

    private void buyBuilding(String[][] gameField) {

        while (true) {
            Scanner scanner = new Scanner(System.in);
//            System.out.println("Доступные юниты для покупки:");
//            Main.waiting(100);
//            for (int i = 0; i < allUnits.size(); i++) {
//                Main.waiting(100);
//                System.out.println((i + 1) + ". " + allUnits.get(i).getName()
//                        + " (Цена: " + allUnits.get(i).getCost() + " золотых)");
//            }
            System.out.print("Выберите тип строения: 1 - добывающие, 2 - технические, 3 - оборонительные (0 - отмена): ");
            int buildingTypeChoice = scanner.nextInt();
            if (buildingTypeChoice >= 0 && buildingTypeChoice <= 3) {
                if (buildingTypeChoice == 0) {
                    break;
                } else {
                    System.out.println("Доступные строения:");
                    switch (buildingTypeChoice) {
                        case 1 -> {
                            for (int i = 0; i <= 2; i++) {
                                System.out.print(STR."Код строения: \{i + 1}. \{allBuildings.get(i).
                                        getName()} (Стоимость:  Золото - \{allBuildings.get(i).getCost()} ед.");
                                if (allBuildings.get(i).getWood() > 0 || allBuildings.get(i).getStone() > 0) {
                                    if (allBuildings.get(i).getWood() > 0) {
                                        System.out.print(STR.", Древесина - \{allBuildings.get(i).getWood()} ед.");
                                    } if (allBuildings.get(i).getStone() > 0) {
                                        System.out.print(STR.", Камень - \{allBuildings.get(i).getStone()} ед.");
                                    }
                                }
                                System.out.println(")");
                            }
                        }

                        case 2 -> {
                            for (int i = 3; i <= 8; i++) {
                                System.out.print(STR."Код строения: \{i + 1}. \{allBuildings.get(i).
                                        getName()} (Стоимость:  Золото - \{allBuildings.get(i).getCost()} ед.");
                                if (allBuildings.get(i).getWood() > 0 || allBuildings.get(i).getStone() > 0) {
                                    if (allBuildings.get(i).getWood() > 0) {
                                        System.out.print(STR.", Древесина - \{allBuildings.get(i).getWood()} ед.");
                                    } if (allBuildings.get(i).getStone() > 0) {
                                        System.out.print(STR.", Камень - \{allBuildings.get(i).getStone()} ед.");
                                    }
                                }
                                System.out.println(")");
                            }
                        }
                        case 3 -> {
                            for (int i = 9; i <= 10; i++) {
                                System.out.print(STR."Код строения: \{i + 1}. \{allBuildings.get(i).
                                        getName()} (Стоимость:  Золото - \{allBuildings.get(i).getCost()} ед.");
                                if (allBuildings.get(i).getWood() > 0 || allBuildings.get(i).getStone() > 0) {
                                    if (allBuildings.get(i).getWood() > 0) {
                                        System.out.print(STR.", Древесина - \{allBuildings.get(i).getWood()} ед.");
                                    } if (allBuildings.get(i).getStone() > 0) {
                                        System.out.print(STR.", Камень - \{allBuildings.get(i).getStone()} ед.");
                                    }
                                }
                                System.out.println(")");
                            }
                        }
                    }
                }
            } else {
                System.out.println("Неверный выбор.");
                Main.waiting(100);
                break;
            }

            System.out.println("Введите код строения (0 - отмена): ");
            int buildingChoice = scanner.nextInt();
            if (buildingChoice >= 0 && buildingChoice <= allBuildings.size()) {
                if (buildingChoice == 0) {
                    break;
                }
                buildingChoice -= 1;
                System.out.println(STR."Код строения: \{buildingChoice + 1}. \{allBuildings.get(buildingChoice).getName()}");
                System.out.println(STR."Здоровье: \{allBuildings.get(buildingChoice).getHealth()} HP.");
                if (allBuildings.get(buildingChoice).getDamage() > 0) {
                    System.out.println(STR."Урон: \{allBuildings.get(buildingChoice).getDamage()} HP.");
                    if (allBuildings.get(buildingChoice).getDamageRange() != 1) {
                        System.out.println(STR."Дальность нанесения урона: \{allBuildings.get(buildingChoice).getDamageRange()} клетки.");
                    } else {
                        System.out.println(STR."Дальность нанесения урона: \{allBuildings.get(buildingChoice).getDamageRange()} клетка.");
                    }
                }
                System.out.print("Особенности: ");
                switch (allBuildings.get(buildingChoice).getType()) {
                    case 1 -> System.out.println("Добыча древесины (5 / ход), если лесопилка расположена на равнине - количество добываемой древесины удваивается!");
                    case 2 -> System.out.println("Добыча камня (5 / ход), если каменоломня расположена на холме - количество добываемого камня удваивается!");
                    case 3 -> System.out.println("Добыча золота (10 / ход), если мастерская расположена в окрестностях замка (2 кл.) - количество добываемого золота удваивается!");
                    case 4 -> System.out.println("Восстановление здоровья союзным юнитам (10HP / ход");
                    case 5 -> System.out.println("Увеличение дальности перемещения союзных юнитов (+1MP / ход)");
                    case 6 -> System.out.println("Увеличение атаки союзных юнитов (+5HP / ход)");
                    case 7 -> System.out.println("Получение дополнительной контратаки союзным юнитам (+1 / ход)");
                    case 8 -> System.out.println("Создание улучшенных юнитов");
                    case 9 -> System.out.println("Покупка и продажа ресурсов");
                    case 10 -> System.out.println("Нанечение урона всем отрядам противника в радиусе действия");
                    case 11 -> System.out.println("Нанечение урона выбранному отряду противника в радиусе действия");


                }
                System.out.print("Цена: ");
                if (allBuildings.get(buildingChoice).getCost() > 0) {
                    System.out.print(STR."\{allBuildings.get(buildingChoice).getCost()} золота. ");
                }
                if (allBuildings.get(buildingChoice).getWood() > 0) {
                    System.out.print(STR."\{allBuildings.get(buildingChoice).getWood()} дерева. ");
                }
                if (allBuildings.get(buildingChoice).getWood() > 0) {
                    System.out.println(STR."\{allBuildings.get(buildingChoice).getWood()} камня.");
                }
                Building selectedBuilding = allBuildings.get(buildingChoice);
                System.out.println("\n1. Купить строение, 0. Отмена");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    if (selectedBuilding.getCost() <= myGold && selectedBuilding.getWood() <= myWood && selectedBuilding.getStone() <= myStone) {
                        System.out.println("Выберите точку, где будет возведено строение");
                        Map.showing(gameField);
                        System.out.println("Выберите точку, где будет возведено строение (на своей половине поля):");
                        System.out.print("Введите номер ряда (1-10) или 0 для завершения: ");
                        int row = scanner.nextInt();
                        if (row == 0) {
                            break;
                        }
                        System.out.print("Введите номер столбца (1-10): ");
                        int col = scanner.nextInt();
                        if (col < 0 || col > 10 || row < 0 || row > 10) {
                            System.out.print("Введены некорректные данные!");
                            break;
                        }
                        if (col >= row) {
                            System.out.print("Вы не можете строить здания на чужой половине поля!");
                        } else if (!Building.canBuildHere(row - 1, col - 1)) {
                            System.out.print("Вы не можете здесь строить!");
                        } else {
                            myGold -= selectedBuilding.getCost();
                            myWood -= selectedBuilding.getWood();
                            myStone -= selectedBuilding.getStone();
                            // здесь нужно вашей логики для добавления выбранного юнита в игру
                            System.out.println(STR."Строение \{allBuildings.get(buildingChoice).getName()} построено.");
                            MyBuilding.spawnBuilding(allBuildings.get(buildingChoice), 1, row - 1, col - 1);
                            Map.showing(gameField);
                            break;
                        }
                    } else {
                        System.out.println("Недостаточно ресурсов для постройки этого строения.");
                    }
                } else if (choice == 0) {
                    break;
                } else { System.out.println("Неверный выбор."); }
            } else {
                System.out.println("Неверный выбор.");
                Main.waiting(100);
            }
        }
    }


}