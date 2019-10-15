package pl.mirek.springtrainingweek4hw.data;

import net.bytebuddy.TypeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.mirek.springtrainingweek4hw.model.Car;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private CarRepository cars;
    private SortCarById sort;
    Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    public CarService(CarRepository cars, SortCarById sort) {
        this.cars = cars;
        this.sort = sort;
    }

    public List<Car> getCars() {
        List<Car> output = cars.getCars();
        Collections.sort(output, sort);
        return output;
    }

    public Car getCarById(long id) {
        Optional<Car> first = cars.getCars().stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    public List<Car> getCarsByColor(String color) {
        List<Car> allSelectedCars = cars.getCars().stream().filter(car -> car.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
        if (allSelectedCars.size() > 0) {
            return allSelectedCars;
        }
        logger.warn("---> there are no cars on the list");
        return null;
    }

    public Boolean addCar(Car car) {
        return cars.getCars().add(car);
    }

    public Boolean deleteCar(Car car) {
        return cars.getCars().remove(car);
    }

    public Boolean deleteCarById(Car input) {
        Car car = getCarById(input.getId());
        return deleteCar(car);
    }

    public void modifyCar(Car newCar) {
        if (
                !newCar.getMark().equals("") &&
                !newCar.getModel().equals("") &&
                !newCar.getColor().equals("")) {
            deleteCarById(newCar);
            addCar(newCar);
        } else {
            Car car = this.getCarById(newCar.getId());
            if (!newCar.getMark().equals("")) {
                car.setMark(newCar.getMark());
            }

            if (!newCar.getModel().equals("")) {
                car.setModel(newCar.getModel());
            }

            if (!newCar.getColor().equals("")) {
                car.setColor(newCar.getColor());
            }
        }
    }

    public Boolean modifyField(Car car, String field, String value) {

        switch (field) {
            case "mark":
                car.setMark(value);
                return true;
            case "model":
                car.setModel(value);
                return true;
            case "color":
                car.setColor(value);
                return true;
            default:
                return false;
        }
    }

    public boolean isCarReadyToAdd(Car car) {
        return true;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeRepository() {
        addCar(new Car(1L, "Mercedes", "GLK", "beige"));
        addCar(new Car(2L, "Citroen", "C3", "RED"));
        addCar(new Car(3L, "Fiat", "Panda", "red"));
        System.out.println("---> CarService is ready");
    }
}
