package com.effibot.robind_manipolator.modules.manual;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.effibot.robind_manipolator.modules.intro.IntroModule;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class WelcomeModule extends WorkbenchModule {

    public WelcomeModule() {
        super("Manuale", MaterialDesign.MDI_BOOK_OPEN);

    }

    @Override
    public Node activate() {
        AnchorPane anchorPane = new AnchorPane();
//        AnchorPane.setTopAnchor(anchorPane,0d);
//        AnchorPane.setLeftAnchor(anchorPane,0d);
//        AnchorPane.setBottomAnchor(anchorPane,0d);
//        AnchorPane.setRightAnchor(anchorPane,0d);
//        anchorPane.setPrefSize(400,400);
        VBox page = new VBox();
        page.minHeightProperty().bind(anchorPane.heightProperty());
        page.minWidthProperty().bind(anchorPane.widthProperty());
        page.setSpacing(15);
        page.setPadding(new Insets(200,0,0,0));
        page.setAlignment(Pos.TOP_CENTER);

        Text titleText = new Text("Benvenuti in Robind Manipulator");
        titleText.setFont(Font.font("Verdana",FontWeight.EXTRA_BOLD,20));
        titleText.setFill(Color.rgb(229,57,53));
        titleText.setTextAlignment(TextAlignment.CENTER);
        Text presentationText = new Text("""
                Questa è la pagina principale del simulatore del manipolatore ABB IRB 120 montato su un Rover 
                sviluppato da Andrea Efficace, Lorenzo Rossi, Martina Liberti dell'Università di Roma Tor Vergata.
                Questo simulatore prevede l'utilizzo di diverse API di JAVA,JAVAFX,Processing.org,MATLAB e Simulink. 
                La API di JAVAFX ha lo scopo di visualizzare e gestire tutte le interazioni dell'utente con il software tramite 
                l'utilizzo di vari strumenti tra cui WorkbenchFX,FormsFX,ControlsFX sviluppati da dlsc e controlsFX.
                La API Processing.org rappresenta il motore grafico dell'applicazione utilizzato sia per il posizionamento degli ostacoli 
                che per la visualizzazione del moto del rover e della cinematica inversa numerica ed analitica del manipolatore.
                Il ruolo di MATLAB API è quello di ricevere ed elaborare tutti i dati e le richieste provenienti da
                JAVAFX la cui comunicazione viene effettuata tramite il protocollo TCP.
                Infine, Simulink viene utilizzato per simulare la traiettoria generata in base alle scelte dell'utente.
                Queste API eseguono le seguenti macro funzionalità: pianificazione
                di traiettoria,pianificazione del moto, legge di controllo del moto, cinematica inversa di posizione e orientamento sia 
                con l'algoritmo di Newton che la sua versione analitica.
                Per ulteriori informazioni per i comandi da utilizzare durante le varie fasi dell'applicazione cliccare i seguenti bottoni.
                """);
        presentationText.setTextAlignment(TextAlignment.LEFT);
        presentationText.setFill(Color.rgb(33,33,33));
        presentationText.setFont(Font.font("Verdana",FontPosture.REGULAR,16));
        HBox hb = new HBox();
        hb.setSpacing(40);
        JFXButton startBtn = new JFXButton("Start Simulation");
        startBtn.setPrefSize(200,50);
        startBtn.setFont(Font.font("Verdana",FontPosture.REGULAR,16));

        startBtn.setOnMouseClicked(evt->{
            IntroModule in = new IntroModule();
            ObservableList<WorkbenchModule> moduleList = this.getWorkbench().getModules();
            for (WorkbenchModule wm:moduleList){
                if(wm.getName().equals(in.getName())) {
                    this.getWorkbench().openModule(wm);
                    return;
                }
            }
            this.getWorkbench().getModules().add(in);
            this.getWorkbench().openModule(in);

        });
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(startBtn);

        page.setBackground(new Background(new BackgroundFill(Color.rgb(115,194,194,0.7d),
                new CornerRadii(20d),new Insets(180,60,140,60))));
        page.getChildren().addAll(titleText,presentationText,hb);
        anchorPane.getChildren().add(page);
        return anchorPane;
    }
}