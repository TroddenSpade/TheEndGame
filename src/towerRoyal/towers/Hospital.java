package towerRoyal.towers;

import towerRoyal.soldiers.Soldier;

public class Hospital extends Tower{
    private final double HEAL = 100;

    public Hospital(int owner){
        super(owner,
                Type.HOSPITAL,
                "Hospital",
                2000,
                40,
                3,
                0,
                System.getProperty("user.dir")+"/assets/"+"hospital.png");
    }

    public void heal(Soldier soldier){
        if(soldier != null){
            soldier.getHeal(HEAL);
        }
    }

    @Override
    public void run() {
        while(isAlive()){
            try{
                Thread.sleep(1000);
            } catch (Exception e){

            }
            if(isAlive()) {
                heal(getTile().findAnSoldier(getRange(),getOwner()));
            }
        }
    }
}
