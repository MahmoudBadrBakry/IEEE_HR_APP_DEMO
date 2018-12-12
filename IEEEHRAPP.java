package ieee.hr.app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author IEEE JAVA TEAM ^_^
 */
public class IEEEHRAPP extends Application {
    Button selectBtn , addBtn , modifyBtn , deleteBtn , backBtnSelectScene;
    Scene selectScene,modifyScene;
    TableView <Member> table;
    ComboBox<String> committeeComboBox;
    Label committeeLabel;
    boolean once = true;
    static boolean answer = false;
    Pane mainPage , selectPage , modifyPage;
    ObservableList<Member> selectedToModify , allMembers;
    public static String css = IEEEHRAPP.class.getResource("myCss.css").toExternalForm();
    
    @Override
    public void start(Stage window) {
        window.setTitle("IEEE HR APP");

        
        mainPage = new Pane();

        //////Buttons Configuration
        selectBtn = new Button("Select Member");
        addBtn = new Button("Add Member");      
        
        addBtn.setMinSize(90,30 );
        addBtn.layoutXProperty().bind(mainPage.widthProperty().divide(10).multiply(7.4));
        addBtn.layoutYProperty().bind(mainPage.heightProperty().divide(10).multiply(8));

        
        selectBtn.setMinSize(90,30 );
        selectBtn.layoutXProperty().bind(mainPage.widthProperty().divide(10).multiply(1));
        selectBtn.layoutYProperty().bind(mainPage.heightProperty().divide(10).multiply(8));
        
        
        /////Buttons Actions
        addBtn.setOnAction(e -> {
            Member newMember = addMember();
            if(newMember.getId() > 0)
                SQLDB.insertMember(newMember);
            updateTableData();
        });
        selectBtn.setOnAction(e -> {
            window.setScene(selectScene);
        });

        /////IEEE logo
        ImageView ieeeLogo = new ImageView(new Image("ieee/hr/app/IEEEMSB Logo.JPG"));
        ieeeLogo.setFitHeight(280);
        ieeeLogo.setFitWidth(250);
        ieeeLogo.layoutXProperty().bind(mainPage.widthProperty().divide(10).multiply(3.5));
        ieeeLogo.layoutYProperty().bind(mainPage.heightProperty().divide(10).multiply(1));
        
        //////Pane view 
        
        mainPage.getChildren().addAll(addBtn,selectBtn,ieeeLogo);
 /////////mainScene creation
        Scene scene = new Scene(mainPage , 810 ,520);
    
        
        
//////////////////////////////select scene///////////////////////////
     try{
         
    ///idColumn 
    TableColumn <Member, Integer> idColumn = new TableColumn<>("ID");
    idColumn.setMinWidth(20);
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

    ///nameColumn
    TableColumn <Member, String> nameColumn = new TableColumn<>("Name");
    nameColumn.setMinWidth(120);
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    ///acadimicYearColumn 
    TableColumn <Member, Integer> acadimicYearColumn = new TableColumn<>("Acadimic Year");
    acadimicYearColumn.setMinWidth(20);
    acadimicYearColumn.setCellValueFactory(new PropertyValueFactory<>("acadimicYear"));

    ///mailColumn
    TableColumn <Member, String> mailColumn = new TableColumn<>("Mail");
    mailColumn.setMinWidth(150);
    mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));

    ///phoneColumn
    TableColumn <Member, String> phoneColumn = new TableColumn<>("Phone");
    phoneColumn.setMinWidth(100);
    phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    
    ///committeeColumn
    TableColumn <Member, String> committeeColumn = new TableColumn<>("Committee");
    committeeColumn.setMinWidth(102);
    committeeColumn.setCellValueFactory(new PropertyValueFactory<>("committee"));

    ///infoColumn
    TableColumn <Member, String> infoColumn = new TableColumn<>("Info");
    infoColumn.setMinWidth(170);
    infoColumn.setCellValueFactory(new PropertyValueFactory<>("info"));
    
    

    ////// table config 
    table = new TableView<>();

