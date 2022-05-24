package com.example.application.views.AdminView;

import java.util.Arrays;
import java.util.List;

//import com.vaadin.demo.domain.Person;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.Route;

import static com.vaadin.flow.dom.ElementFactory.createListItem;
//import  com.example.application.data.service
//import com.vaadin.demo.domain.DataService;

//@Route("grid-basic")
@Route(value = "grid-basic", layout = MainLayout.class)

public class GridBasic extends Div {
    Grid<Person> grid = new Grid<>(Person.class, false);
    Main Content = new Main();


    public GridBasic() {
        Grid<Person> grid = new Grid<>(Person.class, false);
//        grid.addColumn(Person::getFirstName).setHeader("First name");
        List<Person> people = Arrays.asList(
                new Person("Nicolaus Copernicus", 1543),
                new Person("Galileo Galilei", 1564),
                new Person("Johannes Kepler", 1571));
        grid.setItems(people);
        grid.addColumn(Person::getFirstName).setHeader("Name");
        grid.addColumn(Person::getId)
                .setHeader("Year of birth");
//        Button button = new Button("Click me");
        grid.addComponentColumn(person -> {
                    Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
              System.out.println(person.getFirstName());
            });
                    return editButton;
                });
//
        add(grid);
    }
//    private static ComponentRenderer<Button> createStatusComponentRenderer() {
//        return new ComponentRenderer<>(Button::new);
//    }
//    private static final SerializableBiConsumer<Span, Person> statusComponentUpdater = (span, person) -> {
//        boolean isAvailable = "Available".equals(person.getStatus());
//        String theme = String
//                .format("badge %s", isAvailable ? "success" : "error");
//        span.getElement().setAttribute("theme", theme);
//        span.setText(person.getStatus());
//    };
    private  Component button(){
        Button button = new Button("Click me");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }


}
