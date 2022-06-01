package com.example.application.views;

import com.example.application.data.postgres.Account;
import com.example.application.security.SecurityService;
import com.example.application.views.AdminView.AdminView;
import com.example.application.views.daftarukm.DaftarUKMView;
import com.example.application.views.profile.ProfileView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class MainLayout extends AppLayout {

    private final SecurityService securityService;
    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, String iconClass, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            link.addClassNames("flex", "h-m", "items-center", "px-s", "relative", "text-secondary");
            link.setRoute(view);

            Span text = new Span(menuTitle);
            text.addClassNames("font-medium", "text-s", "whitespace-nowrap");

            link.add(new LineAwesomeIcon(iconClass), text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

        @NpmPackage(value = "line-awesome", version = "1.3.0")
        public static class LineAwesomeIcon extends Span {
            public LineAwesomeIcon(String lineawesomeClassnames) {
                // Use Lumo classnames for suitable font size and margin
                addClassNames("me-s", "text-l");
                if (!lineawesomeClassnames.isEmpty()) {
                    addClassNames(lineawesomeClassnames);
                }
            }
        }

    }

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "flex-col", "w-full");

        Div layout = new Div();
        layout.addClassNames("flex", "h-xl", "items-center", "px-l","pt-s");

        H1 appName = new H1("Pilih UKM Yuk");
        appName.addClassNames("my-0", "me-auto", "text-xl");
        layout.add(appName);

        Nav nav = new Nav();
        nav.addClassNames("flex", "gap-s", "overflow-auto", "px-m");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("flex", "list-none", "m-0", "p-0");
        nav.add(list);
        Button logout = new Button("Log out", e -> securityService.logout());
        layout.add(logout);
        for (MenuItemInfo menuItem : createMenuItems()) {
            list.add(menuItem);
        }
        header.add(layout, nav);
        return header;
    }

    private MenuItemInfo[] createMenuItems() {
        Account account = Account.getInstance();
        account.setNim(SecurityContextHolder.getContext().getAuthentication().getName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toArray()[0].toString();
        if (role.equals("ROLE_USER")) {
            return new MenuItemInfo[]{
                    new MenuItemInfo("Profile", "la la-user", ProfileView.class), //
                    new MenuItemInfo("Daftar UKM", "la la-swimmer", DaftarUKMView.class), //
            };

        }
        else{

            return new MenuItemInfo[]{
                    new MenuItemInfo("Profile", "la la-user", ProfileView.class), //
                    new MenuItemInfo("Admin", "la la-swimmer", AdminView.class), //
            };
        }
    }
}

