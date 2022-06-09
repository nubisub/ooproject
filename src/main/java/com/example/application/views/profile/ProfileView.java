package com.example.application.views.profile;

import com.example.application.data.entity.SamplePerson;
import com.example.application.data.postgres.Account;
import com.example.application.data.postgres.DaftarUKM;
import com.example.application.data.postgres.Update;
import com.example.application.data.service.SamplePersonService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.GeneratedVaadinTextField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

@PageTitle("Profile")
@Route(value = "profile", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class ProfileView extends Div {
    ComboBox<String> adminUKM = new ComboBox<>("Admin UKM");
    private static final Set<String> ukm = new LinkedHashSet<>();
    static DaftarUKM daftarukm = new DaftarUKM();
    static {
        ukm.addAll(daftarukm.getDaftarUKM());
    }
    Update update = new Update();
    TextField nama = new TextField("Nama");
    TextField nim = new TextField("NIM");
    public PhoneNumberField phone = new PhoneNumberField("Nomor Telepon");
    public TextField alamat = new TextField("Alamat");
    public Button save = new Button("Save");
    private Binder<SamplePerson> binder = new Binder<>(SamplePerson.class);
    public ProfileView(SamplePersonService personService) {

        addClassName("profile-view");
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);
        clearForm();
    }

    private void clearForm() {
        binder.setBean(new SamplePerson());
    }

    private Component createTitle() {
        return new H3("Profile");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        EmailField email = new EmailField("Email");
        DatePicker dateOfBirth = new DatePicker("Tanggal Lahir");
        email.setErrorMessage("Please enter a valid email address");
        nim.setReadOnly(true);
        Account account = Account.getInstance();
        nim.setValue(account.getNim());
        nama.setValue(account.getNama());
        alamat.setValue(account.getAlamat());
        email.setValue(account.getEmail());
        phone.number.setValue(account.getPhone());
        phone.setInvalid(false);
        dateOfBirth.setValue(account.getTanggalLahir());

        if(account.getStatus().equals("1")){
            nama.setReadOnly(true);
            alamat.setReadOnly(true);
            email.setReadOnly(true);
            phone.number.setReadOnly(true);
            phone.countryCode.setReadOnly(true);
            dateOfBirth.setReadOnly(true);
            save.setEnabled(false);

        }

//        save button listener
        save.addClickListener(event -> {
            Dialog dialog = new Dialog();
            dialog.setMinWidth("370px");

            H4 title = new H4("Update Profile");
            title.addClassNames("mb-m","mt-s");
            Span content = new Span("Are you sure you want to save ?");
            Button confirm = new Button("Confirm");
            Button cancel = new Button("Cancel");
            confirm.addClassNames("bg-primary","text-primary-contrast");
            cancel.addClassNames("bg-transparent","text-error");
            Div header = new Div(title, content);
            Div footer = new Div(cancel, confirm);
            header.addClassName("mb-s");
            footer.addClassNames("flex","gap-s", "mt-l");
            footer.addClassNames("justify-end", "align-center");
            dialog.add(header, footer);
            dialog.open();
            cancel.addClickListener(event1 -> dialog.close());
//            confirm button listener
            confirm.addClickListener(event1 -> {
                Notification notification = Notification.show("Data berhasil disimpan", 3000, Notification.Position.BOTTOM_START);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String role = authentication.getAuthorities().toArray()[0].toString();
                if (role.equals("ROLE_admin")){
                    account.setAdminUKM(adminUKM.getValue());
                    try {
                        update.UpdateAdminUKM(account.getNim(), account.getAdminUKM());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
                account.setNama(nama.getValue());
                account.setPhone(phone.number.getValue());
                account.setAlamat(alamat.getValue());
                account.setEmail(email.getValue());
                account.setTanggalLahir(String.valueOf(dateOfBirth.getValue()));
                try {
                    update.Update(nim.getValue(), nama.getValue(), alamat.getValue(), String.valueOf(dateOfBirth.getValue()), email.getValue(), phone.number.getValue());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
//
                dialog.close();
            });
            add(dialog);
        });




        adminUKM.setItems(ukm);
        if(account.getAdminUKM() != null){
            adminUKM.setValue(account.getAdminUKM());
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toArray()[0].toString();

        if (!role.equals("ROLE_admin")) {
            formLayout.add(nama, nim, dateOfBirth, phone, email, alamat);
        } else {
            formLayout.add(nama, nim, dateOfBirth, phone, email, adminUKM);

        }

        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        return buttonLayout;
    }

    private static class PhoneNumberField extends CustomField<String> {
        private ComboBox<String> countryCode = new ComboBox<>();
        public TextField number = new TextField();
        public PhoneNumberField(String label) {

            setLabel(label);
            countryCode.setWidth("120px");
            countryCode.setPlaceholder("Country");
            countryCode.setPattern("\\+\\d*");
            countryCode.setPreventInvalidInput(false);
            countryCode.setItems("+62");
            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));

            number.setPattern("\\d*");
            number.setPreventInvalidInput(true);

            countryCode.setValue("+62");
            HorizontalLayout layout = new HorizontalLayout(countryCode, number);
            layout.setFlexGrow(1.0, number);
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            if (countryCode.getValue() != null && number.getValue() != null) {
                String s = countryCode.getValue() + " " + number.getValue();
                return s;
            }
            return "";
        }

        @Override
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
            if (parts.length == 1) {
                countryCode.clear();
                number.setValue(parts[0]);
            } else if (parts.length == 2) {
                countryCode.setValue(parts[0]);
                number.setValue(parts[1]);
            } else {
                countryCode.clear();
                number.clear();
            }
        }
    }
}
