package com.vaadin.C3Application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("")
@CssImport("./styles/shared-styles.css")

public class LoginView extends VerticalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginView() {
		
		setSizeFull();
		createHeader();
		createLogin();
		
	}

	private void createHeader() {

		final Div header = new Div();
		header.getStyle().set("flexShrink", "0");
		header.setClassName("header");
		header.setHeight("100px");
		header.getStyle().set("display", "flex");
		header.getStyle().set("alignItems", "center");
		header.getStyle().set("justifyContent", "center");

		// Create the image component
		Image logo = new Image("images/zurich.png", "Zurich");
		logo.setWidth("90px");
		logo.getStyle().set("marginRight", "10px");

		// Create the text component
		Div title = new Div();
		title.setText("Contract Certainty Cockpit");
		title.setClassName("header-title");

		Image logocc = new Image("images/ccc.jpg", "CCC");
		logocc.setHeight("64px");
		logocc.getStyle().set("marginLeft", "40px");

		// Create a horizontal layout to contain the image and title
		HorizontalLayout headerLayout = new HorizontalLayout();
		headerLayout.setAlignItems(Alignment.CENTER);
		headerLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		headerLayout.add(logo, title, logocc);
		headerLayout.setSpacing(true);

		header.add(headerLayout);
		add(header);
	}
	
	private void createLogin(){
        
        TextField name = new TextField("Username");
        name.setRequired(true);
        PasswordField password = new PasswordField("Password");
        password.setRequired(true);
        ComboBox<String> domain = new ComboBox<>("Domain");
        domain.setItems("","AZCORP","EZCORP","PZCORP","GITDIR");
        domain.setRequired(true);
     
        Span msg = new Span("Please use your GAD credentials");
        msg.addClassName("login-msg");

        Button login = new Button("Login");
        login.addClassName("login-button");
        
        login.addClickListener(event -> {
            if (name.isEmpty() || password.isEmpty() || domain.isEmpty()) {
            	Notification notification = Notification.show("Some required information are missing!", 3000, Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
            } else {
                // Handle the login logic here
            	Notification notification = Notification.show("Login Successful", 3000, Notification.Position.MIDDLE);
            	 notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            	// Store the user name in the session
                 VaadinSession.getCurrent().setAttribute("username", name.getValue());
            	 UI.getCurrent().navigate("home");
            }
        });
        
        Div line1 = new Div();
        line1.setText("You are accessing the Zurich Insurance application Contract Certainty Cockpit (CCI).");
        line1.getStyle().set("margin-top", "40px").set("font-size", "14px");

        Div line2 = new Div();
        line2.setText("The application is intended solely for internal use by the authorized employees of Zurich Insurance Group and its affiliated entities (Zurich)");
        line2.getStyle().set("font-size", "12px").set("font-style", "italic");
		
        
        VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setAlignItems(Alignment.CENTER);
        loginLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        loginLayout.add(name,password,domain,msg,login,line1,line2);
        loginLayout.setSpacing(false);
        add(loginLayout);
	}
    
}
		
