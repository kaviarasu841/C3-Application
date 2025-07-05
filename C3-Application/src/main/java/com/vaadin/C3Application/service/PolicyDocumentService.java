package com.vaadin.C3Application.service;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.C3Application.NewPolicy;

public class PolicyDocumentService {

	private static PolicyDocumentService instance;
	private final List<NewPolicy.Document> documents = new ArrayList<>();

	private PolicyDocumentService() {
	}

	public static PolicyDocumentService getInstance() {
		if (instance == null) {
			instance = new PolicyDocumentService();
		}
		return instance;
	}

	public void setDocuments(List<NewPolicy.Document> docs) {
		documents.clear();
		documents.addAll(docs);
	}

	public List<NewPolicy.Document> getDocuments() {
		return new ArrayList<>(documents);
	}
}