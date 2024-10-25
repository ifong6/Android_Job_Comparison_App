# Assignment 5: Design Description

Erik Bakke\
ebakke3@gatech.edu

Design Document Summary
---

This document describes the UML specification, outlining how each requirement for the initial design of the job offer comparison app.

Class Overview
---

To help facilitate clear descriptions of requirement fulfillment in this document, I will first describe each class in the UML specification.

**Enumeration UserStateEnum**: This enumerated type specifies the different states of a finite state machine (FSM). This FSM defines how the user may interact with the app depending on UI interaction.

**Class Job**: This class includes member variables to specify all necessary parameters of a job offer or the user's current job.

**Class ComparisonSettings**: This class includes member variables to specify all necessary parameters of a the comparison settings for comparing between two jobs.

**Class AppManager:** This class is the entry point for the app and includes a finite state machine to manage user input from the UI. This is encoded in the enumerate type member variable **userState**. It also includes member variables:

-   **jobOffers**: a list of **Job** objects, representing all job offers entered by the user

-   **jobCurrent**: the user's current **Job**.

-   **compareSettings**: **ComparisonSettings** object storing the current comparison settings

-   **compareJobIndex1, compareJobIndex2**: Indices into the **jobOffers** list indicating which two jobs are being compared.

-   Multiple methods are included as well, which will be discussed in the requirements section.

Requirements
---

**Requirement:** 
*When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).*

The entry point and primary class in the app is **AppManager**, which manages all UI elements based on a member enum variable **userState**. The **userState** encodes the current state of the user's interaction with the app in an FSM.

Upon starting the app, an 'initialize' method is called to initialize the FSM.

**AppManager.jobCurrent** may be edited using the **AppManager.editCurrentJob(Job)** method.

**AppManager.editCurrentJob(Job)** method allows the user to enter or edit the current job details. Changes are stored in **AppManager.jobCurrent.** The **editCurrentJob(Job)** method's Job parameter will be populated from the UI edit dialog data.

**AppManager.addJobOffer(Job)** method allows the user to enter job offers

**AppManager.editCompareSettings(ComparisonSettings)** method allows the user to adjust the comparison settings.

**AppManager.selectTwoJobs(int, int)** method allows the user to select two jobs to compare by index into the **AppManager.jobOffers** list. This allows the user to compare job offers. The step to display the actual job comparison is detailed later in this document.

It should be noted that many of these functions can be seen as callback functions, which will be executed based on some data in the UI dialog and pressing a UI action button. This will be managed by the **AppManager.userState** and **AppManager.enterState(UserStateEnum)** method.

---

**Requirement:** 
*When choosing to enter current job details, a user will:*

*Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consists of:*

*Title, Company, Location (entered as city and state), Cost of living in the location (expressed as an index), Yearly salary, Yearly bonus, Training and Development Fund, Leave Time, Telework Days per Week.*

As noted earlier, **AppManager.userState** and **AppManager.enterState(UserStateEnum)** method allow the app to transition between UI states. In this case, transition to state **EditCurrentJob.**

**AppManager.jobCurrent** member variable is of type class **Job.** This class includes all the required member parameters in the requirement.

**title**, **company**, **locationCity**, **locationState**, **costOfLiving**, **yearlySalary**,\
**yearlyBonus**, **trainingDevFund**, **leaveTime**, and **teleworkDays**.

Upon transitioning to state **EditCurrentJob,** the UI dialog is populated with the **AppManager.jobCurrent**'s member variable values listed above.

---

**Requirement:** 
*Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.*

**AppManager.editCurrentJob(Job)** method is called when the user saves the job details. Cancel or exit is executed by **AppManager.enterState(MainMenu).**

---

**Requirement:** 
*When choosing to enter job offers, a user will: be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.*

Call **AppManager.enterState(EditJobOffer)** to show the user interface allowing to edit details of a new job offer.

---

**Requirement:** 
*Be able to either save the job offer details or cancel.*

**AppManager.addJobOffer(Job)** method is called when the user saves the job offer details. This stores the newly added Job's index in the\
**AppManager.jobOffers** list in the **AppManager.curJobOfferIndex** member variable.

Cancel or exit is executed by **AppManager.enterState(MainMenu).**

---

**Requirement:** 
*Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).*

Call **AppManager.enterState(EditJobOffer)** to enter another offer.

Return to main menu by calling **AppManager.enterState(MainMenu)**.

Compare the offer by calling **AppManager.selectTwoJobs(int, int). S**pecify -1 for the current job, and **AppManager.curJobOfferIndex** for the second job. Then call **AppManager.enterState(CompareTwoJobs),** transitioning the FSM to update the UI with a job comparison dialog.

---

**Requirement:** 
*When adjusting the comparison settings, the user can assign integer weights to: Yearly salary, Yearly bonus, Training and Development Fund, Leave Time, Telework Days per Week.*

*If no weights are assigned, all factors are considered equal.*

**AppManager.editCompareSettings(ComparisonSettings)** method allows the user to adjust the comparison settings. The changes are stored in the **AppManager.compareSettings** member variable.

---

**Requirement:** 
*When choosing to compare job offers, a user will:*

*Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.*

**AppManager.enterState(SelectTwoJobs),** transitions the FSM to update the UI with a job list selection dialog. This dialog will populate a UI list of all job offers from **AppManager.jobOffers** list and include **AppManager.jobCurrent**. These are sorted by rank using **AppManager.sortJobs()** method.

---

**Requirement:** 
*Select two jobs to compare and trigger the comparison.*

*Be shown a table comparing the two jobs, displaying, for each job:*

*Title, Company, Location, Yearly salary adjusted for cost of living, Yearly bonus adjusted for cost of living, TDF, LT, RWT.*

When the user selects two job offers in the UI, they are stored in\
**AppManager.compareJobIndex1** and **AppManager.compareJobIndex2** member variables.

Calling **AppManager.enterState(CompareTwoJobs)** transitions the FSM to update the UI with a job comparison dialog.

---

**Requirement:** 
*Be offered to perform another comparison or go back to the main menu.*
*When ranking jobs, a job's score is computed as the weighted average of:*
*AYS + AYB + TDF + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8))*

The **Job.getRank()** method implements the required job score calculation using all of the class **Job**'s member variables. Class **Job** includes all required member variables to calculate the job score:

**title**, **company**, **locationCity**, **locationState**, **costOfLiving**, **yearlySalary**,\
**yearlyBonus**, **trainingDevFund**, **leaveTime**, and **teleworkDays**.

---

**Requirement:** 
*The user interface must be intuitive and responsive.*

Intuitiveness and responsiveness will be implemented in the app's graphical user interface, and is not represented in the design herein.