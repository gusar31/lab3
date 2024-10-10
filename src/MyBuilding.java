import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class MyBuilding extends Building implements Serializable  {

    @Serial
    private static final long serialVersionUID = 1L;

    public static ArrayList<Building> myBuildings;
    public static ArrayList<Building> oppBuildings;
    public static boolean myTurn;

    public MyBuilding(int type, String name, String symbol, int remainingHealth, int health, int damage, int damageRange, int cost, int wood, int stone) {
        super(type, name, symbol, remainingHealth, health, damage, damageRange, cost, wood, stone);
        myBuildings = new ArrayList<>();
        oppBuildings = new ArrayList<>();
    }

    public MyBuilding(int id, int team) {
        super(id, team);
        myBuildings = new ArrayList<>();
        oppBuildings = new ArrayList<>();
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
    }

    public static void spawnBuilding(Building selectedBuilding, int team, int row, int col) {
        Random random = new Random();



        if (team == 1) {
            MyBuilding Building = new MyBuilding(Unit.giveID(1), 1);
            // Размещаем нового юнита на игровом поле
            giveStats(Building, selectedBuilding);
            gameFieldBuilding[row][col] = Building;
            System.out.println(STR."Строение \{selectedBuilding.getName()} размещено на клетке [\{1 + row},\{1 + col}]");
            myBuildings.add(Building);


        } else if (team == 2){
            // Проверяем, что выбранная клетка пуста и находится в пределах игрового поля
            while (Building.gameFieldBuilding[6 + row][1 + col].getID() != 1) {
                row = random.nextInt(3);
                col = random.nextInt(3);
            }
            MyBuilding OppBuilding = new MyBuilding(Unit.giveID(1), 2);
            // Размещаем нового вражеского юнита на игровом поле
            gameFieldBuilding[row][col] = OppBuilding;
            System.out.println(STR."Вражеское строение \{selectedBuilding.getName()} размещено на клетке [\{1 + row},\{1 + col}]");
            giveStats(OppBuilding, selectedBuilding);
            oppBuildings.add(OppBuilding);
        }
    }

    public static void deleteBuilding(Building selectedBuilding) {
        Building empty = new Building(1, 3);
        if (selectedBuilding.getTeam() == 1) {
            for (int i = 0; i < myBuildings.size(); i++) {
                if (myBuildings.get(i).getID() == selectedBuilding.getID()) {
                    myBuildings.remove(selectedBuilding);

                }
            }
            selectedBuilding.setID(0);
            Building.gameFieldBuilding[buildingPosY(selectedBuilding)][buildingPosX(selectedBuilding)] = empty;
        } else if (selectedBuilding.getTeam() == 2) {
            for (int i = 0; i < oppBuildings.size(); i++) {
                if (oppBuildings.get(i).getID() == selectedBuilding.getID()) {
                    oppBuildings.remove(selectedBuilding);
                }
            }
            selectedBuilding.setID(0);
            Building.gameFieldBuilding[buildingPosY(selectedBuilding)][buildingPosX(selectedBuilding)] = empty;
        } else {
            int posY = buildingPosY(selectedBuilding);
            int posX = buildingPosX(selectedBuilding);
            Building.gameFieldBuilding[posY][posX].setID(1);
            Building.gameFieldBuilding[posY][posX].setTeam(3);

        }
    }
}