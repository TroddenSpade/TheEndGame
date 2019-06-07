package towerRoyal.towers;

public class Black extends Tower{
    public Black(){
        super(Type.BLACK,
                "Black",
                2000,
                40,
                2,
                300,
                System.getProperty("user.dir")+"/assets/"+"black.png");
    }

}
