package com.example.application.views.AdminView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.data.postgres.Connect;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;


@Route(value = "Admin", layout = MainLayout.class)
@RolesAllowed({"ROLE_admin"})
@PermitAll
public class AdminView extends Div {
//    Main Content = new Main();
    List<Person> people = new ArrayList<>();

    public AdminView() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toArray()[0].toString();
        if (!role.equals("ROLE_admin")) {
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
                    } else if (person.getStatus().equals("1")) {
                        div.add(confirmed);
                    }
                    terimaButton.addClickListener(event -> {
                        Dialog dialog = new Dialog();
                        dialog.setMinWidth("370px");

                        String nama = person.getFirstName();

                        H4 title = new H4("Terima Mahasiswa");
                        title.addClassNames("mb-m","mt-s");
                        Span detail = new Span("Apakah anda yakin ingin menerima " + nama + " ?");
                        Button confirm = new Button("Confirm");
                        Button cancel = new Button("Cancel");
                        confirm.addClassNames("bg-primary","text-primary-contrast");
                        cancel.addClassNames("bg-transparent","text-error");
                        Div header = new Div(title, detail);
                        Div footer = new Div(cancel, confirm);
                        header.addClassName("mb-s");
                        footer.addClassNames("flex","gap-s", "mt-l");
                        footer.addClassNames("justify-end", "align-center");
                        dialog.add(header, footer);
                        dialog.open();
                        cancel.addClickListener(event1 -> dialog.close());
                        confirm.addClickListener(event1 -> {
                            dialog.close();
                            Notification notification = Notification.show(nama + " Berhasil Diterima", 3000, Notification.Position.BOTTOM_START);
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            div.removeAll();
                            div.add(confirmed);
                        });
                        add(dialog);

                    });
                    tolakButton.addClickListener(event -> {
                        Dialog dialog = new Dialog();
                        dialog.setMinWidth("370px");

                        String nama = person.getFirstName();

                        H4 title = new H4("Tolak Mahasiswa");
                        title.addClassNames("mb-m","mt-s");
                        Span detail = new Span("Apakah anda yakin ingin menolak " + nama + " ?");
                        Button confirm = new Button("Confirm");
                        Button cancel = new Button("Cancel");
                        confirm.addClassNames("bg-primary","text-primary-contrast");
                        cancel.addClassNames("bg-transparent","text-error");
                        Div header = new Div(title, detail);
                        Div footer = new Div(cancel, confirm);
                        header.addClassName("mb-s");
                        footer.addClassNames("flex","gap-s", "mt-l");
                        footer.addClassNames("justify-end", "align-center");
                        dialog.add(header, footer);
                        dialog.open();
                        cancel.addClickListener(event1 -> dialog.close());
                        confirm.addClickListener(event1 -> {
                            dialog.close();
                            Notification notification = Notification.show(nama + " Berhasil Ditolak", 3000, Notification.Position.BOTTOM_START);
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            div.removeAll();
                            div.add(denied);
                        });
                        add(dialog);

                            });
                    return div;
                }).setHeader("Status");

        TextField searchField = new TextField();
        searchField.setWidth("30%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
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

}
