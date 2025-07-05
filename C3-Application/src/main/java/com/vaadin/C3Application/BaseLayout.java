package com.vaadin.C3Application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.server.VaadinSession;

@CssImport("./styles/shared-styles.css")
public class BaseLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public BaseLayout() {
        setSizeFull();
        createHeader();
        createNavBar();
    }

    private void createHeader() {
        final Div header1 = new Div();
        header1.getStyle().set("flexShrink", "0");
        header1.setClassName("header");
        header1.setWidthFull();
        header1.setHeight("60px");
        header1.getStyle().set("display", "flex");
        header1.getStyle().set("margin-left", "10px");
        header1.getStyle().set("alignItems", "center");

        Image logo = new Image("images/zurich.png", "Zurich");
        logo.setWidth("90px");
        logo.getStyle().set("marginRight", "5px");

        Div title = new Div();
        title.setText("Contract Certainty Cockpit");
        title.setClassName("header-title");

        Image logocc = new Image("images/ccc.jpg", "CCC");
        logocc.setHeight("64px");
        logocc.getStyle().set("marginLeft", "40px");

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(logo, title, logocc);
        headerLayout.setPadding(false);
        headerLayout.setSpacing(false);
        headerLayout.setMargin(false);

        header1.add(headerLayout);

        add(header1);
    }

    private void createNavBar() {
        String username = VaadinSession.getCurrent().getAttribute("username").toString().toLowerCase();
        String currentPath = UI.getCurrent().getInternals().getLastHandledLocation().getPath();

        HorizontalLayout navBar = new HorizontalLayout();
        navBar.setWidthFull();
        navBar.setAlignItems(Alignment.CENTER);
        navBar.getStyle().set("border-bottom", "1px solid #ccc");

        HorizontalLayout navBarContent = new HorizontalLayout();
        navBarContent.setWidthFull();
        navBarContent.setPadding(false);
        navBarContent.setSpacing(true);
        navBarContent.getStyle().set("margin-left", "10px");
        navBarContent.setAlignItems(Alignment.CENTER);

        Anchor homeLink = createStyledLink("home", "Home", currentPath);
        Anchor historyLink = createStyledLink("history", "History", currentPath);
        Anchor helpLink = createStyledLink("help", "Help", currentPath);

        // User section
        HorizontalLayout userLayout = new HorizontalLayout();
        Span user = new Span("User: " + username);
        Icon logoutIcon = VaadinIcon.SIGN_OUT.create();
        logoutIcon.setSize("22px");
        Button logoutButton = new Button("Logout", logoutIcon);
        logoutButton.addClassName("logoutbutton");

        ProgressBar progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);

        logoutButton.addClickListener(event -> {
            progressBar.setVisible(true);
            getUI().ifPresent(ui -> ui.access(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                ui.navigate("");
            }));
        });

        userLayout.setAlignItems(Alignment.BASELINE);
        user.getStyle().set("line-height", "36px");
        
        userLayout.add(user, logoutButton);
        userLayout.setSpacing(true);
        userLayout.getElement().getStyle().set("margin-left", "auto");

        navBarContent.add(homeLink, historyLink, helpLink, userLayout);
        navBar.add(navBarContent);
        add(navBar);
    }

    private Anchor createStyledLink(String target, String label, String currentPath) {
        Anchor link = new Anchor(target, label);
        link.getElement().getStyle().set("text-decoration", "underline");
        link.getElement().getStyle().set("color", "#00005a");

        // Normalize current path and target
        String normalizedPath = currentPath == null ? "" : currentPath.trim().toLowerCase();
        String normalizedTarget = target.trim().toLowerCase();

        // Highlight only if paths match exactly
        if (normalizedPath.equals(normalizedTarget)) {
            link.getStyle()
                .set("background-color", "#e0ecf9")
                .set("border", "1px solid #00005a")
                .set("border-radius", "6px")
                .set("padding", "4px 8px");
        }

        return link;
    }


}


