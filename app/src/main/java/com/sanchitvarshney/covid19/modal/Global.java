package com.sanchitvarshney.covid19.modal;

public class Global {

    private String confirmed;
    private String recovered;
    private String death;

    public Global(String confirmed, String recovered, String death) {
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.death = death;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }
}
