package com.vaadin.C3Application;

import com.vaadin.C3Application.service.PolicyDocumentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("removal")
@Route("policy-upload")
@CssImport("./styles/shared-styles.css")
public class MasterPolicyPage extends BaseLayout {

    private static final long serialVersionUID = 1L;

    public MasterPolicyPage() {
        super();
        setWidth("100%");
        setPadding(true);
        setSpacing(true);

        // Program details
        VerticalLayout programDetails = createProgramDetailsLayout();
        programDetails.setWidthFull();
        
        VerticalLayout contractDetails = createContractDetailsLayout();
        contractDetails.setWidthFull();

        // Policy document grid container
        Div gridContainer = new Div();
        gridContainer.addClassName("grid-container");
        gridContainer.setWidth("600px"); // Set a fixed width for the grid container

        // Policy document grid
        Grid<PolicyDocument> documentGrid = createDocumentGrid();
        gridContainer.add(documentGrid);

        Button modifyButton = new Button("Modify documents ...");
        modifyButton.getStyle().set("background-color", "#e7e7e7").set("color", "black");

        Label program = new Label("Program:");
        program.getElement().getStyle().set("font-weight", "bold");
        Label contract = new Label("Contract:");
        contract.getElement().getStyle().set("font-weight", "bold");

        // Processing status
        Div processingStatus = new Div();
        processingStatus.setText("Processing status: Document pre-processed");
        
        Label policy = new Label("Policy:");
        policy.getElement().getStyle().set("font-weight", "bold");
        
        Label doc = new Label("Uploaded documents");
        doc.addClassName("uploaded-documents-label");
        
     // Create a layout to hold the "Uploaded documents" label and the grid together
        VerticalLayout documentsLayout = new VerticalLayout();
        documentsLayout.setPadding(false);
        documentsLayout.setSpacing(false);
        documentsLayout.add(doc, gridContainer,modifyButton,processingStatus);

        // Add all components to the main layout
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add(program, programDetails, contract, contractDetails, policy, documentsLayout);
        mainLayout.setPadding(false);
        add(mainLayout); // Add mainLayout to the BaseLayout
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

    private Grid<PolicyDocument> createDocumentGrid() {
        Grid<PolicyDocument> grid = new Grid<>();
        List<PolicyDocument> documents = new ArrayList<>();
        List<NewPolicy.Document> docList = PolicyDocumentService.getInstance().getDocuments();

        for (NewPolicy.Document doc : docList) {
            documents.add(new PolicyDocument(doc.getType(), doc.getName(), doc.getOrder(), doc.getUploadedFile()));
        }


        ListDataProvider<PolicyDocument> dataProvider = new ListDataProvider<>(documents);
        grid.setDataProvider(dataProvider);

        grid.addColumn(PolicyDocument::getType).setHeader("Types").setWidth("150px");
        grid.addColumn(PolicyDocument::getName).setHeader("Name").setWidth("200px");
        grid.addColumn(PolicyDocument::getOrder).setHeader("Order").setWidth("100px");
        grid.addColumn(PolicyDocument::getUploadedFile).setHeader("Uploaded file").setWidth("300px");

        grid.addClassName("custom-grid"); // Add this line to apply custom styles

        grid.setHeight("150px"); // Set fixed height for the grid
        grid.setWidthFull();
        return grid;
    }

    public class PolicyDocument {
        private String type;
        private String name;
        private int order;
        private String uploadedFile;

        public PolicyDocument(String type, String name, int order, String uploadedFile) {
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

}
