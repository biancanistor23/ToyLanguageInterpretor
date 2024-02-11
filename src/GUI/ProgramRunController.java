package GUI;

import Controller.Controller;
import Model.ADTs.IDict;
import Model.ADTs.IHeap;
import Model.ProgramState;
import Model.Statements.IStmt;
import Model.Values.IValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ProgramRunController implements Initializable {
    public Button AllStepButton;

    public ProgramRunController(){}

    private Controller ctrl;
    private ProgramState selectedProgram;

    @FXML
    private TableView<HashMap.Entry<Integer, String>> heapTableView=new TableView<>();
    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, Integer> heapAddressColumn=new TableColumn<>();
    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, String> heapValueColumn=new TableColumn<>();

    @FXML
    private ListView<String> outputListView=new ListView<>();

    @FXML
    private ListView<String> fileListView= new ListView<>();

    @FXML
    private ListView<Integer> programStatesView=new ListView<>();

    @FXML
    private TableView<Map.Entry<String, String>> symTableView=new TableView<>();
    @FXML
    private TableColumn<Map.Entry<String, String>, String> symVarNameColumn=new TableColumn<>();
    @FXML
    private TableColumn<Map.Entry<String, String>, String> symValueColumn=new TableColumn<>();

    @FXML
    private ListView<String> exeStackView=new ListView<>();

    @FXML
    private TextField nrProgramStatesField=new TextField("");


    public void setController(Controller ctr) {
        ctrl=ctr;

        selectedProgram=ctrl.getRepo().getAllPrograms().get(0);

        loadData();
    }

    @FXML
    public void setSelectedProgram(){
        if(programStatesView.getSelectionModel().getSelectedIndex()>=0 && programStatesView.getSelectionModel().getSelectedIndex()<=this.ctrl.getRepo().getAllPrograms().size()){
            selectedProgram=ctrl.getRepo().getAllPrograms().get(programStatesView.getSelectionModel().getSelectedIndex());
            loadData();
        }
    }

    private void loadData(){
        this.programStatesView.getItems().setAll( ctrl.getRepo().getAllPrograms().stream().map(ProgramState::getCurrentID).collect(Collectors.toList()) );

        if(selectedProgram!=null){

            outputListView.getItems().setAll( selectedProgram.getOutput().getList().stream().map(Object::toString).collect(Collectors.toList()));

            fileListView.getItems().setAll(String.valueOf(selectedProgram.getFileTable().getDict().keySet()));

            List<String> executionStackList=selectedProgram.getExeStack().getStack().stream().map(IStmt::toString).collect(Collectors.toList());
            Collections.reverse(executionStackList);
            exeStackView.getItems().setAll(executionStackList);

            IHeap<IValue> heapTable=selectedProgram.getHeapTable();
            List<Map.Entry<Integer, String>> heapTableList=new ArrayList<>();
            for(Map.Entry<Integer, IValue> element:heapTable.getHeap().entrySet()){
                Map.Entry<Integer, String> el=new AbstractMap.SimpleEntry<Integer, String>(element.getKey(),element.getValue().toString());
                heapTableList.add(el);
            }
            heapTableView.setItems(FXCollections.observableList(heapTableList));
            heapTableView.refresh();

            heapAddressColumn.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getKey()).asObject());
            heapValueColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));

            IDict<String, IValue> symbolTable=this.selectedProgram.getSymTable();
            List<Map.Entry<String, String>> symbolTableList=new ArrayList<>();
            for(Map.Entry<String, IValue> element:symbolTable.getDict().entrySet()){
                Map.Entry<String, String> el=new AbstractMap.SimpleEntry<String, String>(element.getKey(),element.getValue().toString());
                symbolTableList.add(el);
            }
            symTableView.setItems(FXCollections.observableList(symbolTableList));
            symTableView.refresh();

            symVarNameColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()));
            symValueColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));

            nrProgramStatesField.setText(Integer.toString(ctrl.getRepo().getSize()));

        }
    }


    @FXML
    public void onRunOneStepButtonPressed() {


        if(selectedProgram.getExeStack().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Horray!");
            alert.setHeaderText("Your program is done!");
            alert.setContentText("Select another program to execute!");
            Image image = new Image("https://static-00.iconduck.com/assets.00/smiling-face-with-sunglasses-emoji-256x256-al6cgijr.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.showAndWait();
            return;
        }

        ctrl.executeOneStep();

        loadData();
    }
    @FXML

    public void onRunAllStepButtonPressed() throws InterruptedException {
        if(selectedProgram.getExeStack().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Horray!");
            alert.setHeaderText("Your program is done!");
            alert.setContentText("Select another program to execute!");
            Image image = new Image("https://static-00.iconduck.com/assets.00/smiling-face-with-sunglasses-emoji-256x256-al6cgijr.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.showAndWait();
            return;
        }

        ctrl.allStep();

        loadData();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}