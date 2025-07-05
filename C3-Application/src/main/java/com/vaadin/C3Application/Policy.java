package com.vaadin.C3Application;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route("policy")
@CssImport("./styles/shared-styles.css")
public class Policy extends BaseLayout {

    private static final long serialVersionUID = 1L;
    public Policy() {
        super();
        setWidth("100%");
        setPadding(true);
        setSpacing(true);

        // Program details
        HorizontalLayout programDetailsLayout = new HorizontalLayout();
        programDetailsLayout.setSpacing(true);
        programDetailsLayout.setWidthFull();

        VerticalLayout programDetails = new VerticalLayout();
        programDetails.setPadding(true);
        programDetails.setSpacing(false);
        programDetails.setWidth("50%");
        programDetails.add(createDetailLine("Program ID:", "114UK5650"));
        programDetails.add(createDetailLine("Program Name:", "Johnson and Johnson"));
        programDetailsLayout.add(programDetails);

        // Master and Local details
        HorizontalLayout detailsLayout = new HorizontalLayout();
        detailsLayout.setPadding(true);
        detailsLayout.setSpacing(true);
        detailsLayout.setWidthFull();

        FormLayout masterDetails = createMasterDetailsLayout();
        masterDetails.setWidth("100%");

        FormLayout localDetails = createLocalDetailsLayout();
        localDetails.setWidth("100%");
        
        Div text = new Div("Notes:");
        TextArea notesArea = new TextArea();
        notesArea.setWidth("100%");
        notesArea.setHeight("90%");
        notesArea.addClassName("text-area");
        text.setWidth("100%");
        text.add(notesArea);
        
        detailsLayout.add(masterDetails, localDetails, text);
        
     // Add instance of Policy1
        VerticalLayout coversDetails = new VerticalLayout();
        coversDetails.setPadding(false);
        coversDetails.setSpacing(false);
        Policy1 policy1 = new Policy1();
        coversDetails.add(policy1);
    
        // Buttons
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(false);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidthFull();

        Button saveReviewButton = new Button("Save Review");
        saveReviewButton.getStyle().set("background-color", "#e7e7e7").set("color", "black");
        Button rejectButton = new Button("Reject");
        rejectButton.getStyle().set("color", "black");
        rejectButton.getStyle().set("background-color", "#C70039");
        rejectButton.getStyle().set("color", "white");
        Button approveButton = new Button("Approve");
        approveButton.getStyle().set("background-color", "#00563B");
        approveButton.getStyle().set("color", "white");

        buttonsLayout.add(saveReviewButton, rejectButton, approveButton);
        buttonsLayout.setJustifyContentMode(JustifyContentMode.END);
        buttonsLayout.getElement().getStyle().set("width", "92%");
        buttonsLayout.addClassName("policy-button");
        
        HorizontalLayout status = new HorizontalLayout();
        Div reviewStatus = new Div();
        reviewStatus.getElement().setProperty("innerHTML", "Review status: <b>Confirmed</b>");
        reviewStatus.setVisible(false); // Initially hidden
        status.add(reviewStatus);
        status.setJustifyContentMode(JustifyContentMode.END);
        status.getElement().getStyle().set("width", "81.5%");
        status.getElement().getStyle().set("margin-top", "0");
        
        /* Create the confirmation dialog
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Review of IPS data");
        dialog.getElement().setProperty("innerHTML", "Once approved it won't be possible to change the decision. <br>Do you want to proceed?</br>");
        dialog.addClassName("review-dialog");
        dialog.setWidth("500px");
        dialog.setHeight("200px");
        dialog.setConfirmText("Ok");
        dialog.addConfirmListener(event -> {
                notesArea.setReadOnly(true);
                buttonsLayout.setVisible(false);
                reviewStatus.setVisible(true);
            dialog.close();
        });
        dialog.setRejectable(true);
        dialog.setRejectText("Cancel");
        dialog.addRejectListener(event -> dialog.close());*/
        
        // Create the custom dialog of IPS
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Review of IPS data");
        Button closeButton = new Button(new Icon("lumo", "cross"),
                (e) -> dialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        dialog.getHeader().add(closeButton);
        
        VerticalLayout dialogContent = new VerticalLayout();
        dialogContent.setPadding(false);
        dialogContent.add(new Div(new Text("Once approved, it won't be possible to change the decision.")));
        dialogContent.add(new Div(new Text("Do you want to proceed?")));
        
        Button okButton = new Button("Ok", event -> {
            notesArea.setReadOnly(true);
            buttonsLayout.setVisible(false);
            reviewStatus.setVisible(true);
            dialog.close();
        });
        okButton.addClassName("review-okbutton");
        dialog.getFooter().add(okButton);
        Button cancelButton = new Button("Cancel", event -> dialog.close());
        cancelButton.addClassName("review-cancel");
        dialog.getFooter().add(cancelButton);
        dialog.add(dialogContent);
        
        approveButton.addClickListener(event -> dialog.open());
        
            
        // Add all components to the main layout
        add(programDetailsLayout, detailsLayout, buttonsLayout,dialog,status,coversDetails);
        
    }
	private FormLayout createMasterDetailsLayout() {
        FormLayout layout = new FormLayout();
        layout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("100px", 1)
        );
        
        Div header = new Div();
        header.setText("Master");
        header.addClassName("master-local");
        header.getStyle().set("text-align", "center");
        header.getStyle().set("width", "100%");
        layout.add(header);

        layout.add(createDetailLine("IPS Contract:", "MT49673A"));
        layout.add(createDetailLine("Policy Holder:", "Johnson and Johnson"));
        layout.add(createDetailLine("Issuance country:", "Switzerland"));
        layout.add(createDetailLine("Line of business:", "MT"));
        layout.add(createDetailLine("Cognitive available:", "NO"));

        return layout;
    }

    private FormLayout createLocalDetailsLayout() {
        FormLayout layout = new FormLayout();
        layout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("100px", 1)
        );

        Div header = new Div();
        header.setText("Local");
        header.addClassName("master-local");
        header.getStyle().set("text-align", "center");
        header.getStyle().set("width", "100%");
        layout.add(header);

        layout.add(createDetailLine("IPS Contract:", "MT49674A"));
        layout.add(createDetailLine("Policy Holder:", "Synthes GmbH"));
        layout.add(createDetailLine("Issuance country:", "Switzerland"));
        layout.add(createDetailLine("Line of business:", "MT"));
        layout.add(createDetailLine("Cognitive available:", "NO"));

        return layout;
    }

    private HorizontalLayout createDetailLine(String labelText, String valueText) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();

        Div label = new Div();
        label.setText(labelText);
        label.addClassName("policy-label-master");
        label.getStyle().set("width", "150px");
        label.setHeight("30px");

        Div value = new Div();
        value.setText(valueText);
        value.addClassName("policy-value");
        label.getStyle().set("width", "115px");
        layout.add(label, value);
        return layout;
    }
}