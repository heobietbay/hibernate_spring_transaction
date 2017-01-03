package khoa.training.hibernate.model;

/**
 * Created by Administrator on 4/20/2016.
 */
public class Car implements ICar {
    String color;
    String engineModel;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEngineModel() {
        return engineModel;
    }

    public void setEngineModel(String engineModel) {
        this.engineModel = engineModel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("color='").append(color).append('\'');
        sb.append(", engineModel='").append(engineModel).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
