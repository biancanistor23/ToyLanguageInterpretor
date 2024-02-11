package GUI;

import Controller.Controller;
import Exceptions.TypeCheckException;
import Model.ADTs.*;
import Model.ProgramState;
import Model.Types.IType;
import Repository.IRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import Model.Expressions.*;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Values.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ProgramListController implements Initializable {

    public ProgramListController(){}

    private List<Controller> programs= new ArrayList<>();

    @FXML
    private ListView<String> programListView=new ListView<>();


    public void setListView(){

        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        try{
            IStack<IStmt> exeStack1=new myStack<IStmt>();
            IDict<String, IValue> symTable1=new myDict<String, IValue>();
            IDict<StringValue, BufferedReader>fileTable1=new myDict<StringValue, BufferedReader>();
            IHeap<IValue> heap1=new myHeap<IValue>();
            IList<IValue> output1=new myList<IValue>();

            IDict<String, IType> typeenv=new myDict<String, IType>();
            ex1.typecheck(typeenv);

            ProgramState prg1 = new ProgramState(exeStack1, symTable1, fileTable1, heap1, output1, ex1, 1);
            List<ProgramState> l1 = new ArrayList<ProgramState>();
            l1.add(prg1);
            IRepository repo1 = new Repository(l1, "log1.txt");
            Controller ctr1 = new Controller(repo1,"Assignment Statement");

            programs.add(ctr1);
        }catch(TypeCheckException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 1 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }


        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithmExp('+',new ValueExp(new IntValue(2)),
                                new ArithmExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithmExp('+',new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        try{
            IStack<IStmt> exeStack2=new myStack<IStmt>();
            IDict<String, IValue> symTable2=new myDict<String, IValue>();
            IDict<StringValue, BufferedReader>fileTable2=new myDict<StringValue, BufferedReader>();
            IHeap<IValue> heap2=new myHeap<IValue>();
            IList<IValue> output2=new myList<IValue>();

            IDict<String, IType> typeenv=new myDict<String, IType>();
            ex2.typecheck(typeenv);

            ProgramState prg2 = new ProgramState(exeStack2,symTable2,fileTable2,heap2,output2,ex2,1);
            List<ProgramState> l2=new ArrayList<ProgramState>();
            l2.add(prg2);
            IRepository repo2 = new Repository(l2,"log2.txt");
            Controller ctr2 = new Controller(repo2,"Arithmentic Operation ( a=2+(3*5), b=a+1)");

            programs.add(ctr2);
        }catch(TypeCheckException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 2 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        IStmt ex9 = new CompStmt(new VarDeclStmt("b",new BoolType()),
                new CompStmt(new VarDeclStmt("c",new IntType()),
                        new CompStmt(new AssignStmt("b",new ValueExp(new BoolValue(true))),
                                new CompStmt(new ConditionalStmt("c",new VarExp("b"),new ValueExp(new IntValue(100)),new ValueExp(new IntValue(200))),
                                        new CompStmt(new PrintStmt(new VarExp("c")),
                                                new CompStmt(new ConditionalStmt("c",new ValueExp(new BoolValue(false)),new ValueExp(new IntValue(100)),new ValueExp(new IntValue(200))),
                                                        new PrintStmt(new VarExp("c"))))))));

        try{
            IStack<IStmt> exeStack9=new myStack<IStmt>();
            IDict<String, IValue> symTable9=new myDict<String, IValue>();
            IDict<StringValue, BufferedReader>fileTable9=new myDict<StringValue, BufferedReader>();
            IHeap<IValue> heap9=new myHeap<IValue>();
            IList<IValue> output9=new myList<IValue>();

            IDict<String, IType> typeenv=new myDict<String, IType>();
            ex9.typecheck(typeenv);

            ProgramState prg9 = new ProgramState(exeStack9, symTable9, fileTable9, heap9, output9, ex9, 1);
            List<ProgramState> l9 = new ArrayList<ProgramState>();
            l9.add(prg9);
            IRepository repo9 = new Repository(l9, "log9.txt");
            Controller ctr9 = new Controller(repo9,"Conditional Statement" );

            programs.add(ctr9);
        }catch(TypeCheckException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 9 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }
        IStmt ex10 = new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                new CompStmt(new NewHeapStmt("a",new ValueExp(new IntValue(20))),
                        new CompStmt(new ForStmt("v",new ValueExp(new IntValue(0)),new ValueExp(new IntValue(3)),new ArithmExp('+',new VarExp("v"),new ValueExp(new IntValue(1))),
                                new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),new AssignStmt("v",new ArithmExp('*',new VarExp("v"),new ReadHeapExp(new VarExp("a"))))))),
                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))));

        try{
            IStack<IStmt> exeStack10=new myStack<IStmt>();
            IDict<String, IValue> symTable10=new myDict<String, IValue>();
            IDict<StringValue, BufferedReader>fileTable10=new myDict<StringValue, BufferedReader>();
            IHeap<IValue> heap10=new myHeap<IValue>();
            IList<IValue> output10=new myList<IValue>();

            IDict<String, IType> typeenv=new myDict<String, IType>();
            ex10.typecheck(typeenv);

            ProgramState prg10 = new ProgramState(exeStack10, symTable10, fileTable10, heap10, output10, ex10, 1);
            List<ProgramState> l10 = new ArrayList<ProgramState>();
            l10.add(prg10);
            IRepository repo10 = new Repository(l10, "log10.txt");
            Controller ctr10 = new Controller(repo10,"For Stmt");

            programs.add(ctr10);
        }catch(TypeCheckException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 10 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        IStmt ex11 = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new VarDeclStmt("c",new IntType()),
                                new CompStmt(new AssignStmt("a",new ValueExp(new IntValue(1))),
                                        new CompStmt(new AssignStmt("b",new ValueExp(new IntValue(2))),
                                                new CompStmt(new AssignStmt("c",new ValueExp(new IntValue(5))),
                                                        new CompStmt(new SwitchStmt(new ArithmExp('*',new VarExp("a"),new ValueExp(new IntValue(10))),
                                                                new ArithmExp('*',new VarExp("b"),new VarExp("c")),
                                                                new ValueExp(new IntValue(10)),
                                                                new CompStmt(new PrintStmt(new VarExp("a")),new PrintStmt(new VarExp("b"))),
                                                                new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),new PrintStmt(new ValueExp(new IntValue(200)))),
                                                                new PrintStmt(new ValueExp(new IntValue(300)))),
                                                                new PrintStmt(new ValueExp(new IntValue(300))))))))));

        try{
            IStack<IStmt> exeStack11=new myStack<IStmt>();
            IDict<String, IValue> symTable11=new myDict<String, IValue>();
            IDict<StringValue, BufferedReader>fileTable11=new myDict<StringValue, BufferedReader>();
            IHeap<IValue> heap11=new myHeap<IValue>();
            IList<IValue> output11=new myList<IValue>();

            IDict<String, IType> typeenv=new myDict<String, IType>();
            ex11.typecheck(typeenv);

            ProgramState prg11 = new ProgramState(exeStack11, symTable11, fileTable11, heap11, output11, ex11, 1);
            List<ProgramState> l11 = new ArrayList<ProgramState>();
            l11.add(prg11);
            IRepository repo11 = new Repository(l11, "log11.txt");
            Controller ctr11 = new Controller(repo11,"Switch Stmt");

            programs.add(ctr11);
        }catch(TypeCheckException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 11 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }


        IStmt ex12 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(0))),
                        new CompStmt(new RepeatStmt(new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v",new ArithmExp('-',new VarExp("v"),new ValueExp(new IntValue(1)))))),
                                new AssignStmt("v",new ArithmExp('+',new VarExp("v"),new ValueExp(new IntValue(1))))),
                                new RelationalExp(new VarExp("v"),new ValueExp(new IntValue(3)),"==")),
                                new PrintStmt(new ArithmExp('*',new VarExp("v"),new ValueExp(new IntValue(10)))))));

        try{
            IStack<IStmt> exeStack12=new myStack<IStmt>();
            IDict<String, IValue> symTable12=new myDict<String, IValue>();
            IDict<StringValue, BufferedReader>fileTable12=new myDict<StringValue, BufferedReader>();
            IHeap<IValue> heap12=new myHeap<IValue>();
            IList<IValue> output12=new myList<IValue>();

            IDict<String, IType> typeenv=new myDict<String, IType>();
            ex12.typecheck(typeenv);

            ProgramState prg12 = new ProgramState(exeStack12, symTable12, fileTable12, heap12, output12, ex12, 1);
            List<ProgramState> l12 = new ArrayList<ProgramState>();
            l12.add(prg12);
            IRepository repo12 = new Repository(l12, "log12.txt");
            Controller ctr12 = new Controller(repo12,"Repeat Until Stmt");

            programs.add(ctr12);
        }catch(TypeCheckException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 12 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        IStmt ex13 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(10))),
                        new CompStmt(new ForkStmt(new CompStmt(new AssignStmt("v",new ArithmExp('-',new VarExp("v"),new ValueExp(new IntValue(1)))),
                                new CompStmt(new AssignStmt("v",new ArithmExp('-',new VarExp("v"),new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("v"))))),
                                new CompStmt(new SleepStmt(10),new PrintStmt(new ArithmExp('*',new VarExp("v"),new ValueExp(new IntValue(10))))))));

        try{
            IStack<IStmt> exeStack13=new myStack<IStmt>();
            IDict<String, IValue> symTable13=new myDict<String, IValue>();
            IDict<StringValue, BufferedReader>fileTable13=new myDict<StringValue, BufferedReader>();
            IHeap<IValue> heap13=new myHeap<IValue>();
            IList<IValue> output13=new myList<IValue>();

            IDict<String, IType> typeenv=new myDict<String, IType>();
            ex13.typecheck(typeenv);

            ProgramState prg13 = new ProgramState(exeStack13, symTable13, fileTable13, heap13, output13, ex13, 1);
            List<ProgramState> l13 = new ArrayList<ProgramState>();
            l13.add(prg13);
            IRepository repo13 = new Repository(l13, "log13.txt");
            Controller ctr13 = new Controller(repo13,"Sleep");

            programs.add(ctr13);
        }catch(TypeCheckException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 13 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }


        IStmt ex14 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new WaitStmt(10),new PrintStmt(new ArithmExp('*',new VarExp("v"),new ValueExp(new IntValue(10)))))));

        try{
            IStack<IStmt> exeStack14=new myStack<IStmt>();
            IDict<String, IValue> symTable14=new myDict<String, IValue>();
            IDict<StringValue, BufferedReader>fileTable14=new myDict<StringValue, BufferedReader>();
            IHeap<IValue> heap14=new myHeap<IValue>();
            IList<IValue> output14=new myList<IValue>();

            IDict<String, IType> typeenv=new myDict<String, IType>();
            ex14.typecheck(typeenv);

            ProgramState prg14 = new ProgramState(exeStack14, symTable14, fileTable14, heap14, output14, ex14, 1);
            List<ProgramState> l14 = new ArrayList<ProgramState>();
            l14.add(prg14);
            IRepository repo14 = new Repository(l14, "log14.txt");
            Controller ctr14 = new Controller(repo14,ex14.toString());

            programs.add(ctr14);
        }catch(TypeCheckException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 14 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        IStmt ex15 = new CompStmt(new VarDeclStmt("v1",new IntType()),
                new CompStmt(new VarDeclStmt("v2",new IntType()),
                        new CompStmt(new AssignStmt("v1",new ValueExp(new IntValue(2))),
                                new CompStmt(new AssignStmt("v2",new ValueExp(new IntValue(3))),
                                        new IfStmt(new RelationalExp(new VarExp("v1"),new ValueExp(new IntValue(0)),">"),
                                                new PrintStmt(new MULExp(new VarExp("v1"),new VarExp("v2"))),new PrintStmt(new VarExp("v1")))))));

        try{
            IStack<IStmt> exeStack15=new myStack<IStmt>();
            IDict<String, IValue> symTable15=new myDict<String, IValue>();
            IDict<StringValue, BufferedReader>fileTable15=new myDict<StringValue, BufferedReader>();
            IHeap<IValue> heap15=new myHeap<IValue>();
            IList<IValue> output15=new myList<IValue>();

            IDict<String, IType> typeenv=new myDict<String, IType>();
            ex15.typecheck(typeenv);

            ProgramState prg15 = new ProgramState(exeStack15, symTable15, fileTable15, heap15, output15, ex15, 1);
            List<ProgramState> l15 = new ArrayList<ProgramState>();
            l15.add(prg15);
            IRepository repo15 = new Repository(l15, "ex15");
            Controller ctr15 = new Controller(repo15,"MUL Expresion");

            programs.add(ctr15);
        }catch(TypeCheckException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 15 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }


        IStmt ex16 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(0))),
                        new CompStmt(new DoWhileStmt(new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v",new ArithmExp('-',new VarExp("v"),new ValueExp(new IntValue(1)))))),
                                new AssignStmt("v",new ArithmExp('+',new VarExp("v"),new ValueExp(new IntValue(1))))),
                                new RelationalExp(new VarExp("v"),new ValueExp(new IntValue(3)),"<")),
                                new PrintStmt(new ArithmExp('*',new VarExp("v"),new ValueExp(new IntValue(10)))))));

        try{
            IStack<IStmt> exeStack16=new myStack<IStmt>();
            IDict<String, IValue> symTable16=new myDict<String, IValue>();
            IDict<StringValue, BufferedReader>fileTable16=new myDict<StringValue, BufferedReader>();
            IHeap<IValue> heap16=new myHeap<IValue>();
            IList<IValue> output16=new myList<IValue>();

            IDict<String, IType> typeenv=new myDict<String, IType>();
            ex16.typecheck(typeenv);

            ProgramState prg16 = new ProgramState(exeStack16, symTable16, fileTable16, heap16, output16, ex16, 1);
            List<ProgramState> l16 = new ArrayList<ProgramState>();
            l16.add(prg16);
            IRepository repo16 = new Repository(l16, "log16.txt");
            Controller ctr16 = new Controller(repo16,"Do While");

            programs.add(ctr16);
        }catch(TypeCheckException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 16 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        programListView.setItems(FXCollections.observableArrayList(
                programs.stream().map(Controller::getProgramName).collect(Collectors.toList())
        ));

    }
    @FXML
    private void onRunButtonPressed() {
        if (programListView.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramRunLayout.fxml"));
                Parent interpreterView = loader.load();
                ProgramRunController controller = loader.getController();
                controller.setController(programs.get(programListView.getSelectionModel().getSelectedIndex()));
                Stage stage = new Stage();
                stage.setTitle("Main Window");
                stage.setScene(new Scene(interpreterView));
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("OH NO!");
                alert.setHeaderText("Program was not selected!");
                alert.setContentText("Please select a program to execute");
                Image image = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Emojione_1F62D.svg/64px-Emojione_1F62D.svg.png");
                ImageView imageView = new ImageView(image);
                alert.setGraphic(imageView);
                alert.showAndWait();
                return;
            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
