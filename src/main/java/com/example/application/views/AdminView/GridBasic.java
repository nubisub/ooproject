package com.example.application.views.AdminView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//import com.vaadin.demo.domain.Person;
import com.example.application.data.postgres.Connect;
import com.example.application.views.MainLayout;
import com.example.application.views.profile.ProfileView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.w3c.dom.events.Event;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import static com.vaadin.flow.dom.ElementFactory.createListItem;
import static com.vaadin.flow.dom.ElementFactory.createRouterLink;
//import  com.example.application.data.service
//import com.vaadin.demo.domain.DataService;

//@Route("grid-basic")
//@RolesAllowed("ROLE_admin")

@Route(value = "grid-basic", layout = MainLayout.class)
@RolesAllowed({"ROLE_admin"})
//@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
//@Secured("ROLE_admin")
public class GridBasic extends Div {
//    Grid<Person> grid = new Grid<>(Person.class, false);
    Main Content = new Main();
    List<Person> people = new ArrayList<>();

    public GridBasic() {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        String role = authentication.getAuthorities().toArray()[0].toString();
//        if role != admin
        if (!role.equals("ROLE_admin")) {
//            before enter listener
            UI.getCurrent().getPage().executeJs("window.location.href = '/'");
            return;
        }

        Grid<Person> grid = new Grid<>(Person.class, false);

            try {
                Connect connect = Connect.getInstance();
                Connection connection = connect.getConnection();
                String sql = "SELECT * FROM oop.\"Mahasiswa\"";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    people.add(new Person(resultSet.getString("nama"),
                            resultSet.getString("nim"), resultSet.getString("status")));
                }




            } catch (Exception e) {
                e.printStackTrace();
            }
                grid.addClassNames("justify-center");
                grid.setItems(people);
                grid.addColumn(Person::getFirstName).setHeader("Nama").setWidth("80px");
                grid.addColumn(Person::getId)
                .setHeader("NIM");
//              Button button = new Button("Click me");
                grid.addComponentColumn(person -> {

                    Div div = new Div();
                    Button terimaButton = new Button("Terima");
                    Button tolakButton = new Button("Tolak");
                    tolakButton.addClassNames("bg-error","ml-s","text-error-contrast");
                    terimaButton.addClassNames("bg-success","text-success-contrast");

                    Span denied = new Span(" Ditolak ");
                    denied.getElement().getThemeList().add("badge error pill");
                    denied.setWidth("100px");
                    denied.addClassNames("my-s");

                    Span confirmed = new Span("Diterima");
                    confirmed.getElement().getThemeList().add("badge success pill");
                    confirmed.setWidth("100px");
                    confirmed.addClassNames("my-s");

                    if (person.getStatus().equals("0")) {
                        div.add(terimaButton);
                        div.add(tolakButton);
                    }
                    terimaButton.addClickListener(event -> {
                        div.removeAll();
                        div.add(confirmed);
                    });
                    tolakButton.addClickListener(event -> {
                        div.removeAll();
                        div.add(denied);
                            });
//                    if (person.getStatus().equals("0")) {
//                        Button editButton = new Button("Terima");
//                        Button tolakButton = new Button("Tolak")
//                    } else {
//                        Button editButton = new Button("Tolak");
//                    }
//                    editButton.addClickListener(e -> {
//                    System.out.println(person.getFirstName());
//                        System.out.println(person.getStatus());
//                });
                    return div;
                }).setHeader("Status");

        TextField searchField = new TextField();
        searchField.setWidth("30%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
//            grid.setItems(people.stream().filter(person -> person.getFirstName().toLowerCase().contains(e.getValue().toLowerCase())).collect(Collectors.toList()));
//            filter by firstnama and nim
            List<Person> peoplefilter = new ArrayList<>();
            for (Person person : people) {
                if (person.getFirstName().toLowerCase().contains(e.getValue().toLowerCase()) || person.getId().toLowerCase().contains(e.getValue().toLowerCase())) {
                    peoplefilter.add(person);
                    }
            }
            grid.setItems(peoplefilter);


        });




        searchField.addClassNames("search-field","mb-m","mt-l");

        Div div = new Div();
        div.addClassNames("wrapper", "m-auto");
        div.setWidth("80%");
        div.add(searchField, grid);
        add(div);
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
