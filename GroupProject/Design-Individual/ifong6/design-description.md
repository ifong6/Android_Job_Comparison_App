### Design Description - CS 6300 Summer 2024 Assignment 5 - Iat Hou Fong

## 1. When the app is started, the user is presented with the main menu, which allows the user to:

1. Enter or edit current job details

2. Enter job offers

3. Adjust the comparison settings

4. Compare job offers (disabled if no job offers were entered yet)

Explanation:

- This startup part is not shown in the UML diagram, as the main menu will be handled within the GUI implementation.

## 2. When choosing to enter current job details, a user will:

a. Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consists of:

1. Title

2. Company

3. Location (entered as city and state)

4. Cost of living in the location (expressed as an index)

5. Yearly salary

6. Yearly bonus

7. Training and Development Fund

8. Leave Time

9. Telework Days per Week

Explanation: (This part is handled by the class 'Job')

- When the user enters or edits the details of their current job, those details will get assigned to the attribute of the 'Job' object.

- The 'location' attribute will be handled by a class called 'Location' which contains the attributes 'city' and 'state'.

- Since the user will be able to choose another job as the current job and display on the UI, the class 'Job' has an attribute called 'isCurrentJob' to handle which job is displayed as the current job.

b. Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

Explanation:

- When the user saves the job details:

  - The class 'Job' will handle this by assigning the details to its attributes. Upon saving, the returning action will be handled by the UI.

- When the user cancels and exits without saving:

  - The returning action will be handled by the UI. Therefore, this is not indicated in the UML diagram.

## 3. When choosing to enter job offers, a user will:

a. Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.

b. Be able to either save the job offer details or cancel.

c. Be able to:
(1) enter another offer;
(2) return to the main menu;
(3) compare the offer (if they saved it) with the current job details (if present).

Explanation:

- Entering the details of offers and saving the job offer details or cancelling will be handled using the class 'Job' similarly to part 2.

- Entering another offer and returning to the main menu will be handled by the GUI implementation.

- Comparing the offer with the current job will be handled by the class 'JobScoreComparator' which implements the comparator interface in Java.

## 4. When adjusting the comparison settings, the user can assign integer weights to:

1. Yearly salary

2. Yearly bonus

3. Training and Development Fund

4. Leave Time

5. Telework Days per Week

Note: If no weights are assigned, all factors are considered equal.

Explanation:<br>

- Assigning integer weights to the above factors will be handled by a class called 'Weight'. The attributes in the 'Weight' object will store the values of those factors.

- All attributes are set to be 1 (all equal) as default, handling the case when no weights are assigned by the user.

## 5. When choosing to compare job offers, a user will:

a. Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.

b. Select two jobs to compare and trigger the comparison.

c. Be presented with a table comparing the two jobs, displaying the following information for each job:

1. Title

2. Company

3. Location

4. Yearly salary adjusted for cost of living

5. Yearly bonus adjusted for cost of living

6. TDF = Training and Development Fund

7. LT = Leave Time

8. RWT = Telework Days per Week

d. Be offered to perform another comparison or go back to the main menu.

Explanation:<br>

- Displaying the Title and Company will be handled by the GUI implementation.

- Ranking the jobs from best to worst including current job will be handled using the attribute 'jobScore' of the 'Job' object. 'jobScore' will be calculated by a formula provided, which uses the default weight values or the values assigned by the user to the 'Weight' object.

- The selections of two jobs to compare and the table for displaying the job information will be handled by the GUI implementation, which takes the attribute 'currentJob' and 'offers' from the class 'User'.

## 6. When ranking jobs, a job’s score is computed as the weighted average:

Explanation:<br>

- The job's score will be computed and store using the attribute 'jobScore' from the class 'Job' and the weight values from the class 'Weight'.
