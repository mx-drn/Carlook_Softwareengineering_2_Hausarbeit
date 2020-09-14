package org.gui.components;

import com.vaadin.ui.*;

public class ProfilVertriebler extends VerticalLayout {

    /*private Vertriebler vertriebler = (Vertriebler)((MainUI) UI.getCurrent()).getBenutzer();
    private ArrayList<AutoDTO> listeDTOAuto = new ArrayList<>();
    private VerticalLayout verticalLayoutAutos = new VerticalLayout();

    public ProfilVertriebler() {
        HorizontalLayout daten = new HorizontalLayout();

        Label name = new Label(vertriebler.getEmail());
        MenuBar menuBar = new MenuBar();

        verticalLayoutAutos.setWidth("100%");
        menuBar.addItem("eingestellte Autos", (MenuBar.Command) menuItem ->
                menuItemAuto);

        FormLayout top = new FormLayout();
        top.setHeight("264.5px");

        daten.addComponent(top);
        this.addComponents(name, new Label("&nbsp", ContentMode.HTML), daten, new Label("&nbsp", ContentMode.HTML), new Label("&nbsp", ContentMode.HTML), menuBar);

    }
        public void menuItemAuto(){
         verticalLayoutAutos.removeAllComponents();

         Label title = new Label("Eingestellte Autos");
         verticalLayoutAutos.addComponent(title);

         if(listeDTOAuto.size() == 0)
             listeDTOAuto = Provider.loadAutosAsDTO(vertriebler);
         if(listeDTOAuto.size() == 0){
             Label info = new Label("Sie haben noch keine Autos eingestellt");
             verticalLayoutAutos.addComponent(info);
         }else{
             Grid<AutoDTO> grid = createAutoGrid();
             grid.setItems(listeDTOAuto);
             grid.setHeightByRows(Math.min(listeDTOAuto.size(),7));
             verticalLayoutAutos.addComponent(grid);
         }
        }

        public Grid<AutoDTO> createAutoGrid(){
        Grid<AutoDTO> result = new Grid<>();
        result.addColumn(AutoDTO::getId).setCaption("ID");
        result.addColumn(AutoDTO::getMarke).setCaption("Modell");
        result.addColumn(AutoDTO::getBeschreibung).setCaption("Farbe");
        result.addColumn(AutoDTO::getBaujahr).setCaption("Baujahr");

        result.setWidth("100%");
        result.setColumnReorderingAllowed(true);
        result.getColumns().forEach(column -> column.setHidable(true));

        return result;

        }*/

}
