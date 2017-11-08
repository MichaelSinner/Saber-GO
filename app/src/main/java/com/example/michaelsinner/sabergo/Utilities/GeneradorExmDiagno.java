package com.example.michaelsinner.sabergo.Utilities;

import android.util.Log;

import java.util.Random;

/**
 * Created by Michael Sinner on 4/5/2017.
 */

public class GeneradorExmDiagno {
    int numTotalPreguntas = 50;
    int numPreguntasMatematicas = 10;
    int numPreguntasLectura = 8;
    int numPreguntasSociales = 7;
    int numPreguntasCiencias = 12;
    int numPreguntasIngles = 10;
    int numPregutasCiv = 3;
    private int[] numsRandom;
    private int[] numbers;
    private Random random = new Random();

    public GeneradorExmDiagno(int numTotalPreguntas, int numPreguntasMatematicas, int numPreguntasLectura, int numPreguntasSociales, int numPreguntasCiencias, int numPreguntasIngles, int numPregutasCiv) {
        this.numTotalPreguntas = numTotalPreguntas;
        this.numPreguntasMatematicas = numPreguntasMatematicas;
        this.numPreguntasLectura = numPreguntasLectura;
        this.numPreguntasSociales = numPreguntasSociales;
        this.numPreguntasCiencias = numPreguntasCiencias;
        this.numPreguntasIngles = numPreguntasIngles;
        this.numPregutasCiv = numPregutasCiv;

        int[] array;
        array = generarMatematicas(numPreguntasMatematicas);

        for (int i = 0; i < numPreguntasMatematicas; i++) {
            Log.e("TAG", String.valueOf(array[i]));

        }

    }

    public int[] generarMatematicas(int tam) {
        int resultado;
        int auxiliar = tam;
        numsRandom = new int[tam];
        numbers = new int[tam];

        for (int i = 0; i < tam; i++) {
            numbers[i] = i + 1;
        }

        for (int i = 0; i < tam; i++) {
            resultado = random.nextInt(auxiliar);
            numsRandom[i] = numbers[resultado];
            numbers[resultado] = numbers[auxiliar - 1];
            auxiliar--;
        }
        return numsRandom;
    }
}
