package classtypes;

import net.dv8tion.jda.api.entities.User;

public interface Class {

    public long getUserID();

    public String getClassChosen();

    public int getHealth();

    public int getAttack();

    public String getWeapon();

    public int getDefense();
}
