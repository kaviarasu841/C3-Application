package com.vaadin.C3Application;

import java.util.*;
import com.vaadin.C3Application.MasterPolicyPage.PolicyDocument;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;

@Route("document")
@CssImport("./styles/shared-styles.css")
public class Document extends BaseLayout {
	
	private List<DocumentDetails> documentDetailsList = new ArrayList<>();


    private static final long serialVersionUID = 1L;

    public Document() {
        super();
        setWidth("100%");
        setPadding(true);
        setSpacing(true);

        // Program details
        VerticalLayout programDetails = createProgramDetailsLayout();
        programDetails.setWidthFull();

        VerticalLayout contractDetails = createContractDetailsLayout();
        contractDetails.setWidthFull();

        Span program = new Span("Program:");
        program.getElement().getStyle().set("font-weight", "bold");
        Span contract = new Span("Contract:");
        contract.getElement().getStyle().set("font-weight", "bold");

        Span policy = new Span("Policy:");
        policy.getElement().getStyle().set("font-weight", "bold");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".pdf", ".doc", ".docx");
        upload.setUploadButton(new Button("Click to upload the policy..."));
        upload.getUploadButton().getStyle().set("color", "black");
        Icon dropIcon = VaadinIcon.CLOUD_UPLOAD_O.create();
        upload.setDropLabel(new Span("or drop it here"));
        upload.setDropLabelIcon(dropIcon);

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            openNewPolicyDialog(fileName);
        });

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add(program, programDetails, contract, contractDetails, policy, upload);
        mainLayout.setPadding(false);
        add(mainLayout);
    }

    private void openNewPolicyDialog(String fileName) {
        Dialog newPolicyDialog = new Dialog();
        newPolicyDialog.setHeaderTitle("New Document");
        newPolicyDialog.setDraggable(true);

        Button maxButton = new Button(new Icon("lumo", "plus"), (e) -> newPolicyDialog.setSizeFull());
        Button closeButton = new Button(new Icon("lumo", "cross"), (e) -> newPolicyDialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        maxButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        newPolicyDialog.getHeader().add(maxButton,closeButton);

        TextField nameField = new TextField("Name");
        nameField.setValue(fileName);
        nameField.setRequired(true);
        nameField.setWidthFull();
        
        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Document types");
        checkboxGroup.setItems("Complete policy", "Schedule", "Standard wording", "Endorsement");
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        checkboxGroup.setRequired(true);
        
        Button okButton = new Button("Ok", event -> {
            newPolicyDialog.close();
            String selectedType = checkboxGroup.getValue().iterator().next();
            addDocumentDetails(selectedType, fileName);
            openDetailedPolicyDialog();
        });
        okButton.addClassName("review-okbutton");
        newPolicyDialog.getFooter().add(okButton);
        Button cancelButton = new Button("Cancel", event -> newPolicyDialog.close());
        cancelButton.addClassName("review-cancel");
        newPolicyDialog.getFooter().add(cancelButton);
        newPolicyDialog.add(nameField, checkboxGroup);

        newPolicyDialog.setWidth("500px");
        newPolicyDialog.setHeight("400px");

        newPolicyDialog.open();
    }

    private void openDetailedPolicyDialog() {
        Dialog detailedPolicyDialog = new Dialog();
        detailedPolicyDialog.setHeaderTitle("New Policy");
        detailedPolicyDialog.setDraggable(true);


        TextField nameField = new TextField("Name");
        nameField.setRequired(true);
        nameField.setWidthFull();

        Grid<DocumentDetails> grid = new Grid<>(DocumentDetails.class);
        grid.removeAllColumns();
        grid.setItems(documentDetailsList);
        grid.addColumn(DocumentDetails::getTypes).setHeader("Types").setResizable(true).setSortable(true);
        grid.addColumn(DocumentDetails::getName).setHeader("Name").setResizable(true).setSortable(true);
        grid.addColumn(DocumentDetails::getOrder).setHeader("Order").setResizable(true).setSortable(true);
        grid.addColumn(DocumentDetails::getUploadedFile).setHeader("Uploaded file").setResizable(true).setSortable(true);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeight("150px");

        Button newDocumentButton = new Button("new document...");
        newDocumentButton.setIcon(new Icon("lumo", "plus"));

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".pdf", ".doc", ".docx");
        upload.setUploadButton(newDocumentButton);
        Icon dropIcon = VaadinIcon.CLOUD_UPLOAD_O.create();
        upload.setDropLabel(new Span("or drop it here"));
        upload.setDropLabelIcon(dropIcon);
        
        upload.addSucceededListener(event -> {
            String newFileName = event.getFileName();
            openNewPolicyDialog(newFileName);
        });

        Select<String> languageSelect = new Select<>();
        languageSelect.setLabel("Language");
        languageSelect.setItems("English", "Other");
        languageSelect.setWidth("150px");
        languageSelect.setRequiredIndicatorVisible(true);

        Button okButton = new Button("Ok", event -> {
            detailedPolicyDialog.close();
        });
        okButton.addClassName("review-okbutton");
        detailedPolicyDialog.getFooter().add(okButton);
        Button cancelButton = new Button("Cancel", event -> detailedPolicyDialog.close());
        cancelButton.addClassName("review-cancel");
        detailedPolicyDialog.getFooter().add(cancelButton);

        detailedPolicyDialog.add(new HorizontalLayout(nameField), new Div(new Label("Documents:")), grid, new Div(upload, languageSelect));
        detailedPolicyDialog.setWidth("600px");
        detailedPolicyDialog.setHeight("500px");

        detailedPolicyDialog.open();
    }

    private void addDocumentDetails(String type, String fileName) {
        int order = documentDetailsList.size() + 1;
        documentDetailsList.add(new DocumentDetails(type, fileName, String.valueOf(order), fileName));
    }


    private VerticalLayout createProgramDetailsLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);

        layout.add(createDetailLine("Program ID:", "114UK5650"));
        layout.add(createDetailLine("Program Name:", "Johnson and Johnson"));
        return layout;
    }

    private VerticalLayout createContractDetailsLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);

        layout.add(createDetailLine("IPS Contract:", "MT49673A"));
        layout.add(createDetailLine("Policy Holder:", "Johnson and Johnson"));
        layout.add(createDetailLine("Issuance country:", "Switzerland"));
        layout.add(createDetailLine("Line of business:", "MT"));

        return layout;
    }

    private HorizontalLayout createDetailLine(String labelText, String valueText) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();

        Div label = new Div();
        label.setText(labelText);
        label.addClassName("label-master");
        label.getStyle().set("width", "150px");

        Div value = new Div();
        value.setText(valueText);
        value.addClassName("value");

        layout.add(label, value);
        return layout;
    }

    public static class DocumentDetails {
        private String types;
        private String name;
        private String order;
        private String uploadedFile;

        public DocumentDetails(String types, String name, String order, String uploadedFile) {
            this.types = types;
            this.name = name;
            this.order = order;
            this.uploadedFile = uploadedFile;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getUploadedFile() {
            return uploadedFile;
        }

        public void setUploadedFile(String uploadedFile) {
            this.uploadedFile = uploadedFile;
        }
    }
}
