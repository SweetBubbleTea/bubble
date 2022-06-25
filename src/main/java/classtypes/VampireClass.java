package classtypes;

public class VampireClass implements Class{

    private long userID;

    public VampireClass(long userID) {
        this.userID = userID;
    }

    @Override
    public long getUserID() {
        return userID;
    }

    @Override
    public String getClassChosen() {
        return "Vampire";
    }

    @Override
    public int getHealth() {
        return 400;
    }

    @Override
    public int getAttack() {
        return 60;
    }

    @Override
    public String getWeapon() {
        return "Standard MageBook";
    }

    @Override
    public int getDefense() {
        return 100;
    }
}
