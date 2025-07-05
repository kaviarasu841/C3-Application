package com.vaadin.C3Application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("Combined")
@CssImport("./styles/shared-styles.css")

public class MLandAlign extends BaseLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MLandAlign() {
		 super();
		setWidth("100%");
		setPadding(true);
		setSpacing(true);

		// Create instances of MasterLocalView and ApprovedView
		MasterLocalView masterLocalView = new MasterLocalView();
		HistoryView approvedView = new HistoryView();
		add(masterLocalView, approvedView);
	}

	@CssImport("./styles/shared-styles.css")
	public static class MasterLocalView extends VerticalLayout {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MasterLocalView() {
			setWidth("100%");
			setPadding(false);
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
			detailsLayout.add(masterDetails, localDetails);

			// Buttons
			VerticalLayout buttonsLayout = new VerticalLayout();
			buttonsLayout.setWidth("100%");
			buttonsLayout.setSpacing(true);

			Button saveReviewButton = new Button("Save Review");
			Button finalizeReviewButton = new Button("Finalize Review");

			saveReviewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			finalizeReviewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

			saveReviewButton.addClickListener(event -> {
				showDialog("Changes saved");
			});

			finalizeReviewButton.addClickListener(event -> {
				// Handle finalize review logic
				showDialog("The local policy will be finalized.");
			});
			buttonsLayout.add(saveReviewButton, finalizeReviewButton);
			detailsLayout.add(buttonsLayout);
			
			VerticalLayout button = new VerticalLayout();
			button.setSpacing(false);
			button.setPadding(true);
			button.setWidthFull();

			Button removeMasterPolicyButton = new Button("Remove Master policy");
			removeMasterPolicyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
			button.add(removeMasterPolicyButton);

			add(programDetailsLayout,detailsLayout, button);
	
		}

		private FormLayout createMasterDetailsLayout() {
			FormLayout layout = new FormLayout();
			layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("100px", 1));

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
			// Add anchor link for "Show Policy documents"
			Anchor showPolicyDocs = new Anchor("#", "Show Policy documents");
			layout.add(showPolicyDocs);

			return layout;
		}

		private FormLayout createLocalDetailsLayout() {
			FormLayout layout = new FormLayout();
			layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("100px", 1));

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
			// Add anchor link for "Show Policy documents"
			Anchor showPolicyDocs = new Anchor("#", "Show Policy documents");
			layout.add(showPolicyDocs);
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

		private void showDialog(String message) {
			Dialog dialog = new Dialog();
			dialog.add(new Label(message));

			Button okButton = new Button("Ok", event -> dialog.close());
			okButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

			Button cancelButton = new Button("Cancel", event -> dialog.close());

			HorizontalLayout dialogButtons = new HorizontalLayout(okButton, cancelButton);
			dialog.add(dialogButtons);

			dialog.open();
		}
	}

	@CssImport("./styles/shared-styles.css")
	public class HistoryView extends HorizontalLayout {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String[] generalElements = { "Period From: 01-Jan-2024", "Period From Time: 00:00:00 UTC",
				"Period To: 31-Dec-2024", "Period To Time: 00:00:00 UTC" };

		private String[] covers = { "Bodily injury", "Additional Insured - Limited Form", "Care, Custody and Control",
				"Defense / legal cost in addition", "Main Constraint" };
		private String[] insuredCompanies = { "TestingLA1", "Company 1", "Company 2" };
		private String[] exclusions = { "Alcoholic beverages (for exposures in USA)", "Asbestos",
				"Aviation and Space Risks", "Diacetyl", "Electromagnetic Fields (EMF) / Radio Frequency (RF)", "HIV",
				"Human Pharmaceutical Products" };

		private HorizontalLayout currentLayout;
		private Map<String, String> misalignmentDetails = new HashMap<>();

		public HistoryView() {
			// Create a list of field names for correction/misalignments
			List<String> fieldNames = Arrays.asList("Policy Period", "Companies Insured", "Premium", "Limit(s)",
					"Sublimit(s)", "Deductible(s) / Self Insured", "Claims Trigger", "Retroactive Date / Extended",
					"Territorial Scope", "Insured Activities", "Coverage", "Extensions", "Exclusions");

			// Create a VerticalLayout for corrections/misalignments and add-alignment
			VerticalLayout correctionsLayout = new VerticalLayout();
			Div correctionsHeader = new Div();
			correctionsHeader.setText("Correction / Misalignments:");
			correctionsHeader.getElement().getStyle().set("fontWeight", "bold");

			correctionsLayout.add(correctionsHeader);
			for (String fieldName : fieldNames) {
				TextField policyField = new TextField();
				policyField.setValue(fieldName);
				policyField.setReadOnly(true);
				policyField.addClassName("policy-field");
				policyField.getStyle().set("font-size", "13px");
				policyField.getElement().getStyle().set("fontWeight", "bold");
				Span addAlignmentLink = new Span("Add Misalignment");
				addAlignmentLink.getElement().getStyle().set("text-decoration", "underline");
				addAlignmentLink.getElement().getStyle().set("color", "#000080");
				addAlignmentLink.getElement().getStyle().set("cursor", "pointer");
				addAlignmentLink.getElement().getStyle().set("fontWeight", "bold");
				addAlignmentLink.getElement().getStyle().set("font-size", "13px");
				addAlignmentLink.addClickListener(event -> showAddMisalignmentDialog(fieldName, addAlignmentLink));

				// Wrap the TextField in a Div to handle click events
				Div clickableFieldWrapper = new Div(policyField);
				clickableFieldWrapper.addClickListener(event -> showExistingDataDialog(fieldName));

				HorizontalLayout fieldWithLinkLayout = new HorizontalLayout(clickableFieldWrapper, addAlignmentLink);
				fieldWithLinkLayout.setWidthFull();
				fieldWithLinkLayout.setAlignItems(Alignment.BASELINE);
				correctionsLayout.add(fieldWithLinkLayout);
				correctionsLayout.getElement().getStyle().set("fontWeight", "bold");
			}

			// Create a VerticalLayout for IPS data
			VerticalLayout ipsDataLayout = new VerticalLayout();
			Div ipsDataHeader = new Div();
			ipsDataHeader.setText("IPS Data:");
			ipsDataHeader.getElement().getStyle().set("fontWeight", "bold");
			ipsDataHeader.getStyle().set("font-size", "13px");
			ipsDataLayout.add(ipsDataHeader);

			// Create an Accordion for IPS data
			ipsDataLayout.add(createCustomDropdown("General Elements", true));
			ipsDataLayout.add(createCustomDropdown("Covers", true));
			ipsDataLayout.add(createCustomDropdown("Exclusions", true));
			ipsDataLayout.add(createCustomDropdown("Insured Companies", true));
			ipsDataLayout.getStyle().set("font-size", "13px");

			// Create a separator
			Div separator = new Div();
			separator.addClassName("separator");

			// Add both layouts and the separator to the main layout
			add(correctionsLayout, separator, ipsDataLayout);

			// Set the width and spacing of the main layout
			setWidthFull();
			setSpacing(true);
		}

		private Details createCustomDropdown(String title, boolean editable) {
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
			details.setSummaryText(title);
			contentLayout.getStyle().set("font-size", "13px");

			details.setContent(contentLayout);
			return details;
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
			notesDialog.getHeader().add(maxButton, closeButton);

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
							.filter(component -> component instanceof Label
									&& ((Label) component).getText().startsWith("Notes:"))
							.findFirst().orElse(null);

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
							.filter(component -> component instanceof Label
									&& ((Label) component).getText().startsWith("Notes:"))
							.findFirst().ifPresent(currentLayout::remove);
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
					.filter(component -> component instanceof Label
							&& ((Label) component).getText().startsWith("Notes:"))
					.map(component -> ((Label) component).getText().substring(7)) // Remove "Notes: " prefix
					.findFirst().orElse("");
		}

		private void showAddMisalignmentDialog(String fieldName, Span addAlignmentLink) {
			Dialog dialog = new Dialog();
			dialog.setWidth("400px");
			dialog.setHeight("300px");

			TextArea misalignmentTextArea = new TextArea("Add Misalignment for " + fieldName);
			misalignmentTextArea.setWidthFull();
			misalignmentTextArea.setHeight("150px");

			Button saveButton = new Button("Save", event -> {
				String misalignmentDetailsText = misalignmentTextArea.getValue();
				if (!misalignmentDetailsText.isEmpty()) {
					this.misalignmentDetails.put(fieldName, misalignmentDetailsText);
					addAlignmentLink.setText("Edit Misalignment");
					addAlignmentLink.setVisible(false); // Hide the Add Misalignment link after saving
				}
				dialog.close();
			});
			Button cancelButton = new Button("Cancel", event -> dialog.close());

			dialog.add(misalignmentTextArea, new HorizontalLayout(saveButton, cancelButton));
			dialog.open();
		}

		private void showExistingDataDialog(String fieldName) {
			Dialog dialog = new Dialog();
			dialog.setWidth("400px");
			dialog.setHeight("300px");

			VerticalLayout contentLayout = new VerticalLayout();
			contentLayout.add(new Label("Existing Data for " + fieldName));

			String existingMisalignment = misalignmentDetails.get(fieldName);
			if (existingMisalignment != null && !existingMisalignment.isEmpty()) {
				TextArea misalignmentTextArea = new TextArea("Misalignment Details");
				misalignmentTextArea.setValue(existingMisalignment);
				misalignmentTextArea.setWidthFull();
				misalignmentTextArea.setHeight("150px");
				misalignmentTextArea.setReadOnly(true);
				contentLayout.add(misalignmentTextArea);
			} else {
				contentLayout.add(new Label("No misalignment details available."));
			}

			Button closeButton = new Button("Close", event -> dialog.close());
			contentLayout.add(closeButton);

			dialog.add(contentLayout);
			dialog.open();
		}

		private int getElementCount(String category) {
			switch (category) {
			case "General Elements":
				return generalElements.length;
			case "Covers":
				return covers.length;
			case "Exclusions":
				return exclusions.length;
			case "Insured Companies":
				return insuredCompanies.length;
			default:
				return 0;
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
			Icon editIcon = new Icon(VaadinIcon.EDIT);
			editIcon.getStyle().set("cursor", "pointer");
			editIcon.setSize("15px");
			if (!editable) {
				editIcon.setVisible(false); // Hide the icon if not editable
			}
			return editIcon;
		}

		private HorizontalLayout createSeparator() {
			HorizontalLayout separator = new HorizontalLayout();
			separator.setWidth("100%");
			separator.getElement().getStyle().set("border-bottom", "1px solid #ccc");
			separator.setSpacing(false);
			separator.setPadding(false);
			return separator;
		}

		private void createGeneralElementsContent(FormLayout contentLayout, boolean editable) {
			contentLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
					new FormLayout.ResponsiveStep("100px", 1));
			contentLayout.setWidth("150%");

			for (String element : generalElements) {
				contentLayout.add(createContentLayout(element, editable));
				contentLayout.add(createSeparator());
			}
		}

		private void addCoversContent(FormLayout contentLayout, boolean editable) {
			contentLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
					new FormLayout.ResponsiveStep("100px", 1));
			contentLayout.setWidth("150%");

			for (String cover : covers) {
				contentLayout.add(createContentLayout(cover, editable));
				contentLayout.add(createSeparator());
			}
		}

		private void addExclusionsContent(FormLayout contentLayout, boolean editable) {
			contentLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
					new FormLayout.ResponsiveStep("100px", 1));
			contentLayout.setWidth("150%");

			for (String exclusion : exclusions) {
				contentLayout.add(createContentLayout(exclusion, editable));
				contentLayout.add(createSeparator());
			}
		}

		private void addInsuredCompaniesContent(FormLayout contentLayout, boolean editable) {
			contentLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
					new FormLayout.ResponsiveStep("100px", 1));
			contentLayout.setWidth("150%");

			for (String insuredCompany : insuredCompanies) {
				contentLayout.add(createContentLayout(insuredCompany, editable));
				contentLayout.add(createSeparator());
			}
		}
	}
}
