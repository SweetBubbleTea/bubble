package classtypes;

public class WarriorClass implements Class{

    private long userID;

    public WarriorClass(long userID) {
        this.userID = userID;
    }

    @Override
    public long getUserID() {
        return userID;
    }

    @Override
    public String getClassChosen() {
        return "Warrior";
    }


    @Override
    public int getHealth() {
        return 300;
    }

    @Override
    public int getAttack() {
        return 80;
    }

    @Override
    public String getWeapon() {
        return "Standard Sword";
    }

    @Override
    public int getDefense() {
        return 50;
    }
}