    table.getColumns().addAll(idColumn , nameColumn
            , acadimicYearColumn , phoneColumn 
            , mailColumn , committeeColumn , infoColumn);
        
    table.setLayoutX(0);
    table.setLayoutY(0);
    table.setMinSize(200, 170);
    table.setMaxSize(900, 460);
    updateTableData();
    
    ///////////////backBtnSelectScene
    backBtnSelectScene = new Button("Back");
    backBtnSelectScene.setLayoutX(460);
    backBtnSelectScene.setLayoutY(440);
    backBtnSelectScene.setMinSize(90,30 );
    backBtnSelectScene.setOnAction(e -> {
        window.setScene(mainPage.getScene());
    });
    
    ///////////////modifyBtn
    modifyBtn = new Button("Modify");
    modifyBtn.setLayoutX(60);
    modifyBtn.setLayoutY(440);
    modifyBtn.setMinSize(90,30 );
    modifyBtn.setOnAction(e -> {
        Member member = table.getSelectionModel().getSelectedItem();
            modifyMemeber(member);
            SQLDB.modifyMember(member);
            updateTableData();
    });
    
    ///////////////deleteBtn
    deleteBtn = new Button("Delete");
    deleteBtn.setLayoutX(260);
    deleteBtn.setLayoutY(440);
    deleteBtn.setMinSize(90,30 );
    deleteBtn.setOnAction( e -> {
        committeeComboBox.setValue("All");
        if(delete(table.getSelectionModel().getSelectedItem().getName())){
            int memberId = table.getSelectionModel().getSelectedItem().getId();
            SQLDB.deleteMember(memberId);
            updateTableData();
        }
        else
            System.out.println("hi");
    });
    
    //////////committee combobox
    committeeLabel = new Label();
    committeeLabel.setText("Committee");
    committeeLabel.setLayoutX(665);
    committeeLabel.setLayoutY(415);
    
    committeeComboBox = new ComboBox<>();
    committeeComboBox.setLayoutX(660);
    committeeComboBox.setLayoutY(445);
    committeeComboBox.getItems().addAll("PR","HR","Media"
            ,"SocialMedia","Operation","CS","RAS","BIO","EMBS","Board","All");
    committeeComboBox.setValue("All");
    committeeComboBox.setOnAction(e -> {
        ////////////////////////////////////////choosing committeeComboBox action
        updateTableData();
    });
    ////////selectPage
    selectPage = new AnchorPane();
    selectPage.getChildren().addAll(table , backBtnSelectScene , deleteBtn
            , modifyBtn , committeeComboBox  , committeeLabel);
    selectScene = new Scene(selectPage,810,520);
    selectScene.getStylesheets().add(css);
     }catch(Exception e ){
//         System.out.println(e.getLocalizedMessage());
//         System.out.println(e.getMessage());
//         System.out.println(e.fillInStackTrace());
     }       
    
