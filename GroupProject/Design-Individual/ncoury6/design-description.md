1. When the app is started, the user is presented with the main menu, which allows the
 user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison
 settings, or (4) compare job offers (disabled if no job offers were entered yet).
 
 The menu is the entry point for the user. By stating that upon opening the app a previous job may be viewable,
 a derived requirment is that there must be persistant storage.
 This storage will be handled by the database class. 
 The Menu of the GUI will contain four buttons, one to view/edit current job
 one for adding jobs, one for adjusting settings, one for triggering comparisons.
 
2. When choosing to enter current job details, a user will:
(a) Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consists of:
(b) Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

Clicking editCurrentJob button will bring up an editable tableview with the current jobs informatio. If it was saved
before it will have previous info, otherwise it will contain rows with desired job attributes ready to be filled in
TableView will, if editable, display buttons for saving or canceling. Saving will be done by exporting out the table and using the JobManager

3. When choosing to enter job offers, a user will:
 (a) Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.
 (b) Be able to either save the job offer details or cancel.
 (c) Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).
 
 Resuse an editable TableView but instead of using existing job, a new job will be made. Same save/cancel buttons will be available if editable
 The displayAddAdditionalJob flag will be True for this view and as a result another button will become visible which will be the exact same add job offer button for recusive use
 
4. When adjusting the comparison settings, the user can assign integer weights to: (desired job attributes)
Again reuse the editable TableView but generate weight table from attributesManager instead of standard data table


 
5. When choosing to compare job offers, a user will:
 (a)Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.
 (b)Select two jobs to compare and trigger the comparison.
 (c)Be shown a table comparing the two jobs, displaying, for each job: (desired job attributes)
 (d)Be offered to perform another comparison or go back to the main menu.
 
 TableView would be capable of displaying tables and selecting rows/jobs. Will need to request table from attribute manager to get all job data and attribute data.
 Compare button will refresh view with only selected jobs and their stats displayed
 TableView will contain the buttons needed to navigate and toggle them on an off as the buttons dictate. 
 
 6. Ranking jobs
 The unique jobRank attribute will manage a jobs score. It will have a method on it implementing the given formula.
It will look at all attributes stored in parent attribute manager and find the ones called for in the calculation. 
Then for each job stored in the attribute.data hashmap it will compute a rank/score

7.  The user interface must be intuitive and responsive.
Taken care of when designing GUI layout.

8. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).
Any storage needed will be taken care of the database class and Manager class. No networking needed. 

 
 

