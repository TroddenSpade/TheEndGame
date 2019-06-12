package towerRoyal.towers;

public class Hospital extends Tower{
    private final double HEAL = 100;

    public Hospital(int owner){
        super(owner,
                Type.HOSPITAL,
                "Hospital",
                2000,
                40,
                2,
                0,
                System.getProperty("user.dir")+"/assets/"+"hospital.png");
    }

}
