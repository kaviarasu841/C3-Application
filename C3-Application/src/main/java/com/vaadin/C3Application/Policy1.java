package com.vaadin.C3Application;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("policy1")
@CssImport("./styles/shared-styles.css")
public class Policy1 extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private String[] generalElements = {"Period From: 01-Jan-2024", "Period From Time: 00:00:00 UTC", "Period To: 31-Dec-2024", "Period To Time: 00:00:00 UTC"};
    private String[] covers = {"Bodily injury", "Additional Insured - Limited Form", "Care, Custody and Control", "Defense / legal cost in addition", "Main Constraint"};
    private String[] insuredCompanies = {"TestingLA1", "Company 1", "Company 2"};
    private String[] exclusions = {"Alcoholic beverages (for exposures in USA)","Asbestos","Aviation and Space Risks",
            "Diacetyl","Electromagnetic Fields (EMF) / Radio Frequency (RF)","HIV","Human Pharmaceutical Products"};

    private HorizontalLayout currentLayout;

    public Policy1() {
        setWidth("100%");
        setPadding(false);
        setSpacing(false);

        // Split layout for master and local data
        VerticalLayout masterDataLayout = createMasterDataLayout();
        VerticalLayout localDataLayout = createLocalDataLayout();
        SplitLayout masterAndLocalLayout = new SplitLayout();
        masterAndLocalLayout.addToPrimary(masterDataLayout);
        masterAndLocalLayout.addToSecondary(localDataLayout);
        masterAndLocalLayout.setSplitterPosition(40);
        masterAndLocalLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        masterAndLocalLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        masterAndLocalLayout.setWidthFull();
        add(masterAndLocalLayout);
    }

    private VerticalLayout createMasterDataLayout() {
        VerticalLayout masterDataLayout = new VerticalLayout();
        masterDataLayout.setWidthFull();
        Div masterDataHeader = new Div();
        masterDataHeader.setText("Master data:");
        masterDataHeader.getElement().getStyle().set("fontWeight", "bold");
        masterDataLayout.add(masterDataHeader);

        // Create dropdowns for IPS data
        masterDataLayout.add(createDropdownButton("General Elements", false));
        masterDataLayout.add(createDropdownButton("Covers", false));
        masterDataLayout.add(createDropdownButton("Exclusions", false));
        masterDataLayout.add(createDropdownButton("Insured Companies", false));
        return masterDataLayout;
    }

    private VerticalLayout createLocalDataLayout() {
        VerticalLayout localDataLayout = new VerticalLayout();
        Div localDataHeader = new Div();
        localDataHeader.setText("Local data:");
        localDataHeader.getElement().getStyle().set("fontWeight", "bold");
        localDataLayout.add(localDataHeader);

        // Create dropdowns for IPS data
        localDataLayout.add(createDropdownButton("General Elements", true));
        localDataLayout.add(createDropdownButton("Covers", true));
        localDataLayout.add(createDropdownButton("Exclusions", true));
        localDataLayout.add(createDropdownButton("Insured Companies", true));
        return localDataLayout;
    }

    private Details createDropdownButton(String title, boolean editable) {
        Details details = new Details();
        details.setSummaryText(title + " (" + getElementCount(title) + " elements)");
        FormLayout contentLayout = new FormLayout();
        contentLayout.setWidth("100%");
        details.addClassName("dropdown-button");

        if (title.equals("General Elements")) {
            createGeneralElementsContent(contentLayout, editable);
        } else if (title.equals("Covers")) {
            addCoversContent(contentLayout, editable);
        } else if (title.equals("Insured Companies")) {
            addInsuredCompaniesContent(contentLayout, editable);
        } else if (title.equals("Exclusions")) {
            addExclusionsContent(contentLayout, editable);
        }

        details.setSummaryText(title + " (" + getElementCount(title) + " elements)");
        contentLayout.getStyle().set("font-size", "13px");

        details.setContent(contentLayout);
        return details;
    }

    private void createGeneralElementsContent(FormLayout contentLayout, boolean editable) {
        contentLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("100px", 1));
        contentLayout.setWidth("150%");

        for (String element : generalElements) { 
            contentLayout.add(createContentLayout(element, editable));
            contentLayout.add(createSeparator());
        }
    }

    private void addExclusionsContent(FormLayout contentLayout, boolean editable) {
        contentLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("100px", 1));
        contentLayout.setWidth("150%");

        for (String element : exclusions) {
            contentLayout.add(createContentLayout(element, editable));
            contentLayout.add(createSeparator());
        }
    }

    private void addCoversContent(FormLayout contentLayout, boolean editable) {
        contentLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("100px", 1));

        String[] indicators = {"No", "No", "No", "Yes", "No"};

        for (int i = 0; i < covers.length; i++) {
            contentLayout.add(createCoverItemLayout(covers[i], indicators[i], editable));
            contentLayout.add(createSeparator());
        }
    }

    private VerticalLayout createCoverItemLayout(String title, String indicator, boolean editable) {
        VerticalLayout itemLayout = new VerticalLayout();
        itemLayout.setSpacing(false);
        itemLayout.setPadding(true);

        HorizontalLayout titleLayout = new HorizontalLayout();
        titleLayout.setAlignItems(Alignment.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.getElement().getStyle().set("font-weight", "bold");
        titleLayout.add(titleLabel);

        Icon editIcon = createEditIcon(editable);
        editIcon.addClickListener(event -> {
            currentLayout = titleLayout;
            createNotesDialog();
        });

        titleLayout.add(editIcon);

        itemLayout.add(titleLayout);

        VerticalLayout tableContainer = new VerticalLayout();
        tableContainer.setPadding(true); // Add padding to the container
        tableContainer.setSpacing(false);
        // Create table-like layout with rows
        VerticalLayout table = new VerticalLayout();
        table.getStyle().set("border", "1px solid #ccc");
        table.setWidth("120px");
        table.setPadding(false);
        table.setSpacing(false);

        HorizontalLayout row1 = new HorizontalLayout();
        row1.add(new Label("Sub Limit Indicator"));
        row1.setWidthFull();
        row1.getStyle().set("border-bottom", "1px solid #ccc");
        row1.getStyle().set("padding", "5px");

        HorizontalLayout row2 = new HorizontalLayout();
        Label indicatorLabel = new Label(indicator);
        row2.add(indicatorLabel);
        row2.setWidthFull();
        row2.getStyle().set("padding", "5px");

        table.add(row1, row2);
        tableContainer.add(table);
        itemLayout.add(tableContainer);

        return itemLayout;
    }

    private void addInsuredCompaniesContent(FormLayout contentLayout, boolean editable) {
        for (String company : insuredCompanies) {
            contentLayout.add(createContentLayout(company, editable));
            contentLayout.add(createSeparator()); // Add separator after each item
        }
    }

    private VerticalLayout createContentLayout(String company, boolean editable) {
        VerticalLayout companyLayout = new VerticalLayout();
        companyLayout.setSpacing(false);
        companyLayout.setPadding(true);

        HorizontalLayout titleLayout = new HorizontalLayout();
        titleLayout.setAlignItems(Alignment.CENTER);

        Label companyLabel = new Label(company);
        titleLayout.add(companyLabel);

        if (editable) {
            Icon editIcon = createEditIcon(true);
            editIcon.addClickListener(event -> {
                currentLayout = titleLayout;
                createNotesDialog();
            });
            titleLayout.add(editIcon);
        }

        companyLayout.add(titleLayout);
        return companyLayout;
    }

    private Icon createEditIcon(boolean editable) {
        Icon editIcon = new Icon(VaadinIcon.PENCIL);
        editIcon.setSize("14px");
        if (!editable) {
            editIcon.getElement().getStyle().set("display", "none");
        }
        editIcon.getStyle().set("cursor", "pointer");
        return editIcon;
    }

    private int getElementCount(String title) {
        switch (title) {
            case "General Elements":
                return generalElements.length;
            case "Covers":
                return covers.length;
            case "Insured Companies":
                return insuredCompanies.length;
            case "Exclusions":
                return exclusions.length;
            default:
                return 0;
        }
    }

    private HorizontalLayout createSeparator() {
        HorizontalLayout separator = new HorizontalLayout();
        separator.setWidth("100%");
        separator.getElement().getStyle().set("border-bottom", "1px solid #ccc");
        separator.setSpacing(false);
        separator.setPadding(false);
        return separator;
    }

    private void createNotesDialog() {
        Dialog notesDialog = new Dialog();
        notesDialog.setWidth("500px");
        notesDialog.setHeight("300px");
        notesDialog.setHeaderTitle("Notes");
        notesDialog.setMaxHeight("100%");
        notesDialog.setMaxWidth("100%");
        // Close button
        Button maxButton = new Button(new Icon("lumo", "plus"), (e) -> notesDialog.setSizeFull());
        Button closeButton = new Button(new Icon("lumo", "cross"), (e) -> notesDialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        maxButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        notesDialog.getHeader().add(maxButton,closeButton);

        // Text area for notes
        TextArea notesTextArea = new TextArea();
        notesTextArea.setWidth("100%");
        notesTextArea.setMinHeight("130px");
        notesTextArea.setMaxHeight("130px");

        // Retain existing notes content
        if (currentLayout != null) {
            notesTextArea.setValue(getNotesFromCurrentLayout(currentLayout));
        }

        // "Ok" button to save notes
        Button okButton = new Button("Ok", event -> {
            String notes = notesTextArea.getValue();

            if (currentLayout != null && !notes.isBlank()) {
                // Check if notes label already exists and update it
                Label existingNotesLabel = (Label) currentLayout.getChildren()
                        .filter(component -> component instanceof Label && ((Label) component).getText().startsWith("Notes:"))
                        .findFirst()
                        .orElse(null);

                if (existingNotesLabel != null) {
                    existingNotesLabel.setText("Notes: " + notes);
                } else {
                    Label notesLabel = new Label("Notes: " + notes);
                    notesLabel.getStyle().set("color", "#00005a");
                    currentLayout.add(notesLabel);
                }
            }
            notesDialog.close();
        });
        okButton.addClassName("review-okbutton");

        // "Cancel" button to close dialog
        Button cancelButton = new Button("Cancel", event -> notesDialog.close());
        cancelButton.addClassName("review-cancel");

        // "Remove All" button to clear notes
        Button removeAllButton = new Button("Remove notes", event -> {
        
            notesTextArea.clear();
            if (currentLayout != null) {
                // Remove the existing notes label if present
                currentLayout.getChildren()
                        .filter(component -> component instanceof Label && ((Label) component).getText().startsWith("Notes:"))
                        .findFirst()
                        .ifPresent(currentLayout::remove);
            }
            notesDialog.close();
        });
        removeAllButton.getStyle().set("background-color", "#FF4433");
        removeAllButton.getStyle().set("color", "white");
        removeAllButton.getStyle().set("border", "none");
        removeAllButton.getStyle().set("cursor", "pointer");
        
        if (currentLayout != null && !getNotesFromCurrentLayout(currentLayout).isEmpty()) {
            notesDialog.getFooter().add(removeAllButton);
        }
        
        // Add buttons to dialog footer
        notesDialog.getFooter().add(okButton, cancelButton);
        notesDialog.add(notesTextArea);

        notesDialog.open();
    }

    private String getNotesFromCurrentLayout(HorizontalLayout layout) {
        return layout.getChildren()
                .filter(component -> component instanceof Label && ((Label) component).getText().startsWith("Notes:"))
                .map(component -> ((Label) component).getText().substring(7)) // Remove "Notes: " prefix
                .findFirst()
                .orElse("");
    }
    
}