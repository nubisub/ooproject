package com.example.application.views.daftarukm;

import com.example.application.data.postgres.Account;
import com.example.application.data.postgres.DaftarUKM;
import com.example.application.data.postgres.Update;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

@PageTitle("Daftar UKM")
@Route(value = "daftarukm", layout = MainLayout.class)
//@RolesAllowed("USER")
public class DaftarUKMView extends Div {
    Update update = new Update();

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toArray()[0].toString();
//        if not admin deny access
        if (!role.equals("ROLE_USER")) {
            UI.getCurrent().getPage().executeJs("window.location.href = '/'");
            return;
        }
        addClassNames("daftar-ukm-view", "flex", "flex-col", "h-full");
        Main content = new Main();
        content.addClassNames("grid", "gap-xl", "items-start", "justify-center", "max-w-screen-xl", "pb-l","px-l");
        content.add(createCheckoutForm());
        add(content);
        if (ukmSelect1.getValue() == null) {
            pay.setEnabled(false);
        }
        if(account.getStatus().equals("1")){
            content.add(createAside());
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

            account.setStatus("1");
                try {
                    update.updateStatus(account.getNim());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
                if (ukmSelect1.getValue() != null){
                    try {
                        update.registrasiUKM(account.getNim(), ukmSelect1.getValue(), 1 );
                    } catch (SQLException err) {
                        throw new RuntimeException(err);
                    } catch (URISyntaxException err) {
                        throw new RuntimeException(err);
                    }
                }
                if (ukmSelect2.getValue() != null && !ukmSelect2.getValue().equals(ukmSelect1.getValue())){
                    try {
                        update.registrasiUKM(account.getNim(), ukmSelect2.getValue(), 2 );
                    } catch (SQLException err) {
                        throw new RuntimeException(err);
                    } catch (URISyntaxException err) {
                        throw new RuntimeException(err);
                    }
                }
                if (ukmSelect3.getValue() != null && !ukmSelect3.getValue().equals(ukmSelect1.getValue()) && !ukmSelect3.getValue().equals(ukmSelect2.getValue())){
                    try {
                        update.registrasiUKM(account.getNim(), ukmSelect3.getValue(), 3 );
                    } catch (SQLException err) {
                        throw new RuntimeException(err);
                    } catch (URISyntaxException err) {
                        throw new RuntimeException(err);
                    }
                }
                if (ukmSelect4.getValue() != null && !ukmSelect4.getValue().equals(ukmSelect1.getValue()) && !ukmSelect4.getValue().equals(ukmSelect2.getValue()) && !ukmSelect4.getValue().equals(ukmSelect3.getValue())){
                    try {
                        update.registrasiUKM(account.getNim(), ukmSelect4.getValue(), 4 );
                    } catch (SQLException err) {
                        throw new RuntimeException(err);
                    } catch (URISyntaxException err) {
                        throw new RuntimeException(err);
                    }
                }
                if (ukmSelect5.getValue() != null && !ukmSelect5.getValue().equals(ukmSelect1.getValue()) && !ukmSelect5.getValue().equals(ukmSelect2.getValue()) && !ukmSelect5.getValue().equals(ukmSelect3.getValue()) && !ukmSelect5.getValue().equals(ukmSelect4.getValue())){
                    try {
                        update.registrasiUKM(account.getNim(), ukmSelect5.getValue(), 5);
                    } catch (SQLException err) {
                        throw new RuntimeException(err);
                    } catch (URISyntaxException err) {
                        throw new RuntimeException(err);
                    }
                }

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
        String ukm1 = account.getUkm1();
        if (!ukm1.equals("")) {
            ukmSelect1.setValue(ukm1);
        }
        ukmSelect1.addValueChangeListener(e -> {
            if (ukmSelect1.getValue() != null) {
                pay.setEnabled(true);
            }
                });


        ukmSelect2.setRequiredIndicatorVisible(true);
        ukmSelect2.addClassNames("mb-s");
        ukmSelect2.setItems(ukm);
        String ukm2 = account.getUkm2();
        if (!ukm2.equals("")) {
            ukmSelect2.setValue(ukm2);
        }



        ukmSelect3.setRequiredIndicatorVisible(true);
        ukmSelect3.addClassNames("mb-s");
        ukmSelect3.setItems(ukm);
        String ukm3 = account.getUkm3();
        if (!ukm3.equals("")) {
            ukmSelect3.setValue(ukm3);
        }


        ukmSelect4.setRequiredIndicatorVisible(true);
        ukmSelect4.addClassNames("mb-s");
        ukmSelect4.setItems(ukm);
        String ukm4 = account.getUkm4();
        if (!ukm4.equals("")) {
            ukmSelect4.setValue(ukm4);
        }



        ukmSelect5.setRequiredIndicatorVisible(true);
        ukmSelect5.addClassNames("mb-s");
        ukmSelect5.setItems(ukm);
        String ukm5 = account.getUkm5();
        if (!ukm5.equals("")) {
            ukmSelect5.setValue(ukm5);
        }
        if (account.getStatus().equals("1")){
            ukmSelect1.setEnabled(false);
            ukmSelect2.setEnabled(false);
            ukmSelect3.setEnabled(false);
            ukmSelect4.setEnabled(false);
            ukmSelect5.setEnabled(false);
            pay.setEnabled(false);
        }

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

//        confirmed.getElement().getThemeList().add("badge success");
//        confirmed.setWidth("120px");
//
//        rejected.getElement().getThemeList().add("badge error");
//        rejected.setWidth("120px");

        ul.addClassNames("list-none", "m-10", "p-0", "flex", "flex-col", "gap-m");

        if (ukmSelect1.getValue() != null) {

            Span badge = new Span();

            if (account.getUkm1Status() == 0){
                badge.setText("Dalam Proses");
                badge.getElement().getThemeList().add("badge");
                badge.setWidth("120px");
            } else if (account.getUkm1Status() == 1){
                badge.setText("Diterima");
                badge.getElement().getThemeList().add("badge success");
                badge.setWidth("120px");
            } else if (account.getUkm1Status() == 2){
                badge.setText("Ditolak");
                badge.getElement().getThemeList().add("badge error");
                badge.setWidth("120px");
            }


            account.setUkm1(ukmSelect1.getValue());
            ul.add(createListItem(ukmSelect1.getValue(),  badge));
        }
        if (ukmSelect2.getValue() != null && !ukmSelect2.getValue().equals(ukmSelect1.getValue())) {
            account.setUkm2(ukmSelect2.getValue());
            Span badge = new Span();
            if (account.getUkm2Status() == 0){
                badge.setText("Dalam Proses");
                badge.getElement().getThemeList().add("badge");
                badge.setWidth("120px");
            } else if (account.getUkm2Status() == 1){
                badge.setText("Diterima");
                badge.getElement().getThemeList().add("badge success");
                badge.setWidth("120px");
            } else if (account.getUkm2Status() == 2){
                badge.setText("Ditolak");
                badge.getElement().getThemeList().add("badge error");
                badge.setWidth("120px");
            }

            ul.add(createListItem(ukmSelect2.getValue(),  badge));
        }
        if (ukmSelect3.getValue() != null && !ukmSelect3.getValue().equals(ukmSelect1.getValue()) && !ukmSelect3.getValue().equals(ukmSelect2.getValue())) {
            account.setUkm3(ukmSelect3.getValue());
            Span badge = new Span();
            if (account.getUkm3Status() == 0){
                badge.setText("Dalam Proses");
                badge.getElement().getThemeList().add("badge");
                badge.setWidth("120px");
            } else if (account.getUkm3Status() == 1){
                badge.setText("Diterima");
                badge.getElement().getThemeList().add("badge success");
                badge.setWidth("120px");
            } else if (account.getUkm3Status() == 2){
                badge.setText("Ditolak");
                badge.getElement().getThemeList().add("badge error");
                badge.setWidth("120px");
            }

            ul.add(createListItem(ukmSelect3.getValue(),  badge));
        }
        if (ukmSelect4.getValue() != null && !ukmSelect4.getValue().equals(ukmSelect1.getValue()) && !ukmSelect4.getValue().equals(ukmSelect2.getValue()) && !ukmSelect4.getValue().equals(ukmSelect3.getValue())) {
            account.setUkm4(ukmSelect4.getValue());
            Span badge = new Span();
            if (account.getUkm4Status() == 0){
                badge.setText("Dalam Proses");
                badge.getElement().getThemeList().add("badge");
                badge.setWidth("120px");
            } else if (account.getUkm4Status() == 1){
                badge.setText("Diterima");
                badge.getElement().getThemeList().add("badge success");
                badge.setWidth("120px");
            } else if (account.getUkm4Status() == 2){
                badge.setText("Ditolak");
                badge.getElement().getThemeList().add("badge error");
                badge.setWidth("120px");
            }

            ul.add(createListItem(ukmSelect4.getValue(),  badge));
        }
        if (ukmSelect5.getValue() != null && !ukmSelect5.getValue().equals(ukmSelect1.getValue()) && !ukmSelect5.getValue().equals(ukmSelect2.getValue()) && !ukmSelect5.getValue().equals(ukmSelect3.getValue()) && !ukmSelect5.getValue().equals(ukmSelect4.getValue())) {
            account.setUkm5(ukmSelect5.getValue());
            Span badge = new Span();
            if (account.getUkm5Status() == 0){
                badge.setText("Dalam Proses");
                badge.getElement().getThemeList().add("badge");
                badge.setWidth("120px");
            } else if (account.getUkm5Status() == 1){
                badge.setText("Diterima");
                badge.getElement().getThemeList().add("badge success");
                badge.setWidth("120px");
            } else if (account.getUkm5Status() == 2){
                badge.setText("Ditolak");
                badge.getElement().getThemeList().add("badge error");
                badge.setWidth("120px");
            }

            ul.add(createListItem(ukmSelect5.getValue(),  badge));
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
