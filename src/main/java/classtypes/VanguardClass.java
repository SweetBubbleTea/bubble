package classtypes;

public class VanguardClass implements Class {

    private long userID;

    public VanguardClass(long userID) {
        this.userID = userID;
    }

    @Override
    public long getUserID() {
        return userID;
    }

    @Override
    public String getClassChosen() {
        return "Vanguard";
    }

    @Override
    public int getHealth() {
        return 450;
    }

    @Override
    public int getAttack() {
        return 50;
    }

    @Override
    public String getWeapon() {
        return "Standard Shield";
    }

    @Override
    public int getDefense() {
        return 150;
    }
}
