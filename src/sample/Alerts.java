package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {


    //Alerta que nos permite elegir el modo que jugaremos.
    public static String Elige_Modo() {
        String modo = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Elige un modo de juego");
        alert.setHeaderText("Humano VS Humano: Partida humano contra humano.\nHumano VS COM: Partida humano contra el ordenador.\nCOM VS COM: Partida ordenador vs ordenador");

        ButtonType btHumanoVsHumano = new ButtonType("VSHumano");
        ButtonType btHumanoVsCom = new ButtonType("VSCom");
        ButtonType btComVsCom = new ButtonType("ComVSCom");

        alert.getButtonTypes().setAll(btHumanoVsHumano,btHumanoVsCom,btComVsCom,ButtonType.CLOSE);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == btHumanoVsHumano) {
            modo = "VSHumano";
        }else if(result.get() == btHumanoVsCom) {
            modo = "VSCom";
        }else if(result.get() == btComVsCom) {
            modo = "ComVSCom";
        }else {
            alert.close();
            modo = null;
        }

        return modo;
    }

    //Alerta que nos permite elegir dificultad.
    public static String Elige_Dificultad() {
        String dificultad = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Vamos a jugar al tres en raya!");
        alert.setHeaderText("Elije una dificultad del juego.");


        ButtonType btFacil = new ButtonType("Fácil");
        ButtonType btNormal = new ButtonType("Normal");
        ButtonType btDificil = new ButtonType("Dificil");

        alert.getButtonTypes().setAll(btFacil, btNormal, btDificil, ButtonType.CLOSE);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == btFacil) {
            dificultad = "Fácil";
        } else if (result.get() == btNormal) {
            dificultad = "Normal";
        } else if (result.get() == btDificil) {
            dificultad = "Difícil";
        } else {
            alert.close();
            dificultad = null;
        }
        return dificultad;
    }

    //Alerta mostrada al indicar que queremos abandonar la partida.
    public static Boolean Abandonar_Partida() {
        Boolean respuesta = null;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Estás seguro que deseas abandonar la partida?");
        alert.setHeaderText("(Si abandonas la partida, obtendrás una derrota.\nY el otro jugador sumará una victoria).");
        alert.getButtonTypes().setAll(ButtonType.CLOSE,ButtonType.YES,ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES) {
            respuesta = true;
        }else{
            respuesta=false;
            alert.close();
        }
        return respuesta;
    }

    //Alerta mostrada al intentar jugar, mientras no está empezada la partida.
    public static void Debes_Empezar_Partida() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setHeaderText("No puedes jugar al tres en raya, sin antes darle a \"EMPEZAR PARTIDA\"");
        alert.getButtonTypes().setAll(ButtonType.CLOSE);
        alert.showAndWait();
    }

    //Alerta mostrada para anuciar al ganador.
    public  static void Anunciar_Ganador(int ganador) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(ganador == 0) {
            alert.setTitle("¡VICTORIA!");
            alert.setHeaderText("¡Enhorabuena jugador 1, has ganado!");
        }else {
            alert.setTitle("¡DERROTA!");
            alert.setHeaderText("¡Jugador 1, has perdido!");
        }
        alert.getButtonTypes().setAll(ButtonType.CLOSE);
        alert.showAndWait();
    }

    //Alerta mostrada al empatar.
    public  static void Anunciar_Empate() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¡EMPATE!");
        alert.setHeaderText("¡Ningun jugador ha logrado ganar al otro!");
        alert.getButtonTypes().setAll(ButtonType.CLOSE);
        alert.showAndWait();
    }
}
