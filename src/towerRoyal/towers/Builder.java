package towerRoyal.towers;

public class Builder extends Tower{

    public Builder(int owner){
        super(owner ,Type.BUILDER,500,
                System.getProperty("user.dir")+"/assets/"+"builder.png");
    }

    @Override
    public void run() {

    }
}
