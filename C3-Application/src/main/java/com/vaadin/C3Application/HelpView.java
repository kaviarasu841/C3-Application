package com.vaadin.C3Application;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route("help")
@CssImport("./styles/shared-styles.css")
public class HelpView extends BaseLayout {

    private static final long serialVersionUID = 1L;

    public HelpView() {
        super();
        setSpacing(true);
        setPadding(true);
        createHelpContent();
    }

    private void createHelpContent() {
    	
    	Span versionText = new Span("C3 - Contract Certainty Cockpit Version : 2.0.5");
        versionText.getStyle().set("font-weight", "normal")
                    .set("font-size", "16px")
                    .set("margin-bottom", "20px")
                    .set("display", "block"); 
        add(versionText);
    	
        createSection("Frequently Asked Questions", "FAQ1", "Faq 1 dewscs");
        createSection("Training Videos", "Super usage", "http://www.yahoo.com");
        createSection("Tool Guide", "Tool1", "Very good, click here");
        createSection("Contact", "Technical support", "ABC");
        
    }

    private void createSection(String header, String subheader, String content) {
        Div section = new Div();
        section.getElement().getStyle().set("margin-bottom", "30px");
        section.addClassName("section");

        Span sectionHeader = new Span(header);
        sectionHeader.addClassName("section-header");

        Div subheaderDiv = new Div();
        subheaderDiv.addClassName("subheader");
        Span subheaderSpan = new Span(subheader);
        subheaderSpan.addClassName("subheader-text");
        subheaderDiv.add(subheaderSpan);

        Div contentDiv = new Div();
        contentDiv.addClassName("content");
        if (header.equals("Tool Guide")) {
            // Add only "click here" as a link and the rest as plain text
            Span plainText = new Span("Very good, ");
            Anchor anchor = new Anchor("http://www.yahoo.com", "click here");
            anchor.addClassName("link");
            anchor.setTarget("_blank");

            contentDiv.add(plainText, anchor);
        } else if (header.equals("Training Videos")) {
            Anchor contentAnchor = new Anchor(content, content);
            contentAnchor.addClassName("link");
            contentAnchor.setTarget("_blank");
            contentDiv.add(contentAnchor);
        } else {
            contentDiv.setText(content);
        }

        section.add(sectionHeader, subheaderDiv, contentDiv);
        add(section);
    }
}
