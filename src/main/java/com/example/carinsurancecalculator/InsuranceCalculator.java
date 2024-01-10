package com.example.carinsurancecalculator;

public class InsuranceCalculator {

    // Basic premium calculation method
    public double calculateBasicPremium(double sumInsured, int engineSize, String coverageType, boolean isWestMalaysia, double ncd) {
        double basicPremium = 0.0;

        // Determine the comprehensive rate based on the region and engine size
        double comprehensiveRate = getComprehensiveRate(engineSize, isWestMalaysia, coverageType.equals("Third Party"));

        // Calculate basic premium based on sum insured and engine size
        double rate = (isWestMalaysia) ? 26.0 : 20.30;
        basicPremium = comprehensiveRate + (sumInsured - 1000) / 1000 * rate;

        return basicPremium;
    }



    // Helper method to get the comprehensive rate based on engine size
    private double getComprehensiveRate(int engineSize, boolean isWestMalaysia, boolean isThirdParty) {
        if (isWestMalaysia) {
            if (engineSize <= 1400) {
                return (isThirdParty) ? 120.6 : 273.8;
            } else if (engineSize <= 1650) {
                return (isThirdParty) ? 135.0 : 305.5;
            } else if (engineSize <= 2200) {
                return (isThirdParty) ? 151.2 : 339.1;
            } else if (engineSize <= 3050) {
                return (isThirdParty) ? 167.4 : 372.6;
            } else if (engineSize <= 4100) {
                return (isThirdParty) ? 181.8 : 404.3;
            } else if (engineSize <= 4250) {
                return (isThirdParty) ? 196.2 : 436.0;
            } else if (engineSize <= 4400) {
                return (isThirdParty) ? 212.4 : 469.6;
            } else {
                return (isThirdParty) ? 226.8 : 501.3;
            }
        } else { // East Malaysia
            if (engineSize <= 1400) {
                return (isThirdParty) ? 67.5 : 196.2;
            } else if (engineSize <= 1650) {
                return (isThirdParty) ? 75.6 : 220.0;
            } else if (engineSize <= 2200) {
                return (isThirdParty) ? 85.2 : 243.9;
            } else if (engineSize <= 3050) {
                return (isThirdParty) ? 93.6 : 266.5;
            } else if (engineSize <= 4100) {
                return (isThirdParty) ? 101.7 : 290.4;
            } else if (engineSize <= 4250) {
                return (isThirdParty) ? 110.1 : 313.0;
            } else if (engineSize <= 4400) {
                return (isThirdParty) ? 118.2 : 336.4;
            } else {
                return (isThirdParty) ? 126.6 : 359.5;
            }
        }
    }


}
