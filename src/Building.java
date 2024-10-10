import java.io.Serial;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.io.Serializable;

public class Building implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static Building[][] gameFieldBuilding = new Building[10][10];
    public Map map;
    public Castle castle;
    public static int difficult;
    public static Building empty = new Building(1, 3);
    private int id;
    private int type;
    private String name;
    private String symbol; // Символ для обозначения юнита на карте
    private int health;
    private int remainingHealth;
    private int damage;
    private int damageRange;
    private boolean hit;
    private int cost;
    private int wood;
    private int stone;
    private boolean active; // Флаг, обозначающий активность юнита в текущем ходу
    private int team;

    public Building(int type, String name, String symbol, int remainingHealth, int health, int damage, int damageRange, int cost, int wood, int stone) {
        this.type = type;
        this.name = name;
        this.symbol = symbol;
        this.health = health;
        this.remainingHealth = remainingHealth;
        this.damage = damage;
        this.damageRange = damageRange;
        this.cost = cost;
        this.wood = wood;
        this.stone = stone;

    }

    public Building(int id, int team) {
        this.id = id;
        this.team = team;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getRemainingHealth() {
        return remainingHealth;
    }

    public void setRemainingHealth(int remainingHealth) {
        this.remainingHealth = remainingHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamageRange() {
        return damageRange;
    }

    public void setDamageRange(int damageRange) {
        this.damageRange = damageRange;
    }

    public boolean getHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getStone() {
        return stone;
    }

    public void setStone(int stone) {
        this.stone = stone;
    }

    public static void setTechObj() {
        //Метод генерации технических строений (замки, бонусный сундук, пустота)
        Random random = new Random();

        Building empty = new Building(1, 3);
        for (int i = 0; i < 10; i++) {
            for (int j = i; j < 10; j++) {
                gameFieldBuilding[i][j] = empty;
                gameFieldBuilding[j][i] = empty;
            }
        }
        Building myCastle = new Building(2, 1);
        myCastle.setHealth(500);
        myCastle.setRemainingHealth(myCastle.getHealth());
        myCastle.setSymbol("ПппП");
        myCastle.setName("Мой замок");
        gameFieldBuilding[7][2] = myCastle;
        Building oppCastle = new Building(3, 2);
        oppCastle.setHealth(500);
        oppCastle.setRemainingHealth(oppCastle.getHealth());
        oppCastle.setSymbol("ПппП");
        oppCastle.setName("Замок противника");
        gameFieldBuilding[2][7] = oppCastle;
    }

    public static boolean isItBuilding(int id) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameFieldBuilding[i][j].getID() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isItBuilding(int row, int col) {
        return gameFieldBuilding[row][col].getID() != 1;
    }


    public static Building getBuildingByID(int id) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameFieldBuilding[i][j].getID() == id) {
                    return gameFieldBuilding[i][j];
                }
            }
        }
        return gameFieldBuilding[5][5];
    }

    public static Building getBuildingByType(int type) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameFieldBuilding[i][j].getType() == type) {
                    return gameFieldBuilding[i][j];
                }
            }
        }
        return gameFieldBuilding[5][5];
    }

    public static int buildingPosX(Building Building) {
        // Метод находит юнита на игровом поле и возвращает его позицию по оси X
        int posX = 10;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameFieldBuilding[i][j] == Building) {
                    posX = j;
                    break;
                }
            }
        }
        return posX;
    }

    public static int buildingPosY(Building Building) {
        // Метод находит юнита на игровом поле и возвращает его позицию по оси Y
        int posY = 10;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameFieldBuilding[i][j] == Building) {
                    posY = i;
                    break;
                }
            }
        }
        return posY;
    }

    public static int buildingsCounter(int team) {
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameFieldBuilding[i][j].getTeam() == team) {
                    counter += 1;
                }
            }
        }
        return counter;
    }

    public static boolean canBuildHere(int row, int col) {
        if (Unit.gameFieldUnit[row][col].getID() == 1) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (!(row + i < 0 || row + i > 9 || col + j < 0 || col + j > 9)) {
                        if (gameFieldBuilding[row + i][col + j].getID() != 1 || Unit.gameFieldUnit[row + i][col + j].getID() == 2) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }


    // Методы использования строений

    public static void useSawmill(int team, int row, int col) {
        if (Objects.equals(Map.gameField[row][col], "Равнина")) {
            Castle.addWood(team, 10);
        } else {
            Castle.addWood(team, 5);
        }
    }

    public static void useQuarry(int team, int row, int col) {
        if (Objects.equals(Map.gameField[row][col], "Холм")) {
            Castle.addStone(team, 10);
        } else {
            Castle.addStone(team, 5);
        }
    }

    public static void useWorkshop(int team, int row, int col) {
        for (int i = row - 2; i <= row + 2; i++) {
            for (int j = col - 2; j <= col + 2; j++) {
                if (i >= 0 && i <= 10 && j >= 0 && j <= 10) {
                    if (gameFieldBuilding[i][j].getID() == 2 || gameFieldBuilding[i][j].getID() == 3) {
                        Castle.addGold(team, 20);
                        return;
                    }
                }
            }
        }
        Castle.addGold(team, 10);
    }

    public static void useHealingHouse(int team) {
        switch (team) {
            case 1 -> {
                for (int i = 0; i < MyUnit.myUnits.size(); i++) {
                    if (MyUnit.myUnits.get(i).getHealth() - MyUnit.myUnits.get(i).getRemainingHealth() > 10) {
                        MyUnit.myUnits.get(i).setRemainingHealth(MyUnit.myUnits.get(i).getRemainingHealth() + 10);
                        System.out.println(STR."Дом лекаря восстановил здоровье вашему юниту: \{MyUnit.myUnits.get(i).getName()} - \{MyUnit.myUnits.get(i).getRemainingHealth()} HP.");
                    }
                }
            }
            case 2 -> {
                for (int i = 0; i < MyUnit.oppUnits.size(); i++) {
                    if (MyUnit.oppUnits.get(i).getHealth() - MyUnit.oppUnits.get(i).getRemainingHealth() > 10) {
                        MyUnit.oppUnits.get(i).setRemainingHealth(MyUnit.oppUnits.get(i).getRemainingHealth() + 10);
                        System.out.println(STR."Дом лекаря восстановил здоровье вражескому юниту: \{MyUnit.oppUnits.get(i).getName()} - \{MyUnit.myUnits.get(i).getRemainingHealth()} HP.");
                    }
                }
            }
        }
    }

    public static void useTavern(int team) {
        switch (team) {
            case 1 -> {
                for (int i = 0; i < MyUnit.myUnits.size(); i++) {
                    MyUnit.myUnits.get(i).setRemainingMoves(MyUnit.myUnits.get(i).getRemainingMoves() + 1);
                    System.out.println(STR."Таверна повышает мораль вашего юнита \{MyUnit.myUnits.get(i).getName()}. Дальность пермещения +1 ");
                }
            }
            case 2 -> {
                for (int i = 0; i < MyUnit.oppUnits.size(); i++) {
                    MyUnit.oppUnits.get(i).setRemainingMoves(MyUnit.oppUnits.get(i).getRemainingMoves() + 1);
                    System.out.println(STR."Таверна повышает мораль важеского юнита \{MyUnit.oppUnits.get(i).getName()}. Дальность пермещения +1 ");
                }
            }
        }
    }

    public static void useBlacksmith(int team) {
        switch (team) {
            case 1 -> {
                for (int i = 0; i < MyUnit.myUnits.size(); i++) {
                    if (MyUnit.myUnits.get(i).getHealth() - MyUnit.myUnits.get(i).getRemainingHealth() > 10) {
                        MyUnit.myUnits.get(i).setDamage(MyUnit.myUnits.get(i).getDamage() + 5);
                        System.out.println(STR."Кузница повышает урон вашему юниту: \{MyUnit.myUnits.get(i).getName()}. Урон +5HP ");
                    }
                }
            }
            case 2 -> {
                for (int i = 0; i < MyUnit.oppUnits.size(); i++) {
                    if (MyUnit.oppUnits.get(i).getHealth() - MyUnit.oppUnits.get(i).getRemainingHealth() > 10) {
                        MyUnit.oppUnits.get(i).setDamage(MyUnit.oppUnits.get(i).getDamage() + 5);
                        System.out.println(STR."Кузница повышает урон вражескому юниту: \{MyUnit.oppUnits.get(i).getName()}. Урон +5HP ");
                    }
                }
            }
        }
    }

    public static void useArmory(int team) {
        switch (team) {
            case 1 -> {
                for (int i = 0; i < MyUnit.myUnits.size(); i++) {
                    if (MyUnit.myUnits.get(i).getHealth() - MyUnit.myUnits.get(i).getRemainingHealth() > 10) {
                        MyUnit.myUnits.get(i).setRemainingMoves(MyUnit.myUnits.get(i).getRemainingMoves() + 1);
                        System.out.println(STR."Арсенал обучает вашего юнита: \{MyUnit.myUnits.get(i).getName()}. +1 дополнительная контратака. ");
                    }
                }
            }
            case 2 -> {
                for (int i = 0; i < MyUnit.oppUnits.size(); i++) {
                    if (MyUnit.oppUnits.get(i).getHealth() - MyUnit.oppUnits.get(i).getRemainingHealth() > 10) {
                        MyUnit.oppUnits.get(i).setRemainingMoves(MyUnit.oppUnits.get(i).getRemainingMoves() + 1);
                        System.out.println(STR."Арсенал обучает вражеского юнита: \{MyUnit.oppUnits.get(i).getName()}. =1 дополнительная контратака. ");
                    }
                }
            }
        }
    }

    public static void useAcademy(int team) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вы в академии. Выберите юнита, которого хотите улучшить:");
        for (int i = 0; i < Castle.allUnits.size() - 1; i++) {
            System.out.println(STR."\{i + 1}. \{Castle.allUnits.get(i).getName()}");
        }
        System.out.println("Ваш выбор: ");
        int unitChoice = scanner.nextInt();
        System.out.println(STR."Стоимость улучшения вашего юнита \{Castle.allUnits.get(unitChoice).getName()}: \{
                Castle.allUnits.get(unitChoice).getCost() * 4} золота. \nПодтвердить покупку?\n1. Да\n2. Нет\nВаш выбор:");
        if (scanner.nextInt() == 1) {
            Unit BoostUnit = new Unit(Castle.allUnits.size() + 1, STR."Улучшенный\{Castle.allUnits.get(unitChoice).getName()}", Castle.allUnits.get(unitChoice).getSymbol(),
                    Castle.allUnits.get(unitChoice).getHealth() * 2, Castle.allUnits.get(unitChoice).getHealth() * 2,
                    Castle.allUnits.get(unitChoice).getDamage() * 2, Castle.allUnits.get(unitChoice).getDamageRange() + 1,
                    Castle.allUnits.get(unitChoice).getMoveRange() + 1, Castle.allUnits.get(unitChoice).getCost());
            Unit last = Castle.allUnits.getLast();
            Castle.allUnits.remove(last);
            Castle.allUnits.add(BoostUnit);
            Castle.allUnits.add(last);
        }
    }

    public static void useMarket(int team) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вы на рынке. Количество ваших ресурсов:");
        System.out.println(STR."Золото - \{Castle.getGold(team)}");
        System.out.println(STR."Древесина - \{Castle.getWood(team)}");
        System.out.println(STR."Камень - \{Castle.getStone(team)}");
        System.out.println("\nЧто вы хотели бы обменять (1 - золото, 2 - древесина, 3 - камень (0 - отмена):");
        int trade1 = scanner.nextInt();
        if (trade1 < 0 || trade1 > 3) {
            System.out.println("Неверные данные!");
            useMarket(team);
        } else if (trade1 == 0) { return; }
        System.out.println("\nНа что вы хотели бы обменять (1 - золото, 2 - древесина, 3 - камень (0 - отмена):");
        int trade2 = scanner.nextInt();
        if (trade1 < 0 || trade1 > 3) {
            System.out.println("Неверные данные!");
            useMarket(team);
        } else if (trade2 == 0) {
            return;
        } else if (trade1 == trade2) {
            System.out.println("Банк может посчитать такую транзакцию мошеннической...");
        } else {
            switch (trade1 + trade2) {
                case 5 -> {
                    if (trade2 == 2) {
                        System.out.println("Курс обмена камня к древесине: 2:1");
                        System.out.println("Введите количество ресурсов, которое хотите купить");
                        int tradeOffer = scanner.nextInt();
                        if (Castle.getStone(team) > tradeOffer * 2) {
                            Castle.addWood(team, tradeOffer);
                            Castle.setStone(team, Castle.getStone(team) - tradeOffer * 2);
                        } else {
                            System.out.println("У вас недостаточно ресурсов!");
                        }
                    } else {
                        System.out.println("Курс обмена древесины к камню: 2:1");
                        System.out.println("Введите количество ресурсов, которое хотите купить");
                        int tradeOffer = scanner.nextInt();
                        if (Castle.getWood(team) > tradeOffer * 2) {
                            Castle.addStone(team, tradeOffer);
                            Castle.setWood(team, Castle.getWood(team) - tradeOffer * 2);
                        } else {
                            System.out.println("У вас недостаточно ресурсов!");
                        }
                    }
                }
                case 4 -> {
                    if (trade2 == 3) {
                        System.out.println("Курс обмена золота к камню: 5:1");
                        System.out.println("Введите количество ресурсов, которое хотите купить");
                        int tradeOffer = scanner.nextInt();
                        if (Castle.getGold(team) > tradeOffer * 5) {
                            Castle.addStone(team, tradeOffer);
                            Castle.setGold(team, Castle.getGold(team) - tradeOffer * 5);
                        } else {
                            System.out.println("У вас недостаточно ресурсов!");
                        }
                    } else {
                        System.out.println("Курс обмена камня к золоту: 1:1");
                    }
                }
                case 3 -> {
                    if (trade2 == 2) {
                        System.out.println("Курс обмена золота к древесине: 5:1");
                        System.out.println("Введите количество ресурсов, которое хотите купить");
                        int tradeOffer = scanner.nextInt();
                        if (Castle.getGold(team) > tradeOffer * 5) {
                            Castle.addWood(team, tradeOffer);
                            Castle.setGold(team, Castle.getGold(1) - tradeOffer * 5);
                        } else {
                            System.out.println("У вас недостаточно ресурсов!");
                        }
                    } else {
                        System.out.println("Курс обмена камня к золоту: 1:1");
                    }
                }
            }
        }
        System.out.println("Обмен произведён!");
        useMarket(team);
    }

    public static void useTower(int team) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(STR."Вы в башне. Башня позволяет нанести \{getBuildingByType(10).getDamage()} урона всем вражеским бойцам в радиусе действия \{getBuildingByType(10).getDamageRange()} клеток(-ки)");
        System.out.println("Башню можно использовать 1 раз за ход, использовать сейчас?\n1 - Да\n0 - Нет");
        int choice = scanner.nextInt();
        if (choice == 1) {
            if (getBuildingByType(10).getHit()) {
                System.out.println("Вы уже использовали башню в этом ходу!");
            } else {
                attack(getBuildingByType(10));
            }
        } else if (choice == 0) {
            return;
        }else { System.out.println("Некорректные данные!"); }
    }

    public static void useBallista(int team) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(STR."Вы в баллисте. Баллиста позволяет нанести \{getBuildingByType(11).getDamage()} выбранному вражескому бойцу в радиусе действия \{getBuildingByType(11).getDamageRange()} клеток(-ки)");
        System.out.println("Баллисту можно использовать 1 раз за ход, использовать сейчас?\n1 - Да\n0 - Нет");
        int choice = scanner.nextInt();
        if (choice == 1) {
            if (getBuildingByType(11).getHit()) {
                System.out.println("Вы уже использовали баллисту в этом ходу!");
            } else {
                attack(getBuildingByType(11));
            }
        } else if (choice == 0) { return; }
        else { System.out.println("Некорректные данные!");}
    }

    private static int calculateDistance(Building activeUnit, Unit targetUnit) {
        // Логика вычисления расстояния, на основе их координат на карте
        // Возвращается целое число, обозначающее расстояние между юнитами
        return (Math.abs(buildingPosX(activeUnit) - Unit.unitPosX(targetUnit)) + Math.abs(buildingPosY(activeUnit) - Unit.unitPosY(targetUnit)));
    }

    public static boolean canAttack(Building activeUnit, Unit targetUnit) {
        int distance = calculateDistance(activeUnit, targetUnit); // Метод для вычисления расстояния между юнитом и строением
        return (distance <= activeUnit.damageRange);
    }

    public static void attack(Building building) {
        Scanner scanner = new Scanner(System.in);
        if (building.getType() == 10) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (Unit.gameFieldUnit[i][j].getTeam() != building.getTeam() && Unit.gameFieldUnit[i][j].getID() != 1 && canAttack(building, Unit.gameFieldUnit[i][j])) {
                        Unit.gameFieldUnit[i][j].setRemainingHealth(Unit.gameFieldUnit[i][j].getRemainingHealth() - building.getDamage());
                        System.out.println(STR."Строение \{building.getName()} наносит \{building.getDamage()} ед. урона бойцу \{Unit.gameFieldUnit[i][j].getName()}");
                        if (Unit.gameFieldUnit[i][j].getRemainingHealth() <= 0) {
                            System.out.println(STR."\{Unit.gameFieldUnit[i][j].getName()} пал судьбой храбрых.");
                            MyUnit.deleteUnit(Unit.gameFieldUnit[i][j]);
                        }
                    }
                }
            }
            building.setHit(false);
        } else if (building.getType() == 11) {
            System.out.print("Введите номер ряда (1-10) или 0 для завершения: ");
            int row = scanner.nextInt();
            if (row == 0) {
                return;
            }

            System.out.print("Введите номер столбца (1-10): ");
            int col = scanner.nextInt();

            // Проверка на валидность введенных данных
            if (row < 1 || row > 10 || col < 1 || col > 10) {
                System.out.println("Неверные координаты клетки!");
                return;
            } else {
                attack(building, Unit.gameFieldUnit[row][col]);
            }

        }
    }

    public static void attack(Building building, Unit targetUnit) {
        if (targetUnit.getTeam() != building.getTeam() && targetUnit.getID() != 1 && canAttack(building, targetUnit)) {
            targetUnit.setRemainingHealth(targetUnit.getRemainingHealth() - building.getDamage());
            System.out.println(STR."Строение \{building.getName()} наносит \{building.getDamage()} ед. урона бойцу \{targetUnit.getName()}");
            if (targetUnit.getRemainingHealth() <= 0) {
                System.out.println(STR."\{targetUnit.getName()} пал судьбой храбрых.");
                MyUnit.deleteUnit(targetUnit);
            }
        } else System.out.println("Неверные данные");
        building.setHit(false);
    }

    public static void useTechBuildings() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10;  j++) {
                int tech = 3;
                if (!Game.isChestBroken()) { tech += 1; }
                if (gameFieldBuilding[i][j].getID() > tech) {
                    switch (gameFieldBuilding[i][j].getType()) {
                        case 1 -> useSawmill(gameFieldBuilding[i][j].getTeam(), i, j);
                        case 2 -> useQuarry(gameFieldBuilding[i][j].getTeam(), i, j);
                        case 3 -> useWorkshop(gameFieldBuilding[i][j].getTeam(), i, j);
                        case 4 -> useHealingHouse(gameFieldBuilding[i][j].getTeam());
                        case 5 -> useTavern(gameFieldBuilding[i][j].getTeam());
                        case 6 -> useBlacksmith(gameFieldBuilding[i][j].getTeam());
                        case 7 -> useArmory(gameFieldBuilding[i][j].getTeam());
                    }
                }
            }
        }
    }
    public static void giveStats(Building Building, Building StatsDonor) {
        Building.name = StatsDonor.name;
        Building.type = StatsDonor.type;
        Building.health = StatsDonor.health;
        Building.remainingHealth = StatsDonor.health;
        Building.damage = StatsDonor.damage;
        Building.damageRange = StatsDonor.damageRange;
        if (StatsDonor.getType() >= 10) {
            Building.setHit(true);
        }
        if (Building.getTeam() == 1) { Building.symbol = StatsDonor.symbol; }
        else {
            // Разворот символов для юнита компьтютера
            var symbol = StatsDonor.getSymbol();
            StringBuilder stringBuilder = new StringBuilder(symbol);
            symbol = stringBuilder.reverse().toString();
            symbol = symbol.replaceAll("E", "Ǝ");
            symbol = symbol.replaceAll(">", "<");
            symbol = symbol.replaceAll("\\)", "(");
            symbol = symbol.replaceAll("\\(", ")");
            symbol = symbol.replaceAll("<", ">");
            symbol = symbol.replaceAll(">", "<");
            symbol = symbol.replaceAll("/", "\\");
            symbol = symbol.replaceAll("\\\\", "/");
            Building.symbol = symbol;

            // Здесь же реализована установка сложности
            Building.damage += (int) Math.round(Building.damage * Math.sqrt(difficult) / 4);
            Building.health += (int) Math.round(Building.health * Math.sqrt(difficult) / 4);
        }
    }
}
