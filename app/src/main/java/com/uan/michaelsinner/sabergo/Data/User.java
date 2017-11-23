package com.uan.michaelsinner.sabergo.Data;

import android.net.Uri;

import java.util.HashMap;

/**
 * Created by Michael Sinner on 13/8/2017.
 */

public class User {
    /*- User Info -*/
    private String userID;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userImageProfile;
    private Uri imageProfile;
    //private URI imageProfile;
    private boolean doExamDiagnostic;
    private HashMap<String, Object> map;

    /*- User Game Info -*/
    private int userNivel;
    private String userRango;
    private int puntosLC;
    private int puntosMT;
    private int puntosIN;
    private int puntosCN;
    private int puntosCS;
    private int userDinero;
    private int progressLevelExp;

    /*- User Show Info -*/
    private int numExamDiagnostic;
    private int numMeteoritosDestruidos;
    private int numPreguntasMT;
    private int numPreguntasLC;
    private int numPreguntasCS;
    private int numPreguntasCN;
    private int numPreguntasIN;
private int numLogros;


    public User() {
        super();
    }

    public User(String userID, String userEmail, String userName) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userName = userName;

    }

    public User(String userID, String userEmail, String userName, Uri imagePhoto) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userName = userName;
        this.imageProfile = imagePhoto;

    }

    public User(String userID, String userEmail, String userPassword, String userName, String userImageProfile, int userNivel, String userRango, int puntosLC, int puntosMT, int puntosIN, int puntosCN, int puntosCS, int userDinero, int progressLevelExp, int numExamDiagnostic, int numMeteoritosDestruidos, int numPreguntasMT, int numPreguntasLC, int numPreguntasCS, int numPreguntasCN, int numPreguntasIN, boolean doExamDiagnostic,int numLogros) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userImageProfile = userImageProfile;
        this.doExamDiagnostic = doExamDiagnostic;
        this.userNivel = userNivel;
        this.userRango = userRango;
        this.puntosLC = puntosLC;
        this.puntosMT = puntosMT;
        this.puntosIN = puntosIN;
        this.puntosCN = puntosCN;
        this.puntosCS = puntosCS;
        this.userDinero = userDinero;
        this.progressLevelExp = progressLevelExp;
        this.numExamDiagnostic = numExamDiagnostic;
        this.numMeteoritosDestruidos = numMeteoritosDestruidos;
        this.numPreguntasMT = numPreguntasMT;
        this.numPreguntasLC = numPreguntasLC;
        this.numPreguntasCS = numPreguntasCS;
        this.numPreguntasCN = numPreguntasCN;
        this.numPreguntasIN = numPreguntasIN;
        this.numLogros = numLogros;
    }

    public HashMap<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("userID", userID);
        result.put("userEmail", userEmail);
        result.put("userPassword", userPassword);
        result.put("userName", userName);
        result.put("userImageProfile", userImageProfile);
        result.put("doExamDiagnostic", doExamDiagnostic);
        result.put("userNivel", userNivel);
        result.put("userRango", userRango);
        result.put("puntosLC", puntosLC);
        result.put("puntosMT", puntosMT);
        result.put("puntosCS", puntosCS);
        result.put("puntosCN", puntosCN);
        result.put("puntosIN", puntosIN);
        result.put("userDinero", userDinero);
        result.put("progressLevelExp", progressLevelExp);
        result.put("numExamDiagnostic", numExamDiagnostic);
        result.put("numMeteoritosDestruidos", numMeteoritosDestruidos);
        result.put("numPreguntasMT", numPreguntasMT);
        result.put("numPreguntasLC", numPreguntasLC);
        result.put("numPreguntasCS", numPreguntasCS);
        result.put("numPreguntasCN", numPreguntasCN);
        result.put("numPreguntasIN", numPreguntasIN);
        result.put("doExamDiagnostic", doExamDiagnostic);
        result.put("numLogros", numLogros);


        return result;

    }

    /*
    public User(String userID, String userEmail, String userPassword, String userName, String userImageProfile, int userNivel, String userRango, int puntosLC, int puntosMT, int puntosIN, int puntosCN, int puntosCS, int userDinero, int progressLevelExp, int numExamDiagnostic, int numMeteoritosDestruidos, int numPreguntasMT, int numPreguntasLC, int numPreguntasCS, int numPreguntasCN, int numPreguntasIN) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userImageProfile = userImageProfile;

        this.userNivel = userNivel;
        this.userRango = userRango;
        this.puntosLC = puntosLC;
        this.puntosMT = puntosMT;
        this.puntosIN = puntosIN;
        this.puntosCN = puntosCN;
        this.puntosCS = puntosCS;
        this.userDinero = userDinero;
        this.progressLevelExp = progressLevelExp;
        this.numExamDiagnostic = numExamDiagnostic;
        this.numMeteoritosDestruidos = numMeteoritosDestruidos;
        this.numPreguntasMT = numPreguntasMT;
        this.numPreguntasLC = numPreguntasLC;
        this.numPreguntasCS = numPreguntasCS;
        this.numPreguntasCN = numPreguntasCN;
        this.numPreguntasIN = numPreguntasIN;
    }
*/
    /*- Getters and Setters -*/
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImageProfile() {
        return userImageProfile;
    }

    public void setUserImageProfile(String userImageProfile) {
        this.userImageProfile = userImageProfile;
    }

    public boolean isDoExamDiagnostic() {
        return doExamDiagnostic;
    }

    public void setDoExamDiagnostic(boolean doExamDiagnostic) {
        this.doExamDiagnostic = doExamDiagnostic;
    }

    public int getUserNivel() {
        return userNivel;
    }

    public void setUserNivel(int userNivel) {
        this.userNivel = userNivel;
    }

    public String getUserRango() {
        return userRango;
    }

    public void setUserRango(String userRango) {
        this.userRango = userRango;
    }

    public int getPuntosLC() {
        return puntosLC;
    }

    public void setPuntosLC(int puntosLC) {
        this.puntosLC = puntosLC;
    }

    public int getPuntosMT() {
        return puntosMT;
    }

    public void setPuntosMT(int puntosMT) {
        this.puntosMT = puntosMT;
    }

    public int getPuntosIN() {
        return puntosIN;
    }

    public void setPuntosIN(int puntosIN) {
        this.puntosIN = puntosIN;
    }

    public int getPuntosCN() {
        return puntosCN;
    }

    public void setPuntosCN(int puntosCN) {
        this.puntosCN = puntosCN;
    }

    public int getPuntosCS() {
        return puntosCS;
    }

    public void setPuntosCS(int puntosCS) {
        this.puntosCS = puntosCS;
    }

    public int getUserDinero() {
        return userDinero;
    }

    public void setUserDinero(int userDinero) {
        this.userDinero = userDinero;
    }

    public int getProgressLevelExp() {
        return progressLevelExp;
    }

    public void setProgressLevelExp(int progressLevelExp) {
        this.progressLevelExp = progressLevelExp;
    }

    public int getNumExamDiagnostic() {
        return numExamDiagnostic;
    }

    public void setNumExamDiagnostic(int numExamDiagnostic) {
        this.numExamDiagnostic = numExamDiagnostic;
    }

    public int getNumMeteoritosDestruidos() {
        return numMeteoritosDestruidos;
    }

    public void setNumMeteoritosDestruidos(int numMeteoritosDestruidos) {
        this.numMeteoritosDestruidos = numMeteoritosDestruidos;
    }

    public int getNumPreguntasMT() {
        return numPreguntasMT;
    }

    public void setNumPreguntasMT(int numPreguntasMT) {
        this.numPreguntasMT = numPreguntasMT;
    }

    public int getNumPreguntasLC() {
        return numPreguntasLC;
    }

    public void setNumPreguntasLC(int numPreguntasLC) {
        this.numPreguntasLC = numPreguntasLC;
    }

    public int getNumPreguntasCS() {
        return numPreguntasCS;
    }

    public void setNumPreguntasCS(int numPreguntasCS) {
        this.numPreguntasCS = numPreguntasCS;
    }

    public int getNumPreguntasCN() {
        return numPreguntasCN;
    }

    public void setNumPreguntasCN(int numPreguntasCN) {
        this.numPreguntasCN = numPreguntasCN;
    }

    public int getNumPreguntasIN() {
        return numPreguntasIN;
    }

    public void setNumPreguntasIN(int numPreguntasIN) {
        this.numPreguntasIN = numPreguntasIN;
    }

    @Override
    public String toString() {
        return "User :" + getUserName() + " " + getUserEmail() + " " + getUserRango() + " " + getUserNivel();
    }

    public Uri getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(Uri imageProfile) {
        this.imageProfile = imageProfile;
    }

    public int getNumLogros() {
        return numLogros;
    }

    public void setNumLogros(int numLogros) {
        this.numLogros = numLogros;
    }


    public void setValues(User values) {
        userID = values.userID;
        userEmail = values.userEmail;
        userPassword = values.userPassword;
        userName = values.userName;
        userImageProfile = values.userImageProfile;
        doExamDiagnostic = values.doExamDiagnostic;
        userNivel = values.userNivel;
        userRango = values.userRango;
        puntosLC = values.puntosLC;
        puntosMT = values.puntosMT;
        puntosIN = values.puntosIN;
        puntosCN = values.puntosCN;
        puntosCS = values.puntosCS;
        userDinero = values.userDinero;
        progressLevelExp = values.progressLevelExp;
        numExamDiagnostic = values.numExamDiagnostic;
        numMeteoritosDestruidos = values.numMeteoritosDestruidos;
        numPreguntasMT = values.numPreguntasMT;
        numPreguntasLC = values.numPreguntasLC;
        numPreguntasCS = values.numPreguntasCS;
        numPreguntasCN = values.numPreguntasCN;
        numPreguntasIN = values.numPreguntasIN;
    }
}
