package towerRoyal.towers;

public class Electric extends Tower{
    public Electric(int owner){
        super(owner,Type.ELECTRIC,
                "Electric",
                1500,
                45,
                3,
                250,
                System.getProperty("user.dir")+"/assets/"+"electric.png");
    }

}
