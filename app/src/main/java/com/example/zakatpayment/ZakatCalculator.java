package com.example.zakatpayment;

public class ZakatCalculator {

        public static double calculateTotalGoldValue(double goldWeight, double goldValue) {
            return goldWeight * goldValue;
        }

        public static double calculateZakatPayable(double goldWeight, double goldValue, String type) {
            double excessWeight;

            if (type.equals("Keep")) {
                excessWeight = goldWeight - 85; // X value for 'Keep'
                if (excessWeight < 0) {
                    excessWeight = 0;
                }
            } else {
                excessWeight = goldWeight - 200; // X value for 'Wear'
                if (excessWeight < 0) {
                    excessWeight = 0;
                }
            }

            return excessWeight * goldValue;
        }

        public static double calculateTotalZakat(double goldWeight, double goldValue, String type) {
            double zakatPayable = calculateZakatPayable(goldWeight, goldValue, type);
            return 0.025 * zakatPayable;
        }
    }

