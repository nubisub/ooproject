package com.example.application.views.profile;

import com.example.application.data.entity.SamplePerson;
import com.example.application.data.postgres.Account;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Profile")
@Route(value = "profile", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class ProfileView extends Div {
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
        nim.setEnabled(false);
        Account account = Account.getInstance();
        nim.setValue(account.getNim());
        nama.setValue(account.getNama());
        alamat.setValue(account.getAlamat());
        email.setValue(account.getEmail());
        phone.number.setValue(account.getPhone());
        dateOfBirth.setValue(account.getTanggalLahir());

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
                account.setNama(nama.getValue());
                account.setPhone(phone.number.getValue());
                account.setAlamat(alamat.getValue());
                account.setEmail(email.getValue());
                account.setTanggalLahir(String.valueOf(dateOfBirth.getValue()));

                dialog.close();
            });
            add(dialog);
        });

        formLayout.add(nama, nim, dateOfBirth, phone, email, alamat);
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
            countryCode.setPreventInvalidInput(true);
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
