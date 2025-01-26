package com.example.application.views.product;

import com.example.application.data.Product;
import com.example.application.services.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.List;
import java.util.Set;


@PageTitle("Product Overview")
@Route("products")
@Menu(order = 15, icon = LineAwesomeIconUrl.PRODUCT_HUNT)
@AnonymousAllowed
public class ProductOverview extends VerticalLayout {
    private final ProductService productService;
    private Grid<Product> grid;
    private boolean isAdmin;
    private NativeLabel footer;

    @Autowired
    public ProductOverview(ProductService productService) {
        this.productService = productService;
        isAdmin = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_ADMIN"));
        initContent();

    }

    private void initContent() {

        grid = getProductGrid();
        if (isAdmin) {

            add(createButtons());
        }
        add(grid);
    }

    private Grid<Product> getProductGrid() {
        Grid<Product> grid = new Grid<>(Product.class, false);
        footer = new NativeLabel("Total products:" + productService.getAllProducts(isAdmin).size());
        HeaderRow headerRow = grid.prependHeaderRow();

        Grid.Column<Product> companyNameColumn = grid.addColumn(Product::getCompanyShortName);
        companyNameColumn
                .setSortable(true)
                .setKey("companyName")
                .setHeader("Company Name")
                .setFooter(footer);
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

        if (isAdmin) {
            grid.setSelectionMode(Grid.SelectionMode.MULTI);

            Grid.Column<Product> activeColumn = grid.addColumn(new ComponentRenderer<>(product -> {
                if (product.getIsActive() == 1) {
                    return new Button(new Icon(VaadinIcon.CHECK), event -> {
                    });
                } else {
                    return new Button(new Icon(VaadinIcon.CLOSE_CIRCLE_O), event -> {
                    });
                }
            }));
            activeColumn.setKey("active").setHeader("Active").isFrozenToEnd();

            grid.addItemDoubleClickListener(e -> openProductForm(e.getItem()));
            ComboBox<Integer> isActiveFilter = new ComboBox<>("IsActive");
            isActiveFilter.setItems(0, 1);
            isActiveFilter.setItemLabelGenerator(i -> i == 0 ? "Inactive" : "Active");
            isActiveFilter.setSizeFull();
            isActiveFilter.setClearButtonVisible(true);

            headerRow.getCell(activeColumn)
                    .setComponent(isActiveFilter);

        }
        TextField companyNameFilter = new TextField();
        companyNameFilter.setSizeFull();
        headerRow.getCell(companyNameColumn).setComponent(companyNameFilter);
        initGridData(grid);

        return grid;
    }

    private void initGridData(Grid<Product> grid) {
        List<Product> allProducts = productService.getAllProducts(isAdmin);
        grid.setItems(allProducts);
        footer.setText(allProducts.size() + " products");
    }

    private HorizontalLayout createButtons() {
        Button addBtn = createAddButton();
        Button editBtn = createEditButton();
        Button deleteBtn = createDeleteButton();

        return new HorizontalLayout(addBtn, editBtn, deleteBtn);
    }


    private Button createEditButton() {
        Button editBtn = new Button(new Icon(VaadinIcon.EDIT),
                event -> openProductForm(grid.getSelectedItems().stream().findFirst().get()));
        editBtn.setEnabled(grid.getSelectedItems().size() == 1);
        return editBtn;
    }

    private Button createAddButton() {
        Button addBtn = new Button(new Icon(VaadinIcon.PLUS), event -> openProductForm(new Product()));
        addBtn.setTooltipText("Add Product");
        return addBtn;
    }

    private Button createDeleteButton() {
        Button deleteBtn = new Button("Delete selected product",l->{
            Set<Product> selectedItems = grid.getSelectedItems();
            selectedItems.forEach(product -> {
                product.setIsActive(0);
                productService.save(product);
            });
            initGridData(grid);
            Notification.show("Selected products deleted", 2000, Notification.Position.TOP_CENTER);
        });

        deleteBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteBtn.setIcon(new Icon(VaadinIcon.TRASH));
        grid.addSelectionListener(event -> {
            deleteBtn.setEnabled(!grid.getSelectedItems().isEmpty());
        });

        return deleteBtn;
    }

    private void openProductForm(Product product) {
        ProductDialog productDialog = new ProductDialog(product, productService);
        productDialog.addSaveListener(e -> initGridData(grid));
        productDialog.open();
    }


}
