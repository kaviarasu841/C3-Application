/*package com.vaadin.C3Application;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.progressbar.ProgressBar;

@Route("home")
public class HomePage extends BaseLayout {

    private static final long serialVersionUID = 1L;

    public HomePage() {
        super();
        createContent();
    }

    private void createContent() {
        // Create header layout
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setSpacing(true);

        // Policy Reviews layout
        VerticalLayout policyLayout = createReviewLayout("Total Policy Reviews", 8, 0, 8);
        policyLayout.setWidth("20%");

        // IPS Reviews layout
        VerticalLayout ipsLayout = createReviewLayout("Total IPS Reviews", 5, 0, 5);
        ipsLayout.setWidth("50%");

        // Add both layouts to header
        header.add(policyLayout, ipsLayout);

        // Pending Reviews Layout
        VerticalLayout pendingReviewsLayout = new VerticalLayout();
        pendingReviewsLayout.add(
            createPendingReviewLayout("Pending Policy Reviews", "No pending reviews"),
            createPendingReviewLayout("Pending IPS Reviews", "No pending reviews")
        );

        // Add the header and pending reviews to the main layout
        add(header, pendingReviewsLayout);
    }

    private VerticalLayout createReviewLayout(String title, int aligned, int misaligned, int total) {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setDefaultHorizontalComponentAlignment(Alignment.START);

        Div header = new Div();
        header.setText(title);
        header.getStyle().set("font-weight", "bold");

        Div alignedDiv = new Div();
        alignedDiv.setText("Aligned:" + aligned);

        Div misalignedDiv = new Div();
        misalignedDiv.setText("Misaligned:     " + misaligned);

        Div totalDiv = new Div();
        totalDiv.setText("Total: " + total);

        HorizontalLayout progressBarLayout = new HorizontalLayout();
        ProgressBar progressBar = new ProgressBar();
        progressBar.setValue((double) aligned / total);
        progressBar.setWidth("100px");

        Div percentageDiv = new Div();
        percentageDiv.setText((aligned * 100 / total) + "%");

        progressBarLayout.add(progressBar, percentageDiv);
        progressBarLayout.setAlignItems(Alignment.CENTER);

        Div alignedOnTotalDiv = new Div();
        alignedOnTotalDiv.setText("Aligned on Total:");

        layout.add(header, alignedDiv, misalignedDiv, totalDiv, alignedOnTotalDiv, progressBarLayout);
        layout.getStyle().set("margin-bottom", "20px");

        return layout;
    }

    private VerticalLayout createPendingReviewLayout(String title, String content) {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setDefaultHorizontalComponentAlignment(Alignment.START);

        Div header = new Div();
        header.setText(title);
        header.getStyle().set("font-weight", "bold");

        Div contentDiv = new Div();
        contentDiv.setText(content);

        layout.add(header, contentDiv);
        layout.getStyle().set("margin-top", "20px");

        return layout;
    }
}*/

package com.vaadin.C3Application;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;

@Route("home")
@CssImport("./styles/shared-styles.css")
public class HomePage extends BaseLayout {

    private static final long serialVersionUID = 1L;

    public HomePage() {
        super();
        createContent();
    }

    private void createContent() {
        // Create header layout
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setSpacing(true);

        // Policy Reviews layout
        VerticalLayout policyLayout = createReviewLayout("Total Policy Reviews", 8, 0, 8);
        policyLayout.setWidth("25%");

        // IPS Reviews layout
        VerticalLayout ipsLayout = createReviewLayout("Total IPS Reviews", 5, 5, 10);
        ipsLayout.setWidth("50%");

        // Add both layouts to header
        header.add(policyLayout, ipsLayout);

        // Pending Reviews Layout
        VerticalLayout pendingReviewsLayout = new VerticalLayout();
        pendingReviewsLayout.add(
            createPendingReviewLayout("Pending Policy Reviews", "No pending reviews"),
            createPendingReviewLayout("Pending IPS Reviews", "No pending reviews")
        );
        pendingReviewsLayout.addClassName("pending-reviews-layout");
        pendingReviewsLayout.setPadding(false);
        pendingReviewsLayout.getStyle().set("font-size", "14px");

        // Add the header and pending reviews to the main layout
        add(header, pendingReviewsLayout);
    }

    private VerticalLayout createReviewLayout(String title, int aligned, int misaligned, int total) {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setDefaultHorizontalComponentAlignment(Alignment.START);

        Div header = new Div();
        header.setText(title);
        header.addClassName("review-header");

        HorizontalLayout alignedLayout = createAlignedLayout("Aligned:", aligned);
        HorizontalLayout misalignedLayout = createAlignedLayout("Misaligned:", misaligned);
        HorizontalLayout totalLayout = createAlignedLayout("Total:", total);

        HorizontalLayout progressBarLayout = new HorizontalLayout();
        progressBarLayout.setAlignItems(Alignment.CENTER);
        
        Div alignedOnTotalDiv = new Div();
        alignedOnTotalDiv.setText("Aligned on Total:");

        ProgressBar progressBar = new ProgressBar();
        progressBar.setValue((double) aligned / total);
        progressBar.addClassName("progress-bar-button");

        Div percentageDiv = new Div();
        percentageDiv.setText((aligned * 100 / total) + "%");

        progressBarLayout.add(alignedOnTotalDiv, progressBar, percentageDiv);

        layout.add(header, alignedLayout, misalignedLayout, totalLayout, progressBarLayout);
        layout.getStyle().set("margin-bottom", "30px");
        layout.getStyle().set("font-size", "14px");

        return layout;
    }

    private HorizontalLayout createAlignedLayout(String labelText, int number) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        
        Div label = new Div();
        label.setText(labelText);
        label.addClassName("label");

        Div numberDiv = new Div();
        numberDiv.setText(String.valueOf(number));
        numberDiv.addClassName("number");

        layout.add(label, numberDiv);
        return layout;
    }

    private VerticalLayout createPendingReviewLayout(String title, String content) {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setDefaultHorizontalComponentAlignment(Alignment.START);

        Div header = new Div();
        header.setText(title);
        header.addClassName("pending-review-header");

        Div contentDiv = new Div();
        contentDiv.setText(content);

        layout.add(header, contentDiv);

        return layout;
    }
}
