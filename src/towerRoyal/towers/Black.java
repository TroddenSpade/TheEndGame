package towerRoyal.towers;

public class Black extends Tower{
    public Black(int owner){
        super(owner,
                Type.BLACK,
                "Black",
                2000,
                40,
                2,
                300,
                System.getProperty("user.dir")+"/assets/"+"black.png");
    }

}
