package com.example.application.views.product;

import com.example.application.data.Product;
import com.example.application.services.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductDialog extends Dialog {
    private final Product product;
    private final ProductService productService;

    private final BeanValidationBinder<Product> binder = new BeanValidationBinder<>(Product.class);
    private final TextField companyShortName = new TextField("Company Short Name");
    private final TextField companyName = new TextField("Company Name");
    private final NumberField buyPrice = new NumberField("Buy Price");
    private final NumberField sellPrice = new NumberField("Sell Price");
    private final ComboBox<Integer> isActive = new ComboBox<>("Is Active");
    private Button saveBtn;

    @Autowired
    public ProductDialog(Product product, ProductService productService) {
        this.product = product;
        this.productService = productService;

        initContext();

    }

    private void initContext() {
        this.setHeaderTitle(product.getId() != null ? "Edit Product" : "New Product");

        isActive.setItems(0, 1);
        isActive.setItemLabelGenerator(i -> i == 1 ? "Active" : "Inactive");

        binder.bindInstanceFields(this);
        binder.readBean(product);
        add(new FormLayout(companyShortName, companyName, buyPrice, sellPrice, isActive));

        saveBtn = new Button("Save", event -> {
            try {
                binder.writeBean(product);
                productService.save(product);
                this.close();
            } catch (ValidationException e) {
                Notification.show("Unable to save product: " + e.getMessage(), 2000, Notification.Position.MIDDLE);
                throw new RuntimeException(e);
            }
        });

        Button cancel = new Button("Cancel", event -> {
            this.close();
        });

        this.getFooter().add(saveBtn,cancel);
    }
}
