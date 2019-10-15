package pl.mirek.springtrainingweek4hw.controller;

import com.sun.org.apache.xpath.internal.objects.XString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mirek.springtrainingweek4hw.data.CarService;
import pl.mirek.springtrainingweek4hw.model.Car;

@Controller
@RequestMapping("/cars/gui")
public class CarGui {

    /*
        wyświetlanie wszystkich pozycji
        pobieranie elementu po jego id
        dodawanie pozycji
        modyfikowanie pozycji
        modyfikowanie jednego z pól pozycji
        usuwania jednej pozycji

    */
    private CarService carService;

    @Autowired
    public CarGui(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public String getCars(Model model) {
        model.addAttribute("name", "Mirek");
        model.addAttribute("cars", carService.getCars());
        model.addAttribute("newCar", new Car());
        return "cars";
    }

    @PostMapping("/add-car")
    public String addCar(@ModelAttribute Car car) {
        if (carService.isCarReadyToAdd(car)) {
            carService.addCar(car);
        }
        return "redirect:";
    }

    @GetMapping("/by-id")
    public String getCarById(@RequestParam long id, Model model) {
        Car car = carService.getCarById(id);
        if (car == null) {
            return "redirect:";
        }
        model.addAttribute("car", car);
        return "single-car";
    }

    @GetMapping("/del-car")
    public String deleteCarForm(Model model) {
        model.addAttribute("cars", carService.getCars());
        model.addAttribute("oldCar", new Car());
        return "deletecar";
    }

    @PostMapping("/del-car")
    public String deleteCar(@ModelAttribute Car car) {
        if (!carService.deleteCarById(car)) {
            return "redirect:del-car";
        }
        return "redirect:";
    }

    @GetMapping("modify-car")
    public String modifyCarForm(Model model) {
        model.addAttribute("name", "Mirek");
        model.addAttribute("cars", carService.getCars());
        model.addAttribute("oldCar", new Car());
        return "modifycar";
    }

    @PostMapping("modify-car")
    public String modifyCar(@ModelAttribute Car newCar) {
        carService.modifyCar(newCar);
        return "redirect:";
    }
}