///////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

        scene.getStylesheets().add(css);
        window.setMaxHeight(520);
        window.setMaxWidth(820);
        window.setMinHeight(520);
        window.setMinWidth(820);
        window.setScene(scene);
        window.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
  
    private void updateTableData() {
        if(once || (committeeComboBox.getValue().equals("All"))){
            table.setItems(SQLDB.selectAllMembers());
            once = false;
        }
        else {
        ObservableList<Member> tableData = FXCollections.observableArrayList();
        for (int i = 0 ; i < SQLDB.selectAllMembers().size() ; ++i ) {
            if ( SQLDB.selectAllMembers().get(i).getCommittee().contains(committeeComboBox.getValue()) ){
                tableData.add(SQLDB.selectAllMembers().get(i));
            }
        }
        table.setItems(tableData); 
        }
    }
    
    private Member addMember(){
        Member member = new Member(0 , "example" , 0 , "example@gmail" , "+201112223334" 
                , "Operation" , "new member");

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label labelId = new Label("Id: ");
        Label labelName = new Label("Name: ");
        Label labelAcadimicYear = new Label("Acadimic Year: ");
        Label labelMail = new Label("Mail: ");
        Label labelPhone = new Label("Phone: ");
        Label labelCommittee = new Label("Committee: ");
        Label labelInfo = new Label("info: ");

        GridPane.setConstraints(labelId, 0, 0);
        GridPane.setConstraints(labelName, 0, 1);
        GridPane.setConstraints(labelAcadimicYear, 0, 2);
        GridPane.setConstraints(labelMail, 0, 3);
        GridPane.setConstraints(labelPhone, 0, 4);
        GridPane.setConstraints(labelCommittee, 0, 5);
        GridPane.setConstraints(labelInfo, 0, 6);

        TextField textFieldId = new TextField();
        textFieldId.setPromptText(" Id");
        TextField textFieldName = new TextField();
        textFieldName.setPromptText(" Name");
        TextField textFieldAcadimicYear = new TextField();
        textFieldAcadimicYear.setPromptText(" Acadimic Year");
        TextField textFieldMail = new TextField();
        textFieldMail.setPromptText(" Mail");
        TextField textFieldPhone = new TextField();
        textFieldPhone.setPromptText(" Phone");
        ComboBox committeeComboBox1 = new ComboBox<>();
        committeeComboBox1.setPromptText("Committee");
        committeeComboBox1.getItems().addAll("PR","HR","Media"
            ,"SocialMedia","Operation","CS","RAS","BIO","EMBS","Board");
        TextField textFieldInfo = new TextField();
        textFieldInfo.setPromptText("Info");
        

        GridPane.setConstraints(textFieldId, 1, 0);
        GridPane.setConstraints(textFieldName, 1, 1);
        GridPane.setConstraints(textFieldAcadimicYear, 1, 2);
        GridPane.setConstraints(textFieldMail , 1, 3);
        GridPane.setConstraints(textFieldPhone , 1, 4);
        GridPane.setConstraints(committeeComboBox1 , 1, 5);
        GridPane.setConstraints(textFieldInfo , 1, 6);


        Button addBtnConfirm = new Button("Add Member");
        addBtnConfirm.setOnAction(e->{
            int id = Integer.parseInt(textFieldId.getText());
            String name = textFieldName.getText();
            int acadimicYear = Integer.parseInt(textFieldAcadimicYear.getText());
            String mail = textFieldMail.getText();
            String phone = textFieldPhone.getText();
            String committee = (String) committeeComboBox1.getValue();
            String info = textFieldInfo.getText();
            
            member.setId(id);
            member.setName(name);
            member.setAcadimicYear(acadimicYear);
            member.setMail(mail);
            member.setPhone(phone);
            member.setCommittee(committee);
            member.setInfo(info);
            window.close();
        });

        GridPane.setConstraints(addBtnConfirm, 1, 7, 2, 1);

        gridPane.getChildren().addAll(labelId, labelName, labelAcadimicYear 
        , labelMail , labelPhone , labelCommittee , labelInfo
        , textFieldId, textFieldName, textFieldAcadimicYear 
        , textFieldMail , textFieldPhone , committeeComboBox1 , textFieldInfo
                , addBtnConfirm);

        Scene scene = new Scene(gridPane, 320, 300);
        scene.getStylesheets().add(css);
        window.setMaxHeight(340);
        window.setMaxWidth(345);
        window.setMinHeight(340);
        window.setMinWidth(345);
        window.setTitle("Add New  Member");
        window.setScene(scene);
        window.showAndWait();
        return member;
    }

    
    private Member modifyMemeber(Member member){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label labelId = new Label("Id: ");
        Label labelName = new Label("Name: ");
        Label labelAcadimicYear = new Label("Acadimic Year: ");
        Label labelMail = new Label("Mail: ");
        Label labelPhone = new Label("Phone: ");
        Label labelCommittee = new Label("Committee: ");
        Label labelInfo = new Label("info: ");

        GridPane.setConstraints(labelId, 0, 0);
        GridPane.setConstraints(labelName, 0, 1);
        GridPane.setConstraints(labelAcadimicYear, 0, 2);
        GridPane.setConstraints(labelMail, 0, 3);
        GridPane.setConstraints(labelPhone, 0, 4);
        GridPane.setConstraints(labelCommittee, 0, 5);
        GridPane.setConstraints(labelInfo, 0, 6);

        TextField textFieldId = new TextField();
        TextField textFieldName = new TextField();
        TextField textFieldAcadimicYear = new TextField();
        TextField textFieldMail = new TextField();
        TextField textFieldPhone = new TextField();
        ComboBox committeeComboBox1 = new ComboBox<>();
        committeeComboBox1.getItems().addAll("PR","HR","Media"
            ,"SocialMedia","Operation","CS","RAS","BIO","EMBS","Board");
        TextField textFieldInfo = new TextField();
        
        textFieldId.setText(""+member.getId());
        textFieldId.setEditable(false);
        textFieldName.setText(""+member.getName());
        textFieldAcadimicYear.setText(""+member.getAcadimicYear());
        textFieldMail.setText(""+member.getMail());
        textFieldPhone.setText(""+member.getPhone());
        committeeComboBox1.setValue(""+member.getCommittee());
        textFieldInfo.setText(""+member.getInfo());

        GridPane.setConstraints(textFieldId, 1, 0);
        GridPane.setConstraints(textFieldName, 1, 1);
        GridPane.setConstraints(textFieldAcadimicYear, 1, 2);
        GridPane.setConstraints(textFieldMail , 1, 3);
        GridPane.setConstraints(textFieldPhone , 1, 4);
        GridPane.setConstraints(committeeComboBox1 , 1, 5);
        GridPane.setConstraints(textFieldInfo , 1, 6);


        Button modifyBtnConfirm = new Button("Modify");
        modifyBtnConfirm.setOnAction(e->{
            int id = Integer.parseInt(textFieldId.getText());
            String name = textFieldName.getText();
            int acadimicYear = Integer.parseInt(textFieldAcadimicYear.getText());
            String mail = textFieldMail.getText();
            String phone = textFieldPhone.getText();
            String committee = (String) committeeComboBox1.getValue();
            String info = textFieldInfo.getText();

            member.setId(id);
            member.setName(name);
            member.setAcadimicYear(acadimicYear);
            member.setMail(mail);
            member.setPhone(phone);
            member.setCommittee(committee);
            member.setInfo(info);
            window.close();
        });

        GridPane.setConstraints(modifyBtnConfirm, 1, 7, 2, 1);

        gridPane.getChildren().addAll(labelId, labelName, labelAcadimicYear 
        , labelMail , labelPhone , labelCommittee , labelInfo
        , textFieldId, textFieldName, textFieldAcadimicYear 
        , textFieldMail , textFieldPhone , committeeComboBox1 , textFieldInfo
                , modifyBtnConfirm);

        Scene scene = new Scene(gridPane, 320, 300);
        scene.getStylesheets().add(css);
        window.setMaxHeight(340);
        window.setMaxWidth(345);
        window.setMinHeight(340);
        window.setMinWidth(345);
        window.setTitle("Modify");
        window.setScene(scene);
        window.showAndWait();
        return member;
    }
    
     public static Boolean delete( String message){
        Stage window = new Stage();
        window.setTitle("Deleting Member!");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(470);
        window.setMinHeight(190);
        window.setMaxHeight(190);
        window.setMaxWidth(470);
        Label qusetion = new Label("Are you Sure you want to delete " + message + " ?!");
        
        Button yesBtn = new Button("Yes");
        yesBtn.setFont(Font.font(12.0));
        yesBtn.setOnAction(e -> {
           answer = true;
           window.close();
        });
        Button noBtn = new Button("No");
        noBtn.setOnAction(e -> {
            answer = false;
            window.close();
        });
       
        
        HBox layoutBtns = new HBox(20);
        layoutBtns.setPadding(new Insets(10, 10, 10, 10));
        layoutBtns.setAlignment(Pos.CENTER);
        layoutBtns.getChildren().addAll(yesBtn , noBtn);
        
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(qusetion , layoutBtns );
        layout.setAlignment(Pos.TOP_CENTER);
        
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(css);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
    
    
}
