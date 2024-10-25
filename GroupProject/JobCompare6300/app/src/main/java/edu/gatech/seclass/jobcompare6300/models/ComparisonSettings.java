package edu.gatech.seclass.jobcompare6300.models;

public class ComparisonSettings {

    protected int yearlySalary;
    protected int yearlyBonus;
    protected int trainingDevFund;
    protected int leaveTime;
    protected int teleworkDays;

    public ComparisonSettings() {
        this(1, 1, 1, 1, 1);
    }

    public ComparisonSettings(int yearlySalary, int yearlyBonus, int trainingDevFund, int leaveTime, int teleworkDays) {
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.trainingDevFund = trainingDevFund;
        this.leaveTime = leaveTime;
        this.teleworkDays = teleworkDays;
    }

    public int getSalaryWeight() {
        return this.yearlySalary;
    }

    public int getBonusWeight() {
        return this.yearlyBonus;
    }

    public int getTrainingWeight() {
        return this.trainingDevFund;
    }

    public int getLeaveWeight() {
        return this.leaveTime;
    }

    public int getTeleworkWeight() {
        return this.teleworkDays;
    }

    public void setSalaryWeight(int value) {
        validateSetting(value);
        this.yearlySalary = value;
    }

    public void setBonusWeight(int value) {
        validateSetting(value);
        this.yearlyBonus = value;
    }

    public void setTrainingDevFundWeight(int value) {
        validateSetting(value);
        this.trainingDevFund = value;
    }

    public void setLeaveTimeWeight(int value) {
        validateSetting(value);
        this.leaveTime = value;
    }

    public void setTeleworkDaysWeight(int value) {
        validateSetting(value);
        this.teleworkDays = value;
    }

    protected void validateSetting(int value) throws IllegalArgumentException {
        if (0 > value || value > 9) {
            throw new IllegalArgumentException("Setting value must be between 0-9 (inclusive)");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparisonSettings other) {
            return this.yearlySalary == other.yearlySalary &&
                    this.yearlyBonus == other.yearlyBonus &&
                    this.trainingDevFund == other.trainingDevFund &&
                    this.leaveTime == other.leaveTime &&
                    this.teleworkDays == other.teleworkDays;
        }
        return false;
    }
}
