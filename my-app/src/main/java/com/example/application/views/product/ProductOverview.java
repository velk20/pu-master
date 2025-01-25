package com.example.application.views.product;

import com.example.application.data.Product;
import com.example.application.services.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;


@PageTitle("Product Overview")
@Route("products")
@Menu(order = 15, icon = LineAwesomeIconUrl.PRODUCT_HUNT)
@AnonymousAllowed
public class ProductOverview extends VerticalLayout {
    private final ProductService productService;
    private final Grid<Product> grid;

    @Autowired
    public ProductOverview(ProductService productService) {
        this.productService = productService;

        grid = getProductGrid();
        HorizontalLayout buttons = createButtons();
        add(buttons, grid);
    }

    private Grid<Product> getProductGrid() {
        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.addColumn(Product::getCompanyShortName)
                .setSortable(true)
                .setKey("companyName")
                .setHeader("Company Name");
        grid.addColumn(Product::getBuyPrice)
                .setSortable(true)
                .setKey("buyPrice")
                .setHeader("Buy Price");
        grid.addColumn(Product::getSellPrice)
                .setSortable(true)
                .setKey("sellPrice")
                .setHeader("Sell Price");
        grid.addColumn(new ComponentRenderer<>(product -> {
            Button sell = new Button(new Icon(VaadinIcon.MINUS), event -> {

            });
            Button buy = new Button(new Icon(VaadinIcon.PLUS), event -> {

            });
            return new HorizontalLayout(sell, buy);
        })).setKey("actions").setHeader("Actions").isFrozenToEnd();
        grid.addColumn(new ComponentRenderer<>(product -> {
            if (product.getIsActive() == 1) {
                return new Button(new Icon(VaadinIcon.CHECK), event -> {
                });
            }else {
                return new Button(new Icon(VaadinIcon.CLOSE_CIRCLE_O), event -> {
                });
            }
        })).setKey("active").setHeader("Active").isFrozenToEnd();
        initGridData(grid);

        return grid;
    }

    private void initGridData(Grid<Product> grid) {
        grid.setItems(productService.getAllProducts());
    }

    private HorizontalLayout createButtons() {
        Button addBtn = createAddButton();
        Button editBtn = createEditButton();
        Button deleteBtn = new Button(new Icon(VaadinIcon.TRASH));
        deleteBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        grid.addSelectionListener(event -> {
            deleteBtn.setEnabled(!grid.getSelectedItems().isEmpty());
            editBtn.setEnabled(grid.getSelectedItems().size() == 1);
        });

        return new HorizontalLayout(addBtn, editBtn, deleteBtn);
    }


    private Button createEditButton() {
        return new Button(new Icon(VaadinIcon.EDIT), event -> {
            ProductDialog productDialog = new ProductDialog(grid.asSingleSelect().getValue(), productService);

            productDialog.open();
        });
    }

    private Button createAddButton() {
        Button addBtn = new Button(new Icon(VaadinIcon.PLUS), event -> {
            ProductDialog productDialog = new ProductDialog(new Product(), productService);
            productDialog.open();
        });
        addBtn.setTooltipText("Add Product");
        return addBtn;
    }


}
