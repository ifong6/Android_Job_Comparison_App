package edu.gatech.seclass.jobcompare6300.models;

public class Job {

    protected String title;
    protected String company;
    protected String locationCity;
    protected String locationState;
    protected int costOfLiving;
    protected float yearlySalary;
    protected float yearlyBonus;
    protected float trainingDevFund;
    protected int leaveTime;
    protected int teleworkDays;

    // Track backing rowid in database, but do not include it as part of serialization
    public transient long rowId = -1;

    public Job(String title, String company, String city, String state, int costOfLiving, float salary, float bonus, float training, int leave, int telework) {
        setTitle(title);
        setCompany(company);
        setLocationCity(city);
        setLocationState(state);
        setCostOfLiving(costOfLiving);
        setYearlySalary(salary);
        setYearlyBonus(bonus);
        setTrainingDevFund(training);
        setLeaveTime(leave);
        setTeleworkDays(telework);
    }

    public String toString() {
        /*
        Returns a string representation of the Job
         */
        return getTitle() + " @ " + getCompany() + " in " + getCity() + ", " + getState();
    }

    public String getTitle() {
        return this.title;
    }

    public String getCompany() {
        return this.company;
    }

    public String getCity() {
        return this.locationCity;
    }

    public String getState() {
        return this.locationState;
    }

    public int getCostOfLiving() {
        return this.costOfLiving;
    }

    public float getSalary() {
        return this.yearlySalary;
    }

    public float getBonus() {
        return this.yearlyBonus;
    }

    public float getTrainingDevFund() {
        return this.trainingDevFund;
    }

    public int getLeaveTime() {
        return this.leaveTime;
    }

    public int getTeleworkDays() {
        return this.teleworkDays;
    }

    public void setTitle(String title) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("Job title cannot be blank");
        }
        this.title = title;
    }

    public void setCompany(String company) {
        if (company.isBlank()) {
            throw new IllegalArgumentException("Job company cannot be blank");
        }
        this.company = company;
    }

    public void setLocationCity(String city) {
        if (city.isBlank()) {
            throw new IllegalArgumentException("Job city cannot be blank");
        }
        this.locationCity = city;
    }

    public void setLocationState(String state) {
        if (state.isBlank()) {
            throw new IllegalArgumentException("Job state cannot be blank");
        }
        this.locationState = state;
    }

    public void setCostOfLiving(int costOfLiving) {
        if (costOfLiving <= 0) {
            throw new IllegalArgumentException("Cost of Living must be a positive integer greater than zero");
        }
        this.costOfLiving = costOfLiving;
    }

    public void setYearlySalary(float salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary must be a positive value");
        }
        this.yearlySalary = salary;
    }

    public void setYearlyBonus(float bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Bonus must be a positive value");
        }
        this.yearlyBonus = bonus;
    }

    public void setTrainingDevFund(float trainingFund) {
        if (0. > trainingFund || trainingFund > 18000.) {
            throw new IllegalArgumentException("Training and development fund must be between $0 and $18000 (inclusive)");
        }
        this.trainingDevFund = trainingFund;
    }

    public void setLeaveTime(int leaveTime) {
        if (0 > leaveTime || leaveTime > 100) {
            throw new IllegalArgumentException("Leave days must be a whole number between 0-100 (inclusive)");
        }
        this.leaveTime = leaveTime;
    }

    public void setTeleworkDays(int teleworkDays) {
        if (0 > teleworkDays || teleworkDays > 7) {
            throw new IllegalArgumentException("Number of telework days per week must be between 0-7 (inclusive)");
        }
        this.teleworkDays = teleworkDays;
    }

    public double getRank(ComparisonSettings settings) {
        double colaFactor = 100. / this.costOfLiving;
        double adjustedSalary = this.yearlySalary * colaFactor;
        double adjustedBonus = this.yearlyBonus * colaFactor;

        // AYS + AYB + TDF + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8))
        return 0 + // Zero just to get remaining values to align visually
                adjustedSalary * (settings.yearlySalary / 8.) +
                adjustedBonus * (settings.yearlyBonus / 8.) +
                this.trainingDevFund * (settings.trainingDevFund / 8.) +
                ((this.leaveTime * adjustedSalary / 260.) * (settings.leaveTime / 8.)) +
                ((260. - (52 * this.teleworkDays) * (adjustedSalary / 260.)) * (settings.teleworkDays / 8.));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Job other) {
            return this.title.equals(other.title) &&
                    this.company.equals(other.company) &&
                    this.locationCity.equals(other.locationCity) &&
                    this.locationState.equals(other.locationState) &&
                    this.costOfLiving == other.costOfLiving &&
                    this.yearlySalary == other.yearlySalary &&
                    this.yearlyBonus == other.yearlyBonus &&
                    this.trainingDevFund == other.trainingDevFund &&
                    this.leaveTime == other.leaveTime &&
                    this.teleworkDays == other.teleworkDays;
        }
        return false;
    }
}
