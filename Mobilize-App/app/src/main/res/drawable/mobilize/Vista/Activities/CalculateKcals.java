package com.app.mobilize.Vista.Activities;

public class CalculateKcals {

    public CalculateKcals() {
    }

    MetKmh[] runningKcal = {new MetKmh(8,8),
            new MetKmh(9,8.4),
            new MetKmh(10, 9.5),
            new MetKmh(11, 10.8),
            new MetKmh(11.5, 11.26),
            new MetKmh(12.5, 12),
            new MetKmh(13.5, 12.9),
            new MetKmh(14, 13.84),
            new MetKmh(15, 14.5),
            new MetKmh(16,16)
    };

    MetKmh[] cyclingKcal = {new MetKmh(4,16),
            new MetKmh(6,20),
            new MetKmh(8, 22),
            new MetKmh(10, 25.8),
            new MetKmh(12, 30.5),
            new MetKmh(16, 32.2)
    };


    public double calculateRunningKcal(double pes, double ritme) {
        double met = getMet(ritme, "running");
        return (met*pes*3.5)/200;
    }

    public double calculateCyclingKcal ( double pes, double ritme ) {
        double met = getMet(ritme, "cycling");
        return (met*pes*3.5)/200;
    }

    private double getMet(double ritme, String disciplina) {
        double dif = 100;
        int it = -1;
        if ( disciplina.equals("running") ) {
            for (int i = 0; i < runningKcal.length; i++) {
                if (Math.abs(runningKcal[i].getKmh() - ritme) < dif) {
                    dif = Math.abs(runningKcal[i].getKmh() - ritme);
                    it = i;
                }
            }
            if ( it > 0 ) return runningKcal[it].getMet();
            else return 5;
        }
        else  {
            for (int i = 0; i < cyclingKcal.length; i++) {
                if (Math.abs(cyclingKcal[i].getKmh() - ritme) < dif) {
                    dif = Math.abs(cyclingKcal[i].getKmh() - ritme);
                    it = i;
                }
            }
            if ( it > 0 ) return cyclingKcal[it].getMet();
            else return 5;
        }
    }

    private class MetKmh {
        private double met;
        private double kmh;

        public MetKmh(double met, double kmh) {
            this.met = met;
            this.kmh = kmh;
        }

        public double getMet() {
            return met;
        }

        public void setMet(double met) {
            this.met = met;
        }

        public double getKmh() {
            return kmh;
        }

        public void setKmh(double kmh) {
            this.kmh = kmh;
        }
    }
}