# Test Plan V2.2

**Author**: \<Team 134\>

**Change Log**:<br>
V3.0 - Final Test Plan document. Reformated Test case table and added all test cases. 
V2.2 - Fixed minor typo at section 2.2
V2.1 - Added location display bug to table
V2.0 - Filled in test cases from initial testing and alpha version of app
V1.0 - Added test cases and revised '1.1 Overall strategy'
V0.0 - Initial Draft<br>

## 1 Testing Strategy

### 1.1 Overall strategy

Our testing strategy for the application includes unit, integration, system, and regression testing to ensure robustness and reliability using techniques such as black and white box testing with JUnit.

1.1.1 Unit test:<br>
Unit test will be developed to validate individual components or units of the app to ensure that each part functions correctly in isolation. It will cover each class and methods using JUnit. Tests are executed continuously during development phase to catch issues early. Each team member will be responsible for writing and maintaining unit test for the code they develop, and also review others' unit tests for coverage and completeness.

1.1.2 Integration test:<br>
Integration test will be developed to verify that modules or services developed by each developer will function together as expected. It will cover interactions between components such as database queries, API calls, and UI interactions, using tools such as Robolectric for simulating Android components in a unit test environment. Usually QA will execute and monitor integration test, reporting issues to the developers.

1.1.3 System test:<br>
System test will be developed to validate the complete and integrated application to ensure it meets the specified requirements. System test cases will be developed based on the application's functional and non-functional requirements. Also, these cases will validate that all features, user workflows, and interactions perform as expected. Similiar to integeration test, QA mainly conduct system testing to idenfity issues with help from development.

1.1.4 Regression Testing<br>
Regression Test will be developed to ensure that new code changes do not affect the existing functionality of the application. This test will be performed during the late stage of development. Both developers and QA will ensure new added code is functional and continue to manage the regression test suite for any issues.

### 1.2 Test Selection

Test selection will involve both white-box and black-box approaches. For unit and integration testing, we will utilize JUnit tests to ensure complete code coverage and identify potential failure points. For integration and system testing, we will apply black box tests to verify all application requirements and overall system functionality.

### 1.3 Adequacy Criterion

Test quality will be ensured using functional and structural coverage across all testing levels. Unit tests will target 90% code coverage with JUnit, ensuring every method and variable is included, covering all potential failure. Integration tests will focus on testing interdependent code under normal, edge, and failure cases. System tests will address all functional requirements and real-world user scenarios. Regression tests will prioritize change impact and critical path coverage. This comprehensive approach ensures our application is both functionally complete and structurally sound.

### 1.4 Bug Tracking

Bug tracking will be managed using a shared document such as a spreadsheet. Team members can log discovered bugs and assign priorities based on their impact and severity. They will document the following details:

- The date when the bug is found.
- A detailed description of the bug.
- record where the bug is located and how the bug is produced.
- Severity (critical, major, minor).
- Priority (high, medium, low).
- The status of the bug (new, in progress, fixed, closed).
- The person identifying the bug
- The person responsible for fixing the bug.

The following will be how the shared bug tracking spreadsheet looks:

| Date       | Description of Bug                       | Where                   | Severity | Priority | Status | Assignee | Found by |
| ---------- | ---------------------------------------- | ----------------------- | -------- | -------- | ------ | -------- | -------- |
| 07/02/2024 | Location of Job 1 displays as City, City | ComparisonTableActivity | 0        | 0        | Fixed  | Nick     | Nick     |

### 1.5 Technology

Junit and espresso will be primarily used for the testings.

## 2 Test Cases

Tests will be added and updated as necessary throughout the development phase. Comprehensive system-wide tests and the final integration test will be conducted during the end of development phase. A complete test suite will be utilized for regression testing before the final release.

### 2.1 Unit Test Cases

