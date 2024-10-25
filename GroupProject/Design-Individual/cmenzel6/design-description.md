# Requirements

####  1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).

The startup elements will be handled by the GUI implementation and is not presented in my diagram

 #### 2. When choosing to enter current job details, a user will:
 - Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consists of:
    - Title
    - Company
    - Location (entered as city and state)
    - Cost of living in the location (expressed as an index)
    - Yearly salary 
    - Yearly bonus 
    - Training and Development Fund
    - Leave Time
    - Telework Days per Week
- Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

To realize this requirement, I created the `User` class to store an instance of a `JobDetails` class in the `activeJob` attribute.  `JobDetails` consists of all the listed properties above, namely `title`, `company`, `city`, `state`, `costOfLiving`, `salary`, `bonus`, `trainingFund`, `leave`, `weeklyTeleworkDays`.

A `User`can add and remove job offers via the `addOffer()` and `removeOffer()` operations, respectively.  Additionally, a `User` can update an offer to be their `activeJob` via the `setActiveJob()` operation

#### 3. When choosing to enter job offers, a user will:
- Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.
- Be able to either save the job offer details or cancel.
- Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

This requirement is realized in part by the `JobDetails` class, which encapsulates the data representing a `User`'s current job and additional job offers.  The remaining components of the requirement will be handled by the GUI implementation.

#### 4. When adjusting the comparison settings, the user can assign integer weights to:
- Yearly salary
- Yearly bonus
- Training and Development Fund
- Leave Time
- Telework Days per Week
If no weights are assigned, all factors are considered equal.

To realize this requirement, I created the `WeightedPreferences` class to store integer weights for the 5 categories, along with an `update()` operation that takes all 5 categories as input to change the user's weights.  To address the default that all weights are equal, the initial values of the preferences will default to 1 if not set otherwise.

#### 5. When choosing to compare job offers, a user will:
- Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.
- Select two jobs to compare and trigger the comparison
- Be shown a table comparing the two jobs, displaying, for each job:
   - Title
   - Company
   - Location 
   - Yearly salary adjusted for cost of living
   - Yearly bonus adjusted for cost of living
   - TDF = Training and Development Fund
   - LT = Leave Time 
  - RWT = Telework Days per Week 
- Be offered to perform another comparison or go back to the main menu.

This requirement is realized by the `User` class' tracking of the `activeJob` as an attribute, along with the `computeRank()` operation of the `User`'s `WeightedPreferences` instance.  The remaining elements of the requirement will be handled by the GUI implementation.

#### 6. When ranking jobs, a job’s score is computed as the weighted average of:
`AYS + AYB + TDF + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8))`

To realize this requirement, I added the computed attributes `adjustedSalary` and `adjustedBonus` that will be calculated and scaled based on the `costOfLiving` index attribute stored in `JobDetails`.  Additionally, I added the `calculateRank()`  operation to the `WeightedPreferences` class that takes a `JobDetails` object as an input and computes the ranking according to the user's assigned preferences.

#### 7. The user interface must be intuitive and responsive.
This requirement is not currently handled in my design diagram, as it will be completely handled by the GUI implementation.

#### 8. For simplicity, you may assume there is a single system running the app
Although not explicitly shown in my diagram, this requirement is inherently met by not concerning the design with serialization and/or communication of job offers and preferences.