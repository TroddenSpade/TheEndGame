package towerRoyal.towers;

public class Electric extends Tower{
    public Electric(){
        super(Type.ELECTRIC,
                "Electric",
                1500,
                45,
                3,
                250,
                System.getProperty("user.dir")+"/assets/"+"electric.png");
    }

}
