package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;


public class Controller {

    //Empezamos partida solo una vez.
   private static Partida partida = new Partida();

    //Hacemos referencia a elementos FXML

    //Botón de empezar y abandonar partida.
@FXML
    Button button_empezar_partida,button_abandonar_partida;

    //TextAreas donde están las victorias/derrotas de ambos jugadores.
@FXML
    TextArea vjugador1,vjugador2,djugador1,djugador2;

    //Texto que determina de quien es el turno.
@FXML
    Text tj1,tj2;

//Botones que son las casillas del tres en raya.
@FXML
    Button b0,b1,b2,b3,b4,b5,b6,b7,b8,bc;

//Array de botones, para poder acceder a ellos de manera dinámica.
@FXML
Button[] listabotones = new Button[8];


/*
* Método Empezar partida, que se ejecuta al hacer click en dicho botón.
* */
@FXML
    public void Empezar_Partida(ActionEvent event) {

    /*Obtenemos id del botón empezar partida, y asignamos al array de botones todos los botones clickables.*/
    button_empezar_partida = (Button) event.getSource();
    listabotones = new Button[]{b0, b1, b2, b3, b4, b5, b6, b7, b8};

    //Pedimos modo de juego al usuario
    partida.setModo(Alerts.Elige_Modo());


    if(partida.getModo() != null) {
        if(partida.getModo().equals("VSHumano")) {
            partida.EmpezarPartida();
            if(partida.getTurno()==0) {
                MostrarTurno(tj1);
            }else {
                MostrarTurno(tj2);
            }
            OcultarBoton(button_empezar_partida);
            MostrarBoton(button_abandonar_partida);
        }else {
            //Pedimos dificultad al usuario
            String dificultad = Alerts.Elige_Dificultad();
            if(dificultad != null) {
                partida.setIa(dificultad);
                partida.EmpezarPartida();
                OcultarBoton(button_empezar_partida);
                MostrarBoton(button_abandonar_partida);
                if(partida.getTurno()==0) {
                    MostrarTurno(tj1);
                    if(partida.getModo().equals("ComVSCom")) {
                        TurnoCOM(partida.getCuadricula());
                    }
                }else {
                    MostrarTurno(tj2);
                    TurnoCOM(partida.getCuadricula());
                }
            }
        }
    }
}

/*
* Método que se ejecuta al hacer click en el botón de abandonar partida.
* */
@FXML
    public void Abandonar_Partida(ActionEvent event) {

    button_abandonar_partida = (Button) event.getSource();
    Boolean respuesta = Alerts.Abandonar_Partida();

    if(respuesta) {
        Partida.AbandonarPartida();
        Restart();
    }
}

/*
* Método que se ejecuta al clickar encima de una casilla de la cuadrícula. (Boton)
* */
@FXML
    public void Marcar(ActionEvent event) throws InterruptedException {

    //Si hemos inicado la partida, podremos marcar.
    if(partida.getEstado()) {
        bc = (Button) event.getSource();
        String sid = bc.getId().replaceAll("[b]","");
        int id =Integer.valueOf(sid);
        char[] cuadricula = partida.getCuadricula();
        partida.PropiedadesTurno();

        //Si la casilla NO está marcada, podremos marcarla.
        if(partida.EstadoCuadricula(cuadricula[id])) {
            bc.styleProperty().setValue("-fx-text-fill: "+partida.getColor()+";");
            bc.setText(partida.getValue());
            partida.setPosCuadricula(id,partida.getValue().charAt(0));

            //Comprobamos si se dan las condiciones de Victoria.
            if(partida.ComprobarVictoria(partida.getValue().charAt(0))) {
                Partida.AnunciarGanador(partida.getValue().charAt(0));
                Restart();
                partida.FinalizarPartida();
            }else {
                if(partida.CuadriculaLLena()) {
                    Alerts.Anunciar_Empate();
                    Restart();
                    partida.FinalizarPartida();
                }
                else {
                    if(partida.getTurno() == 0) {
                        SetTurno(1,tj1,tj2);
                    }else {
                        SetTurno(0,tj2,tj1);
                    }
                }
            }
            if(!partida.getModo().equals("VSHumano") && partida.getEstado()) {
                TurnoCOM(cuadricula);
            }
        }
    }else {
        Alerts.Debes_Empezar_Partida();
    }
}

