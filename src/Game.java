import java.io.Serial;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.io.Serializable;

public class Game implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public String[][] gameField;
    public Unit[][] gameFieldUnit;
    public Building[][] gameFieldBuilding;
    private boolean myTurn = true; // Поле для отслеживания хода игрока
    private static Castle playerCastle;
    private static Castle opponentCastle;
    public Random random;
    public static int difficult;
    public static int bonus = 0;
    public static int bonusNum = 0;

    public Game() {
        gameField = new String[10][10];
        gameFieldUnit = new Unit[10][10];
        gameFieldBuilding = new Building[10][10];
        playerCastle = new Castle();
        opponentCastle = new Castle();
        random = new Random();
    }

    private void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    private boolean isMyTurn() {
        return myTurn;
    }
    // Метод установки сложности
    public static void setDifficult() {
        System.out.print("Выберите сложность:\n1. Лёгкая;\n2. Средняя;\n3. Сложная;\n4. Кошмарная!\nВведите число:");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            difficult = scanner.nextInt();
            if (1 <= difficult & difficult <= 4) {
                break;
            } else {
                System.out.print("Указано неверное значение сложности, попробуйте ещё раз!\nВаш выбор:");
            }
        }

    }

        public void myTurn(String[][] gameField) {
            Main.skip();  // Пропуск хода или другие игровые действия
            System.out.print("Ваш ход!\n");
            Main.waiting(100);  // Ожидание

            while (isMyTurn()) {
                Map.showing(gameField);  // Показ игровой карты
                Scanner scanner = new Scanner(System.in);
                System.out.print("Выберите действие: 1 - посмотреть информацию, 2 - войти в замок, 3 - выбрать героя," +
                        " 4 - использовать строение, 5 - сохранить игру, 0 - завершить ход): ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 0 -> setMyTurn(false);  // Завершение хода
                    case 1 -> {
                        while (true) {
                            System.out.print("Введите номер ряда (1-10) или 0 для завершения: ");
                            int row = scanner.nextInt();
                            if (row == 0) {
                                break;
                            }

                            System.out.print("Введите номер столбца (1-10): ");
                            int col = scanner.nextInt();

                            // Проверка на валидность введенных данных
                            if (row < 1 || row > 10 || col < 1 || col > 10) {
                                System.out.println("Неверные координаты клетки!");
                                break;
                            }

                            String terrain = gameField[row - 1][col - 1];

                            System.out.println(STR."Тип местности: \{terrain}");
                            switch (terrain) {
                                case "Равнина" -> System.out.println("Особенности: Нет");
                                case "Болото" -> System.out.println("Особенности: Перемещение -1");
                                case "Холм" -> System.out.println("Особенности: Дальность атаки +1");
                            }

                            // Логика для отображения юнита на карте
                            if (Unit.gameFieldUnit[row - 1][col - 1] != null) {
                                switch (Unit.gameFieldUnit[row - 1][col - 1].getTeam()) {
                                    case 1 -> System.out.println(STR."Объект: Ваш \{Unit.gameFieldUnit[row - 1][col - 1].
                                            getName()}.\nЗдоровье: \{Unit.gameFieldUnit[row - 1][col - 1].
                                            getHealth()} (\{Unit.gameFieldUnit[row - 1][col - 1].
                                            getRemainingHealth()}). \nПеремещение: \{Unit.gameFieldUnit[row - 1][col - 1].
                                            getMoveRange()} (\{Unit.gameFieldUnit[row - 1][col - 1].
                                            getRemainingMoves()}). \nУрон: \{Unit.gameFieldUnit[row - 1][col - 1].getDamage()}");
                                    case 2 -> System.out.println(STR."Объект: \{Unit.gameFieldUnit[row - 1][col - 1].
                                            getName()} противника.\nЗдоровье: \{Unit.gameFieldUnit[row - 1][col - 1].
                                            getHealth()} (\{Unit.gameFieldUnit[row - 1][col - 1].getRemainingHealth()}). \nПеремещение: \{Unit.
                                            gameFieldUnit[row - 1][col - 1].getMoveRange()} (\{Unit.gameFieldUnit[row - 1][col - 1].
                                            getRemainingMoves()}). \nУрон: \{Unit.gameFieldUnit[row - 1][col - 1].getDamage()}");
                                }
                            }

                            // Логика для отображения строения на карте
                            if (Building.gameFieldBuilding[row - 1][col - 1] != null) {
                                switch (Building.gameFieldBuilding[row - 1][col - 1].getTeam()) {
                                    case 1 -> System.out.println(STR."Ваше строение: \{Building.gameFieldBuilding[row - 1][col - 1].
                                            getName()}.\nЗдоровье: \{Building.gameFieldBuilding[row - 1][col - 1].
                                            getHealth()} (\{Building.gameFieldBuilding[row - 1][col - 1].
                                            getRemainingHealth()}). \nУрон: \{Building.gameFieldBuilding[row - 1][col - 1].getDamage()}");
                                    case 2 -> System.out.println(STR."Вражеское строение: \{Building.gameFieldBuilding[row - 1][col - 1].
                                            getName()}.\nЗдоровье: \{Building.gameFieldBuilding[row - 1][col - 1].getHealth()} (\{Building.
                                            gameFieldBuilding[row - 1][col - 1].getRemainingHealth()}). \nУрон: \{Building.
                                            gameFieldBuilding[row - 1][col - 1].getDamage()}");
                                }
                            }
                        }
                    }
                    case 2 -> playerCastle.enterCastle(gameField);  // Логика для входа в замок
                    case 3 -> {
                        // Логика для выбора героя
                        Map.showing(gameField);
                        System.out.println("Введите ID юнита. Доступные юниты:");
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                if (Unit.gameFieldUnit[i][j].getTeam() == 1) {
                                    System.out.println(STR."\{Unit.gameFieldUnit[i][j].getID()}. \{Unit.
                                            gameFieldUnit[i][j].getName()} [ \{i + 1} ][ \{j + 1} ];");
                                }
                            }
                        }
                        System.out.println("ID юнита:");
                        int unitChoice = scanner.nextInt();
                        if (Unit.unitOnDesk(unitChoice) && Unit.getUnitByID(unitChoice).getTeam() != 2) {
                            System.out.println("Доступные действия: \n1. Атака \n2. Перемещение \n3. Укрепление позиций\n0. Отмена\nВведите номер действия:");
                            int unitChoice1 = scanner.nextInt();
                            if (unitChoice1 == 1) {
                                Unit.showTargets(Unit.getUnitByID(unitChoice));
                                System.out.println("Введите ID юнита(0 - отмена):");
                                int targetID = scanner.nextInt();
                                if (targetID == 0) {
                                    break;
                                } else if (Unit.unitOnDesk(targetID)) {
                                    Unit.attack(Unit.getUnitByID(unitChoice), Unit.getUnitByID(targetID));
                                    if (isChestBroken() && bonusNum == 0) {
                                        getBonus(1);
                                    }
                                } else if (Unit.buildingOnDesk(targetID)) {
                                    Unit.attack(Unit.getUnitByID(unitChoice), Building.getBuildingByID(targetID));
                                    if (!isGameNotOver()) {
                                        Main.endingPage(1);
                                    }
                                } else { System.out.println("Введён неверный ID!"); }
                            } else if (unitChoice1 == 2) {
                                Unit.moving(Unit.getUnitByID(unitChoice));
                            } else if (unitChoice1 == 3) {
                                Unit.healing(Unit.getUnitByID(unitChoice));
                            }
                        } else {
                            System.out.println("Введён неверный ID!");
                        }
                        // Логика обработки выбора и действия с юнитом
                    }
                    case 4 -> {
                        // Логика для использования строений
                        Map.showing(gameField);
                        System.out.println("Введите ID строения. Доступные строения:");
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                if (Building.gameFieldBuilding[i][j].getTeam() == 1 &&
                                        Building.gameFieldBuilding[i][j].getID() != 2) {
                                    System.out.println(STR."\{Building.gameFieldBuilding[i][j].getID()}. \{Building.
                                            gameFieldBuilding[i][j].getName()} [ \{i + 1} ][ \{j + 1} ];");
                                }
                            }
                        }
                        // Логика обработки действия с выбранным строением
                    }
                    case 5 -> GameSaveLoad.saveGame(this);  // Сохранение игры
                }
            }
            // Логика для хода игрока
        }

    public void oppTurn(String[][] gameField) {
        int counter1;
        setMyTurn(false);
        while (!isMyTurn()) {
            counter1 = 0;
            System.out.println("Ход противника! " + Castle.getGold(2));
            if (Unit.unitsCounter(2) >= 2) {
                if (Unit.unitsCounter(1) > 3 && Castle.getGold(2) >= 40) {
                    MyUnit.spawnUnit(Castle.allUnits.get(4), 2);
                    Castle.addGold(2, -20);
                    Main.skip();
                    Map.showing(gameField);
                    Main.waiting(200);
                    counter1 += 1;
                }
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (Unit.gameFieldUnit[i][j].getTeam() == 2 && Unit.gameFieldUnit[i][j].getHit()) {
                            Unit activeUnit = Unit.gameFieldUnit[i][j];
                            int targX = 0;
                            int targY = 0;
                            for (int row = 0; row < 10; row++) {
                                for (int col = 0; col < 10; col++) {
                                    if (Unit.gameFieldUnit[row][col].getID() != 1 && Unit.gameFieldUnit[row][col].getTeam() != 2 && Building.gameFieldBuilding[row][col].getTeam() != 2) {
                                        if (Unit.canAttack(activeUnit, Unit.gameFieldUnit[row][col]) || Unit.canAttack(activeUnit, Building.gameFieldBuilding[row][col])) {
                                            counter1 += 1;
                                            if (!Building.isItBuilding(row, col)) {
                                                if (Unit.gameFieldUnit[row][col].getHealth() <= activeUnit.getDamage()) {
                                                    Unit.attack(activeUnit, Unit.gameFieldUnit[row][col]);
                                                } else if (Unit.gameFieldUnit[row][col].getRevenge() == 0) {
                                                    Unit.attack(activeUnit, Unit.gameFieldUnit[row][col]);
                                                } else if (Unit.gameFieldUnit[row][col].getDamageRange() > 1) {
                                                    Unit.attack(activeUnit, Unit.gameFieldUnit[row][col]);
                                                } else {
                                                    targY = row;
                                                    targX = col;
                                                }
                                            } else {
                                                Unit.attack(activeUnit, Building.gameFieldBuilding[row][col]);
                                            }
                                        }
                                        if (!isGameNotOver()) {
                                            Main.endingPage(2);
                                        } else if (isChestBroken() && bonusNum == 0) {
                                            getBonus(2);
                                        }
                                    }
                                }
                            }
                            if (targX != 0 && targY != 0) {
                                if (Building.isItBuilding(targY, targX)) {
                                    Unit.attack(activeUnit, Building.gameFieldBuilding[targY][targX]);
                                    counter1 += 1;
                                    if (!isGameNotOver()) {
                                        Main.endingPage(2);
                                    } else if (isChestBroken() && bonusNum == 0) {
                                        getBonus(2);
                                        MyUnit.deleteUnit(Unit.getUnitByID(4));
                                    }
                                } else {
                                    Unit.attack(activeUnit, Unit.gameFieldUnit[targY][targX]);
                                    counter1 += 1;
                                }
                            }
                            if (activeUnit.getRemainingMoves() > 0) {
                                counter1 += 1;
                                if (!isChestBroken()) {
                                    int posX = Unit.unitPosX(activeUnit);
                                    int posY = Unit.unitPosY(activeUnit);
                                    if (Math.abs(Unit.unitPosX(Unit.getUnitByID(4)) - posX) >
                                            Math.abs(Unit.unitPosY(Unit.getUnitByID(4)) - posY)) {
                                        if (Unit.isCellFreeForMovement(posY, posX - 1)) {
                                            Unit.moving(activeUnit, posY, posX - 1);
                                        } else if (Unit.isCellFreeForMovement(posY + 1, posX)) {
                                            Unit.moving(activeUnit, posY + 1, posX);
                                        } else {
                                            Unit.healing(activeUnit);
                                        }
                                    } else {
                                        if (Unit.isCellFreeForMovement(posY + 1, posX)) {
                                            Unit.moving(activeUnit, posY + 1, posX);
                                        } else if (Unit.isCellFreeForMovement(posY, posX - 1)) {
                                            Unit.moving(activeUnit, posY, posX - 1);
                                        } else {
                                            Unit.healing(activeUnit);
                                        }
                                    }
                                } else {
                                    int posX = Unit.unitPosX(activeUnit);
                                    int posY = Unit.unitPosY(activeUnit);
                                    if (Math.abs(Building.buildingPosX(Building.getBuildingByID(2)) - posX) >
                                            Math.abs(Building.buildingPosY(Building.getBuildingByID(2)) - posY)) {
                                        if (Unit.isCellFreeForMovement(posY, posX - 1)) {
                                            Unit.moving(activeUnit, posY, posX - 1);
                                        } else if (Unit.isCellFreeForMovement(posY + 1, posX)) {
                                            Unit.moving(activeUnit, posY + 1, posX);
                                        } else {
                                            Unit.healing(activeUnit);
                                        }
                                    } else {
                                        if (Unit.isCellFreeForMovement(posY + 1, posX)) {
                                            Unit.moving(activeUnit, posY + 1, posX);
                                        } else if (Unit.isCellFreeForMovement(posY, posX - 1)) {
                                            Unit.moving(activeUnit, posY, posX - 1);
                                        } else {
                                            Unit.healing(activeUnit);
                                        }
                                    }
                                }
                            }
                            if (!isChestBroken() && Castle.getGold(2) >= 20) {

                            }
                            Map.showing(gameField);
                            Main.waiting(1000);
                        }
                    }
                }
                if (Unit.unitsCounter(2) > 5 && Castle.getGold(2) >= 30) {
                    MyUnit.spawnUnit(Castle.allUnits.get(5), 2);
                    Castle.addGold(2, -30);
                    Main.skip();
                    Map.showing(gameField);
                    Main.waiting(200);
                    counter1 += 1;
                }
            } else if (Castle.getGold(2) >= 40) {
                MyUnit.spawnUnit(Castle.allUnits.get(6), 2);
                Castle.addGold(2, -40);
                Main.skip();
                Map.showing(gameField);
                Main.waiting(200);
                counter1 += 1;
            } else if (Castle.getGold(2) >= 30) {
                MyUnit.spawnUnit(Castle.allUnits.get(5), 2);
                Castle.addGold(2, -30);
                Main.skip();
                Map.showing(gameField);
                Main.waiting(200);
                counter1 += 1;
            } else if (Castle.getGold(2) >= 10) {
                MyUnit.spawnUnit(Castle.allUnits.get(1), 2);
                Castle.addGold(2, -10);
                Main.skip();
                Map.showing(gameField);
                Main.waiting(200);
                counter1 += 1;
            }
            if (counter1 == 0) {
                //Проверка на совершение действий. Если у юнитов не осталось ходов, а у противника возможных действий -
                // цикл while прерывается
                setMyTurn(true);
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        Unit opp = Unit.gameFieldUnit[i][j];
                        if (opp.getTeam() == 2) {
                            System.out.println(STR."\{opp.getID()}.  \{opp.getName()}:  [\{Unit.unitPosY(opp)} . \{Unit.unitPosX(opp)}]  \{opp.getRemainingMoves()}  \{opp.getHit()}");
                        }
                    }
                }
            }
            // Логика для хода компьютера

        }

    }


    // Метод получения первинства хода виртуальной игральной костью
    private static boolean Roll() {
        Random random = new Random();
        System.out.println("Пора решить, кто будет ходить первым!");
        Main.waiting(200);
        System.out.println("НАЖМИ ENTER ЧТОБЫ БРОСИТЬ КУБИК");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().isEmpty()) {
            System.out.println(" Я правда впечатлён твоими навыками пользования клавиатурой, но ты не на конкурсе талантов, а пока что...");
            Main.waiting(1000);
            System.out.println("НАЖМИ ENTER ЧТОБЫ БРОСИТЬ КУБИК");
        }
        Main.waiting(500);
        int playerDice = random.nextInt(6) + 1;
        System.out.println(STR."У ВАС ВЫПАЛО \{playerDice}");
        Main.waiting(200);
        int opponentDice = random.nextInt(6) + 1;
        System.out.println(STR."У ПРОТИВНИКА ВЫПАЛО \{opponentDice}");
        Main.waiting(200);

        if (playerDice == opponentDice) {
            System.out.println("У ВАС НИЧЬЯ");
            return Roll(); // Рекурсивный вызов при ничьей
        } else {
            if (playerDice > opponentDice) {
                System.out.println("Вы ходите первым!");
                Main.waiting(1000);
            } else {
                System.out.println("Противник ходит первым!");
                Main.waiting(1000);
            }
            return playerDice > opponentDice;
        }
    }

    // Метод выдачи юнитам штрафов и бонусов за нахождение на местности
    public static void getGameField(Unit activeUnit, String[][] gameField) {
        switch (gameField[Unit.unitPosY(activeUnit)][Unit.unitPosX(activeUnit)]) {
            case "Болото" -> {
                if (activeUnit.getRemainingMoves() != 1) {
                    activeUnit.setRemainingMoves(activeUnit.getMoveRange() - 1);
                    if (activeUnit.getTeam() == 1) {
                        System.out.println(STR."Ваш \{activeUnit.getName()} [ \{Unit.unitPosY(activeUnit) + 1} ][ \{Unit.
                                unitPosX(activeUnit) + 1} ] получает штраф местности: -1 к перемещению");
                    } else {
                        System.out.println(STR."Вражеский \{activeUnit.getName()} [ \{Unit.unitPosY(activeUnit) + 1} ][ \{Unit.
                                unitPosX(activeUnit) + 1} ] получает штраф местности: -1 к перемещению");
                    }
                }
            }
            case "Холм" -> {
                activeUnit.setDamageRange(activeUnit.getDamageRange() + 1);
                if (activeUnit.getTeam() == 1) {
                    System.out.println(STR."Ваш \{activeUnit.getName()} [ \{Unit.unitPosY(activeUnit) + 1} ][ \{Unit.
                            unitPosX(activeUnit) + 1} ] получает бонус: +1 дальность акатки.");
                } else {
                    System.out.println(STR."Вражеский \{activeUnit.getName()} [ \{Unit.unitPosY(activeUnit) + 1} ][ \{Unit.
                            unitPosX(activeUnit) + 1} ] получает бонус: +1 дальность атаки.");
                }
            }
        }
        Main.waiting(150);
    }

    // Метод присвоения бонуса за сундук игроку или компьютеру
    public static void getBonus(int tech) {
        Random random = new Random();
        switch (tech) {
            case 1 -> bonus = 1;
            case 2 -> bonus = 2;
        }
        bonusNum = random.nextInt(4);
    }

    // Метод получения бонуса для юнита
    public static void getBonus(Unit unit) {
        if (bonus == 1) {
            switch (bonusNum) {
                case 1 -> {
                    unit.setMoveRange(unit.getMoveRange() + 1);
                    System.out.println(STR."Ваш \{unit.getName()} [ \{Unit.unitPosX(unit)} ][ \{Unit.
                            unitPosX(unit)} ] получает бонус: +1 к передвижению");
                }
                case 2 -> {
                    unit.setDamage((int) (unit.getDamage() * 1.25));
                    System.out.println(STR."Ваш \{unit.getName()} [ \{Unit.unitPosX(unit)} ][ \{Unit.
                            unitPosX(unit)} ] получает бонус: +25% к урону");
                }
                case 3 -> {
                    unit.setDamageRange(unit.getDamageRange() + 1);
                    System.out.println(STR."Ваш \{unit.getName()} [ \{Unit.unitPosX(unit)} ][ \{Unit.
                            unitPosX(unit)} ] получает бонус: +1 к дальности атаки");
                }
                case 4 -> {
                    unit.setRevenge(unit.getRevenge() + 1);
                    System.out.println(STR."Ваш \{unit.getName()} [ \{Unit.unitPosX(unit)} ][ \{Unit.
                            unitPosX(unit)} ] получает бонус: +1 ответный удар");
                }
            }
        } else if (bonus == 2) {
            switch (bonusNum) {
                case 1 -> {
                    unit.setMoveRange(unit.getMoveRange() + 1);
                    System.out.println(STR."Вражеский \{unit.getName()} [ \{Unit.unitPosX(unit)} ][ \{Unit.
                            unitPosX(unit)} ] получает бонус: +1 к передвижению");
                }
                case 2 -> {
                    unit.setDamage((int) (unit.getDamage() * 1.25));
                    System.out.println(STR."Вражеский \{unit.getName()} [ \{Unit.unitPosX(unit)} ][ \{Unit.
                            unitPosX(unit)} ] получает бонус: +25% к урону");
                }
                case 3 -> {
                    unit.setDamageRange(unit.getDamageRange() + 1);
                    System.out.println(STR."Вражеский \{unit.getName()} [ \{Unit.unitPosX(unit)} ][ \{Unit.
                            unitPosX(unit)} ] получает бонус: +1 к дальности атаки");
                }
                case 4 -> {
                    unit.setRevenge(unit.getRevenge() + 1);
                    System.out.println(STR."Вражеский \{unit.getName()} [ \{Unit.unitPosX(unit)} ][ \{Unit.
                            unitPosX(unit)} ] получает бонус: +1 ответный удар");
                }
            }
        }
    }


    // Метод проверки наличия сундука на карте
    public static boolean isChestBroken() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (Objects.equals(Unit.gameFieldUnit[i][j].getID(), 4)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Метод проверки окончания игры
    public static boolean isGameNotOver() {
        // Проверка условия завершения игры
        return (Building.getBuildingByID(2).getHealth() > 0 && Building.getBuildingByID(3).getHealth() > 0);
    }

    // Метод возвращающий юнитам их параметры в начале раунда
    public static void afterRound(String[][] gameField) {
        Castle.addGold(1, 20);
        Castle.addGold(2, 20);
        for (int i = 0; i < MyUnit.myUnits.size(); i++) {
            MyUnit.myUnits.get(i).setMoveRange(Castle.allUnits.get(MyUnit.myUnits.get(i).getType() - 1).getMoveRange());
            MyUnit.myUnits.get(i).setRemainingMoves(MyUnit.myUnits.get(i).getMoveRange());
            MyUnit.myUnits.get(i).setDamage(Castle.allUnits.get(MyUnit.myUnits.get(i).getType() - 1).getDamage());
            MyUnit.myUnits.get(i).setDamageRange(Castle.allUnits.get(MyUnit.myUnits.get(i).getType() - 1).getDamageRange());
            MyUnit.myUnits.get(i).setHit(true);
            MyUnit.myUnits.get(i).setRevenge(1);
            getGameField(MyUnit.myUnits.get(i), gameField);
            getBonus(MyUnit.myUnits.get(i));
        }
        for (int i = 0; i < MyUnit.oppUnits.size(); i++) {
            MyUnit.oppUnits.get(i).setMoveRange(Castle.allUnits.get(MyUnit.oppUnits.get(i).getType() - 1).getMoveRange());
            MyUnit.oppUnits.get(i).setRemainingMoves(MyUnit.oppUnits.get(i).getMoveRange());
            MyUnit.oppUnits.get(i).setDamage(Castle.allUnits.get(MyUnit.oppUnits.get(i).getType() - 1).getDamage());
            MyUnit.oppUnits.get(i).setDamageRange(Castle.allUnits.get(MyUnit.oppUnits.get(i).getType() - 1).getDamageRange());
            MyUnit.oppUnits.get(i).setHit(true);
            MyUnit.oppUnits.get(i).setRevenge(1);
            getGameField(MyUnit.oppUnits.get(i), gameField);
            getBonus(MyUnit.oppUnits.get(i));
        }
        Building.useTechBuildings();
    }

    public void startingGame(String[][] gameField) {
        setDifficult();
        gameProcess(gameField, Roll());

    }
    public void gameProcess(String[][] gameField, boolean myTurnIsFirst) {


        Unit.setTechObj();
        Building.setTechObj();

        playerCastle = new Castle();
        opponentCastle = new Castle();

        while (isGameNotOver()) {
            if (myTurnIsFirst) {
                myTurn(gameField);
                oppTurn(gameField);
                afterRound(gameField);
            } else {
                oppTurn(gameField);
                myTurn(gameField);
                afterRound(gameField);
            }
        }
    }

    public static void loadSavedGame() {
        List<String> savedGames = GameSaveLoad.getSavedGames();
        if (savedGames.isEmpty()) {
            System.out.println("Нет сохраненных игр.");
            return;
        }

        // Выводим список сохраненных игр
        System.out.println("Выберите сохранение для загрузки:");
        for (int i = 0; i < savedGames.size(); i++) {
            System.out.println((i + 1) + ". " + savedGames.get(i));
        }

        // Пользователь выбирает сохранение
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= savedGames.size()) {
            String selectedSave = "saves/" + savedGames.get(choice - 1);
            Game loadedGame = GameSaveLoad.loadGame(selectedSave);
            if (loadedGame != null) {
                System.out.println("Игра загружена.");
                loadedGame.gameProcess(loadedGame.gameField, true);  // Возвращаемся в процесс игры
            }
        } else {
            System.out.println("Некорректный выбор.");
        }
    }
}