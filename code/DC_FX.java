


import javafx.application.Application;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DC_FX extends Application {

  Donor current_donor = new Donor("D0", "123", "-");
  NGO current_NGO = new NGO("N0", "123", 1);
  DC the_DC = new DC();

  Scene scene1, scene2, scene3, scene4;

  public void start(Stage primaryStage) {
   Stage window = primaryStage;

    // SCENE 1, MAIN MENU----------------------------------------------
  
    Label label1 = new Label("MAIN MENU");

    Button toScene2 = new Button("UPDATE USER INFO");
    toScene2.setOnAction(e -> window.setScene(scene2));

    Button toScene3 = new Button("DONATE / REQUEST");
    toScene3.setOnAction(e -> window.setScene(scene3));

    Button toScene4 = new Button("DO AIDS MATCHING");
    toScene4.setOnAction(e -> window.setScene(scene4));

    VBox layout1 = new VBox(10);
    layout1.setPadding(new Insets(10, 10, 10, 10));
    layout1.getChildren().addAll(label1, toScene2, toScene3, toScene4);

    scene1 = new Scene(layout1, 550, 550);
    scene1.getStylesheets().add("font.css");


    // SCENE 2, UPDATE INFO ----------------------------------------------

    Label label2 = new Label("UPDATE INFO");

    Label idLabel = new Label("User id:");
    TextField id_input = new TextField("-");

    Label phoneLabel = new Label("Donor phone:");
    TextField phone_input = new TextField("-");

    Label powerLabel = new Label("NGO manpower:");
    TextField power_input = new TextField("1");

    Button update_donor = new Button("UPDATE DONOR INFO");
    update_donor.setOnAction(e ->  {
      current_donor = new Donor(id_input.getText(),"123", phone_input.getText());
    });

    Button update_NGO = new Button("UPDATE NGO INFO");
    update_NGO.setOnAction(e ->  {
      current_NGO = new NGO(id_input.getText(),"123", Integer.parseInt(power_input.getText()));
    });

    Button return_2 = new Button("return");
    return_2.setOnAction(e -> window.setScene(scene1));

    VBox layout2 = new VBox(10);
    layout2.setPadding(new Insets(10, 10, 10, 10));
    layout2.getChildren().addAll(label2, idLabel, id_input, phoneLabel, phone_input,powerLabel, power_input, update_donor, update_NGO, return_2);
    
    scene2 = new Scene(layout2, 550, 550);
    scene2.getStylesheets().add("font.css");


    // SCENE 3, DONATE / REQUEST ----------------------------------------------

    Label label3 = new Label("DONATE / REQUEST");

    Label itemLabel = new Label("Item name:");
    TextField item_input = new TextField("-");

    Label quantityLabel = new Label("Aids quantity:");
    TextField quantity_input = new TextField("1");

    Button donate_aids  = new Button("Donate aids");
    donate_aids.setOnAction(e -> the_DC.add_aids(current_donor, item_input.getText(), Integer.parseInt(quantity_input.getText())));

    Button req_aids = new Button("Request aids");
    req_aids.setOnAction(e -> the_DC.add_req(current_NGO, item_input.getText(), Integer.parseInt(quantity_input.getText())));

    Button clear_button = new Button("Clear all aids and request");
    clear_button.setOnAction(e -> the_DC.clear_all());

    Button return_3 = new Button("return");
    return_3.setOnAction(e -> window.setScene(scene1));

    VBox layout3 = new VBox(10);
    layout3.setPadding(new Insets(10, 10, 10, 10));
    layout3.getChildren().addAll(label3, itemLabel, item_input, quantityLabel, quantity_input, donate_aids, req_aids, clear_button, return_3);
    
    scene3 = new Scene(layout3, 550, 550);
    scene3.getStylesheets().add("font.css");

    // SCENE 4, MATCHING ----------------------------------------------
    Label label4 = new Label("MATCHING");

    ListView<String> matching = new ListView<>();
    matching.setPrefHeight(1000);
      
    Button view_all  = new Button("View all");
    view_all.setOnAction(e -> {
      matching.getItems().clear();
      for(String i : the_DC.get_view_all())
          matching.getItems().add(i); 
    });

    Button one_one = new Button("One-to-one");
    one_one.setOnAction(e->{
      matching.getItems().clear();
      for(String i : the_DC.match_one_to_one())
        matching.getItems().add(i);
    });

    Button one_many = new Button("One-to-many");
    one_many.setOnAction(e ->{
      matching.getItems().clear();
      for(String i : the_DC.match_one_to_many())
        matching.getItems().add(i);
    });

    Button many_one = new Button("Many-to-one");
    many_one.setOnAction(e->{
      matching.getItems().clear();
      for(String i : the_DC.match_many_to_one())
        matching.getItems().add(i);
    });

    Button many_many = new Button("Many-to-many");
    many_many.setOnAction(e->{
      matching.getItems().clear();
      for(String i : the_DC.match_many_to_many())
        matching.getItems().add(i);
    });

    Button return_4 = new Button("return");    
    return_4.setOnAction(e -> {
      window.setScene(scene1);
      matching.getItems().clear();
    });

    HBox horizon  = new HBox();
    horizon.getChildren().addAll(view_all, one_one, one_many,many_one, many_many, return_4);

    VBox layout4 = new VBox(10);
    layout4.setPadding(new Insets(10, 10, 10, 10));
    layout4.getChildren().addAll(label4, horizon, matching);

    scene4 = new Scene(layout4, 750, 750);
    scene4.getStylesheets().add("font.css");
  
//------------------------------------------------------------------------------
    window.setTitle("Distribution Centre");
    window.setScene(scene1);
    window.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
