package com.vaadin.C3Application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("history")
public class HistoryPage extends BaseLayout {

	private static final long serialVersionUID = 1L;

	private List<PolicyReview> allReviews1;
	private List<PolicyReview> allReviews2;
	private Grid<PolicyReview> grid1;
	private Grid<PolicyReview> grid2;

	public HistoryPage() {
		super();
		setWidth("100%");
		setPadding(true);
		setSpacing(true);

		VerticalLayout form = new VerticalLayout();
		form.setPadding(false);
		form.setSpacing(true);

		Div header = new Div();
		header.setText("Filters:");
		header.getElement().getStyle().set("font-weight", "bold");

		TextField id = new TextField("Contract ID:");
		TextField year = new TextField("Contract start year:");
		TextField program = new TextField("Program Name:");

		ComboBox<String> country = new ComboBox<>("Country:");
		country.setItems("","India", "United States", "Japan", "Switzerland");

		DatePicker reviewFrom = new DatePicker("Review finalised from:");
		DatePicker reviewTo = new DatePicker("Review finalised to:");

		ComboBox<String> results = new ComboBox<>("Number of results:");
		results.setItems("10", "20", "50", "ALL");
		results.setPlaceholder("10");

		ComboBox<String> reviewSelection = new ComboBox<>("Review selection:");
		reviewSelection.setItems("User reviews", "Country reviews", "All accessible reviews");
		reviewSelection.setPlaceholder("User reviews");

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		buttons.setWidthFull();

		Button apply = new Button("Apply Filter");
		apply.addClassName("apply-filter");

		Button reset = new Button("Reset Filter");
		reset.addClassName("remove-filter");

		buttons.add(apply, reset);
		buttons.setJustifyContentMode(JustifyContentMode.START);

		FormLayout formLayout = new FormLayout();
		formLayout.setWidth("60%");
		formLayout.add(id, year, program, country, reviewFrom, reviewTo, results, reviewSelection);
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 3));

		form.add(header, formLayout, buttons);
		form.getElement().getStyle().set("margin-bottom", "25px");

		// Table 1: Policy Reviews
		VerticalLayout table1 = new VerticalLayout();
		table1.setPadding(false);
		table1.setSpacing(false);

		Div header1 = new Div();
		header1.setText("Policy reviews performed:");
		header1.getElement().getStyle().set("font-weight", "bold");

		allReviews1 = Arrays.asList(
				new PolicyReview("MA34285A", "MA31949A", "Baxter International Ltd", 2023, "Japan", "KAVIARASU.S",
						"Aligned", "kaviarasu.s", LocalDateTime.of(2023, 11, 21, 19, 12, 27)),
				new PolicyReview("LA86595C", "LA86867C", "TestingLA1", 2023, "Singapore", "KAVIARASU.S", "Aligned",
						"kaviarasu.s", LocalDateTime.of(2023, 11, 21, 15, 4, 1)),
				new PolicyReview("LA98765C", "LA86867C", "TestingLA1", 2023, "Singapore", "KAVIARASU.S", "Aligned",
						"kaviarasu.s", LocalDateTime.of(2023, 11, 21, 15, 4, 1)));

		grid1 = createConfiguredGrid();
		grid1.setItems(allReviews1);

		table1.add(header1, grid1);
		table1.getElement().getStyle().set("margin-bottom", "25px");

		// Table 2: IPS Reviews (same data for example)
		VerticalLayout table2 = new VerticalLayout();
		table2.setPadding(false);
		table2.setSpacing(false);

		Div header2 = new Div();
		header2.setText("IPS reviews performed:");
		header2.getElement().getStyle().set("font-weight", "bold");

		allReviews2 = new ArrayList<>(allReviews1); // clone or reuse

		grid2 = createConfiguredGrid();
		grid2.setItems(allReviews2);

		table2.add(header2, grid2);
		table2.getElement().getStyle().set("margin-bottom", "30px");
		table2.getElement().getStyle().set("padding-bottom", "60px");

		add(form, table1, table2);

		// Apply Filter logic
		apply.addClickListener(e -> {
			String contractIdValue = id.getValue().trim();
			String yearValue = year.getValue().trim();
			String programValue = program.getValue().trim();
			String countryValue = country.getValue();
			LocalDate fromDate = reviewFrom.getValue();
			LocalDate toDate = reviewTo.getValue();

			List<PolicyReview> filtered = allReviews1.stream().filter(pr -> {
				boolean matches = true;

				if (!contractIdValue.isEmpty()) {
					matches &= pr.getLocalPolicyId().toLowerCase().contains(contractIdValue.toLowerCase());
				}
				if (!yearValue.isEmpty()) {
					try {
						matches &= pr.getStartYear() == Integer.parseInt(yearValue);
					} catch (NumberFormatException ex) {
						matches = false;
					}
				}
				if (!programValue.isEmpty()) {
					matches &= pr.getProgramName().toLowerCase().contains(programValue.toLowerCase());
				}
				if (countryValue != null && !countryValue.isEmpty()) {
					matches &= pr.getIssuanceCountry().equalsIgnoreCase(countryValue);
				}
				if (fromDate != null) {
					matches &= !pr.getFinalisedOn().toLocalDate().isBefore(fromDate);
				}
				if (toDate != null) {
					matches &= !pr.getFinalisedOn().toLocalDate().isAfter(toDate);
				}

				return matches;
			}).collect(Collectors.toList());

			grid1.setItems(filtered);
			grid2.setItems(filtered); // or keep separate if needed
		});

		// Reset Filter logic
		reset.addClickListener(e -> {
			id.clear();
			year.clear();
			program.clear();
			country.clear();
			reviewFrom.clear();
			reviewTo.clear();
			results.clear();
			reviewSelection.clear();

			grid1.setItems(allReviews1);
			grid2.setItems(allReviews2);
		});
	}

	private Grid<PolicyReview> createConfiguredGrid() {
		Grid<PolicyReview> grid = new Grid<>(PolicyReview.class);
		grid.removeAllColumns();
		grid.addColumn(new ComponentRenderer<RouterLink, PolicyReview>(review -> {
		    RouterLink link = new RouterLink();
		    link.setText(review.getLocalPolicyId());
		    link.setRoute(Policy.class); // Goes to /policy
		    link.getElement().getStyle().set("text-decoration", "underline");
		    link.getElement().getStyle().set("color", "#00005a");
		    return link;
		})).setHeader("Local Policy ID").setResizable(true).setSortable(true);

		grid.addColumn(PolicyReview::getMasterPolicyId).setHeader("Master Policy ID").setResizable(true).setSortable(true);
		grid.addColumn(PolicyReview::getProgramName).setHeader("Program Name").setResizable(true).setSortable(true);
		grid.addColumn(PolicyReview::getStartYear).setHeader("Start year").setResizable(true).setSortable(true);
		grid.addColumn(PolicyReview::getIssuanceCountry).setHeader("Issuance Country").setResizable(true).setSortable(true);
		grid.addColumn(PolicyReview::getUnderwriter).setHeader("Underwriter").setResizable(true).setSortable(true);
		grid.addColumn(PolicyReview::getReviewResult).setHeader("Review Result").setResizable(true).setSortable(true);
		grid.addColumn(PolicyReview::getReviewer).setHeader("Reviewer").setResizable(true).setSortable(true);
		grid.addColumn(PolicyReview::getFinalisedOn).setHeader("Finalised on").setResizable(true).setSortable(true);
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		grid.addClassName("policy-review");
		grid.addClassName("my-grid");
		return grid;
	}

	public static class PolicyReview {
		private String localPolicyId;
		private String masterPolicyId;
		private String programName;
		private int startYear;
		private String issuanceCountry;
		private String underwriter;
		private String reviewResult;
		private String reviewer;
		private LocalDateTime finalisedOn;

		public PolicyReview(String localPolicyId, String masterPolicyId, String programName, int startYear,
				String issuanceCountry, String underwriter, String reviewResult, String reviewer,
				LocalDateTime finalisedOn) {
			this.localPolicyId = localPolicyId;
			this.masterPolicyId = masterPolicyId;
			this.programName = programName;
			this.startYear = startYear;
			this.issuanceCountry = issuanceCountry;
			this.underwriter = underwriter;
			this.reviewResult = reviewResult;
			this.reviewer = reviewer;
			this.finalisedOn = finalisedOn;
		}

		public String getLocalPolicyId() {
			return localPolicyId;
		}

		public String getMasterPolicyId() {
			return masterPolicyId;
		}

		public String getProgramName() {
			return programName;
		}

		public int getStartYear() {
			return startYear;
		}

		public String getIssuanceCountry() {
			return issuanceCountry;
		}

		public String getUnderwriter() {
			return underwriter;
		}

		public String getReviewResult() {
			return reviewResult;
		}

		public String getReviewer() {
			return reviewer;
		}

		public LocalDateTime getFinalisedOn() {
			return finalisedOn;
		}
	}
}