    /*Método que se ejecuta cuando es el Turno de COM, seteando las propiedades del turno,
    * y eligiendo movimiento a realizar segun dificultad elegida.*/
    private void TurnoCOM(char[] cuadricula) {
        partida.PropiedadesTurno();
        if(partida.getIa().equals("Fácil")) {
            IAFacil(cuadricula);
        }else if(partida.getIa().equals("Normal")) {
            //TODO: IANormal(cuadricula);
            IAFacil(cuadricula);
        }else {
            //TODO: IADificil(cuadricula);
            IAFacil(cuadricula);
        }

        if(partida.getEstado()) {
            if(partida.getModo().equals("ComVSCom")) {
                if(partida.getTurno()==0) {
                    SetTurno(1,tj1,tj2);
                }else {
                    SetTurno(0,tj2,tj1);
                }
                TurnoCOM(cuadricula);
            }else {
                SetTurno(0,tj2,tj1);
            }


        }
    }

    /*Método que se encarga del comportamiento de COM cuando su IA es fácil.
    * La asignación de casilla es aleatoria, siempre y cuando no esté ocupada.
    * */
    private void IAFacil(char[] cuadricula) {
        int random;
        do{
            random = (int) (Math.random()*10-1);
        }while (!partida.EstadoCuadricula(cuadricula[random]));
        listabotones[random].styleProperty().setValue("-fx-text-fill: "+partida.getColor()+";");
        listabotones[random].setText(partida.getValue());
        partida.setPosCuadricula(random,partida.getValue().charAt(0));
        if(partida.ComprobarVictoria(partida.getValue().charAt(0))) {
            Partida.AnunciarGanador(partida.getValue().charAt(0));
            Restart();
            partida.FinalizarPartida();
        }else {
            if(partida.CuadriculaLLena()) {
                Alerts.Anunciar_Empate();
                Restart();
                partida.FinalizarPartida();
            }
        }
    }

    //Método que se encarga de ocultar el botón indicado.
    private void OcultarBoton(Button button) {
        button.styleProperty().setValue("Visibility: false");
    }

    //Método que se encarga de Mostrar el botón indiciado.
    private void MostrarBoton(Button button) {
        button.styleProperty().setValue("Visibility: true");
    }

    //Método que se encarga de mostrar el Texto del turno
    private void MostrarTurno(Text tj) {
        tj.styleProperty().setValue("Visibility:true");
    }

    //Método que se encarga de ocultar el texto del turno.
    private void OcultarTurno(Text tj) {
        tj.styleProperty().setValue("Visibility: false");
    }

    //Método que se encarga de refrescar el marcador visual.
    private void RefrescarMarcador(Partida partida,TextArea vj1,TextArea vj2,TextArea dj1,TextArea dj2) {
        vj1.setText(String.valueOf(partida.getVjugador1()));
        vj2.setText(String.valueOf(partida.getVjugador2()));
        dj1.setText(String.valueOf(partida.getDjugador1()));
        dj2.setText(String.valueOf(partida.getDjugador2()));
    }

    //Método que se encarga de vaciar la cuadricula.
    private void VaciarCuadriculaFX() {
        for (Button button : listabotones) {
            button.textProperty().setValue("");
        }
    }

    //Método que se encarga de poner el tablero por default.
    private void Restart() {
        RefrescarMarcador(partida,vjugador1,vjugador2,djugador1,djugador2);
        OcultarTurno(tj1);
        OcultarTurno(tj2);
        OcultarBoton(button_abandonar_partida);
        MostrarBoton(button_empezar_partida);
        VaciarCuadriculaFX();
    }

    //Método que se encarga de setear los turnos.
    private void SetTurno(int turno,Text turnoOcultar, Text turnoMostrar) {
        partida.setTurno(turno);
        OcultarTurno(turnoOcultar);
        MostrarTurno(turnoMostrar);
    }
}
