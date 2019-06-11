package towerRoyal.towers;

public class Builder extends Tower{

    public Builder(){
        super(Type.BUILDER,500,
                System.getProperty("user.dir")+"/assets/"+"builder.png");
    }

    @Override
    public void run() {

    }
}