| No. | Test Case                           | Test Description                                                                                           | Steps to execute               | Expected Result                                            | Actual Result                                              | Pass/Fail |
|-----|-------------------------------------|------------------------------------------------------------------------------------------------------------|--------------------------------|------------------------------------------------------------|------------------------------------------------------------|-----------|
| 1   | SalaryWeightMustBePositive          | Test Compare Settings for invalid salary settings                                                          | run ComparisonSettingsUnitTest | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 2   | SalaryWeightCannotExceed9           | Test Compare Settings for invalid salary settings                                                          | run ComparisonSettingsUnitTest | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 3   | BonusWeightMustBePositive           | Test Compare Settings for  invalid Bonus settings                                                          | run ComparisonSettingsUnitTest | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 4   | BonusWeightCannotExceed9            | Test Compare Settings for  invalid Bonus settings                                                          | run ComparisonSettingsUnitTest | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 5   | TrainingDevFundWeightMustBePositive | Test Compare Settings for  invalid Development settings                                                    | run ComparisonSettingsUnitTest | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 6   | TrainingDevFundWeightCannotExceed9  | Test Compare Settings for  invalid Development settings                                                    | run ComparisonSettingsUnitTest | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 7   | LeaveTimeWeightMustBePositive       | Test Compare Settings for  invalid Leave time settings                                                     | run ComparisonSettingsUnitTest | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 8   | LeaveTimeWeightCannotExceed9        | Test Compare Settings for  invalid Leave time settings                                                     | run ComparisonSettingsUnitTest | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 9   | TeleworkDaysWeightMustBePositive    | Test Compare Settings for  invalid Telework settings                                                       | run ComparisonSettingsUnitTest | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 10  | TeleworkDaysWeightCannotExceed9     | Test Compare Settings for  invalid Telework settings                                                       | run ComparisonSettingsUnitTest | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 11  | defaultSettingsEqualWeight          | JobComparator unit test validating ranks are same if all jobs are same                                     | run JobComparatorTest          | All Job ranks are same                                     | All Job ranks are same                                     | Pass      |
| 12  | ensureSortedOrdering                | JobComparator unit test validating jobs will be sorted as expected                                         | run JobComparatorTest          | Jobs are in correct order                                  | Jobs are in correct order                                  | Pass      |
| 13  | orderingWithZeroWeight              | JobComparator unit test validating jobs will be sorted as expected with changes to Bonus settings          | run JobComparatorTest          | Jobs are in correct order                                  | Jobs are in correct order                                  | Pass      |
| 14  | lowCostOfLiving                     | JobComparator unit test validating jobs will be sorted as expected with changes to cost of living settings | run JobComparatorTest          | Jobs are in correct order                                  | Jobs are in correct order                                  | Pass      |
| 15  | TitleCannotBeBlank                  | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 16  | CompanyCannotBeBlank                | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 17  | CityCannotBeBlank                   | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 18  | StateCannotBeBlank                  | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 19  | CostOfLivingMustBePositive          | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 20  | CostOfLivingCannotBeZero            | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 21  | SalaryMustBePositive                | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 22  | BonusMustBePositive                 | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 23  | TrainingMustBePositive              | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 24  | TrianingCappedAtMaxmimum            | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 25  | LeaveMustBePositive                 | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 26  | LeaveCannotExceed100                | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 27  | TeleworkMustBePositive              | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 28  | TeleworkCannotExceed7               | Job unit test checking invalid input exceptions                                                            | run JobUnitTest                | IllegalArgumentException                                   | IllegalArgumentException                                   | Pass      |
| 29  | DatabaseRoundTrip                   | Storage unit test checking data can be saved and retrieved                                                 | run StorageTest                | Same data saved is same once retrieved                     | Same data saved is same once retrieved                     | Pass      |
| 30  | DatabaseUpdate                      | Storage unit test checking data can be updated                                                             | run StorageTest                | data is updated                                            | data is updated                                            | Pass      |
| 31  | RemoveJob                           | Storage unit test checking data can be removed                                                             | run StorageTest                | data is removed                                            | data is removed                                            | Pass      |
| 32  | RemovingCurrentJobClearsEntry       | Storage unit test checking if current job is removed,  the method retrieveCurrentJob  returns null         | run StorageTest                | null                                                       | null                                                       | Pass      |
| 33  | CurrentJob                          | Storage unit test checking currentJob can be set                                                           | run StorageTest                | current job set                                            | current job set                                            | Pass      |
| 34  | DefaultSettings                     | Storage unit test checking that default compare settings are all 1                                         | run StorageTest                | all values 1                                               | all values 1                                               | Pass      |
| 35  | UpdateSettings                      | Storage unit test checking that compare settings can be updated                                            | run StorageTest                | settings updated                                           | settings updated                                           | Pass      |
| 36  | EnsureJobUniqueness                 | Storage unit test checking that same job can't be stored twice                                             | run StorageTest                | adding second identical job does not increase storage size | adding second identical job does not increase storage size | Pass      |
| 37  | MultipleOffers                      | Storage unit test checking that multiple jobs can be stored at once                                        | run StorageTest                | storage size increases                                     | storage size increases                                     | Pass      |
| 38  | SettingCurrentJobAddsToDatabase     | Storage unit test checking that setting current job would add to database if not present already           | run StorageTest                | current job added                                          | current job added                                          | Pass      |


