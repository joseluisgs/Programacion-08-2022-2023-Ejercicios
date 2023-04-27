package utils;

import models.GradoModulo;

public class Utils {

    public static int sacarNumeroAleatorioEntre1Y2(){
        double chance = Math.random();
        int numero;
        if(chance <= 0.5){
            numero = 1;
        }else{
            numero = 2;
        }
        return numero;
    }

    public static GradoModulo toEnumGrado(String grado) {
        GradoModulo gradoModulo = GradoModulo.SMR;
        switch (grado){
            case "DAM" ->  {
                gradoModulo = GradoModulo.DAM;
            }
            case "DAW" ->  {
                gradoModulo = GradoModulo.DAW;
            }
            case "ASIR" ->  {
                gradoModulo = GradoModulo.ASIR;
            }
        }
        return gradoModulo;
    }
}
