package towerRoyal.soldiers;

public enum SoldierKinds {
    ARCHER(new Archer()),
    DRAGON(new Dragon()),
    GOBLIN(new Goblin()),
    HEALER(new Healer()),
    KNIGHT(new Knight()),
    SHIELD(new Shield()),
    SWORDSMAN(new Swordsman());

    private Soldier soldier;

    SoldierKinds(Soldier soldier){
        this.soldier = soldier;
    }

    public Soldier getSoldier() {
        return soldier;
    }
}
