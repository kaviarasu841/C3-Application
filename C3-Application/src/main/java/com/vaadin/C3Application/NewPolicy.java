package com.vaadin.C3Application;


import com.vaadin.C3Application.service.PolicyDocumentService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("NewPolicy")
@CssImport("./styles/shared-styles.css")
public class NewPolicy extends VerticalLayout {

	    private final Grid<Document> documentGrid = new Grid<>(Document.class, false);
	    private final List<Document> documents = new ArrayList<>();
	    
	    
	    private Dialog parentDialog;

	    public NewPolicy(Dialog dialog) {
	        this.parentDialog = dialog;
	    	
	        addClassName("new-policy-form-view");

	        Label nameLabel = new Label("Name: ");
	        nameLabel.addClassName("form-label");

	        TextField nameField = new TextField();
	        nameField.setValue("Zurich_Policy_Instruction_Form");
	        nameField.addClassName("name-field");

	        HorizontalLayout nameLayout = new HorizontalLayout(nameLabel, nameField);
	        nameLayout.addClassName("name-layout");

	        ComboBox<String> languageComboBox = new ComboBox<>();
	        languageComboBox.setLabel("Language:");
	        languageComboBox.setItems("English","Spanish");
	        languageComboBox.setValue("English");
	        languageComboBox.addClassName("language-combo-box");

	        documentGrid.addColumn(Document::getType).setHeader("Type");
	        documentGrid.addColumn(Document::getName).setHeader("Name");
	        documentGrid.addColumn(Document::getOrder).setHeader("Order");
	        documentGrid.addColumn(Document::getUploadedFile).setHeader("Uploaded file");
	        documentGrid.addThemeVariants(GridVariant. LUMO_COLUMN_BORDERS);
	        documentGrid.addClassName("document-grid");
	        documentGrid.setItems(documents);
	        documentGrid.setMaxHeight("200px");

	        Document initialDocument = new Document("Complete policy", "Zurich_Policy_Instruction_Form", 1, "Zurich_Policy_Instruction_Form");
	        documents.add(initialDocument);

	        Button newDocumentButton = new Button("+ new document...");
	        newDocumentButton.addClickListener(e -> openNewDocumentDialog());
	        newDocumentButton.addClassName("new-document-button");

	     // Create a file upload component
			MemoryBuffer buffer = new MemoryBuffer();
			Upload upload = new Upload(buffer);
			upload.setAcceptedFileTypes(".pdf", ".doc", ".docx");
			upload.setUploadButton(new Button("Click to upload the policy...", event -> {
				

			}));
			upload.getUploadButton().getStyle().set("color", "black");
			/* upload.setDropLabel(new Label("or drop it here")); */
			add(upload);
		
	        HorizontalLayout documentLayout = new HorizontalLayout(newDocumentButton,upload);
	        documentLayout.addClassName("document-layout");

			Button okButton = new Button("Ok");
			okButton.addClickListener(event -> {
				PolicyDocumentService.getInstance().setDocuments(documents);

				// Save logic here (example shown with Notification)
				Notification.show("Policy saved successfully!");
				if (parentDialog != null) {
					parentDialog.close();
				}
				 getUI().ifPresent(ui -> ui.navigate("policy-upload"));
			});
	        Button cancelButton = new Button("Cancel");

	        HorizontalLayout buttonLayout = new HorizontalLayout(okButton, cancelButton);
	        buttonLayout.addClassName("button-layout");

	        FormLayout formLayout = new FormLayout();
	        formLayout.add(nameLayout, documentGrid, documentLayout, languageComboBox, buttonLayout);
	        formLayout.addClassName("form-layout");

	        add(formLayout);
	    }

	    private void openNewDocumentDialog() {
	        Dialog dialog = new Dialog();
	        dialog.setWidth("400px");

	        TextField nameField = new TextField("Name:");
	        nameField.setValue("Zurich_Policy_Instruction_Form");

	        Checkbox completePolicyCheckbox = new Checkbox("COMPLETE_POLICY");
	        Checkbox scheduleCheckbox = new Checkbox("SCHEDULE");
	        Checkbox standardWordingCheckbox = new Checkbox("STANDARD_WORDING");
	        Checkbox endorsementCheckbox = new Checkbox("ENDORSEMENT");

	        VerticalLayout checkboxesLayout = new VerticalLayout(completePolicyCheckbox, scheduleCheckbox, standardWordingCheckbox, endorsementCheckbox);

	        Button addButton = new Button("Ok", e -> {
	            String name = nameField.getValue();
	            List<String> selectedTypes = new ArrayList<>();
	            if (completePolicyCheckbox.getValue()) selectedTypes.add("COMPLETE_POLICY");
	            if (scheduleCheckbox.getValue()) selectedTypes.add("SCHEDULE");
	            if (standardWordingCheckbox.getValue()) selectedTypes.add("STANDARD_WORDING");
	            if (endorsementCheckbox.getValue()) selectedTypes.add("ENDORSEMENT");

	            for (String type : selectedTypes) {
	                Document newDocument = new Document(type, name, documents.size() + 1, name);
	                documents.add(newDocument);
	            }
	            documentGrid.getDataProvider().refreshAll();
	            dialog.close();
	        });

	        Button cancelButton = new Button("Cancel", e -> dialog.close());

	        HorizontalLayout dialogButtonLayout = new HorizontalLayout(addButton, cancelButton);
	        dialogButtonLayout.addClassName("dialog-button-layout");

	        FormLayout dialogFormLayout = new FormLayout(nameField, checkboxesLayout);
	        dialogFormLayout.addClassName("dialog-form-layout");

	        VerticalLayout dialogLayout = new VerticalLayout(dialogFormLayout, dialogButtonLayout);
	        dialogLayout.addClassName("dialog-layout");

	        dialog.add(dialogLayout);
	        dialog.open();
	    }

	    public static class Document {
	        private String type;
	        private String name;
	        private int order;
	        private String uploadedFile;

	        public Document(String type, String name, int order, String uploadedFile) {
	            this.type = type;
	            this.name = name;
	            this.order = order;
	            this.uploadedFile = uploadedFile;
	        }

	        public String getType() {
	            return type;
	        }

	        public String getName() {
	            return name;
	        }

	        public int getOrder() {
	            return order;
	        }

	        public String getUploadedFile() {
	            return uploadedFile;
	        }
	    }
	    @Override
	    protected void onAttach(AttachEvent attachEvent) {
	        super.onAttach(attachEvent);
	        openNewDocumentDialog(); // Open the second dialog once the UI is ready
	    }
	}