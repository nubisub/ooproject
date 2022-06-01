package com.example.application.views.daftarukm;

import com.example.application.data.postgres.Account;
import com.example.application.data.postgres.DaftarUKM;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Set;

@PageTitle("Daftar UKM")
@Route(value = "daftarukm", layout = MainLayout.class)
//@RolesAllowed("USER")
public class DaftarUKMView extends Div {
    Account account = Account.getInstance();
     ComboBox<String> ukmSelect1 = new ComboBox<>("Pilihan Pertama");
     ComboBox<String> ukmSelect2 = new ComboBox<>("Pilihan Kedua");
     ComboBox<String> ukmSelect3 = new ComboBox<>("Pilihan Ketiga");
     ComboBox<String> ukmSelect4 = new ComboBox<>("Pilihan Keempat");
     ComboBox<String> ukmSelect5 = new ComboBox<>("Pilihan Kelima");
    Span confirmed = new Span("Diterima");
    Span rejected = new Span("Ditolak");

    Aside aside = new Aside();
    UnorderedList ul = new UnorderedList();
    Button pay = new Button("Submit", new Icon(VaadinIcon.LOCK));

    private static final Set<String> ukm = new LinkedHashSet<>();
    static DaftarUKM daftarukm = new DaftarUKM();
    static {
    ukm.addAll(daftarukm.getDaftarUKM());
    }

