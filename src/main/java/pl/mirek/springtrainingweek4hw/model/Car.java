package pl.mirek.springtrainingweek4hw.model;

import java.util.Objects;

public class Car {
    private long id;
    private String mark;
    private String model;
    private String color;

    public Car(long id, String mark, String model, String color) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.color = color.toLowerCase();
    }

    public Car() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color.toLowerCase();
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public int compareTo(Car o) {
        return Long.compare(this.getId(), o.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark, model, color);
    }


}
