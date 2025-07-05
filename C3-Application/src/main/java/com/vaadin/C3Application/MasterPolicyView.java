package com.vaadin.C3Application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;

@Route("MasterPolicy")
@CssImport("./styles/shared-styles.css")
public class MasterPolicyView extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	public MasterPolicyView() {
		createContent();
	}
	

	private void createContent() {
		// Create labels for displaying policy information
		Label program = new Label("Program");
		program.getElement().getStyle().set("fontWeight", "bold");
		add(program);
		add(createPolicyInfo("Program ID:", "114UK550"));
		add(createPolicyInfo("Program Name:", "Johnson and Johnson"));
		Label contract = new Label("Contract");
		contract.getElement().getStyle().set("fontWeight", "bold");
		add(contract);
		add(createPolicyInfo("IPS Contract:", "MT49673A"));
		add(createPolicyInfo("Policy Holder:", "Johnson and Johnson"));
		add(createPolicyInfo("Issuance Country:", "Switzerland"));
		add(createPolicyInfo("Line of Business:", "MT"));

		// Create a label for policy section
		Label policyLabel = new Label("Policy:");
		policyLabel.addClassName("section-label");
		add(policyLabel);

		// Create a file upload component
		MemoryBuffer buffer = new MemoryBuffer();
		Upload upload = new Upload(buffer);
		upload.setAcceptedFileTypes(".pdf", ".doc", ".docx");
		upload.setUploadButton(new Button("Click to upload the policy...", event -> {
			Dialog policyDialog = new Dialog();
            policyDialog.setWidth("600px");
            policyDialog.setHeight("600px");

            // Pass the dialog to NewPolicy so it can close it
            NewPolicy newPolicyView = new NewPolicy(policyDialog);
            policyDialog.add(newPolicyView);
            policyDialog.open();
		}));
		upload.getUploadButton().getStyle().set("color", "black");
		upload.setDropLabel(new Label("or drop it here"));
		add(upload);
	}

	private HorizontalLayout createPolicyInfo(String label, String value) {
		Label infoLabel = new Label(label);
		infoLabel.addClassName("info-label");
		infoLabel.getStyle().set("width", "150px");
		Label infoValue = new Label(value);
		infoValue.addClassName("info-value");

		HorizontalLayout layout = new HorizontalLayout();
		layout.add(infoLabel, infoValue);

		layout.setSpacing(true);
		layout.setVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
		layout.setFlexGrow(1, infoValue); // Allow value to take up remaining space
		// Add the label and value to the horizontal layout
		layout.add(infoLabel, infoValue);

		// Make the layout take up the full width
		// layout.expand(infoValue);
		return layout;
	}
}

