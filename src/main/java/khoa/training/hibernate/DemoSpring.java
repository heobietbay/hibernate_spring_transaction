package khoa.training.hibernate;

import khoa.training.hibernate.model.Car;
import khoa.training.hibernate.model.ICar;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 4/20/2016.
 */
public class DemoSpring {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application_context2.xml");
        ICar car = (ICar)context.getBean("car");
        System.out.println(car instanceof Car);

        Car subCar = new Car(){
            Car innerCar = new Car();
            @Override
            public String getColor() {
                return innerCar.getColor();
            }

            @Override
            public String getEngineModel() {
                return innerCar.getEngineModel();
            }

            @Override
            public void setColor(String color) {
                innerCar.setColor(color);
            }

            @Override
            public void setEngineModel(String engineModel) {
                innerCar.setEngineModel(engineModel);
            }

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("$classname{");
                sb.append("innerCar=").append(innerCar);
                sb.append('}');
                return sb.toString();
            }
        };

        subCar.setColor("Red");
        subCar.setEngineModel("Megatron 42");
        System.out.println(subCar instanceof Car);
    }
}