### 2.2 System Test Cases

| No. | Test Case                    | Test Description                                                                                                                                                                                                                                   | Steps to execute                 | Expected Result                                                                                                                        | Actual Result                                                                                                                          | Pass/Fail |
|-----|------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------|----------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------|-----------|
| 39  | saveCurrentJobTest           | Validate Requirement 1,2 by checking jobs can be enter and saved using the UI                                                                                                                                                                      | run SaveCurrentJobTest           | Job info persists  between android activities                                                                                          | Job info persists  between android activities                                                                                          | Pass      |
| 40  | saveCurrentJobTest           | Validate Requirement 1,2 by checking jobs can be enter and not saved/canceled using the UI                                                                                                                                                         | run CancelCurrentJobTest         | Job info does not persist  between android activities                                                                                  | Job info does not persist  between android activities                                                                                  | Pass      |
| 41  | comparisonSettingsSaveTest   | Validate Requirement 1,4 by checking settings can be enter and saved using the UI                                                                                                                                                                  | run ComparisonSettingsTest       | Setting info persists  between android activities                                                                                      | Setting info persists  between android activities                                                                                      | Pass      |
| 42  | comparisonSettingsCancelTest | Validate Requirement 1,4 by checking settings can be enter and not saved/canceled using the UI                                                                                                                                                     | run ComparisonSettingsTest       | Setting info does not persist  between android activities                                                                              | Setting info does not persist  between android activities                                                                              | Pass      |
| 43  | canSaveOffer                 | Validate Requirement 3 by checking the proper intents get launched in UI for saving offer                                                                                                                                                          | run enterOfferIntentTest         | Launches MainActivity                                                                                                                  | Launches MainActivity                                                                                                                  | Pass      |
| 44  | canCancelOffer               | Validate Requirement 3 by checking the proper intents get launched in UI for canceling offer                                                                                                                                                       | run enterOfferIntentTest         | Launches MainActivity                                                                                                                  | Launches MainActivity                                                                                                                  | Pass      |
| 45  | canEnterNewOffer             | Validate Requirement 3 by checking the proper intents get launched in UI entering new offer                                                                                                                                                        | run enterOfferIntentTest         | Clears fields                                                                                                                          | Clears fields                                                                                                                          | Pass      |
| 46  | canCompareOffer              | Validate Requirement 3 by checking the proper intents get launched in UI for comparing offer                                                                                                                                                       | run enterOfferIntentTest         | Launches CompareJobsActivity                                                                                                           | Launches CompareJobsActivity                                                                                                           | Pass      |
| 47  | compareJobOffersActivity     | Large espresso test validating requirements 5 and 3 by clearing all existing storage. Entering in current job along with multiple job offers. Tests that they will be sorted by rank and that they can be selected in that list to further compare | run CompareJobOffersActivityTest | CompareActivity will have sorted list of jobs, two jobs can be selected and the informatino for those jobs will be correctly displayed | CompareActivity will have sorted list of jobs, two jobs can be selected and the informatino for those jobs will be correctly displayed | Pass      |