    public DaftarUKMView() {

        addClassNames("daftar-ukm-view", "flex", "flex-col", "h-full");

        Main content = new Main();
        content.addClassNames("grid", "gap-xl", "items-start", "justify-center", "max-w-screen-xl", "pb-l","px-l");

        content.add(createCheckoutForm());
        add(content);
        if (ukmSelect1.getValue() == null) {
            pay.setEnabled(false);
        }
        pay.addClickListener(e -> {
            Dialog dialog = new Dialog();
            dialog.setMinWidth("370px");

            H4 title = new H4("Kunci UKM Pilihan Anda");
            title.addClassNames("mb-m","mt-s");
            Span detail = new Span("Apakah anda sudah yakin dengan pilihan UKM anda ?");
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
                Notification notification = Notification.show("Pilihan UKM anda berhasil disimpan", 3000, Notification.Position.BOTTOM_START);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        content.add(createAside());
                        pay.setEnabled(false);
                    });
            add(dialog);


        });
    }

    private Component createCheckoutForm() {
        Section checkoutForm = new Section();
        checkoutForm.addClassNames("flex", "flex-col", "flex-grow");

        H2 header = new H2("Pilih UKM");
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        Paragraph note = new Paragraph("Pilih UKM yang ingin Anda ikuti, urutan menunjukkan prioritas");
        note.addClassNames("mb-xl", "mt-0", "text-secondary");
        checkoutForm.add(header, note);

        checkoutForm.add(createPersonalDetailsSection());
        checkoutForm.add(new Hr());
        checkoutForm.add(createFooter());

        return checkoutForm;
    }

    private Section createPersonalDetailsSection() {
        Section personalDetails = new Section();
        personalDetails.addClassNames("flex", "flex-col", "mb-xl", "mt-m");

        ukmSelect1.setRequiredIndicatorVisible(true);
        ukmSelect1.addClassNames("mb-s");
        ukmSelect1.setItems(ukm);
        ukmSelect1.isPreventInvalidInput();
        ukmSelect1.setValue(account.getUkm1());
        ukmSelect1.addValueChangeListener(e -> {
            if (ukmSelect1.getValue() != null) {
                pay.setEnabled(true);
                account.setUkm1(ukmSelect1.getValue());
            }
                });


        ukmSelect2.setRequiredIndicatorVisible(true);
        ukmSelect2.addClassNames("mb-s");
        ukmSelect2.setItems(ukm);

        ukmSelect3.setRequiredIndicatorVisible(true);
        ukmSelect3.addClassNames("mb-s");
        ukmSelect3.setItems(ukm);

        ukmSelect4.setRequiredIndicatorVisible(true);
        ukmSelect4.addClassNames("mb-s");
        ukmSelect4.setItems(ukm);

        ukmSelect5.setRequiredIndicatorVisible(true);
        ukmSelect5.addClassNames("mb-s");
        ukmSelect5.setItems(ukm);

        personalDetails.add( ukmSelect1,ukmSelect2,ukmSelect3,ukmSelect4,ukmSelect5);
        return personalDetails;
    }


    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames("flex", "items-center", "justify-between", "my-m");

        pay.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        pay.addClassName("submit-button");
        footer.add(pay);
        return footer;
    }

    private Aside createAside() {

        aside.addClassNames("bg-contrast-5", "box-border", "p-l", "rounded", "sticky");
        aside.setMinWidth("350px");
        Header headerSection = new Header();
        headerSection.addClassNames("flex", "items-center", "justify-between", "mb-m");
        H3 header = new H3("Status Pendaftaran");
        header.addClassNames("m-0");
        headerSection.add(header);


        confirmed.getElement().getThemeList().add("badge success");
        confirmed.setWidth("120px");

        rejected.getElement().getThemeList().add("badge error");
        rejected.setWidth("120px");

        ul.addClassNames("list-none", "m-10", "p-0", "flex", "flex-col", "gap-m");


        if (ukmSelect1.getValue() != null) {
            Span pending = new Span("Dalam Proses");
            pending.getElement().getThemeList().add("badge");
            pending.setWidth("120px");
            ul.add(createListItem(ukmSelect1.getValue(),  pending));
        }
        if (ukmSelect2.getValue() != null && !ukmSelect2.getValue().equals(ukmSelect1.getValue())) {
            Span pending = new Span("Dalam Proses");
            pending.getElement().getThemeList().add("badge");
            pending.setWidth("120px");
            ul.add(createListItem(ukmSelect2.getValue(),  pending));
        }
        if (ukmSelect3.getValue() != null && !ukmSelect3.getValue().equals(ukmSelect1.getValue()) && !ukmSelect3.getValue().equals(ukmSelect2.getValue())) {
            Span pending = new Span("Dalam Proses");
            pending.getElement().getThemeList().add("badge");
            pending.setWidth("120px");
            ul.add(createListItem(ukmSelect3.getValue(),  pending));
        }
        if (ukmSelect4.getValue() != null && !ukmSelect4.getValue().equals(ukmSelect1.getValue()) && !ukmSelect4.getValue().equals(ukmSelect2.getValue()) && !ukmSelect4.getValue().equals(ukmSelect3.getValue())) {
            Span pending = new Span("Dalam Proses");
            pending.getElement().getThemeList().add("badge");
            pending.setWidth("120px");
            ul.add(createListItem(ukmSelect4.getValue(),  pending));
        }
        if (ukmSelect5.getValue() != null && !ukmSelect5.getValue().equals(ukmSelect1.getValue()) && !ukmSelect5.getValue().equals(ukmSelect2.getValue()) && !ukmSelect5.getValue().equals(ukmSelect3.getValue()) && !ukmSelect5.getValue().equals(ukmSelect4.getValue())) {
            Span pending = new Span("Dalam Proses");
            pending.getElement().getThemeList().add("badge");
            pending.setWidth("120px");
            ul.add(createListItem(ukmSelect5.getValue(),  pending));
        }
        ukmSelect1.setEnabled(false);
        ukmSelect2.setEnabled(false);
        ukmSelect3.setEnabled(false);
        ukmSelect4.setEnabled(false);
        ukmSelect5.setEnabled(false);

        aside.add(headerSection, ul);
        ukmSelect1.setEnabled(false);
        return aside;
    }

    private ListItem createListItem(String primary, Span price) {
        ListItem item = new ListItem();
        item.addClassNames("flex", "justify-between");

        Div subSection = new Div();
        subSection.addClassNames("flex", "flex-col");

        subSection.add(new Span(primary));
        item.add(subSection, price);

        return item;
    }


}
