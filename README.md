![mymusic](https://github.com/ianbl8/mymusic/assets/48052595/3bd81cca-d598-49c4-9197-3cd11af24d18)
# mymusic

## About this app
**mymusic** is a personal music collection app, where users can store details of any releases (e.g. albums or singles) that they own, whether physical or digital. Data is stored in a Firebase Realtime Database, and the app supports mutliple users.

## Technical Report

### Functionality
This is an Android app written in Kotlin with Views. The original app used Activities to display data, but the current version uses Fragments and ViewModels instead.

Users can create an account and log in with email and password, or log in with their Google account. Users can add details of the releases they own, add cover images they have on their devices, and can add track details for the releases. Users can swipe to edit and delete releases or tracks. Users can also see all users' releases and tracks, but cannot edit them.

All releases are stored in the Firebase Realtime Database, along with tracks when they are input.

### Intended functionality
I was unable to extend the functionality much beyond that of the original app due to issues implementing the current functionality when moving from Activities to Fragments and ViewModels, and from local storage to an online database.

I had originally hoped to:
- allow users to save cover images to Cloud Storage, not just the URIs for the files
- allow users to copy releases from other users into their own collection
- allow users to store details of record stores with locations

These functions could be added to extend the app further at a later date.

### Diagram
This is a simple diagram showing the main Models, Activities, Fragments and ViewModels used in this app.

![diagram](https://github.com/ianbl8/mymusic/assets/48052595/83100dc4-1cf3-4cf7-b840-43269ba31090)

### UX/DX approach
The app implements Navigation through menus and the nav drawer, as well as allowing swipe to edit and delete in the List fragments. The UI is clear and follows Material Design guidelines. The Model View Viewmodel design pattern is implemented through the use of Fragments and ViewModels.

### Git approach adopted
I used **branches** for the updated app for the different development features implemented as follows:

- **dev-fragments** for transferring the original Activities to Fragments. I thought my original attempt did not work and rolled it back; I later reimplemented them.
- **dev-viewmodel** for implementing ViewModels to accompany the Fragments.
- **dev-sampledata** for reimplementing the sample data function.
- **dev-firebase** for implementing Firebase Authentication (email/password and Google), Realtime Database, and multiple users.

There is also one abandoned branch:
- **dev-users-old** was my original attempt to introduce authentication and users to the app.

I rolled back twice due to problems with features being developed, once with fragments and once with users. In both cases I tried to maintain a commit history rather than deleting commits.

I used **pull requests** to merge in to main from each branch when each feature was ready for implementation. 

The versions of the app are tagged; the version for Assignment 1 was v0.3.1 and **the current version for Assignment 2 is v0.7.0**.

I did not set up a release for this app, as there are still some issues that would need to be ironed out before this could be released.

### Personal statement
To be honest, I did find this assignment particularly challenging. As I was catching up with the course content, I thought that I understood Fragments and ViewModels and how to use them to refactor my original Activities-based app. However I found it very difficult to make this work at first, and to carry data forward from one fragment to the other.

I had a number of bugs that arose during development; almost every time I tried to implement a new feature, a new bug appeared. I also have one ViewModel for each Fragment but realised towards the end of development that the TrackListViewModel and TrackViewModel are largely redundant and could be incorporated into the ReleaseViewModel.

In fact, it's only now that I'm writing this up that I've realised that **Login**  is an *Activity* with two *ViewModels*, and not a *Fragment* - so I may have been able to update the original app without implementing Fragments?

I probably would have found it a lot easier to rewrite the app from scratch rather than to trying to update the original app.

### References
In addition to the course materials I used information from the following resources:

Android Developers
- ViewModel overview: https://developer.android.com/topic/libraries/architecture/viewmodel
- Navigation: https://developer.android.com/jetpack/androidx/releases/navigation
- Navigate to a destination: https://developer.android.com/guide/navigation/use-graph/navigate
- Fragment manager: https://developer.android.com/guide/fragments/fragmentmanager
- Communicate with fragments: https://developer.android.com/guide/fragments/communicate
- Pass data between destinations: https://developer.android.com/guide/navigation/use-graph/pass-data
- Fragment transactions: https://developer.android.com/guide/fragments/transactions

Firebase:
- Authenticate with Google on Android: https://firebase.google.com/docs/auth/android/google-signin?hl=en&authuser=0
- Read and Write Data on Android: https://firebase.google.com/docs/database/android/read-and-write
- Saving Data: https://firebase.google.com/docs/database/admin/save-data
- DatabaseException: https://firebase.google.com/docs/reference/kotlin/com/google/firebase/database/DatabaseException
- Exclude: https://firebase.google.com/docs/reference/kotlin/com/google/firebase/database/Exclude
- IgnoreExtraProperties: https://firebase.google.com/docs/reference/kotlin/com/google/firebase/database/IgnoreExtraProperties

Google for Developers:
- Start Integrating Google Sign-In into Your Android App: https://developers.google.com/identity/sign-in/android/start-integrating
- Authenticating Your Client: https://developers.google.com/android/guides/client-auth
- Integrating Google Sign-In into Your Android App: https://developers.google.com/identity/sign-in/android/sign-in

StackOverflow
- android - recyclerview No adapter attached - skipping layout: https://stackoverflow.com/questions/29141729/recyclerview-no-adapter-attached-skipping-layout
- Cannot resolve symbol default_web_client_id in Firebase's Android Codelab: https://stackoverflow.com/questions/37810552/cannot-resolve-symbol-default-web-client-id-in-firebases-android-codelab
- android - Expected a List while deserializing, but got a class java.util.HashMap: https://stackoverflow.com/questions/55694354/expected-a-list-while-deserializing-but-got-a-class-java-util-hashmap
- android - Firebase Error: Expected a List, but got a class java.util.HashMap: https://stackoverflow.com/questions/65775069/firebase-error-expected-a-list-but-got-a-class-java-util-hashmap
