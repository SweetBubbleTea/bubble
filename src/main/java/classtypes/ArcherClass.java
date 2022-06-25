package classtypes;

public class ArcherClass implements Class{

    private long userID;

    public ArcherClass(long userID) {
        this.userID = userID;
    }

    @Override
    public long getUserID() {
        return userID;
    }

    @Override
    public String getClassChosen() {
        return "Archer";
    }

    @Override
    public int getHealth() {
        return 250;
    }

    @Override
    public int getAttack() {
        return 75;
    }

    @Override
    public String getWeapon() {
        return "Standard Bow";
    }

    @Override
    public int getDefense() {
        return 40;
    }
}
