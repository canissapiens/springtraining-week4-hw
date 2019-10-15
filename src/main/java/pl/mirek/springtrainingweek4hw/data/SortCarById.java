package pl.mirek.springtrainingweek4hw.data;

import org.springframework.stereotype.Component;
import pl.mirek.springtrainingweek4hw.model.Car;

import java.util.Comparator;

@Component
class SortCarById implements Comparator<Car> {
    public int compare(Car a, Car b) {
        return Long.compare(a.getId(), b.getId());
    }
}
