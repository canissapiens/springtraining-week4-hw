package pl.mirek.springtrainingweek4hw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mirek.springtrainingweek4hw.data.CarService;
import pl.mirek.springtrainingweek4hw.model.Car;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarApi {

    private CarService carService;

    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Car>> getCars() {
        return new ResponseEntity<List<Car>>(carService.getCars(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Car car = carService.getCarById(id);
        if (car != null) {
            return new ResponseEntity<Car>(car, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/color/{color}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Car>> getCarsBycolor(@PathVariable String color) {
        List<Car> cars = carService.getCarsByColor(color);
        if (cars != null) {
            return new ResponseEntity<List<Car>>(cars, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car newCar) {
        Boolean result = carService.addCar(newCar);
        if (result) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity modifyCar(@RequestBody Car newCar) {
        System.out.println("modifyCar");
        Car car = carService.getCarById(newCar.getId());
        if (car == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Boolean result = carService.deleteCar(car);
        if (result) {
            Boolean isCarAdded = carService.addCar(newCar);
            if (isCarAdded) {
                return new ResponseEntity(HttpStatus.OK);
            }
            // I bring the old car back to database
            carService.addCar(car);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PatchMapping(value = "/modify/{id}/{field}/{value}")
    public ResponseEntity modifyFieldOfCar(@PathVariable long id, @PathVariable String field, @PathVariable String value) {
        Car car = carService.getCarById(id);
        if (car != null) {
            Boolean result = carService.modifyField(car, field, value);
            if (result) {
                return new ResponseEntity(HttpStatus.ACCEPTED);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
